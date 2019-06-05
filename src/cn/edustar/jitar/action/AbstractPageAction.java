package cn.edustar.jitar.action;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.edustar.jitar.data.DataBean;
import cn.edustar.jitar.data.DataHost;
import cn.edustar.jitar.util.ParamUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 提供给 index.py, blogs.py, groups.py 等做为基类
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractPageAction extends AbstractBasePageAction implements DataHost {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 539420397619467378L;
	
	/** 数据集合, 一般是 List */
	protected Collection data_list = new ArrayList();

	/** 数据集合, 一般是 List */
	public Collection getDataList() {
		return this.data_list;
	}

	/** 数据集合, 一般是 List. */
	public void setDataList(Collection data_list) {
		this.data_list = data_list;
	}

	/**
	 * 添加一个数据 bean
	 * 
	 * @param bean
	 */
	public void addBean(Object bean) {
		data_list.add(bean);
	}

	/**
	 * 为应用准备数据环境
	 */
	public void prepareData() {
		if (data_list == null)
			return;
		
		for (Object data : data_list) {
			if (data == null || !(data instanceof DataBean))
				continue;
			((DataBean) data).prepareData(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.data.DataHost#setData(java.lang.String, java.lang.Object)
	 */
	public void setData(String name, Object value) {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute(name, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.data.DataHost#getContextObject(java.lang.String)
	 */
	public Object getContextObject(String name) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.data.DataHost#getParameters()
	 */
	public ParamUtil getParameters() {
		HttpServletRequest request = ServletActionContext.getRequest();
		return new ParamUtil(request.getParameterMap());
	}
	
}
