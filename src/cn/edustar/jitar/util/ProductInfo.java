package cn.edustar.jitar.util;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;

import cn.edustar.jitar.JitarRequestContext;
import cn.edustar.jitar.data.Command;
import cn.edustar.jitar.pojos.ProductConfig;
import cn.edustar.jitar.pojos.SiteStat;
import cn.edustar.jitar.service.ProductConfigService;

@SuppressWarnings({ "deprecation", "rawtypes" })
public class ProductInfo {
	private String errMessage="";
	private String productName="";
	private String productID="";
	private String days="";
	
	private ProductConfigService productConfigDao;
	
	public void setProductConfigDao(ProductConfigService productConfigDao) {
		this.productConfigDao = productConfigDao;
	}	
	
	public String getErrMessage() {
		return errMessage;
	}
	public String getProductName() {
		return productName;
	}
	public String getProductID() {
		return productID;
	}
	public String getDays() {
		return days;
	}

	public boolean isValid()
	{
		JitarRequestContext jrc=JitarRequestContext.getRequestContext();
		ServletRequest sr= jrc.getRequest();
		@SuppressWarnings("unused")
		String Webpath=jrc.getSiteUrl();
		String path=sr.getRealPath("/license");
		File f=new File(path);
		if(!f.exists())
		{
			errMessage="许可证文件不存在";
			System.out.println(path+"文件不存在");
			return false;
		}
		System.out.println(path);
		try
		{
			FileLicense filelic = new FileLicense();
			boolean bLoadOk=filelic.Load(path);
			if(!bLoadOk)
			{
				errMessage="不能加载许可证文件，可能该许可证非法";
				System.out.println("不能加载许可证文件，可能该许可证非法");
				return false;
			}
			System.out.println("-------------------------得到注册文件信息----------------");
			String ItemValue= (String) filelic.GetValue("id");
			System.out.println("id="+ItemValue);
			productID=ItemValue;
			
			ItemValue= (String) filelic.GetValue("name");
			System.out.println("name="+ItemValue);
			productName=ItemValue;
			
			ItemValue= (String) filelic.GetValue("days");
			System.out.println("days="+ItemValue);
			days=ItemValue;
			
			if(days=="0" || days=="-1")
			{
				//是正式版本,正式版本需要验证一下下????
				return true;
			}
			else
			{
				//是试用版本
				//判断数据库里是否存放了信息,以及信息是否一致
		      String hql = " FROM ProductConfig";
		      Command cmd=new Command(hql);
		      List list = cmd.open();
		      boolean Add=false;
				if((list==null) || (list.size()==0))
					Add=true;
				else
					Add=false;

				Date dbNow=null;
				Date dNow=DateUtil.getNow();
				String sTemp=DateUtil.toStandardString(dNow);
				dNow=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sTemp);
				String s=encodeDate(dNow);	
				System.out.println("s:" + s);
		      hql = " FROM SiteStat";
		      cmd=new Command(hql);
		      list = cmd.open();
				String DateDbNow="";
		      if((list==null) || (list.size()==0))
		      {
					DateDbNow=dNow.toString();
					return true;
		      }
		      else
		      {
		      	System.out.println("s1:" + s);
		      	SiteStat ss=(SiteStat)list.get(0);
		      	System.out.println("s2:id=" + ss.getId());
		      	if((ss.getDateCount()==null) || (ss.getDateCount()==""))
		      	{
		      		System.out.println("s3");
		      		DateDbNow=dNow.toString();
		      		System.out.println("DateDbNow="+DateDbNow);
			      hql = "Update SiteStat set dateCount='"+s+"'";
				  cmd=new Command(hql);
			      cmd.update();

					System.out.println("---------------------");
		      	}
		      	else
		      	{
		      		DateDbNow=ss.getDateCount();
						dbNow=decodeDate(DateDbNow);
						if(DateUtil.compareDateTime(dbNow, dNow)==1)
						{
							errMessage="服务器的系统日期被修改,本系统无法启动";
							System.out.println("服务器的系统日期被修改,本系统无法启动");
							return false;
						}
				      hql = "Update SiteStat set DateCount='"+s+"'";
				      cmd=new Command(hql);
				      cmd.update();
		      	}
		      }
		      
				if(Add)
				{
					
					ProductConfig cfg=new ProductConfig();
					cfg.setProductID(productID);
					cfg.setProductName(productName);
					cfg.setInstallDate(s);
					System.out.println("productConfigDao="+(productConfigDao==null));
					productConfigDao.createProductConfig(cfg);
					return true;
				}
				
				//
				if(dbNow==null) dbNow=dNow;
		      hql = " FROM ProductConfig ";
		      cmd=new Command(hql);
		      list = cmd.open();
		      ProductConfig pcf=(ProductConfig)list.get(0);
		      String SdateInstall = pcf.getInstallDate();
		      Date dateInstall=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(SdateInstall);
		      Date dateFinished=DateUtil.addDays(dateInstall,Integer.parseInt(days));
		      if(DateUtil.compareDateTime(dNow, dateFinished)==1)
		      {
					errMessage="试用期限已过,本系统无法启动";
					System.out.println("试用期限已过,本系统无法启动");
					return false;
		      }
		      else if(DateUtil.compareDateTime(dbNow, dateFinished)==1)
		      {
					errMessage="试用期限已过,本系统无法启动";
					System.out.println("试用期限已过,本系统无法启动");
					return false;
		      }
			}
			
		}
      catch(Exception e){
         e.printStackTrace();
         errMessage=e.getMessage();
         System.out.println(e.toString());
         return false;
     }
		return true;
	}
	
	//对日期进行解密
	private Date decodeDate(String s) {		
		FileLicense filelic = new FileLicense();
		byte[] b=s.getBytes();
		byte[] bb=new byte[b.length];
		byte c=122;
		for(int i=0;i<b.length;i++)
		{
			bb[i]=(byte)(b[i]^c);
		}
		char[] cc=filelic.getChars(bb);
		String sw=String.copyValueOf(cc);	
		System.out.println("sw="+sw);
		Date d;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sw);
		} catch (ParseException e) {
			d=new Date(sw);
		}
		return d;
	}	
	
	//对日期进行加密
	private String encodeDate(Date d)
	{
		FileLicense filelic = new FileLicense();
		String dNow=d.toString();
		byte[] b=dNow.getBytes();
		byte[] bb=new byte[b.length];
		byte c=122;
		for(int i=0;i<b.length;i++)
		{
			bb[i]=(byte)(b[i]^c);
		}
		char[] cc=filelic.getChars(bb);
		String s=String.copyValueOf(cc);	
		return s;
	}
}
