package cn.edustar.jitar.pojos;

import java.util.Arrays;
import java.util.List;

/**
 * SSubjectWebpart entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SubjectWebpart implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3486878281828725330L;
	private int subjectWebpartId;
	private String moduleName;
	private String displayName;
	private Boolean systemModule;
	private Integer subjectId;
	private Integer webpartZone;
	private Integer rowIndex;
	private String content;
	private Boolean visible;
	private Integer sysCateId;
	private Integer showCount;
	
	/** 0：自定义 1：列表 2：图片 3：列表+图片 */
	private int showType;
	
	/** 0：自定义 1：系统分类 2：自定义分类 */
	private int partType;

	/**
	 * 位置区域常数
	 */
	public static final int WEBPART_TOP = 1;
	public static final int WEBPART_BOTTOM = 2;
	public static final int WEBPART_LEFT = 3;
	public static final int WEBPART_MIDDLE = 4;
	public static final int WEBPART_RIGHT = 5;
	
	
	/**
	 * 系统模块名称 ,词顺序决定在界面上的位置，详细见 add_old_subject_page.py , AdminSubjectAction
	 */
	public static final String[] MODULE_NAME = {"图片新闻","教研员工作室","名师工作室","学科带头人","友情链接","学科统计","学科文章","学科资源","工作室","协作组","教研视频","提问与解答","学科动态","学科公告","调查投票","教研活动","教研专题","话题讨论"};
	public static final List<String> SUBJECT_NAVNAME = Arrays.asList("总站首页", "学科首页", "文章", "资源", "工作室", "协作组", "集备", "视频", "活动", "专题");
	public static final List<String> SUBJECT_NAVURL = Arrays.asList("py/subjectHome.action", "", "article/", "resource/", "blog/", "groups/", "preparecourse/", "video/", "activity/", "specialsubject/");
	public static final List<String> SUBJECT_NAVHIGHLIGHT = Arrays.asList("index", "subject", "article", "resource", "blog", "groups", "preparecourse", "video", "activity", "specialsubject");


	
	public static final String WEBPART_MODULENAME_ARTICLE = "学科文章";
	public static final String WEBPART_MODULENAME_RESOURCE = "学科资源";
	public static final String WEBPART_MODULENAME_JIAOYANSHIPIN = "教研视频";
	public static final String WEBPART_MODULENAME_PICNEWS = "图片新闻";
	public static final String WEBPART_MODULENAME_NEWS = "学科动态";
	public static final String WEBPART_MODULENAME_NOTICE = "学科公告";
	public static final String WEBPART_MODULENAME_LINKS = "友情链接";
	public static final String WEBPART_MODULENAME_STATISTICS = "学科统计";
	public static final String WEBPART_MODULENAME_VOTE = "调查投票";
	public static final String WEBPART_MODULENAME_JIAOYANYUAN = "教研员工作室";
	public static final String WEBPART_MODULENAME_MINGSHI = "名师工作室";
	public static final String WEBPART_NODULENAME_DAITOUREN = "学科带头人";
	public static final String WEBPART_MODULENAME_GONGZUOSHI = "工作室";
	public static final String WEBPART_MODULENAME_XIEZUOZU = "协作组";
	public static final String WEBPART_MODULENAME_JIAOYANHUODONG = "教研活动";
	public static final String WEBPART_MODULENAME_JIAOYANZHUANTI = "教研专题";
	public static final String WEBPART_MODULENAME_WENDA = "提问与解答";
	public static final String WEBPART_MODULENAME_TOPIC = "话题讨论";
	
	
	// Constructors

	/** default constructor */
	public SubjectWebpart() {
	}

	// Property accessors

	public int getSubjectWebpartId() {
		return this.subjectWebpartId;
	}

	public void setSubjectWebpartId(int subjectWebpartId) {
		this.subjectWebpartId = subjectWebpartId;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Boolean getSystemModule() {
		return this.systemModule;
	}

	public void setSystemModule(Boolean systemModule) {
		this.systemModule = systemModule;
	}

	public Integer getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getWebpartZone() {
		return this.webpartZone;
	}

	public void setWebpartZone(Integer webpartZone) {
		this.webpartZone = webpartZone;
	}

	public Integer getRowIndex() {
		return this.rowIndex;
	}

	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Integer getSysCateId() {
		return sysCateId;
	}

	public void setSysCateId(Integer sysCateId) {
		this.sysCateId = sysCateId;
	}

	public Integer getShowCount() {
		return showCount;
	}

	public void setShowCount(Integer showCount) {
		this.showCount = showCount;
	}

	public int getShowType() {
		return showType;
	}

	public void setShowType(int showType) {
		this.showType = showType;
	}

	public int getPartType() {
		return partType;
	}

	public void setPartType(int partType) {
		this.partType = partType;
	}
	
}