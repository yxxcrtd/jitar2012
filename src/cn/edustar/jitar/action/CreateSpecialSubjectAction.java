package cn.edustar.jitar.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import cn.edustar.jitar.pojos.SpecialSubject;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.SpecialSubjectService;

/**
 * 
 * 
 * @author renliang
 */
public class CreateSpecialSubjectAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6168373478740439785L;
	private BaseSubject baseSubject = null;
	private SpecialSubjectService specialSubjectService = null;
	private SpecialSubject specialSubject = null;
	private Subject subject = null;

	@Override
	protected String execute(String cmd) throws Exception {
		baseSubject = new BaseSubject();
		subject = baseSubject.getSubject();
		if (super.getLoginUser() == null) {
			return LOGIN;
		}

		if (!baseSubject.isAdmin() && !baseSubject.isContentAdmin()) {
			addActionError("您你没有管理的权限！");
			return ERROR;
		}

		Integer specialSubjectId = params.safeGetIntParam("specialSubjectId");

		if (specialSubjectId > 0) {
			specialSubject = specialSubjectService
					.getSpecialSubject(specialSubjectId);
		}
		if (request.getMethod().trim().equalsIgnoreCase("POST")) {
			return save_post();
		}
		return get_method();
	}

	private String get_method() {
		request.setAttribute("specialSubject", specialSubject);
		return "createspecialsubject";
	}

	private String save_post() {
		String st = params.safeGetStringParam("st");
		if (st.trim().equals("")) {
			addActionError("请输入专题标题。");
			return ERROR;
		}
		String so = params.safeGetStringParam("so");
		String sd = params.safeGetStringParam("sd");
		String se = params.safeGetStringParam("se");
		Date expire_date = new Date();
		try {
			expire_date = new SimpleDateFormat("yyyy-MM-dd").parse(se);
		} catch (Exception e) {
			String actionErrors = "输入的日期格式不正确，应当是: '年年年年-月月-日日' 格式";
			request.setAttribute("actionErrors", actionErrors);
			return ERROR;
		}

		if (specialSubject == null) {
			specialSubject = new SpecialSubject();
			specialSubject.setObjectType("subject");
			specialSubject.setObjectId(subject.getSubjectId());
			specialSubject.setCreateDate(new Date());
			specialSubject.setCreateUserId(super.getLoginUser().getUserId());
		}

		specialSubject.setTitle(st);
		if (!"".equals(so.trim())) {
			specialSubject.setLogo(so);
		} else {
			specialSubject.setLogo(null);
		}
		if (!"".equals(sd.trim())) {
			specialSubject.setDescription(sd);
		} else {
			specialSubject.setDescription(null);
		}
		specialSubject.setExpiresDate(expire_date);

		specialSubjectService.saveOrUpdateSpecialSubject(specialSubject);
		return SUCCESS;
	}

	public void setSpecialSubjectService(SpecialSubjectService specialSubjectService) {
		this.specialSubjectService = specialSubjectService;
	}
	
}
