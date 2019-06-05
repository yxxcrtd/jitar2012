package cn.edustar.jitar.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.edustar.jitar.JitarRequestContext;
import cn.edustar.jitar.dao.hibernate.BaseDaoHibernate;
import cn.edustar.jitar.data.Command;
import cn.edustar.jitar.pojos.ProductConfig;
import cn.edustar.jitar.pojos.SiteStat;
import cn.edustar.jitar.service.ProductConfigService;
import cn.edustar.jitar.util.DateUtil;
import cn.edustar.jitar.util.FileLicense;

/**
 * 实现.
 * 
 * @author bai
 * @version 1.0.0 Apr 30, 2008 3:13:43 PM
 */
public class ProductConfigImpl extends BaseDaoHibernate implements
		ProductConfigService {

	/**
	 * 初始化方法,从'Spring'调用
	 */
	public void init() {
	}

	private String errMessage = "";
	private String productName = "";
	private String productID = "";
	private String days = "";
	private String remainDays = "";
	private String productGuid = "";
	private String unitLevel = "";
	private String usercount = "0";

	public String getRemainDays() {
		return remainDays;
	}

	public String getDays() {
		return days;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductGuid() {
		return productGuid;
	}

	public String getProductId() {
		return productID;
	}

	public String getUnitLevel() {
		JitarRequestContext jrc = JitarRequestContext.getRequestContext();
		ServletContext sr = jrc.getServletContext();
		String path = sr.getRealPath("/license");
		//System.out.println("path = " + path);
		File f = new File(path);
		if (!f.exists()) {
			unitLevel = "1";
		}
		try {
			FileLicense filelic = new FileLicense();
			boolean bLoadOk = filelic.Load(path);
			if (!bLoadOk) {
				unitLevel = "1";
			}
			unitLevel = (String) filelic.GetValue("unitlevel");
		}
		catch(Exception e)
		{
			unitLevel = "1";
		}
		
		return unitLevel;
	}
	
	/**
	 * 系统用户数量限制
	 * 
	 * @return
	 */
	public String getUsercount() {
		JitarRequestContext jrc = JitarRequestContext.getRequestContext();
		ServletContext sr = jrc.getServletContext();
		String path = sr.getRealPath("/license");
		//System.out.println("path = " + path);
		File f = new File(path);
		
		// 如果许可证不存在的话，不允许用户注册
		if (!f.exists()) {
			usercount = "1";
		}
		
		try {
			FileLicense filelic = new FileLicense();
			boolean bLoadOk = filelic.Load(path);
			// 许可证无效，也不允许用户注册
			if (!bLoadOk) {
				usercount = "1";
			}
			usercount = (String) filelic.GetValue("usercount");
		}
		catch(Exception e) {
			usercount = "1";
		}
		
		return usercount;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public boolean isValid() {
		//JitarRequestContext jrc = JitarRequestContext.getRequestContext();
		//ServletRequest sr = jrc.getRequest();
		
		//为了适应非Request请求能够正确运行，采用了下面的方法。
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		ServletContext sc = wac.getServletContext();
		
		String path = sc.getRealPath("/license");
		File f = new File(path);
		if (!f.exists()) {
			errMessage = "许可证文件不存在";
			// log.info(path + "文件不存在");
			return false;
		}
		// log.info(path);
		try {
			FileLicense filelic = new FileLicense();
			boolean bLoadOk = filelic.Load(path);
			if (!bLoadOk) {
				errMessage = "不能加载许可证文件，可能该许可证非法";
				// log.info("不能加载许可证文件，可能该许可证非法");
				return false;
			}

			//log.info("得到注册文件信息");

			String ItemValue = (String) filelic.GetValue("id");

			//System.out.println("id=" + ItemValue);

			productID = ItemValue;

			ItemValue = (String) filelic.GetValue("name");

			//System.out.println("name=" + ItemValue);

			productName = ItemValue;

			ItemValue = (String) filelic.GetValue("days");
			//System.out.println("days="+ItemValue);

			days = ItemValue;

			ItemValue = (String) filelic.GetValue("guid");
			productGuid = ItemValue;

			ItemValue = (String) filelic.GetValue("unitlevel");
			this.unitLevel = ItemValue;
			//System.out.println("unitlevel="+ItemValue);

			if (days.equals("0") || days.equals("-1") || days == "0"
					|| days == "-1") {
				// 是正式版本,正式版本需要验证一下下????
				return true;
			} else {
				// 是试用版本
				// 判断数据库里是否存放了信息,以及信息是否一致
				String hql = " FROM ProductConfig";
				List list = this.getSession().createQuery(hql).list();
				boolean Add = false; 
				if ((list == null) || (list.size() == 0))
					Add = true;
				else
					Add = false;

				// System.out.println("ProductConfig没有记录吗:" + Add);

				Date dbNow = null;
				Date dNow = DateUtil.getNow();

				String sTemp = String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", new Date());
				// String sTemp = DateUtil.toStandardString(dNow);

				// System.out.println("2当前日期:" + sTemp);

				dNow = new SimpleDateFormat("yyyy-MM-dd").parse(sTemp);

				String s = encodeDate(sTemp);
				// System.out.println("当前日期加密结果:" + s);

				hql = " FROM SiteStat";
				//cmd = new Command(hql);
				list = this.getSession().createQuery(hql).list();
				String DateDbNow = "";
				if ((list == null) || (list.size() == 0)) {
					DateDbNow = dNow.toString();
					return true;
				} else {
					SiteStat ss = (SiteStat) list.get(0);
					if ((ss.getDateCount() == null)
							|| (ss.getDateCount() == "")) {
						// System.out.println("统计表中的日期还没设置");
						DateDbNow = dNow.toString();
						// System.out.println("将当前日期设置到统计表中" + s);
						hql = "Update SiteStat set dateCount='" + s + "'";
						this.getSession().createQuery(hql).executeUpdate();
						
						//cmd = new Command(hql);
						//cmd.update();
						// System.out.println("---------------------");
					} else {

						DateDbNow = ss.getDateCount();
						// System.out.println("统计表中的日期是"+DateDbNow);
						dbNow = decodeDate(DateDbNow);
						// System.out.println("统计表中的日期解密是"+dbNow);
						// System.out.println("当前日期是="+dNow);

						if (DateUtil.compareDateTimeEx(dbNow, dNow) == 1) {
							errMessage = "服务器的系统日期被修改,本系统无法启动";
							// System.out.println("服务器的系统日期被修改,本系统无法启动");
							return false;
						}
						hql = "Update SiteStat set DateCount='" + s + "'";
						this.getSession().createQuery(hql).executeUpdate();
						
						//cmd = new Command(hql);
						//cmd.update();
					}
				}

				// System.out.println("Add="+Add);

				if (Add) {
					if (SaveLicGif(s)) {
						ProductConfig cfg = new ProductConfig();
						cfg.setProductID(productID);
						cfg.setProductName(productName);
						cfg.setInstallDate(s);
						// System.out.println("getHibernateTemplate=" +
						// (getSession() == null));
						this.getSession().saveOrUpdate(cfg);
						return true;
					} else {
						if (errMessage == "") {
							errMessage = "数据库产品系统被修改,本系统无法启动";
						}
						return false;
					}
				}

				if (dbNow == null)
					dbNow = dNow;

				hql = " FROM ProductConfig ";
				//cmd = new Command(hql);
				list = this.getSession().createQuery(hql).list();
				ProductConfig pcf = (ProductConfig) list.get(0);
				String SdateInstall = pcf.getInstallDate();
				if (!Add) {
					if (checkLicGif(SdateInstall) == false) {
						// System.out.println(errMessage);
						return false;
					}
				}
				Date dateInstall = decodeDate(SdateInstall);
				Date dateFinished = DateUtil.addDays(dateInstall,Integer.parseInt(days));

				long remaindays = DateUtil.daysNum(dateFinished);

				// System.out.println("remaindays="+remaindays);

				remainDays = "" + remaindays;

				if (DateUtil.compareDateTimeEx(dNow, dateFinished) == 1) {
					errMessage = "试用期限已过,本系统无法启动";
					// System.out.println("试用期限已过,本系统无法启动");
					return false;
				} else if (DateUtil.compareDateTimeEx(dbNow, dateFinished) == 1) {
					errMessage = "试用期限已过,本系统无法启动";
					// System.out.println("试用期限已过,本系统无法启动");
					return false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			errMessage = e.getMessage();
			log.info(e.toString());
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	private boolean checkLicGif(String s) {
		//JitarRequestContext jrc = JitarRequestContext.getRequestContext();
		//ServletRequest sr = jrc.getRequest();
		//@SuppressWarnings("unused")
		//String Webpath = jrc.getSiteUrl();
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		ServletContext sc = wac.getServletContext();
		String path = sc.getRealPath("/manage/images/lic.gif");
		File f = new File(path);
		if (!f.exists()) {
			errMessage = "站点文件不全,无法继续运行!";
			return false;
		}
		if (f.length() < 5) {
			// errMessage = "站点文件不全,无法继续运行!!";
			SaveLicGif(s);
			return true;
		} else {
			// 读文件
			FileInputStream inFile = null;
			try {
				inFile = new FileInputStream(f);
			} catch (FileNotFoundException ex) {
				errMessage = ex.getMessage();
				return false;
			}
			BufferedInputStream bin = new BufferedInputStream(inFile);
			byte[] b = new byte[(int) f.length()];
			try {
				bin.read(b);
			} catch (java.io.IOException ex) {
				errMessage = ex.getMessage();
				return false;
			} finally {
				if (bin != null)
					try {
						bin.close();
					} catch (java.io.IOException ex) {
					}
				if (inFile != null)
					try {
						inFile.close();
					} catch (java.io.IOException ex) {
					}
			}

			// 比较文件
			FileLicense ff = new FileLicense();
			char[] cc = ff.getChars(b);
			String c = String.copyValueOf(cc);

			// System.out.println("GIF 内容="+c);
			// System.out.println("比较内容s="+s);

			if (s.equals(c)) {
				return true;
			} else {
				if (c.substring(0, 10).equals(s.substring(0, 10))) {
					return true;
				} else {
					// System.out.println("GIF 内容="+c);
					// System.out.println("比较内容s="+s);

					errMessage = "数据库系统被非法改动,无法继续运行";
					return false;
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private boolean SaveLicGif(String s) {
		//JitarRequestContext jrc = JitarRequestContext.getRequestContext();
		//ServletRequest sr = jrc.getRequest();
		//@SuppressWarnings("unused")
		//String Webpath = jrc.getSiteUrl();
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		ServletContext sc = wac.getServletContext();
		String path = sc.getRealPath("/manage/images/lic.gif");
		File f = new File(path);
		if (!f.exists()) {
			errMessage = "站点文件不全,无法继续运行";
			// System.out.println(path + "站点文件不全,无法继续运行");
			return false;
		}
		if (f.length() > 5) {
			errMessage = "";
			return false;
		} else {
			// 写文件
			FileOutputStream outFile = null;
			try {
				outFile = new FileOutputStream(f);
			} catch (FileNotFoundException ex) {
				errMessage = ex.getMessage();
				return false;
			}
			BufferedOutputStream bin = new BufferedOutputStream(outFile);
			byte[] b = s.getBytes();
			try {
				bin.write(b);
			} catch (java.io.IOException ex) {
				errMessage = ex.getMessage();
				return false;
			} finally {
				if (bin != null)
					try {
						bin.close();
					} catch (java.io.IOException ex) {
					}
				if (outFile != null)
					try {
						outFile.close();
					} catch (java.io.IOException ex) {
					}
			}
		}
		return true;
	}

	// 对日期进行解密
	@SuppressWarnings("deprecation")
	private Date decodeDate(String s) {

		FileLicense filelic = new FileLicense();
		byte[] b = s.getBytes();
		byte[] bb;
		int iLength;
		if (b[b.length - 1] == 0) {
			bb = new byte[b.length - 1];
			iLength = b.length - 1;
		} else {
			bb = new byte[b.length];
			iLength = b.length;
		}
		byte c = 122;

		// System.out.println("------------------------开始解密----------------------------");

		for (int i = 0; i < iLength; i++) {
			bb[i] = (byte) (b[i] ^ c);
			// System.out.println(b[i]+"="+bb[i]);
		}
		char[] cc = filelic.getChars(bb);
		String sw = String.copyValueOf(cc);

		// System.out.println("sw="+sw);
		// System.out.println("sw.length="+sw.length());

		if (sw.length() > 0) {
			// log.info("(byte)sw.charAt(sw.length()-1)="+((byte)sw.charAt(sw.length()-1)));
			if (((byte) sw.charAt(sw.length() - 1)) == 0) {
				sw = sw.substring(0, sw.length() - 1);
			}
		}

		// System.out.println("decodeDate sw=" + sw);
		// System.out.println("decodeDate sw====" +
		// String.format("%1$tY-%1$tm-%1$td", sw));

		Date d;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd").parse(sw);
		} catch (ParseException e) {
			d = new Date(sw);
		}
		return d;
	}

	// 对日期进行加密
	private String encodeDate(String dNow) {
		FileLicense filelic = new FileLicense();
		// String dNow = d.toString();
		// String dNow = String.format("%1$tY-%1$tm-%1$td", d);
		byte[] b = dNow.getBytes();
		byte[] bb = new byte[b.length];
		byte c = 122;
		// System.out.println("------------------------开始加密----------------------------");
		for (int i = 0; i < b.length; i++) {
			bb[i] = (byte) (b[i] ^ c);
			// System.out.println(b[i]+"="+bb[i]);
		}
		char[] cc = filelic.getChars(bb);
		String s = String.copyValueOf(cc);
		return s;
	}

	public void createProductConfig(ProductConfig cfg) {
		// TODO Auto-generated method stub

	}

	public ProductConfig findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateProdcutConfig(ProductConfig cfg) {
		// TODO Auto-generated method stub

	}

}
