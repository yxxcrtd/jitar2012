package cn.edustar.jitar.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import com.chinaedustar.util.StringUtils;
import com.edustar.common.utils.StringUnit;

import cn.edustar.jitar.JitarConst;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.GroupKTUser;
import cn.edustar.jitar.pojos.Meetings;
import cn.edustar.jitar.pojos.PrepareCourseMember;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.UnitDayCount;
import cn.edustar.jitar.pojos.UserOnLine;
import cn.edustar.jitar.pojos.UserType;
import cn.edustar.jitar.pojos.Video;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.MetaSubject;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.MeetingsService;
import cn.edustar.jitar.service.OnLineService;
import cn.edustar.jitar.service.OnlineManager;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.ConfigurationFileIni;
import cn.edustar.jitar.util.EncryptDecrypt;
import cn.edustar.jitar.util.ParamUtil;
import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 系统级别的模板工具类模型, 其提供一组工具函数, 这些工具函数能够在'FreeMarker'中安全的使用
 *
 * @remark 使用方法
 *   <li>$Util.userById(id) - userById 返回为一个方法, 得到指定标识的用户.
 *   <li>$Util.userByName(loginName) - 得到指定登录名的用户.
 *   <li>$Util.groupById(id) - 得到指定标识的群组.
 *   <li>$Util.isOnline(id/loginName) - 判定指定标识或登录名的用户是否在线.
 *   <li>$Util.url(href) - 计算一个地址的 url 完全表示.
 *     其中如果 href 以 '/' 开头则返回 $SiteUrl + href
 *     如果 href 以 '//' 开头则返回 HostUrl + href (HostUrl 不含 SiteUrl 中 context path 部分)
 *     如果 href 以 http://, https:// 开头则直接返回.
 *   <li>$Util.tagToList(tags) - 将一个标签字符串切分为 List&lt;String&gt;
 *   <li>$Util.subjectById(subjectId) - 得到指定标识的学科对象.
 *   <li>$Util.gradeById(gradeId) - 得到指定标识的学段对象.
 *   <li>$Util.districtById(districtId) - 得到指定标识的区县对象.
 *   <li>$Util.unitById(unitId) - 得到指定标识的机构对象.
 *   <li>$Util.subjectList() - 得到所有学科列表.
 *   <li>$Util.fileName(href) - 得到一个文件的文件名部分.
 *   <li>$Util.fileExtension(href) - 得到一个文件的后缀.
 *   <li>$Util.eraseHtml(html, [len]) - 提取 html 的前 len 个显示字符, len 缺省 = 255.
 *   <li>$Util.random - 产生一个随机整数.
 *   <li>$Util.uuid - 产生一个新的 uuid.
 *   <li>$Util.today - 得到当前日期.
 *   <li>$Util.iconImage(href or fileName) - 得到指定文件的图标, 返回为 'images/xxx.gif' 形式字符串.
 *   <li>$Util.fsize(fsize) - 以 1 k, 2M 的形式显示文件大小.
 *   <li>$Util.subView(href) - 得到照片的缩略图.
 *   <li>$Util.styleNameById(id)
 *   <li>$Util.getEncryptGuid(userGuid) - 得到加密的 Guid
 *   <li>$Util.subjectBySubjectId(subjectId) - 得到学科的信息
 *   <li>$Util.convertUnitStringPathToUnitTitlePath(unitPathInfo) - 将"/2/6/"转换成"/机构2/机构6/"
 *   <li>$Util.getAccessControlListByUserAndObject(userId,objectType,objectId) - 得到一个用户在某一对象类型中的所有管理权限列表
 *   <li>$Util.checkUserIsAdmin(userId) - 判断用户是否是一个管理员
 *   <li>$Util.getGroupArticleCate(groupArticleCateId) - 得到群组文章的分类名称
 *   <li>$Util.convertIntFrom10To36(intVal)    - 将十进制整数转成36进制。
 *   <li>$Util.getCategory(intVal)    - 得到分类对象。
 *   <li>$Util.checkVideoInPrepareCourse(intPrepareCourseId, intVideoId)    - 判断一个视频是否已经推送在一个集备中。
 *   <li>$Util.articleAbstract(articleId,count)	- 得到文章的部分摘要信息。
 *   <li>$Util.drawoffHTML(content, count)	- 从FCKeditor编辑后的内容中抽取出HTML标签
 *   <li>$Util.isOpenMeetings() - 是否开启视频会议功能？
 *   <li>$Util.isVideoUser(userId) - 是否视频用户？(参数：用户Id)
 *   <li>$Util.IsVideoGroup(createUserId) - 是否视频群组？(参数：创建者的用户Id)
 *   <li>$Util.IsVideoJibei(createUserId) - 是否视频集备？(参数：创建者的用户Id)
 *   <li>$Util.getPrivateCourseType(prepareCourseId,userId) - 得到个案的类型
 *   <li>$Util.getSubjectGradeListByUserId(userId) - 得到用户的学科、学段
 *   <li>$Util.toUnicode(txt)  - A\"B'D<E>F\\H修改  ->  \u0041\u0022\u0042\u0027\u0044\u003C\u0045\u003E\u0046\u005C\u0048\u4FEE\u6539
 *   <li>$Util.typeIdToName(typeId) - 返回typeId的中文名称，typeId可能是/1/2/这样的格式，有人可能是2，并按照原始格式返回，如/名师/推荐/
 *   <li>$Util.JitarConst.xxx  -  返回JitarConst定义的静态变量。
 */
@SuppressWarnings("rawtypes")
public class UtilModel implements ServletContextAware, TemplateHashModelEx, TemplateModelObject {
	/** WEB 应用程序环境对象。 */
	private ServletContext servlet_ctxt;
	
	/** 应用系统环境 */
	private JitarContext jtar_ctxt;

	/** 缺省图标放置的位置. */
	private String iconImageBase = "manage/userfm/images/icons/";

	/** 所有方法的集合 */
	private Map<String, Object> methods = new HashMap<String, Object>();

    /**
	 * 构造.
	 */
	public UtilModel() {
		methods.put("userById", new UserById());
		methods.put("userByName", new UserByName());
		methods.put("groupById", new GroupById());
		methods.put("groupByName", new GroupByName());
		methods.put("isOnline", new IsOnline());
		methods.put("url", new Url());
		methods.put("tagToList", new TagToList());
		methods.put("subjectById", new SubjectById());
		methods.put("gradeById", new GradeById());
		methods.put("unitById", new UnitById());
		methods.put("subjectList", new SubjectList());
		methods.put("fileName", new FileName());
		methods.put("fileExtension", new FileExtension());
		methods.put("eraseHtml", new EraseHtml());
		methods.put("random", new Random());
		methods.put("uuid", new Uuid());
		methods.put("today", new Today());
		methods.put("iconImage", new IconImage());
		methods.put("iconCss", new IconCss());
		methods.put("fsize", new Fsize());
		methods.put("thumbNails", new ThumbNails());
		methods.put("styleNameById", new StyleNameById());
		methods.put("getCountedWords", new GetCountedWords());
		methods.put("GetimgUrl", new GetimgUrl());
		methods.put("getEncryptGuid", new GetEncryptGuid());
		methods.put("getFieldNameInIni", new FieldNameInIni());
		methods.put("subjectBySubjectId", new SubjectBySubjectId());
		methods.put("convertUnitStringPathToUnitTitlePath", new ConvertUnitStringPathToUnitTitlePath());
		methods.put("getAccessControlListByUserAndObject", new GetAccessControlListByUserAndObject());
		methods.put("checkUserIsAdmin", new CheckUserIsAdmin());		
		methods.put("getGroupArticleCate", new GetGroupArticleCate());
		methods.put("convertIntFrom36To10", new ConvertIntFrom36To10());	
		methods.put("getCategory", new GetCategory());
		methods.put("checkVideoInPrepareCourse", new CheckVideoInPrepareCourse());
		methods.put("articleAbstract", new ArticleAbstract());
		methods.put("drawoffHTML", new DrawoffHTML());
		methods.put("isOpenMeetings", new isOpenMeetings());
		methods.put("isVideoUser", new IsVideoUser());
		methods.put("IsVideoGroup", new IsVideoGroup());
		methods.put("IsVideoJibei", new IsVideoJibei());
		methods.put("getPrivateCourseType", new GetPrivateCourseType());
		methods.put("getSubjectGradeListByUserId", new GetSubjectGradeListByUserId());
		methods.put("getKtUserById", new getKtUserById());
		methods.put("toUnicode", new ToUnicode());
		methods.put("typeIdToName", new TypeIdToName());		
		methods.put("JitarConst", this.getJitarConst());
		methods.put("getUnitDayCount", new GetUnitDayCount());
	}

    /*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servlet_ctxt = servletContext;
	}

	/**
	 * 教研应用系统环境
	 * 
	 * @param jtar_ctxt
	 */
	public void setJitarContext(JitarContext jtar_ctxt) {
		this.jtar_ctxt = jtar_ctxt;
	}

	/** 缺省图标放置的位置. */
	public void setIconImageBase(String val) {
		this.iconImageBase = val;
	}

	/**
	 * 设置新的方法, 实质是在原基础上添加.
	 * @param m
	 */
	@SuppressWarnings("unchecked")
	public void setMethods(Map m) {
		methods.putAll(m);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.model.TemplateModelObject#getVariableName()
	 */
	public String getVariableName() {
		return "Util";
	}

	/**
	 * 封装jitar常量
	 *
	 */
    public TemplateHashModel getJitarConst() {
        BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
        TemplateHashModel staticModels = wrapper.getStaticModels();
        TemplateHashModel jitarConst = (TemplateHashModel) TemplateHashModel.NOTHING;
        try {
            jitarConst = (TemplateHashModel) staticModels.get("cn.edustar.jitar.JitarConst");
        } catch (TemplateModelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }          
        return jitarConst;
    }	
	
	/**
	 * 得到指定名字的函数、属性等.
	 * @param name
	 * @return
	 * @throws TemplateModelException
	 */
	public TemplateModel get(String name) throws TemplateModelException {
		Object val = this.methods.get(name);
		if (val == null) return TemplateModel.NOTHING;
		if (val instanceof TemplateModel) return (TemplateModel)val;
		
		return Environment.getCurrentEnvironment().getObjectWrapper().wrap(val);
	}

	/**
	 * 判定集合是否非空.
	 * @return
	 * @throws TemplateModelException
	 */
	public boolean isEmpty() throws TemplateModelException {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see freemarker.template.TemplateHashModelEx#keys()
	 */
	public TemplateCollectionModel keys() throws TemplateModelException {
		return (TemplateCollectionModel)Environment.getCurrentEnvironment().getObjectWrapper().wrap(this.methods.keySet());
	}

	/*
	 * (non-Javadoc)
	 * @see freemarker.template.TemplateHashModelEx#size()
	 */
	public int size() throws TemplateModelException {
		return this.methods.size();
	}

	/*
	 * (non-Javadoc)
	 * @see freemarker.template.TemplateHashModelEx#values()
	 */
	public TemplateCollectionModel values() throws TemplateModelException {
		throw new TemplateModelException("Unsupport values() method.");
	}

	/**
	 * 得到在线服务.
	 * @return
	 */
	private OnlineManager getOnlineManager() {
		return jtar_ctxt.getOnlineManager();
	}
	
	/**
	 * 得到用户服务
	 * 
	 * @return
	 */
	private UserService getUserService() {
		return jtar_ctxt.getUserService();
	}	
	
	/**
	 * @author renliang
	 * 返回相应缩略图地址
	 * 第一个参数原地址
	 * 第二个参数尺寸
	 */
	public class GetimgUrl implements TemplateMethodModelEx{
		@Override
		public Object exec(List arg0) throws TemplateModelException {
			if (arg0 == null || arg0.size() == 0)
				throw new TemplateModelException(" GetimgUrl 需要2个参数");
			String url = arg0.get(0).toString();
			//文件名前面的部分
			String urlPrev = url.substring(0, url.lastIndexOf("/") + 1);
			String fileName = url.substring( url.lastIndexOf("/") + 1);
			if(fileName.startsWith(arg0.get(1) + "_"))
			{
			    return url;
			}
			else
			{
			    return urlPrev + arg0.get(1) + "_" + fileName;
			}
		}
		
	}
	
	/**
	 * 该函数支持第三个参数
	 * 如果第三个参数是1 则：
	 * 		截取前多少个字符串，由于有汉字和字母，返回截取的长度以汉字宽度为准。
	 * 		即：两个英文字母或数字代表一个长度，一个汉字代表一个长度
	 * 		例如：
	 * 		参数  abcdefghi,截取4个字符,实际返回 'abcdefg...'
	 * 		参数  我爱中国，我爱人民 ,截取6个字符,实际返回   '我爱中国，我...'
	 * 		参数  我爱ABC,截取3个字符,实际返回   '我爱AB...'
	 * 如果没有第三个参数，则和原来截取方式一样
	 */
	public class GetCountedWords implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数
			if (args == null || args.size() == 0)
				throw new TemplateModelException(" getCountedWords 需要2个参数");	
			if(args.get(0) == null) return "";
			String strContent = args.get(0).toString();
			String countShow = args.get(1).toString();
			boolean unicodeLength = false;
			if(args.size() == 3){
				if("1".equals(args.get(2).toString())){
					unicodeLength = true;
				}
			}
			if (ParamUtil.isInteger(countShow) == false)
				throw new TemplateModelException(" getCountedWords 需要 1 个整数型参数");
			String ret = "";
			int count = Integer.valueOf(countShow);
			if(count < 1) throw new TemplateModelException(" getCountedWords 第二个参数必须大于1");
			if (strContent.length() > count) {
				if(unicodeLength){
					int iIndex = 0 ;
					String sValue = "";
					for(int i = 0; i < strContent.length(); i++){
						String s = strContent.substring(i,i+1);
						if(s.getBytes().length == s.length()){
							iIndex++;
						}else{
							//汉字
							iIndex++;
							iIndex++;
						}
						sValue = sValue + s;
						if(iIndex>=2*count){
							break;
						}
					}
					if(sValue.equals(strContent)){
						ret = sValue;
					}else{
						ret = sValue + "…";
					}
				}else{
					ret = strContent.substring(0, count-1) + "…";
				}
			} else {
				return strContent;
			}
			return ret;
		}
	}	
	
	/**
	 * 根据用户标识得到用户对象的函数方法
	 */
	public class UserById implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数
			if (args == null || args.size() == 0)
				throw new TemplateModelException("userById 需要 1 个整数型参数");
			if(args.get(0) == null){
				return null;
			}
			String idstr = args.get(0).toString();
			if (ParamUtil.isInteger(idstr) == false)
				throw new TemplateModelException("userById 需要 1 个整数型参数");
			// 得到用户标识参数
			int id = Integer.parseInt(idstr);
			User u = jtar_ctxt.getUserService().getUserById(id);
			return u == null ? NOTHING : u;
		}
	}
	
	/**
	 * 根据课题组Id,得到课题负责人
	 * @author dell
	 *
	 */
	public class getKtUserById implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数
			if (args == null || args.size() == 0)
				throw new TemplateModelException("getKtUserById 需要 1 个整数型参数");
			String idstr = args.get(0).toString();
			if (ParamUtil.isInteger(idstr) == false)
				throw new TemplateModelException("getKtUserById 需要 1 个整数型参数");
			// 得到Group标识参数
			int id = Integer.parseInt(idstr);
			List<GroupKTUser> us = jtar_ctxt.getGroupService().GetGroupKTUsers(id);
			return us == null ? NOTHING : us;
		}
	}	
	
	
	/**
	 * 根据用户标识得到用户对象的函数方法
	 */
	public class FieldNameInIni implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数
			if (args == null || args.size() != 4)
				throw new TemplateModelException("userById 需要 4 个参数");
			String iniFile = args.get(0).toString();
			String iniSession = args.get(1).toString();
			String iniKey = args.get(2).toString();
			String iniFieldName = args.get(3).toString();
			String sValue="";
			try{
				sValue=ConfigurationFileIni.getValue(iniFile,iniSession,iniKey,"");
			}
			catch(java.io.IOException ex ){
				return iniFieldName;
			}
			if(sValue==""){
				return iniFieldName;
			}
			else{
				sValue=","+sValue+",";
				if(sValue.indexOf(","+iniFieldName+",")>=0){
					return iniFieldName;
				}
				else{
					return "";
				}
			}
		}
	}
	
	public class GetEncryptGuid implements TemplateMethodModelEx{
		@SuppressWarnings("unchecked")
		public String exec(List args) throws TemplateModelException {
			if (args == null || args.size() == 0)
				throw new TemplateModelException("getEncryptGuid 需要 1 个整数型参数");
			String privateKey = servlet_ctxt.getInitParameter("privateKey");
			if (privateKey == null) privateKey = "www.chinaedustar.com";
			String encGuid = args.get(0).toString();
			try
			{
				EncryptDecrypt ed = new EncryptDecrypt(privateKey);
				encGuid = ed.encrypt(encGuid);
			}
			catch(Exception ex)
			{
				//出错了。
			}
			return encGuid;
		}
	}
	
	/**
	 * 
	 */
	public class StyleNameById implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public String exec(List args) throws TemplateModelException {
			// 判断参数.
			if (args == null || args.size() == 0)
				throw new TemplateModelException("objTypeId 需要 1 个整数型参数");
			String idstr = args.get(0).toString();
			if (ParamUtil.isInteger(idstr) == false)
				throw new TemplateModelException("objTypeId 需要 1 个整数型参数");
			String objIDstr="0";
			if (args.size() >= 2)
				objIDstr = args.get(1).toString();

			// 得到标识参数.
			int id = Integer.parseInt(idstr);
			int objid = Integer.parseInt(objIDstr);
			if(id ==0 && objid==0) return "站点首页";
			
			String u ="";
			if (args.size()==1)
			{
				u = ObjectType.getTypeNameChinese(id);
			}
			else
			{
				String stype=ObjectType.fromTypeId(id).getTypeName();
				if(stype=="user")
				{
					User uu = jtar_ctxt.getUserService().getUserById(objid);
					if(uu==null)
					{
						u="用户可能被删除";
					}
					else
					{
						u=uu.getTrueName();
					}
				}
				else if(stype=="group")
				{
					Group g = jtar_ctxt.getGroupService().getGroupMayCached(objid);
					if(g==null)
					{
						u="未能加载的协作组";	
					}
					else
					{
						u=g.getGroupName();
					}
					
				}
				else if(stype=="article")
				{
					//System.out.println(stype);
					//System.out.println("objId="+objid);
					Article article= jtar_ctxt.getArticleService().getArticle(objid);
					if(article==null)
					{
						u="";
					}
					else
					{
						u=article.getTitle();
					}
					//System.out.println("title="+u);
				}
				else if(stype=="resource")
				{
					//System.out.println(stype);
					//System.out.println("objId="+objid);
					Resource resource= jtar_ctxt.getResourceService().getResource(objid);
					if(resource==null)
					{
						u="";
					}
					else
					{
						u=resource.getTitle();
					}
					//System.out.println("title="+u);
				}
				else if(stype=="video")
				{
					//System.out.println(stype);
					//System.out.println("objId="+objid);
					Video video= jtar_ctxt.getVideoService().findById(objid);
					if(video==null)
					{
						u="";
					}
					else
					{
						u=video.getTitle();
					}
					//System.out.println("title="+u);
				}
				else if(stype=="subject")
				{
					Subject s = jtar_ctxt.getSubjectService().getSubjectById(objid);
					if(s==null)
					{
					u="站点首页";	
					}
					else
					{
					u=s.getSubjectName();
					}
				}
				else if(stype=="channel")
				{
					Channel s = jtar_ctxt.getChannelPageService().getChannel(objid);
					if(s==null)
					{
						u="站点首页";	
					}
					else
					{
						u=s.getTitle();
					}
				}
				else if(stype=="system")
				{
					u="站点首页";
				}
				else {u="";}
			}
			return u ;
		}
	}
	
	/**
	 * 根据用户登录名得到用户信息.
	 */
	public class UserByName implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数.
			if (args == null || args.size() == 0)
				throw new TemplateModelException("userByName 需要 1 个字符串参数");
			
			String loginName = args.get(0).toString();
			User u = getUserService().getUserByLoginName(loginName);
			return u == null ? NOTHING : u;
		}
	}

	/**
	 * 根据群组标识得到指定群组对象的函数方法.
	 *
	 *
	 */
	public class GroupById implements TemplateMethodModelEx {
		/*
		 * (non-Javadoc)
		 * @see freemarker.template.TemplateMethodModelEx#exec(java.util.List)
		 */
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数.
			if (args == null || args.size() == 0)
				throw new TemplateModelException("groupById 需要 1 个整数型参数");
			String idstr = args.get(0).toString();
			if (ParamUtil.isInteger(idstr) == false)
				throw new TemplateModelException("groupById 需要 1 个整数型参数");
			
			// 得到群组标识参数.
			int id = Integer.parseInt(idstr);
			Group g = jtar_ctxt.getGroupService().getGroupMayCached(id);
			return g == null ? NOTHING : g;
		}
	}
	
	/**
	 * 根据群组名得到群组的信息.
	 */
	public class GroupByName implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数.
			if (args == null || args.size() == 0)
				throw new TemplateModelException("groupByName 需要 1 个字符串参数");
			
			String groupName = args.get(0).toString();
			Group g = jtar_ctxt.getGroupService().getGroupMayCached(groupName);
			return g == null ? NOTHING : g;
		}
	}

	/**
	 * 判定指定标识或登录名的用户是否在线
	 */
	public class IsOnline implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数
			if (args == null || args.size() == 0)
				throw new TemplateModelException("isOnline 需要 1 个字符串参数");

			String id_name = args.get(0).toString();
			if (ParamUtil.isInteger(id_name))
				return isOnline(Integer.parseInt(id_name));
			else
				return isOnline(id_name);
		}

		private Boolean isOnline(int userId) {
			User user = getUserService().getUserFromCache(userId);
			if (user == null)
				return Boolean.FALSE;
			OnLineService  onLineService = (OnLineService)jtar_ctxt.getSpringContext().getBean("onlineService");
			UserOnLine userOnLine = onLineService.findUserOnLineByUserName(user.getLoginName());
			Boolean isLine = (userOnLine != null);
			userOnLine = null;
			user = null;
			return isLine;
		}
		
		private Boolean isOnline(String loginName) {
			User user = getUserService().getUserByLoginName(loginName);
			if (user == null)
				return Boolean.FALSE;
			int uid = user.getUserId();
			user = null;
			return isOnline(uid);
		}
	}

	/**
	 * 计算一个地址的 url 完全表示.
	 *
	 *、
	 * 此方法依赖固定的url，已废弃
	 */
	public class Url implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() == 0)
				throw new TemplateModelException("url() 方法需要 1 个字符串参数");
			String href = args.get(0).toString();
			if (href == null || href.length() == 0) return "";
			if(href.toLowerCase().startsWith("http://") || href.toLowerCase().startsWith("https://")) return href;
			if(href.startsWith("/")) return href;
			return servlet_ctxt.getContextPath() + "/" + href;
			
			//以下代码不再使用，使用绝对路径可能存在问题
			
			/*String ctxt_path = servlet_ctxt.getContextPath();
			if (ctxt_path.length() > 1 && href.startsWith(ctxt_path + "/")) 
				return href;
			String siteUrl=SiteUrlModel.getSiteUrl();
			if(siteUrl.endsWith("/") && href.startsWith("/")){
				return SiteUrlModel.getSiteUrl() + href.substring(1);
			}
			return SiteUrlModel.getSiteUrl() + href;*/
		}
	}
	
	/**
	 * 得到照片的缩略图.
	 *
	 * @author Yang XinXin
	 * @version 1.0.0 Aug 5, 2008 1:58:52 PM
	 */
	public static class ThumbNails implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() == 0)
				throw new TemplateModelException("SubView() 方法需要 1 个字符串参数");
			String href = args.get(0).toString();
			
			return CommonUtil.thumbnails2(href);
		}
	}

	/**
	 * 将一个 tag 字符串切分为 List&lt;String&gt; 格式.
	 */
	public static class TagToList implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() == 0)
				throw new TemplateModelException("tagToList() 方法需要 1 个字符串参数");
			String tags = safeGetString(args.get(0));
			
			String[] tags_a = CommonUtil.parseTagList(tags);
			if (tags_a == null || tags_a.length == 0)
				return CommonUtil.EMPTY_LIST;
			List<String> tags_list = new ArrayList<String>(tags_a.length);
			for (int i = 0; i < tags_a.length; ++i)
				tags_list.add(tags_a[i]);
			return tags_list;
		}
	}
	
	private static final String safeGetString(Object v) {
		if (v == null) return "";
		return v.toString();
	}

	/**
	 * $Util.subjectById(subjectId) - 得到指定标识的学科对象.
	 */
	public class SubjectById implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() == 0)
				throw new TemplateModelException("subjectById 需要 1 个整数型参数");
			String idstr = args.get(0).toString();
			if ("".equals(idstr)) {
				idstr = "0"; // 如果为空，则给0
			}
			if (ParamUtil.isInteger(idstr) == false)
				throw new TemplateModelException("subjectById 需要 1 个整数型参数");

			// 得到标识参数.
			int id = Integer.parseInt(idstr);
			MetaSubject s = jtar_ctxt.getSubjectService().getMetaSubjectById(id);
			return s == null ? NOTHING : s;
		}
	}

	/**
	 * 得到真正的学科对象
	 * @author admin
	 *
	 */
	public class SubjectBySubjectId implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() == 0)
				throw new TemplateModelException("SubjectBySubjectId 需要 1 个整数型参数");
			String idstr = args.get(0).toString();
			if ("".equals(idstr)) {
				idstr = "0"; // 如果为空，则给0
			}
			if (ParamUtil.isInteger(idstr) == false)
				throw new TemplateModelException("SubjectBySubjectId 需要 1 个整数型参数");

			// 得到标识参数.
			int id = Integer.parseInt(idstr);
			Subject s = jtar_ctxt.getSubjectService().getSubjectById(id);
			return s == null ? NOTHING : s;
		}
	}
	
	/**
	 * $Util.gradeById(gradeId) - 得到指定标识的学段对象.
	 */
	public class GradeById implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() == 0)
				throw new TemplateModelException("GradeById 需要 1 个整数型参数");
			String idstr = args.get(0).toString();
			if ("".equals(idstr)) {
				idstr = "0"; // 如果为空，则给0
			}
			if (ParamUtil.isInteger(idstr) == false)
				throw new TemplateModelException("GradeById 需要 1 个整数型参数");
			
			int id = Integer.parseInt(idstr);
			Grade g = jtar_ctxt.getSubjectService().getGrade(id);
			return g == null ? NOTHING : g;
		}
	}

	
	/**
	 * $Util.unitById(unitId) - 得到指定标识的机构对象.
	 */
	public class UnitById implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() == 0)
				throw new TemplateModelException("UnitById 需要 1 个整数型参数");
			String idstr = args.get(0).toString();
			if ("".equals(idstr)) {
				return NOTHING;
			}
			if (ParamUtil.isInteger(idstr) == false)
				throw new TemplateModelException("UnitById 需要 1 个整数型参数");
			
			int id = Integer.parseInt(idstr);
			if(id<1) return NOTHING;
			Unit u = jtar_ctxt.getUnitService().getUnitById(id);
			return u == null ? NOTHING : u;
		}
	}
	
	public class ArticleAbstract implements TemplateMethodModelEx{
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() < 2)
				throw new TemplateModelException("ArticleAbstract 需要 2 个参数");
			String articleId = args.get(0).toString();
			String count = args.get(1).toString();
		
			if (ParamUtil.isInteger(articleId) == false)
				throw new TemplateModelException("ArticleAbstract 需要 1 个整数型参数");
			if (ParamUtil.isInteger(count) == false)
				throw new TemplateModelException("ArticleAbstract 需要 2 个整数型参数");
			Article a = jtar_ctxt.getArticleService().getArticle(Integer.valueOf(articleId));
			String content = null;
			if(a.getArticleFormat() == null){
			    a.setArticleFormat(0);
			}
			if(a.getArticleFormat()==0){//网页
				content = a.getArticleContent();
			}else{
				content = a.getArticleAbstract();
			}
			if(content == null) {
			    content = "";
			}
			return wipeHTML(content, count);
		}
	}
	
	public class DrawoffHTML implements TemplateMethodModelEx {
		public Object exec(List args) throws TemplateModelException {
			if (null == args || 2 > args.size()) {
				throw new TemplateModelException("DrawoffHTML 需要2个参数！");
			}
			String content = args.get(0).toString();
			String count = args.get(1).toString();
			return wipeHTML(content, count);
		}
	}
	
	private String wipeHTML(String content, String count) {
		if (null == content || "".equals(content) || 0 == content.trim().length()) {
			return "";
		}
		//去除html标记，否则，页面会乱套啊
		content = CommonUtil.eraseHtml(content);
		content = content.replaceAll("&nbsp;", " ");
		// 当内容的长度大于设置的长度的时候才去截取
		if (content.length() > Integer.valueOf(count)) {
			content = content.substring(0, Integer.valueOf(count)) + "...";
		}
		
		return content;
	}
	
	public class ConvertUnitStringPathToUnitTitlePath implements TemplateMethodModelEx {
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() == 0)
				throw new TemplateModelException("ConvertUnitStringPathToUnitTitlePath 需要 1 个整数型参数");
			String unitPathInfo = args.get(0).toString();			
			
			unitPathInfo = jtar_ctxt.getUnitService().convertUnitStringToUnitTitle(unitPathInfo);
			return unitPathInfo == null ? NOTHING : unitPathInfo;
		}
	}
	
	public class GetAccessControlListByUserAndObject implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() != 3 )
				throw new TemplateModelException(" getAccessControlListByUserAndObject 需要 3 个整数型参数");
			int userId = Integer.valueOf(args.get(0).toString());	
			int objectType = Integer.valueOf(args.get(1).toString());
			int objectId = Integer.valueOf(args.get(2).toString());
			AccessControlService accessControlService = (AccessControlService)jtar_ctxt.getSpringContext().getBean("accessControlService");
			AccessControl a = accessControlService.getAccessControlByUserAndObject(userId, objectType, objectId);
			return null == a ? "" : ConvertAccessToTitle(a);
		}
		
		private String ConvertAccessToTitle(AccessControl ac) {
		    return AccessControl.getAccessControlChinese(ac.getObjectType());
		}
	}
	
	public class CheckUserIsAdmin implements TemplateMethodModelEx {
		@SuppressWarnings("rawtypes")
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() != 1 )
				throw new TemplateModelException(" getAccessControlListByUserAndObject 需要 1  个整数型参数");
			int userId = Integer.valueOf(args.get(0).toString());
			AccessControlService accessControlService = (AccessControlService)jtar_ctxt.getSpringContext().getBean("accessControlService");
			User user = jtar_ctxt.getUserService().getUserById(userId);
			List<AccessControl> a = accessControlService.getAllAccessControlByUser(user);
			return (null == a || 1 > a.size()) ? "0" : "1";
		}
	}
	
	/**
	 * $Util.subjectList() - 得到所有学科列表.
	 */
	public class SubjectList implements TemplateMethodModelEx {
		@SuppressWarnings("rawtypes")
		public Object exec(List arguments) throws TemplateModelException {
			return jtar_ctxt.getSubjectService().getSubjectList();
		}
	}

	/**
	 * $Util.fileName(href) - 得到一个文件的文件名部分.
	 */
	public static class FileName implements TemplateMethodModelEx {
		/*
		 * (non-Javadoc)
		 * @see freemarker.template.TemplateMethodModelEx#exec(java.util.List)
		 */
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数.
			if (args == null || args.size() == 0)
				throw new TemplateModelException("fileName() 方法需要 1 个字符串参数");
			
			String href = args.get(0).toString();
			return CommonUtil.getFileNameFromHref(href);
		}
		
	}
	
	/**
	 * $Util.fileExtension(href) - 得到一个文件的后缀.
	 */
	public static class FileExtension implements TemplateMethodModelEx {
		/*
		 * (non-Javadoc)
		 * @see freemarker.template.TemplateMethodModelEx#exec(java.util.List)
		 */
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数.
			if (args == null || args.size() == 0)
				throw new TemplateModelException("fileExtension() 方法需要 1 个字符串参数");
			
			String href = args.get(0).toString();
			return CommonUtil.getFileExtension(href);
		}
	}

	/**
	 * ${Util.eraseHtml(html, [len]) - 提取 html 的前 len 个显示字符, len 缺省 = 255.
	 *
	 *
	 */
	public static class EraseHtml implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数.
			if (args == null || args.size() == 0)
				throw new TemplateModelException("eraseHtml() 方法需要 1 个字符串参数");
			String html = args.get(0).toString();
			int len = 255;
			if (args.size() >= 2)
				len = ParamUtil.safeParseInt(args.get(1).toString(), 255);
			
			String result = CommonUtil.eraseHtml(html).trim();
			if (result.length() < len)
				return result;
			return result.substring(0, len) + "...";
		}
	}

	/**
	 * ${Util.random } - 产生一个随机整数.
	 *
	 *
	 */
	public static class Random implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			java.util.Random r = new java.util.Random();
			return r.nextInt();
		}
	}
	
	/**
	 * ${Util.uuid } - 产生一个新的 UUID.
	 *
	 *
	 */
	public static class Uuid implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			return java.util.UUID.randomUUID();
		}
	}

	/**
	 * ${Util.today } - 得到当前日期.
	 *
	 *
	 */
	public static class Today implements TemplateMethodModelEx {
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			return new Date();
		}
	}

	/**
	 * ${Util.iconImage(href or fileName) - 得到指定文件的图标, 返回为 'images/xxx.gif' 形式字符串.
	 * 此方法依赖固定的url，不再使用
	 */
	public class IconImage implements TemplateMethodModelEx {
		/*
		 * (non-Javadoc)
		 * @see freemarker.template.TemplateMethodModelEx#exec(java.util.List)
		 */
		@SuppressWarnings("unchecked")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数.
			if (args == null || args.size() == 0)
				throw new TemplateModelException("iconImage() 方法需要 1 个字符串参数");
			String fileName = args.get(0).toString();
			String ext = CommonUtil.getFileExtension(fileName);
			
			String context_path = servlet_ctxt.getContextPath() + "/";
			//if(!context_path.endsWith("/")) context_path += "/";
			
			// 使用 'manage/userfm/images/icons' 下面的图标.
			if (ext == null || ext.length() == 0)
				return context_path + iconImageBase + "default.icon.gif";
			
			// 计算 icon_url, 如果该文件存在则返回, 否则返回缺省图标.
			String icon_url = iconImageBase + ext.toLowerCase() + ".gif";
			File icon_file = new File(servlet_ctxt.getRealPath(icon_url));
			if (icon_file.exists())
				return context_path + icon_url;
			else
				return context_path + iconImageBase + "default.icon.gif";
			
		}		
	}

	
	/**
     * ${Util.iconCss(href or fileName) - 得到指定文件的图标, 返回为 'images/xxx.gif' 形式字符串.
     * 此方法依赖固定的url，不再使用
     */
    public class IconCss implements TemplateMethodModelEx {
        /*
         * (non-Javadoc)
         * @see freemarker.template.TemplateMethodModelEx#exec(java.util.List)
         */
        @SuppressWarnings("unchecked")
        public Object exec(List args) throws TemplateModelException {
            // 判断参数.
            if (args == null || args.size() == 0)
                throw new TemplateModelException("iconCss() 方法需要 1 个字符串参数");
            String fileName = args.get(0).toString();
            String ext = CommonUtil.getFileExtension(fileName);            
            Object icon = JitarConst.imgs.get(ext.trim().toLowerCase());
            return (null!=icon&&!"".equals(icon))?icon:"liIcon9"; //TODO 暂时这么写 直接返回后缀名        
        }       
    }
    
	public static final long FSIZE_K = 1024L;
	public static final long FSIZE_M = 1024L*1024L;
	public static final long FSIZE_G = 1024L*1024L*1024L;
	
	/**
	 * $Util.fsize(fsize) - 以 1 k, 2M 的形式显示文件大小.
	 */
	public static class Fsize implements TemplateMethodModelEx {
		/*
		 * (non-Javadoc)
		 * @see freemarker.template.TemplateMethodModelEx#exec(java.util.List)
		 */
		@SuppressWarnings("rawtypes")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数.
			if (args == null || args.size() == 0)
				throw new TemplateModelException("fsize() 方法需要 1 个字符串参数");
			String t = args.get(0).toString();
			if (t == null || t.length() == 0) return "";
			
			try {
				long fsize = Long.parseLong(args.get(0).toString());
				if (fsize < FSIZE_K) return "1 k";		// 1 k
				if (fsize < FSIZE_M) return "" + (fsize/FSIZE_K) + " K";
				if (fsize < FSIZE_G) return "" + (fsize/FSIZE_M) + " M";
				return "" + (fsize/FSIZE_G) + " G";
			} catch (Exception ex) {
				return "";
			}
		}
		
	}
	
	public class GetGroupArticleCate implements TemplateMethodModelEx
	{				
		@SuppressWarnings("rawtypes")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数.
			if (args == null || args.size() == 0)
				throw new TemplateModelException("getGroupArticleCate() 方法需要 1 个字符串参数");
			String t = args.get(0).toString();
			int cateId = Integer.valueOf(t);
			CategoryService categoryService = (CategoryService)jtar_ctxt.getSpringContext().getBean("categoryService");
			Category c = categoryService.getCategory(cateId);
			if(c == null) return "";
			else
			return c.getName();
		}
	}
	
	public class GetCategory implements TemplateMethodModelEx
	{
		@SuppressWarnings("rawtypes")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数.
			if (args == null || args.size() == 0)
				throw new TemplateModelException("getCategory() 方法需要 1 个字符串参数");
			String t = args.get(0).toString();
			if(t.indexOf("/")>-1){
				if(t.endsWith("/")) t = t.substring(0,t.length()-1);
				t=t.substring(t.lastIndexOf("/")+1);
			}
			
			if(isInteger(t)==false)
			{				
				return NOTHING;
			}
			int cateId = Integer.valueOf(t);
			CategoryService categoryService = (CategoryService)jtar_ctxt.getSpringContext().getBean("categoryService");
			Category c = categoryService.getCategory(cateId);
			if(c == null) 
				return NOTHING;
			else
				return c;
		}		
		public boolean isInteger( String input )
		{
		   try
		   {
		      Integer.parseInt( input );
		      return true;
		   }
		   catch( Exception e)
		   {
		      return false;
		   }
		}
	}
	
	
	public class CheckVideoInPrepareCourse implements TemplateMethodModelEx
	{
		@SuppressWarnings("rawtypes")
		public Object exec(List args) throws TemplateModelException {
			// 判断参数.
			if (args == null || args.size() < 2)
				throw new TemplateModelException(" checkVideoInPrepareCourse() 方法需要 2 个字符串参数");
			String pcIds = args.get(0).toString();
			String videoIds = args.get(1).toString();
			
			Integer pcId = Integer.valueOf(pcIds);
			Integer videoId = Integer.valueOf(videoIds);
			if(pcId == null || videoId == null) return "0";
			
			PrepareCourseService prepareCourseService = (PrepareCourseService)jtar_ctxt.getSpringContext().getBean("prepareCourseService");
			
			if(prepareCourseService.checkVideoInPrepareCourse(pcId, videoId)) 
				return "1";
			else
				return "0";
		}
	}
	
	public class ConvertIntFrom36To10 implements TemplateMethodModelEx
	{
		public Object exec(List args) throws TemplateModelException {
			// 判断参数.
			if (args == null || args.size() == 0)
				throw new TemplateModelException("convertIntFrom10To36() 方法需要 1 个字符串参数");
			String t = args.get(0).toString();
			String[] ts = t.split("/");
			t = "/";
			for(int i=0;i<ts.length;i++)
			{   
				String cp = ts[i];
				if(cp.equals("") == false)
				{
					t += String.valueOf(Integer.parseInt(cp,36)) + "/";
				}
			}		
			return t;
		}
	}

	@SuppressWarnings("rawtypes")
	public class isOpenMeetings implements TemplateMethodModelEx {
		public Object exec(List args) throws TemplateModelException {
			String v = servlet_ctxt.getInitParameter("video_url");
			return (null == v || "".equals(v) || 0 == v.length()) ? "0" : v;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public class IsVideoUser implements TemplateMethodModelEx {
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() != 1 ) {
				throw new TemplateModelException("");
			}
			int userId = Integer.valueOf(args.get(0).toString());
			MeetingsService meetingsService = (MeetingsService) jtar_ctxt.getSpringContext().getBean("meetingsService");
			Meetings meetings = meetingsService.getMeetingsByObjAndObjId("user", userId);
			return (null == meetings) ? 0 : 1;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public class IsVideoGroup implements TemplateMethodModelEx {
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() != 1 ) {
				throw new TemplateModelException("");
			}
			int groupId = Integer.valueOf(args.get(0).toString());
			MeetingsService meetingsService = (MeetingsService) jtar_ctxt.getSpringContext().getBean("meetingsService");
			Meetings meetings = meetingsService.getMeetingsByObjAndObjId("group", groupId);
			return (null == meetings) ? 0 : 1;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public class IsVideoJibei implements TemplateMethodModelEx {
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() != 1 ) {
				throw new TemplateModelException("");
			}
			int jibeiId = Integer.valueOf(args.get(0).toString());
			MeetingsService meetingsService = (MeetingsService) jtar_ctxt.getSpringContext().getBean("meetingsService");
			Meetings meetings = meetingsService.getMeetingsByObjAndObjId("jibei", jibeiId);
			return (null == meetings) ? 0 : 1;
		}
	}
	
	
	public class GetPrivateCourseType implements TemplateMethodModelEx {
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() != 2 ) {
				throw new TemplateModelException("GetPrivateCourseType 需要2个参数");
			}
			
			int prepareCourseId = Integer.valueOf(args.get(0).toString());
			int userId = Integer.valueOf(args.get(1).toString());
			PrepareCourseService pcs = (PrepareCourseService)jtar_ctxt.getSpringContext().getBean("prepareCourseService");
			PrepareCourseMember pcm = pcs.getPrepareCourseMemberByCourseIdAndUserIdWithNoStatus(prepareCourseId, userId);
			if(pcm == null) return 0;
			return pcm.getContentType();
		}
	}	
	
	public class GetSubjectGradeListByUserId implements TemplateMethodModelEx {
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() != 1 ) {
				throw new TemplateModelException("GetSubjectGradeListByUserId 需要1个参数");
			}
			if ("".equals(args.get(0))) {
				return NOTHING;
			}
			int userId = Integer.valueOf(args.get(0).toString());
			UserService userService = jtar_ctxt.getUserService();
			User user = userService.getUserById(userId);
			if(user == null) return NOTHING;
			return userService.getAllUserSubjectGradeListByUserId(userId);			
		}
	}
	
	public class GetUnitDayCount implements TemplateMethodModelEx{
        public Object exec(List args) throws TemplateModelException {
            // 判断参数.
            if (args == null || args.size() == 0)
                throw new TemplateModelException("GetUnitDayCount() 方法需要 1 个参数");
            if(args.get(0) == null){
                throw new TemplateModelException("GetUnitDayCount() 方法需要 1 个不为null的参数");
            }
            String t = args.get(0).toString();
            int unitId = Integer.valueOf(t);
            UnitService unitService = (UnitService)jtar_ctxt.getSpringContext().getBean("unitService");
            UnitDayCount unitDayCount = unitService.queryUnitDayCount(unitId);
            if(unitDayCount == null){
                return NOTHING;
            }
            else
            {
                return unitDayCount;
            }
        }
    }
	
	public class ToUnicode implements TemplateMethodModelEx {
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() != 1 ) {
				throw new TemplateModelException("ToUnicode 需要1个参数");
			}
			return CommonUtil.toUnicode(args.get(0).toString());			
		}
	}	
	
	public class TypeIdToName implements TemplateMethodModelEx {
		public Object exec(List args) throws TemplateModelException {
			if (args == null || args.size() != 1 ) {
				throw new TemplateModelException("TypeIdToName 需要1个参数");
			}
			String typeId = args.get(0).toString();
			if(typeId.equals("")) return NOTHING;
			UserService userService = jtar_ctxt.getUserService();
			List<UserType> ls = userService.getAllUserType();
			//如果系统没有定义用户类型，就直接返回
			if(ls == null || ls.size() == 0)
			{
				return typeId;
			}
			
			if(typeId.contains("/") == false)
			{
				//只有一个数字的处理，找到后直接返回
				for(UserType ut : ls)
				{
					if(typeId.equals(String.valueOf(ut.getTypeId())))
					{
						return ut.getTypeName();
					}
				}
			}
			else
			{
				//对于/1/2/这样的格式
				for(UserType ut : ls)
				{
					if(typeId.contains("/" + String.valueOf(ut.getTypeId()) + "/"))
					{
						typeId = typeId.replaceAll("/" + String.valueOf(ut.getTypeId()) + "/", "/" + ut.getTypeName() + "/");
					}
				}
				return typeId;				
			}
			return NOTHING;			
		}
	}
	
	
}
