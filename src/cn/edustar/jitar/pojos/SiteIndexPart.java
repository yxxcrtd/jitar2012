package cn.edustar.jitar.pojos;

/**
 * SiteIndexPart entity. @author mxh
 */

public class SiteIndexPart implements java.io.Serializable {

    // Fields
    /**
	 * 
	 */
    private static final long serialVersionUID = -6014529006957198085L;
    private int siteIndexPartId;
    private String moduleName;
    private int moduleZone;
    private int moduleDisplay;
    private int moduleHeight = 60;
    private int moduleWidth = 300;
    private int textLength = 12;
    private int multiColumn = 1;
    private int moduleOrder;
    private int showBorder;
    private String content;
    private Integer sysCateId;
    private Integer showCount;
    private int partType;
    private int showType;
    // Constructors

    /** default constructor */
    public SiteIndexPart() {
    }

    public int getSiteIndexPartId() {
        return siteIndexPartId;
    }

    public void setSiteIndexPartId(int siteIndexPartId) {
        this.siteIndexPartId = siteIndexPartId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getModuleZone() {
        return moduleZone;
    }

    public void setModuleZone(int moduleZone) {
        this.moduleZone = moduleZone;
    }

    public int getModuleDisplay() {
        return moduleDisplay;
    }

    public void setModuleDisplay(int moduleDisplay) {
        this.moduleDisplay = moduleDisplay;
    }

    public int getModuleHeight() {
        return moduleHeight;
    }

    public void setModuleHeight(int moduleHeight) {
        this.moduleHeight = moduleHeight;
    }

    public int getModuleWidth() {
        return moduleWidth;
    }

    public void setModuleWidth(int moduleWidth) {
        this.moduleWidth = moduleWidth;
    }

    public int getTextLength() {
        return textLength;
    }

    public void setTextLength(int textLength) {
        this.textLength = textLength;
    }

    public int getMultiColumn() {
        return multiColumn;
    }

    public void setMultiColumn(int multiColumn) {
        this.multiColumn = multiColumn;
    }

    public int getModuleOrder() {
        return moduleOrder;
    }

    public void setModuleOrder(int moduleOrder) {
        this.moduleOrder = moduleOrder;
    }

    public int getShowBorder() {
        return showBorder;
    }

    public void setShowBorder(int showBorder) {
        this.showBorder = showBorder;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

}