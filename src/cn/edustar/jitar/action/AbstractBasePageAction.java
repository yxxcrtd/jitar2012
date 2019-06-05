package cn.edustar.jitar.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import net.zdsoft.passport.service.client.PassportClient;

import org.springframework.web.context.ContextLoader;

import com.alibaba.fastjson.JSONObject;

import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Config;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.MetaSubject;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 前台页面action的基类。
 * <p>
 * 为了使用简单，里面封装了常用的一些参数。
 * </p>
 * 
 * @author mxh
 * 
 */
public abstract class AbstractBasePageAction extends AbstractServletAction {
   
    private static final long serialVersionUID = -6943918379853200870L;

    /** 参数实用类 */
    protected ParamUtil params;
    protected ParamUtil param_util;
    /** 机构服务 */
    protected UnitService unitService;

    /** 学科服务 */
    protected SubjectService subjectService;
    
    public CacheService cacheService;

    /** 根机构对象 */
    protected Unit rootUnit;

    /** 用户的id */
    protected Integer userId = null;

    /** 学段Id */
    protected Integer gradeId = null;

    /** 结构id */
    protected Integer unitId = null;

    /** 学科Id */
    protected Integer subjectId = null;

    /** 公司的统一用户系统的服务器地址 */
    protected String SSOServerURL = null;
    protected String SSOServerURL1 = null;   //外网地址
    protected String SSOServerURL2 = null;	 //内网地址

    /** 公司的统一用户系统的服务器登录地址 */
    protected String SSOServerLoginURL = null;

    /** 登陆成功后返回的地址 * */
    protected String ReciverAction = null;

    /** 登陆失败后返回的地址 * */
    protected String ClientLoginUrl = null;

    /** 系统在用户管理中的编码 */
    protected String ClientCode = null;


    /**
     * 执行action的入口
     */
    @Override
    public final String execute() throws Exception {
        String result = NONE;
        try {
            unitService = ContextLoader.getCurrentWebApplicationContext().getBean("unitService", UnitService.class);
            subjectService = ContextLoader.getCurrentWebApplicationContext().getBean("subjectService", SubjectService.class);
            cacheService = ContextLoader.getCurrentWebApplicationContext().getBean("cacheService", CacheService.class);
            rootUnit = unitService.getRootUnit();
            if (null == rootUnit) {
                response.sendRedirect(request.getServletContext().getContextPath() + "/addRootUnit.action");
                return NONE;
            }

            getSSO();
            checkZDSOFTLogin();
            
            this.params = new ParamUtil(getActionContext().getParameters());
            this.param_util = this.params;
            String cmd = params.safeGetStringParam("cmd");
            result = beforeExecute();
            if (result != null) {
                return result;
            }
            // 添加一些常用的查询参数。
            userId = params.getIntParamZeroAsNull("userId");
            gradeId = params.getIntParamZeroAsNull("gradeId");
            unitId = params.getIntParamZeroAsNull("unitId");
            subjectId = params.getIntParamZeroAsNull("subjectId");
            if (null != userId) {
                request.setAttribute("userId", userId);
            }
            if (null != gradeId) {
                request.setAttribute("gradeId", gradeId);
            }
            if (null != unitId) {
                request.setAttribute("unitId", unitId);
            }
            if (null != subjectId) {
                request.setAttribute("subjectId", subjectId);
            }
            request.setAttribute("rootUnit", rootUnit);
            return execute(cmd);
        } catch (Exception e) {
            throw e;
        } finally {
            if (hasActionLinks() == false) {
                addDefaultReturnActionLink();
            }
        }
    }

    private void checkZDSOFTLogin(){
        //# 是否是浙大统一用户
    	String passportURL="";
        Integer passportServerId = 0;
        String passportVerifyKey = "";
        try{
        if(null!=PassportClient.getInstance()){
        	if(null!=PassportClient.getInstance().getPassportURL()){
        		passportURL = PassportClient.getInstance().getPassportURL();
        	}
    	}
        if(passportURL == null){passportURL = "";}
        if(passportURL.equals("http://")){passportURL = "";};
        if(null!=PassportClient.getInstance()){
        	if(null!=PassportClient.getInstance().getVerifyKey()){
	        	passportServerId = PassportClient.getInstance().getServerId();
	        	passportVerifyKey = PassportClient.getInstance().getVerifyKey();
        	}
        }
        }catch(Exception ex){}
        request.setAttribute("passportURL", passportURL);
        request.setAttribute("passportServerId", passportServerId);
        request.setAttribute("passportVerifyKey", passportVerifyKey);    
    }    

    private final void getSSO() {
        ServletContext servletContext = this.getServletContext();
        if (null == servletContext) {
            servletContext = request.getServletContext();
        }
        SSOServerURL = servletContext.getInitParameter("SSOServerURL");
        if (null != SSOServerURL) {
            SSOServerURL1 = SSOServerURL;
            SSOServerURL2 = SSOServerURL;
            if(SSOServerURL.indexOf(";")>-1){
            	String[] arrayUrl = SSOServerURL.split("\\;");
            	SSOServerURL1 = arrayUrl[0];
            	SSOServerURL2 = arrayUrl[1];
            }         	
            if (SSOServerURL1.endsWith("/")) {
            	SSOServerURL1 = SSOServerURL1.substring(0, SSOServerURL1.length() - 1);
            }
            if (SSOServerURL2.endsWith("/")) {
            	SSOServerURL2 = SSOServerURL2.substring(0, SSOServerURL2.length() - 1);
            }
            if (SSOServerURL.endsWith("/")) {
            	SSOServerURL = SSOServerURL.substring(0, SSOServerURL.length() - 1);
            }            
            SSOServerLoginURL = SSOServerURL1 + servletContext.getFilterRegistration("ssoFilter").getInitParameter("serverUserLoginUrl");

            String clientLoginUrl = servletContext.getFilterRegistration("ssoFilter").getInitParameter("clientLoginUrl");
            String reciverAction = servletContext.getFilterRegistration("ssoFilter").getInitParameter("reciverAction");
            String url = CommonUtil.getSiteUrl(request);
            url = url.substring(0, url.length() - 1);
            ReciverAction = url + reciverAction;
            ClientLoginUrl = url + clientLoginUrl;
        }
        if (null != servletContext.getInitParameter("clientCode")) {
            ClientCode = servletContext.getInitParameter("clientCode");
        }

        request.setAttribute("SSOServerURL", SSOServerURL);
        request.setAttribute("SSOServerURL1", SSOServerURL1);
        request.setAttribute("SSOServerURL2", SSOServerURL2);
        request.setAttribute("ClientCode", ClientCode);
        request.setAttribute("SSOServerLoginURL", SSOServerLoginURL);
        request.setAttribute("ReciverAction", ReciverAction);
        request.setAttribute("ClientLoginUrl", ClientLoginUrl);
        request.setAttribute("FullUrl", CommonUtil.getSiteUrl(request));
    }
    
    /**
     * 返回常用的学段+学科的树html
     * 
     * @param cache
     * @return
     */
    public String getGradeSubjectTreeHtml(CacheService cache) {
        String outHtml = null;
        if (outHtml == null || outHtml.trim().equals("")) {
            String cache_key = "meta_subject_list";
            List<MetaSubject> meta_subject_list = null;
            if (cache != null) {
                meta_subject_list = null != cache.get(cache_key) ? (List<MetaSubject>) cache.get(cache_key) : new ArrayList<MetaSubject>();
            }
            /*
             * if (subject_list == null || subject_list.size() == 0) { //
             * subject_list = subjectService.getMetaSubjectList();//TODO 这里 //
             * 暂时注释掉 subject_list = subjectService.getMetaSubjectList();
             * cache.put(cache_key, null); }
             */
            outHtml = "\r\n";
            meta_subject_list = subjectService.getMetaSubjectList();
            for (MetaSubject s : meta_subject_list) {
                int msid = s.getMsubjId();
                outHtml = outHtml
                        + "<h4 title='" + s.getMsubjName() + "' class=\"leftNavF\"><span class=\"leftNavIcon\"></span><span class=\"folder\"></span><a href=\"javascript:void(0)\" metaGradeId=\"0\" metaSubjectId=\""
                        + s.getMsubjId() + "\" metaGradeName=\"\"  metaSubjectName=\"" + s.getMsubjName() + "\"><nobr>" + s.getMsubjName()
                        + "</nobr></a></h4>\r\n";
                cache_key = "gradeIdList" + msid;
                Object gradeIdList = null;
                /*
                 * if (gradeIdList == null) { gradeIdList =
                 * subjectService.getMetaGradeListByMetaSubjectId(msid);
                 * cache.put(cache_key, null); }
                 */
                gradeIdList = subjectService.getMetaGradeListByMetaSubjectId(msid);
                outHtml += "<ul class=\"leftNavS\">";
                for (Grade gid : (List<Grade>) gradeIdList) {

                    cache_key = "gradeLevelList" + gid.getGradeId();
                    List<Grade> gradeLevelList = null;
                    /*
                     * if (gradeLevelList == null) { gradeLevelList =
                     * subjectService
                     * .getGradeLevelListByGradeId(gid.getGradeId());
                     * cache.put(cache_key, gradeLevelList); }
                     */
                    gradeLevelList = subjectService.getGradeLevelListByGradeId(gid.getGradeId());
                    if (gradeLevelList != null && gradeLevelList.size() > 0) {
                        outHtml += "<li title='" + gid.getGradeName() + "'><span class=\"leftNavIcon\"></span><span class=\"liFolder\"></span><a href=\"javascript:void(0)\" class=\"leftNavText\" metaGradeId=\""
                                + gid.getGradeId()
                                + "\" metaSubjectId=\""
                                + s.getMsubjId()
                                + "\" metaGradeName=\""
                                + gid.getGradeName()
                                + "\"  metaSubjectName=\"" + s.getMsubjName() + "\">" + gid.getGradeName() + "</a>\r\n";
                    } else {
                        outHtml += "<li title='" + gid.getGradeName() + "'><span class=\"liFolder\"></span><a href=\"javascript:void(0)\" class=\"leftNavText\" metaGradeId=\""
                                + gid.getGradeId() + "\" metaSubjectId=\"" + s.getMsubjId() + "\" metaGradeName=\"" + gid.getGradeName()
                                + "\"  metaSubjectName=\"" + s.getMsubjName() + "\"><nobr>" + gid.getGradeName() + "</nobr></a>\r\n";
                    }

                    if (gradeLevelList != null && gradeLevelList.size() > 0) {
                        outHtml += "<ul>\r\n";
                        for (Grade glevel : (List<Grade>) gradeLevelList) {
                            outHtml += "<li title='" + glevel.getGradeName() + "'><span class=\"liFolder\"></span><a href=\"javascript:void(0)\" class=\"leftNavText\" metaGradeId=\""
                                    + glevel.getGradeId() + "\" metaSubjectId=\"" + s.getMsubjId() + "\" metaGradeName=\"" + glevel.getGradeName()
                                    + "\"  metaSubjectName=\"" + s.getMsubjName() + "\"><nobr>" + glevel.getGradeName() + "</nobr></a></li>\r\n";
                        }
                        outHtml += "</ul>";
                    }
                    outHtml += "</li>\r\n";

                }
                outHtml += "</ul>\r\n";
            }
        }
        return outHtml;
    }

    public String makeJsonReturn(int code, String message) {

        return makeJsonReturn(code, message, null);
    }
    public String makeJsonReturn(int code, String message, Object data) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("message", message);
        if (null != data) {
            json.put("data", data);
        }
        String returnString = json.toJSONString();
        json = null;
        return returnString;
    }

    /**
     * 举报类型，根据配置得到
     */
    public void getReportTypeList() {
        String reportContent = "涉及色情或暴力,敏感信息,人身攻击,其它";
        ConfigService configService = ContextLoader.getCurrentWebApplicationContext().getBean("configService", ConfigService.class);
        Config reportTypeConfig = configService.getConfigByItemTypeAndName("jitar", "reportType");
        if (reportTypeConfig == null) {
            reportTypeConfig = new Config();
            reportTypeConfig.setItemType("jitar");
            reportTypeConfig.setName("reportType");
            reportTypeConfig.setValue(reportContent);
            reportTypeConfig.setType("string");
            reportTypeConfig.setDefval(reportContent);
            reportTypeConfig.setTitle("举报类型");
            reportTypeConfig.setDescription("举报类型");
            configService.createConfig(reportTypeConfig);
            configService.reloadConfig();
        } else {
            reportContent = reportTypeConfig.getValue();
            if(reportContent==null || reportContent.trim().length() == 0){
                reportContent = "涉及色情或暴力,敏感信息,人身攻击,其它";
            }
        }
        request.setAttribute("reportTypeList", reportTypeConfig.getValue().split(","));
    }

    /**
     * 把元学科信息添加到请求对象中。
     */
    public void putSubjectList() {
        request.setAttribute("subject_list", subjectService.getSubjectList());
    }

    /**
     * 把元学段信息添加到请求对象中。
     */
    public void putGradeList() {
        List<Grade> list = this.subjectService.getGradeList();
        request.setAttribute("grade_list", list);
        request.setAttribute("gradelist", list);
    }
    
    /**
     * 缓存浏览次数。一个会话为一个缓存单位。
     * @param objectType
     * @param objId
     * @return
     */
    public int saveObjectCount(ObjectType objectType, int objId){
        this.cacheService = ContextLoader.getCurrentWebApplicationContext().getBean("cacheService", CacheService.class);
        //这个时间必须大于session的超时时间
        int expireTime = 3 * 24 * 60 * 60;
        String prex = objectType.getTypeName();
        //当前用户的标识作为Key，来记录用户访问的文章Id，因为一个用户访问的文章毕竟有限，一次能看10篇文章就算是很厉害了吧。        
        String sessionCacheKey = prex + "_" + this.getSession().getId();
        
        //先得到该会话的访问的对象              
        String sessionObjectId = (String)this.cacheService.get(sessionCacheKey);
        if(sessionObjectId == null || sessionObjectId.length() == 0){
            sessionObjectId = "|" + objId + "|";
            this.cacheService.put(sessionCacheKey, sessionObjectId, expireTime);
        }
        else{
            //先记录当前用户的访问的对象Id
            if(!sessionObjectId.contains("|" + objId + "|")){
                sessionObjectId += objId + "|";
                this.cacheService.put(sessionCacheKey, sessionObjectId, expireTime);
            }
        }
        
        String objKey = objectType.getTypeId() + "_" + objId;
        String objCount =  (String)this.cacheService.get(objKey);
        if(objCount == null || objCount.length() == 0 || !CommonUtil.isInteger(objCount)){
            objCount = "1";
        }
        else{
            objCount =  (Integer.valueOf(objCount).intValue() + 1) + "";
        }
        this.cacheService.put(objKey, objCount, expireTime);
        /*System.out.println("保存时的：key = " + sessionCacheKey + " , value = " + sessionObjectId);
        System.out.println("保存时的 objKey ：key = " + objKey + " , value = " + objCount);
        System.out.println("保存时的expire ：" + this.cacheService.getExpireTime());        
        System.out.println("保存时的 cacheService 类型：" + this.cacheService.getClass().getCanonicalName());*/
        return Integer.valueOf(objCount).intValue();   
    }


    protected String beforeExecute() throws Exception {
        return null;
    }

    protected abstract String execute(String cmd) throws Exception;

    public Unit getRootUnit() {
        return rootUnit;
    }
}
