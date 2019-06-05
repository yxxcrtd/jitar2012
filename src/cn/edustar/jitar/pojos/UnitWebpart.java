package cn.edustar.jitar.pojos;

/**
 * UnitWebpart entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UnitWebpart implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5433269536118960579L;
	private int unitWebpartId;
	private String moduleName;
	private String displayName;
	private boolean systemModule = true;
	private int unitId;
	private int webpartZone;
	private int rowIndex;
	private String content;
	private boolean visible;
	private int partType;
	private int showType;
	private int showCount = 6;
	private Integer cateId;

	/**
	 * 位置区域常数
	 */
	public static final int WEBPART_TOP = 1;
	public static final int WEBPART_BOTTOM = 2;
	public static final int WEBPART_LEFT = 3;
	public static final int WEBPART_MIDDLE = 4;
	public static final int WEBPART_RIGHT = 5;
	
	/**
	 * 模块名称
	 */
	public static final String WEBPART_MODULENAME_ARTICLE = "机构文章";
	public static final String WEBPART_MODULENAME_RESOURCE = "机构资源";
	public static final String WEBPART_MODULENAME_PHOTO = "机构图片";
	public static final String WEBPART_MODULENAME_VIDEO = "机构视频";
	public static final String WEBPART_MODULENAME_PICNEWS = "图片新闻";
	public static final String WEBPART_MODULENAME_NEWESTNEWS = "最新动态";
	public static final String WEBPART_MODULENAME_UNITNOTICE = "最新公告";
	public static final String WEBPART_MODULENAME_LINKS = "友情链接";
	public static final String WEBPART_MODULENAME_STATISTICS = "统计信息";
	public static final String WEBPART_MODULENAME_VOTE = "调查投票";
	public static final String WEBPART_MODULENAME_UNITSUBJECT = "机构学科";
	public static final String WEBPART_MODULENAME_UNITGROUP = "机构协作组";
	public static final String WEBPART_MODULENAME_UNITPREPARECOURSE = "机构集备";
	
	
	// Constructors

	/** default constructor */
	public UnitWebpart() {
	}

	// Property accessors

	public int getUnitWebpartId() {
		return this.unitWebpartId;
	}

	public void setUnitWebpartId(int unitWebpartId) {
		this.unitWebpartId = unitWebpartId;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean getSystemModule() {
		return this.systemModule;
	}

	public void setSystemModule(boolean systemModule) {
		this.systemModule = systemModule;
	}

	public int getUnitId() {
		return this.unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public int getWebpartZone() {
		return this.webpartZone;
	}

	public void setWebpartZone(int webpartZone) {
		this.webpartZone = webpartZone;
	}

	public int getRowIndex() {
		return this.rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getPartType() {
		return partType;
	}

	public void setPartType(int partType) {
		this.partType = partType;
	}

	public int getShowType() {
		return showType;
	}

	public void setShowType(int showType) {
		this.showType = showType;
	}

	public int getShowCount() {
		return showCount;
	}

	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

}