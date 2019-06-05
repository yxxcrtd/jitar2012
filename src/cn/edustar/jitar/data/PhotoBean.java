package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.service.PhotoQueryParam;
import cn.edustar.jitar.service.PhotoService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 获得相册图片的基本数据对象, 缺省 varName = 'photo_list'.
 *
 *
 */
public class PhotoBean extends AbstractPageDataBean {
	/** 查询参数 */
	private PhotoQueryParam param = new PhotoQueryParam();
	
	/** 图片服务 */
	private PhotoService photo_svc;

	/**
	 * 构造.
	 */
	public PhotoBean() {
		super.setVarName("photo_list");
		this.photo_svc = JitarContext.getCurrentJitarContext().getPhotoService();
	}
	
	/** 图片服务 */
	public void setPhotoService(PhotoService photo_svc) {
		this.photo_svc = photo_svc;
	}

	/** 如果给出 userId, 则表示查询该用户的文章；否则不区分用户。缺省 = null */
	public void setUserId(String userId) {
		param.userId = ParamUtil.safeParseIntegerWithNull(userId, null);
	}
	
	/** 获得文章条数，缺省 = 10。此条件仅当未指定分页参数时生效。 */
	public void setCount(int count) {
		param.count = count;
	}
	
	/** (请使用ORDER_TYPE_XXX 常量) 排序方法，为1按发表时间，为2按点击数，为3按回复数。缺省 = 1 */
	public void setOrderType(int orderType) {
		param.orderType = orderType;
	}
	
	/** 是否精华，缺省为 null 表示所有文章; = true 表示获取精华的; = false 表示获取非精华的 */
	public void setBestType(String bestType) {
		param.bestType = ParamUtil.safeParseBooleanWithNull(bestType, null);
	}
	
	/** 调用多少天内的文章，以天为单位。 缺省 = 0 表示不限定。 */
	public void setDays(int days) {
		param.days = days;
	}
	
	/** 系统分类id，缺省为 null 则表示不限定文章的系统分类。 */
	public void setSysCateId(String sysCateId) {
		param.sysCate = ParamUtil.safeParseIntegerWithNull(sysCateId, null);
	}
	
	/** 用户分类id, 缺省为 null 表示不区分用户分类，通常同时设定 userId 参数。 */
	public void setUserStapleId(String usreStapleId) {
		param.userStaple = ParamUtil.safeParseIntegerWithNull(usreStapleId, null);
	}
	
	/** 查询审核状态，== null 表示不区分，缺省 = 0 查询审核通过的。 */
	public void setAuditState(String auditState) {
		param.auditState = ParamUtil.safeParseIntegerWithNull(auditState, null);
	}
	
	/** 查询隐藏状态，== null 表示不区分，缺省 = 0 查询非隐藏的。 */
	public void setHideState(String hideState) {
		param.hideState = ParamUtil.safeParseIntegerWithNull(hideState, null);
	}
	
	/** 删除状态，== null 表示不区分，缺省 = false 查询未删除的。  */
	public void setDelState(String delState) {
		param.delState = ParamUtil.safeParseBooleanWithNull(delState, null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		PhotoQueryParam param = getQueryParam();
		Pager pager = getUsePager() ? getContextPager(host) : null;
		List<Photo> photo_list = photo_svc.getPhotoListEx(param, pager);
		
		host.setData(getVarName(), photo_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}

	/**
	 * 得到图片查询参数.
	 * @return
	 */
	public PhotoQueryParam getQueryParam() {
		return this.param;
	}
}
