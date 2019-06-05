package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.MetaSubject;
import cn.edustar.jitar.pojos.SiteNav;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.SubjectWebpart;
import cn.edustar.jitar.service.SiteNavService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 学科管理.
 * 
 *
 */
@SuppressWarnings("serial")
public class AdminSubjectAction extends ManageBaseAction {
	
	/** 学科服务接口 */
	private SubjectService subj_svc;

	/** 学科服务接口的set方法 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}
	
	private SiteNavService siteNavService;
	
	public void setSiteNavService(SiteNavService siteNavService) {
		this.siteNavService = siteNavService;
	}

	/** 学科ID */
	private int subjectId;
	
	/** 学科名称 */
	private String subjectName;
	
	/** 所属元学科标识 */
	private int msubjId;
	/** 所属元学段标识 */
	private int gradeId;
	
	/** 学科代码 */
	private String subjectCode;
	
	/** 所属元学科 */
	private MetaSubject metaSubject;
	
	/** 所属元学段 */
	private Grade metaGrade;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		// 验证权限.
		if (this.canAdmin() == false) {
			this.addActionError("没有管理学科的权限.");
			return ERROR;
		}		
		
		if (cmd == null || cmd.length() == 0)
			cmd = "list";

		if ("list".equals(cmd))
			return list();
		else if ("add".equals(cmd))
			return add();
		else if ("edit".equals(cmd))
			return edit();
		else if ("save".equals(cmd))
			return save();
		else if ("delete".equals(cmd))
			return delete();
		else if ("order".equals(cmd))
			return order();
		else if ("link".equals(cmd))
			return link();

		return super.unknownCommand(cmd);
	}

	/**
	 * 所有学科列表
	 * 
	 * @return
	 */
	private String list() {
		List<Subject> subject_list = subj_svc.getSubjectList();
		setRequestAttribute("subject_list", subject_list);
		return LIST_SUCCESS;
	}

	/**
	 * 保存顺序
	 * @return
	 */
	private String order()
	{
		List<Integer> subjID = param_util.safeGetIntValues("subjID");
		for(Integer id : subjID)
		{
			int sid = param_util.safeGetIntParam("order_" + id);
			Subject subject = subj_svc.getSubjectById(id);
			if(subject != null){
				if(subject.getOrderNum() != sid)
				{
					subject.setOrderNum(sid);
					subj_svc.saveOrUpdateSubject(subject);
				}
			}
		}
		return list();
	}
	
	/**
	 * 自定义链接的保存
	 * @return
	 */
	private String link()
	{
		List<Integer> subjID = param_util.safeGetIntValues("subjID");
		for(Integer id : subjID)
		{
			String shortcutTarget = param_util.safeGetStringParam("shortcutTarget_" + id,null);
			if(shortcutTarget.equals("")) shortcutTarget = null;
			Subject subject = subj_svc.getSubjectById(id);
			if(subject != null){				
				subject.setShortcutTarget(shortcutTarget);
				subj_svc.saveOrUpdateSubject(subject);		
			}
		}
		return list();
	}
	/**
	 * 添加一个新学科
	 * 
	 * @return
	 */
	private String add() {
		Subject subject = new Subject();
	 /*subject.setSubjectName("请输入学科名");
		subject.setSubjectCode("");*/

		setRequestAttribute("subject", subject);
		
		subj_svc.clearCacheData();
		List<MetaSubject> msubj_list = subj_svc.getMetaSubjectList();
		List<Grade> grade_list = subj_svc.getGradeList();
		
		setRequestAttribute("msubj_list", msubj_list);
		setRequestAttribute("grade_list", grade_list);
		setRequestAttribute("__referer", getRefererHeader());
		
		
		return "Add_Or_Edit";
	}

	/**
	 * 编辑/修改学科(元学科,元学段)
	 * 
	 * @return
	 */
	private String edit() {
		int subjectId = param_util.getIntParam("subjectId");
		Subject subject = subj_svc.getSubjectById(subjectId);
		if (subject == null) {
			addActionError("未找到指定标识的学科");
			return ERROR;
		}
		
		setRequestAttribute("subject", subject);
		setRequestAttribute("__referer", getRefererHeader());

		List<MetaSubject> msubj_list = subj_svc.getMetaSubjectList();
		List<Grade> grade_list = subj_svc.getGradeList();
		
		setRequestAttribute("msubj_list", msubj_list);
		setRequestAttribute("grade_list", grade_list);
		
		return "Add_Or_Edit";
	}

	/**
	 * 保存或修改学科,同时满足'学科名称'和'学科代码'不能相同
	 * 
	 * @return
	 */
	private String save() {
		boolean isNew = false;
		String shortcutTarget = this.param_util.safeGetStringParam("shortcutTarget", null);
		// 获得学科对象.
		if (collectSubjectObject() == false)
			return ERROR;
		
		if(msubjId == 0) {
			this.addActionError("所属元学科标识不能为空！!!!");
			return add();
		}
		if(gradeId == 0) {
			this.addActionError("所属元学段标识不能为空！!!!");
			return add();
		}
		

		if (subjectName == null || "".equalsIgnoreCase(subjectName) || subjectName.length() == 0){
			this.addActionError("学科名称不能为空！");
			return add();
		}

		if (subjectCode == null || "".equalsIgnoreCase(subjectCode) || subjectCode.length() == 0){
			this.addActionError("学科代码不能为空！");
			return add();
		}
		
		if(CommonUtil.isValidName(subjectCode) == false)
		{
			this.addActionError("学科代码必须是数字或者字母！");
			return add();
		}
		
		if (subjectId == 0) {
			isNew = true;
			cur_subject.setOrderNum(subj_svc.getSubjectMaxOrderNum() + 1);
			Subject getSubjectByName = subj_svc.getSubjectByName(subjectName);
			if (getSubjectByName != null) {
				String oldSubjectName = getSubjectByName.getSubjectName();
				if (oldSubjectName.equals(subjectName)) {
					this.addActionError("学科名称不能重复！");
					return add();
				}
			}
			Subject getSubjectByCode = subj_svc.getSubjectByCode(subjectCode);
			if (getSubjectByCode != null) {
				String oldSubjectCode = getSubjectByCode.getSubjectCode();
				if (oldSubjectCode.equals(subjectCode)) {
					this.addActionError("学科代码不能重复！");
					return add();
				}
			}
		} 		
		
		
		if (subjectId != 0) {
			Subject getSubjectById = subj_svc.getSubjectById(subjectId);
			cur_subject.setOrderNum(getSubjectById.getOrderNum());
			String oldSubjectName = getSubjectById.getSubjectName();
			String oldSubjectCode = getSubjectById.getSubjectCode();
			
			if (!oldSubjectName.equals(subjectName)) {
				Subject objName = subj_svc.getSubjectByName(subjectName);
				if (objName != null) {
					String newSubjectByName = objName.getSubjectName();
					if (newSubjectByName.equals(subjectName)) {
						this.addActionError("学科名称不能重复！");
						return add();
					}
				}
			}
			if (!oldSubjectCode.equals(subjectCode)) {
				Subject objCode = subj_svc.getSubjectByCode(subjectCode);
				if (objCode != null) {
					String newSubjectByCode = objCode.getSubjectCode();
					if (newSubjectByCode.equals(subjectCode)) {
						this.addActionError("学科代码不能重复！");
						return add();
					}
				}
			}
		}
		
		if(shortcutTarget.equals("")) shortcutTarget = null;
		cur_subject.setShortcutTarget(shortcutTarget);
		// 保存或修改学科
		subj_svc.saveOrUpdateSubject(cur_subject);
		if(isNew)
		{
			//添加主页内容
			addSubjectWebpart(cur_subject);
			addSubjectNav(cur_subject);			
		}
		else
		{
			subj_svc.updateAccessControlSubjectTitle(cur_subject);
		}
		return SUCCESS;
	}
	
	
	/**
	 * 添加主页内容
	 */
	private void addSubjectWebpart(Subject subject)
	{
		if(cur_subject == null) return;
		String[] moduleName = SubjectWebpart.MODULE_NAME;
		for(int i = 0; i < moduleName.length; i++)
		{
			SubjectWebpart subjectWebpart = new SubjectWebpart();
			subjectWebpart.setModuleName(moduleName[i]);
			subjectWebpart.setDisplayName(moduleName[i]);
			subjectWebpart.setRowIndex(i);
			subjectWebpart.setVisible(true);
			subjectWebpart.setSystemModule(true);
			subjectWebpart.setSubjectId(subject.getSubjectId());
         if( i < 6)
         {
            subjectWebpart.setWebpartZone(3);
         }
         else if (i <13)
         {
             subjectWebpart.setWebpartZone(4);
         }
         else
         {
             subjectWebpart.setWebpartZone(5);
         }
         this.subj_svc.saveOrUpdateSubjectWebpart(subjectWebpart);
		}
	}
	
	private void addSubjectNav(Subject subject)
	{
		String[] siteNameArray = {"总站首页", "学科首页", "文章", "资源", "工作室", "协作组", "集备", "视频", "活动", "专题"};
		String[] siteUrlArray = {"py/subjectHome.action", "", "article/", "resource/", "blog/", "groups/", "preparecourse/", "video/", "activity/", "specialsubject/"};
		String[] siteHightlightArray = {"index", "subject", "article", "resource", "blog", "groups", "preparecourse", "video", "activity", "specialsubject"};
	
            for (int i = 0;i<siteNameArray.length;i++)
            {
            	SiteNav siteNav = new SiteNav();
                siteNav.setSiteNavName(siteNameArray[i]);
                siteNav.setIsExternalLink(false);
                siteNav.setSiteNavUrl(siteUrlArray[i]);
                siteNav.setSiteNavIsShow(true);
                siteNav.setSiteNavItemOrder(i);
                siteNav.setCurrentNav(siteHightlightArray[i]);
                siteNav.setOwnerType(2);
                siteNav.setOwnerId(subject.getSubjectId());
                siteNavService.saveOrUpdateSiteNav(siteNav);
            }
	}
	
	/**
	 * 删除一个学科
	 * 
	 * @return
	 */
	private String delete() {
		// 得到学科对象.
		int subjectId = param_util.getIntParam("subjectId");
		Subject subject = subj_svc.getSubjectById(subjectId);
		if (subject == null) {
			this.addActionError("未找到指定标识的学科");
			return ERROR;
		}

		//删除导航
		siteNavService.deleteSiteNavOfOwnerType(SiteNav.SITENAV_OWNERTYPE_SUBJECT, subjectId, null);
		
		// 删除学科.
		subj_svc.deleteSubject(subject);

		return SUCCESS;
	}
	
	/** 当前正在操作的学科对象 */
	private Subject cur_subject;

	private boolean collectSubjectObject() {
		cur_subject = new Subject();
		cur_subject.setSubjectId(param_util.getIntParam("subjectId"));
		
		int msubjId = param_util.safeGetIntParam("msubjId");
		int gradeId = param_util.safeGetIntParam("gradeId");
		
		MetaSubject metaSubject = subj_svc.getMetaSubjectById(msubjId);
		Grade metaGrade = subj_svc.getGrade(gradeId);
		
		/*if(metaSubject == null) {
			this.addActionError("未找到所属元学科！");
			return false;
		}
		if(metaGrade == null) {
			this.addActionError("未找到所属元学段！");
			return false;
		}*/
		
		cur_subject.setMetaSubject(metaSubject);
		cur_subject.setMetaGrade(metaGrade);
		cur_subject.setSubjectName(param_util.safeGetStringParam("subjectName"));
		cur_subject.setSubjectCode(param_util.safeGetStringParam("subjectCode"));		
		cur_subject.setReslibCId(param_util.safeGetIntParam("reslibCId", 0));
		return true;
	}
	
	// Get and set
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public void setMetaSubject(MetaSubject metaSubject) {
		this.metaSubject = metaSubject;
	}
	public MetaSubject getMetaSubject() {
		return metaSubject;
	}
	
	public Grade getMetaGrade() {
		return metaGrade;
	}

	public void setMetaGrade(Grade metaGrade) {
		this.metaGrade = metaGrade;
	}

	public int getMsubjId() {
		return msubjId;
	}

	public void setMsubjId(int msubjId) {
		this.msubjId = msubjId;
	}

	public int getGradeId() {
		return gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	
}
