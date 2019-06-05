package cn.edustar.jitar.module;

/**
 * 模块基类
 * 
 *
 */
public abstract class AbstractModule implements Module {

	/** 该模块是否启用 */
	private boolean enabled = true;

	/** 模块名 */
	private String moduleName = "未命名";

	/** 模块标题 */
	private String moduleTitle = "未命名";

	/**
	 * 构造
	 * 
	 * @param moduleName
	 * @param moduleTitle
	 */
	protected AbstractModule(String moduleName, String moduleTitle) {
		this.moduleName = moduleName;
		this.moduleTitle = moduleTitle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.module.Module#getModuleName()
	 */
	public final String getModuleName() {
		return this.moduleName;
	}

	/**
	 * 设置模块名
	 * 
	 * @param moduleName
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.module.Module#getModuleTitle()
	 */
	public final String getModuleTitle() {
		return this.moduleTitle;
	}

	/**
	 * 设置模块标题
	 * 
	 * @param moduleTitle
	 */
	public void setModuleTitle(String moduleTitle) {
		this.moduleTitle = moduleTitle;
	}

	/*
	 * 该模块是否启用
	 * 
	 * @see cn.edustar.jitar.module.Module#getEnabled()
	 */
	public boolean getEnabled() {
		return this.enabled;
	}

	/**
	 * 该模块是否启用
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
