package cn.edustar.jitar;

import java.util.HashMap;
import java.util.Map;



/**
 * 教研系统使用的公共常量类，此类可以在ftl通过 ${Util.JitarConst.MIN_TITLE_LENGTH} 得到
 * 
 * @author 孟宪会
 * 
 */
public class JitarConst {
    /** 内容详情页显示的评论条数  */
    public static final int SHOW_COMMENT_LIST_COUNT = 30;
    public static final String USERMGR_SITENAME = "usermgr2";
    public static final int MAX_TITLE_LENGTH = 128; // 文章标题最大长度
    public static final int MIN_TITLE_LENGTH = 1; // 文章标题最短长度
    public static final int MIN_ARTICLE_LENGTH = 100; // 文章内容字数限制

    /** 首页焦点图 缩略图宽度 320px */
    public static final int IMG_WIDTH_CONTROL = 320;

    /** 首页焦点图 缩略图高度 240px */
    public static final int IMG_HEIGHT_CONTROL = 240;
    
	public static final int RESOURCE_MIN_TITLE = 10; //资源文件标题的最短长度
	

    public static final int MIN_GROUP_DESC = 10;// 协作组说明最短长度
    public static final int DEFAULT_IMG_WIDTH = 320;// 发布图片的默认所录如图宽度
    public static final int DEFAULT_IMG_HEIGHT = 240;// 发布图片的默认缩略图高度
	
	public static final int DEFAULT_IMG_WIDTH_1=200;//发布图片的默认缩略图宽度
	public static final int DEFAULT_IMG_HEIGHT_1=240;//发布图片的默认缩略图高度
	
	public static final int DEFAULT_IMG_WIDTH_2=200;//发布图片的默认缩略图宽度
	public static final int DEFAULT_IMG_HEIGHT_2=120;//发布图片的默认缩略图高度
	
	public static final int DEFAULT_IMG_WIDTH_3=160;//发布图片的默认缩略图宽度
	public static final int DEFAULT_IMG_HEIGHT_3=120;//发布图片的默认缩略图高度
	
	// 专题首页的图片尺寸
	public static final int DEFAULT_IMG_WIDTH_4 = 565; //发布图片的默认缩略图宽度
	public static final int DEFAULT_IMG_HEIGHT_4 = 280; //发布图片的默认缩略图高度
	
	//图片频道幻灯片大图
	public static final int DEFAULT_IMG_HEIGHT_6 = 400;
	public static final int DEFAULT_IMG_WIDTH_6 = 690;
	
	//图片频道图片分类尺寸
	public static final int DEFAULT_IMG_WIDTH_7 = 230;
	public static final int DEFAULT_IMG_HEIGHT_7 = 250;
	public static final int DEFAULT_IMG_HEIGHT_8 = 136;
	public static final int DEFAULT_IMG_WIDTH_8 = 230;
	public static final int DEFAULT_IMG_WIDTH_9 = 230;
	public static final int DEFAULT_IMG_HEIGHT_9 = 100;
	
	
	public final static  Map<String,Object> imgs = new HashMap<String,Object>();
	public static final int DEFAULT_IMG_WIDTH_10 = 665;
	public static final int DEFAULT_IMG_HEIGHT_10 = 500;
	public static final int DEFAULT_IMG_HEIGHT_11 = 100;
	public static final int DEFAULT_IMG_WIDTH_11 = 125;
	public static final int DEFAULT_IMG_WIDTH_5 = 70;
	public static final int DEFAULT_IMG_HEIGHT_5 = 70;
	
	
	
	/**
	 * 这里维护一个map集合存放文件扩展名和对应的小图标样式对应
	 */
	static{
		//word
		imgs.put("doc", "liW");
		imgs.put("docx", "liW");
		imgs.put("wps", "liW");
		imgs.put("docm", "liW");
		imgs.put("dotm", "liW");
		imgs.put("dot", "liW");
		imgs.put("rtf", "liW");
		imgs.put("odt", "liW");
		imgs.put("wtf", "liW");
		
		//excel
		imgs.put("xls", "liX");
		imgs.put("xlsx", "liX");
		imgs.put("xlsm", "liX");
		imgs.put("xlsb", "liX");
		imgs.put("xml", "liX");
		imgs.put("xltx", "liX");
		imgs.put("xltm", "liX");
		imgs.put("xlt", "liX");
		imgs.put("cvs", "liX");
		imgs.put("xltm", "liX");
		imgs.put("xlam", "liX");
		imgs.put("xla", "liX");
		imgs.put("xps", "liX");
		imgs.put("ods", "liX");
		
		//ppt
		imgs.put("ppt", "liP");
		imgs.put("pptx", "liP");
		imgs.put("pptm", "liP");
		imgs.put("potx", "liP");
		imgs.put("potm", "liP");
		imgs.put("pot", "liP");
		imgs.put("ppsx", "liP");
		imgs.put("ppsm", "liP");
		imgs.put("pps", "liP");
		imgs.put("ppam", "liP");
		imgs.put("ppa", "liP");
		imgs.put("odp", "liP");
		
		//flash
		imgs.put("swf", "liIcon2");
		imgs.put("fla", "liIcon2");
		
		//sound
		imgs.put("acm", "liIcon3");
		imgs.put("aif", "liIcon3");
		imgs.put("aif", "liIcon3");
		imgs.put("aifc", "liIcon3");
		imgs.put("aiff", "liIcon3");
		imgs.put("asf", "liIcon3");
		imgs.put("au", "liIcon3");
		imgs.put("mp3", "liIcon3");
		imgs.put("wma", "liIcon3");
		imgs.put("ape", "liIcon3");
		imgs.put("flac", "liIcon3");
		
		//img
		imgs.put("bmp", "liIcon5");
		imgs.put("pcx", "liIcon5");
		imgs.put("tiff", "liIcon5");
		imgs.put("gif", "liIcon5");
		imgs.put("jpeg", "liIcon5");
		imgs.put("tag", "liIcon5");
		imgs.put("exif", "liIcon5");
		imgs.put("fpx", "liIcon5");
		imgs.put("svg", "liIcon5");
		imgs.put("png", "liIcon5");
		imgs.put("jpg", "liIcon5");
		imgs.put("raw", "liIcon5");
		imgs.put("ico", "liIcon5");
		
		//video
		imgs.put("avi", "liIcon4");
		imgs.put("mpg", "liIcon4");
		imgs.put("mpeg", "liIcon4");
		imgs.put("asf", "liIcon4");
		imgs.put("mov", "liIcon4");
		imgs.put("wmv", "liIcon4");
		imgs.put("rm", "liIcon4");
		imgs.put("rmvb", "liIcon4");
		imgs.put("mp4", "liIcon4");
		imgs.put("divx", "liIcon4");
		imgs.put("tp", "liIcon4");
		imgs.put("ts", "liIcon4");
	    
		//pdf
		imgs.put("pdf", "liIcon1");
		
		//txt
		imgs.put("txt", "liIcon6");
		
		//压缩文件
		imgs.put("rar", "liIcon8");
		imgs.put("arj", "liIcon8");
		imgs.put("gz", "liIcon8");
		imgs.put("z", "liIcon8");
		
		//zip压缩文件
		imgs.put("zip", "liIcon7");
	}
	
}
