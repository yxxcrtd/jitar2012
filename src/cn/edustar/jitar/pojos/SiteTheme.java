package cn.edustar.jitar.pojos;

/**
 * SSiteTheme entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SiteTheme implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6246437140074053656L;
	private Integer siteThemeId;
	private String title;
	private String folder;
	private int status;

	// Constructors

	/** default constructor */
	public SiteTheme() {
	}

	/** full constructor */
	public SiteTheme(Integer siteThemeId, String title, String folder,
			Byte status) {
		this.siteThemeId = siteThemeId;
		this.title = title;
		this.folder = folder;
		this.status = status;
	}

	// Property accessors

	public Integer getSiteThemeId() {
		return this.siteThemeId;
	}

	public void setSiteThemeId(Integer siteThemeId) {
		this.siteThemeId = siteThemeId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFolder() {
		return this.folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}