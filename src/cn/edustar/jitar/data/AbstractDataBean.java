package cn.edustar.jitar.data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 提供给 DataBean 的基类, 带有 enabled 设置开关功能.
 *
 *
 */
public abstract class AbstractDataBean implements DataBean {
	/** 文章记录器 */
	public static final Log logger = LogFactory.getLog(AbstractDataBean.class);
	
	/** bean 执行的数据环境 */
	private DataHost host;
	
	/** 使用使用, 缺省 = true */
	private boolean enabled = true;
	
	/**
	 * 构造.
	 */
	public AbstractDataBean() {
		this.host = new DataHostImpl();
	}
	
	/**
	 * 得到缺省环境对象.
	 * @return
	 */
	public DataHost getDataHost() {
		return this.host;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.HostAwared#setDataHost(cn.edustar.jitar.data.DataHost)
	 */
	public void setDataHost(DataHost host) {
		this.host = host;
	}
	
	/** 使用使用, 缺省 = true */
	public boolean getEnabled() {
		return this.enabled;
	}
	
	/** 使用使用, 缺省 = true */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/** 放到环境中的变量名字, 缺省 = 'data', 派生类可以根据自己需要修改缺省名字 */
	private String varName = "data";

	/** 放到环境中的变量名字, 缺省 = 'data', 派生类可以根据自己需要修改缺省名字 */
	public String getVarName() {
		return this.varName;
	}
	
	/** 放到环境中的变量名字, 缺省 = 'data', 派生类可以根据自己需要修改缺省名字 */
	public void setVarName(String varName) {
		this.varName = varName;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.DataBean#open()
	 */
	public final Object open() {
		if (enabled == false) return null;
		
		return doOpen();
	}
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.DataBean#prepareData(cn.edustar.jitar.data.DataHost)
	 */
	public final void prepareData(DataHost host) {
		if (enabled == false) return;
		
		doPrepareData(host);
	}
	
	/**
	 * 派生类用于实际实现.
	 * @param host
	 */
	protected abstract void doPrepareData(DataHost host);

	/**
	 * 派生类用于实际实现获取数据.
	 */
	protected Object doOpen() {
		return null;
	}
}
