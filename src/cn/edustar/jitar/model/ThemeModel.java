package cn.edustar.jitar.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 表示 portal 类型页面使用的模板方案.
 * 
 * 每个模板方案中所有模板都放在一个目录下，在加载的时候遍历该目录建立所有模板的映射 map, 一旦建立之后，该 map 将只读不能再写入.
 * 如果需要重新加载，则建立一个新的 map, 然后替换掉原来的。不要直接向原来的 map 中 put 内容.
 * 
 *
 */
@SuppressWarnings("unchecked")
public class ThemeModel {
	
	/** 父模板模型对象。 */
	private ThemeModel parent_theme;

	/** 模板方案中所有模板集合。 */
	private Map<String, String> template_map = java.util.Collections.EMPTY_MAP;

	/**
	 * 缺省构造函数.
	 */
	public ThemeModel() {

	}

	/**
	 * 使用指定的父模板模型对象构造一个 ThemeModel 的新实例。
	 * 
	 * @param parent_theme
	 */
	public ThemeModel(ThemeModel parent_theme) {
		this.parent_theme = parent_theme;
	}

	/**
	 * 使用指定的模板路径、URL 初始加载模板方案。
	 * 
	 * @param theme_path -
	 *           模板物理存放目录，如 'C:/Tomcat/WebApps/Groups/WEB-INF/syst/default' 一般使用
	 *           ServletContext 转换 theme_base_url 为物理路径即可。
	 * 
	 * @param theme_base_url -
	 *           模板方案在 Web 中的 url, 如 '/WEB-INF/syst/default/' 返回的模板方案名都在前面添加上
	 *           theme_base_url.
	 * @throws IOException -
	 *            所给 theme_path 不存在或非法。
	 */
	public void init(String theme_path, String theme_base_url)
			throws IOException {
		if (theme_path == null)
			throw new IllegalArgumentException("theme_path == null");
		if (theme_base_url == null)
			throw new IllegalArgumentException("theme_base_url == null");

		// 判定文件夹参数是否合法。

		File file = new File(theme_path);
		if (!file.exists())
			throw new IOException(theme_path + " 不存在.");
		if (!file.isDirectory())
			throw new IOException(theme_path + " 不是一个文件夹.");

		// 列出其所有子文件或子文件夹。

		if (!theme_base_url.endsWith("/"))
			theme_base_url += "/";
		File[] childs = file.listFiles();
		Map<String, String> new_map = new HashMap<String, String>();
		if (childs != null && childs.length > 0) {
			for (int i = 0; i < childs.length; ++i) {
				File child = childs[i];
				if (!child.isFile())
					continue; // ignore dir
				String key = getTemplateKey(child.getName());
				if (key.length() > 0)
					new_map.put(key, theme_base_url + child.getName());
			}
		}

		this.template_map = new_map;
	}

	// 得到一个文件名的 key 名字，方法是去掉其后缀 (.html)，如果不幸目录下有两个同名

	// 不同后缀的文件，则将发生覆盖问题。

	private static String getTemplateKey(String file_name) {
		if (file_name == null)
			return "";
		if (file_name.length() == 0)
			return "";
		int dot = file_name.lastIndexOf('.');
		if (dot < 0)
			return file_name;
		return file_name.substring(0, dot);
	}

	/**
	 * 得到此模板方案中指定键的模板。
	 * 
	 * @param name -
	 *           模板名字，如 'index', 'article_list', 'win_wrap' 等。
	 * 
	 * @return 返回该模板方案中模板路径，如 '/WEB-INF/syst/default/index.html'
	 *         'schema:/WEB-INF/other'. 如果模板方案中没有指定名字的模板，则向父模板方案进行请求。
	 * 
	 */
	public String get(String name) {
		String template = template_map.get(name);
		if (template == null && this.parent_theme != null)
			return parent_theme.get(name);

		return template;
	}

	/** 模板方案的名字，如果未给出，则使用其所在目录做为名字。 */
	private String theme_name = "";

	/**
	 * 得到此模板方案的名字。
	 * 
	 * @return
	 */
	public String getThemeName() {
		return theme_name;
	}
	
}
