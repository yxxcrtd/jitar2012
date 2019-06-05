package cn.edustar.jitar.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.ServletContextAware;

import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.FileStorage;
import cn.edustar.jitar.service.StoreManager;

/**
 * 用户存储服务的一个简单实现. * 
 *
 *
 * 这个实现直接从数据库中加载 S_VdirMap{id, vdir, path} 信息, 不需要 Hibernate 映射 ,
 *    这样可以节省一些加载时间吧.
 * 
 */
public class StoreManagerImpl implements StoreManager, ServletContextAware {
	
	/** 日志 */
	private static final Log log = LogFactory.getLog(StoreManagerImpl.class);
	
	/** 加载 VdirMap 的 SQL 语句, 必须至少含有 Vdir, Path 两个列，且大小写不能错. */
	private String load_sql = "SELECT Vdir, Path FROM S_VdirMap WHERE Enabled = 1";
	
	/** 从用户所在 folder */
	private Map<String, File> user_root_maps = new HashMap<String, File>();

	/** 简化数据库访问的 spring jdbc template 对象 */
	private JdbcTemplate jdbc_t;
	
	/** Web 运行环境对象 */
	private ServletContext servlet_ctxt;
	
	/**
	 * 设置数据库连接对象, 内部使用 Spring JdbcTemplate 进行包装以简化数据库访问.
	 * @param ds
	 */
	public void setDataSource(DataSource ds) {
		this.jdbc_t = new JdbcTemplate(ds);
	}

	/**
	 * 由 Spring 负责注入 Web 运行环境对象, 通过该对象我们要计算出当前 Web App 的物理路径.	 * @param servletContext
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servlet_ctxt = servletContext;
	}
	
	/**
	 * 高级配置: 设置加载 VdirMap 的 SQL 语句, 必须至少含有 Vdir, Path 两个列，且大小写不能错.
	 * @param load_sql
	 */
	public void setLoadSql(String load_sql) {
		this.load_sql = load_sql;
	}
	
	/**
	 * 初始化, 由 Spring 负责调用.
	 */
	public void init() {
		log.info("StoreManagerImpl initializing...");		
		internalInit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.StoreManager#getUserFileStorage(cn.edustar.jitar.pojos.User)
	 */
	public FileStorage getUserFileStorage(User user) throws IOException {
		if (user == null) {
			throw new IllegalArgumentException("user == null");
		}
		
		// 根据 user.virtualDirectory 得到该虚拟的映射
		String vdir = user.getVirtualDirectory();
		log.info("vdir is: " + vdir);
		
		if (vdir == null || vdir.length() == 0) {
			vdir = "u";
		}
		File root = this.user_root_maps.get(vdir);
		
		log.info("root.getPath is: " + root.getPath());
		log.info("root.getAbsolutePath is: " + root.getAbsolutePath());
		log.info("root.getCanonicalPath is: " + root.getCanonicalPath());
		if (root == null)
			throw new IOException("未能找到用户 '" + user.getLoginName() + "' 的虚拟目录 '" + vdir + "' 映射的物理路径, 请确定您的配置正确");

		// 根据 loginName 计算该用户的存储根, 一定要求是一个文件夹.
		File user_root = new File(root.getCanonicalPath() + File.separator + user.getLoginName() + File.separator);
		log.info("user_root.getPath is: " + user_root.getPath());
		log.info("user_root.getCanonicalFile is: " + user_root.getCanonicalFile());
		user_root = user_root.getCanonicalFile();
		if (user_root.exists() == false) {
			user_root.mkdirs();
		}
		return new FileStorageImpl(user, user_root);
	}
	
	public Map<String, File> getUser_root_maps() {
		return user_root_maps;
	}

	@SuppressWarnings("unchecked")
	private void internalInit() {
		boolean bExist_u=false;
		try {
			// 从数据库中加载所有映射, "SELECT Vdir, Path FROM S_VdirMap"
			List<Map<String, Object>> list = jdbc_t.queryForList(load_sql);
			//if (log.isInfoEnabled())
			//{
				//log.info("VdirMap config is: " + list);
			//}	
			for (Map m : list) {
				String vdir = safeGetMap(m, "Vdir");
				String path = safeGetMap(m, "Path");
				if (vdir == null || path == null || vdir.length() == 0 || path.length() == 0) {
					log.error("从 VdirMap 中得到的配置不正确 vdir = " + vdir + ", path = " + path);
					continue;
				}
				if(vdir.equals("u"))
					bExist_u=true;
				
				// 根据 vdir, path 计算正确的地址, 放置到 user_root_maps 中.
				addVdirPathMap(vdir, path);
			}
		} catch (Exception ex) {
			log.error("在从数据库中加载目录映射的时候发生异常", ex);
		}
		if(bExist_u==false){
			try {			
				addVdirPathMap("u", "/user"); // 映射系统缺省的 'u' '/user/'
			} catch (IOException ex) {
				log.error("无法映射缺省用户存储路径 'u' 到 '/user/'", ex);
			}
		}
	}
	
	// 根据 vdir, path 计算正确的地址, 放置到 user_root_maps 中.
	private void addVdirPathMap(String vdir, String path) throws IOException {
		// 计算实际物理路径
		String phy_path="";
		//log.info("path="+ path);
		if (path.indexOf(":")>0)
			phy_path = canonicalPath(path);
		else
			phy_path = canonicalPath(calcPhysicalPath(path));
		
		//log.info("vdir = " +vdir +" phy_path="+ phy_path);
		// 如果不存在则创建
		File folder = new File(phy_path);
		if (folder.exists() == false) {
			this.mkdirs(folder);
		}
		
		// 放到 map 中.
		this.user_root_maps.put(vdir, folder);
	}
	
	private String calcPhysicalPath(String path) {
		if (path.startsWith("/"))
			return servlet_ctxt.getRealPath(path);
		return path;
	}
	
	private String canonicalPath(String path) throws IOException {
		File f = new File(path);
		return f.getCanonicalPath();
	}
	
	@SuppressWarnings("unchecked")
	private static final String safeGetMap(Map m, Object key) {
		Object v = m.get(key);
		return v == null ? null : v.toString();
	}

	/**
	 * 关闭, 由 Spring 负责调用.
	 */
	public void destroy() {
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.StoreManager#getUserStoreRoot(cn.edustar.jitar.pojos.User)
	 */
	public File getUserStoreRoot(User user) {
		// 我们暂时使用一个简单的规则，用户 user 的路径为
		//   store_root + user.virtualFolder + user.loginName
		
		File user_root = internalGetUserRoot(user);
		
		if (user_root.exists() == false)
			user_root.mkdirs();
		
		return user_root;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.iface.StoreManager#createUserFile(cn.edustar.jitar.pojos.User, java.lang.String)
	 */
	public File createUserFile(User user, String path) throws IOException {
		File user_root = this.internalGetUserRoot(user);
		
		File user_file = new File(user_root.getAbsolutePath() + "/" + path);
		user_file = user_file.getCanonicalFile();
		
		mkdirs(user_file.getParentFile());
		
		return user_file;
	}

	//删除用户的目录
	public void DeleteUserDir(User user) throws IOException{
		FileStorage fs= getUserFileStorage(user);
		File fo=fs.getRootFolder();
		if(fo.exists()){
			fo.delete(); 
		}
	}
	
	private void mkdirs(File file) throws IOException {
		if (file.exists()) return;
		if (!file.mkdirs())
			throw new IOException("无法创建目录: " + file);
	}
	
	private File internalGetUserRoot(User user) {
		throw new java.lang.UnsupportedOperationException();
		// File user_root = new File(f_store_root.getAbsolutePath() + "/u/" + user.getLoginName() + "/");
		// return user_root;
	}
}
