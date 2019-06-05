package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.MetaSubject;
import cn.edustar.jitar.service.SubjectService;

/**
 * 学科管理.(管理员使用)
 *
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 */
@SuppressWarnings("serial")
public class AdminMetaSubjectAction extends ManageBaseAction {
	
	/** 学科服务接口 */
	private SubjectService subj_svc;

	/** 学科服务接口的set方法 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}

	
	/** 元学科ID */
	private int msubjId;
	/** 元学科名称 */
	private String msubjName;
	/** 元学科代码 */
	private String msubjCode;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		// 验证权限.
		if (this.canAdmin() == false) {
			this.addActionError("没有管理元学科的权限.");
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
		
		return super.unknownCommand(cmd);
	}

	/**
	 * 所有元学科列表
	 * 
	 * @return
	 */
	private String list() {
		List<MetaSubject> msub_list =  subj_svc.getMetaSubjectListForceReload();
		setRequestAttribute("msub_list", msub_list);
		return "List_Success";
	}

	/**
	 * 添加一个新元学科
	 * 
	 * @return
	 */
	private String add() {
		MetaSubject msubject = new MetaSubject();
		setRequestAttribute("msubject", msubject);
		setRequestAttribute("__referer", getRefererHeader());
		
		return "Add_Or_Edit";
	}

	/**
	 * 编辑/修改元学科
	 * 
	 * @return
	 */
	private String edit() {
		int msubjId = param_util.getIntParam("msubjId");
		MetaSubject msubject = subj_svc.getMetaSubjectById(msubjId);
		if (msubject == null) {
			addActionError("未找到指定标识的元学科");
			return ERROR;
		}
		
		setRequestAttribute("msubjId", msubjId);
		setRequestAttribute("msubject", msubject);
		setRequestAttribute("__referer", getRefererHeader());
		
		return "Add_Or_Edit";
	}

	/**
	 * 保存或修改元学科,同时满足'元学科名称'和'元学科代码'不能相同
	 * 
	 * @return
	 */
	private String save() {

		// 获得学科对象.
		if (collectSubjectObject() == false)
			return ERROR;
		
		if (msubjName == null || "".equalsIgnoreCase(msubjName) || msubjName.length() == 0){
			this.addActionError("学科名称不能为空！");
			return add();
		}

		if (msubjCode == null || "".equalsIgnoreCase(msubjCode) || msubjCode.length() == 0){
			this.addActionError("学科代码不能为空！");
			return add();
		}
				
		if (msubjId == 0) {
			MetaSubject getMetaSubjectByName = subj_svc.getMetaSubjectByName(msubjName);
			if (getMetaSubjectByName != null) {
				String oldMetaSubjectName = getMetaSubjectByName.getMsubjName();
				if (oldMetaSubjectName.equals(msubjName)) {
					this.addActionError("学科名称不能重复！");
					return add();
				}
			}
			MetaSubject getMetaSubjectByCode = subj_svc.getMetaSubjectByCode(msubjCode);
			if (getMetaSubjectByCode != null) {
				String oldMetaSubjectCode = getMetaSubjectByCode.getMsubjCode();
				if (oldMetaSubjectCode.equals(msubjCode)) {
					this.addActionError("学科代码不能重复！");
					return add();
				}
			}
		} 		
		
		if (msubjId != 0) {
			MetaSubject getMetaSubjectById = subj_svc.getMetaSubjectById(msubjId);
			String oldMetaSubjectName = getMetaSubjectById.getMsubjName();
			String oldMetaSubjectCode = getMetaSubjectById.getMsubjCode();
			
			if (!oldMetaSubjectName.equals(msubjName)) {
				MetaSubject objName = subj_svc.getMetaSubjectByName(msubjName);
				if (objName != null) {
					String newSubjectByName = objName.getMsubjName();
					if (newSubjectByName.equals(msubjName)) {
						this.addActionError("学科名称不能重复！");
						return add();
					}
				}
			}
			if (!oldMetaSubjectCode.equals(msubjCode)) {
				Subject objCode = subj_svc.getSubjectByCode(msubjCode);
				if (objCode != null) {
					String newSubjectByCode = objCode.getSubjectCode();
					if (newSubjectByCode.equals(msubjCode)) {
						this.addActionError("学科代码不能重复！");
						return add();
					}
				}
			}
		}
		
		// 保存或修改元学科
		
		subj_svc.saveOrUpdateMetaSubject(cur_subject);
		return SUCCESS;
	}
	
	/**
	 * 删除一个元学科
	 * 
	 * @return
	 */
	private String delete() {
		// 得到学科对象.
		int msubjId = param_util.getIntParam("msubjId");
		MetaSubject msubject = subj_svc.getMetaSubjectById(msubjId);
		if (msubject == null) {
			this.addActionError("未找到指定标识的学科");
			return ERROR;
		}
		List<Subject> subject_list = subj_svc.getSubjectByMetaSubjectId(msubjId);
		if(subject_list != null) {
			this.addActionError("请先删除与元学科相关联的学科！");
			return ERROR;
		}
		
		// 删除学科.
		subj_svc.deleteMetaSubject(msubject);
		
		return SUCCESS;
	}
	
	/** 当前正在操作的学科对象 */
	private MetaSubject cur_subject;
	
	private boolean collectSubjectObject() {
		cur_subject = new MetaSubject();
		cur_subject.setMsubjId(param_util.safeGetIntParam("msubjId"));
		cur_subject.setMsubjName(param_util.safeGetStringParam("msubjName"));
		cur_subject.setMsubjCode(param_util.safeGetStringParam("msubjCode"));
		cur_subject.setOrderNum(subj_svc.getMetaSubjectMaxOrderNum()+1);
		return true;
	}
	

	//geter and setter方法
	/** 元学科ID */
	public int getMsubjId() {
		return msubjId;
	}
	/** 元学科ID */
	public void setMsubjId(int msubjId) {
		this.msubjId = msubjId;
	}
	/** 元学科名称 */
	public String getMsubjName() {
		return msubjName;
	}
	/** 元学科名称 */
	public void setMsubjName(String msubjName) {
		this.msubjName = msubjName;
	}
	/** 学科代码 */
	public String getMsubjCode() {
		return msubjCode;
	}
	/** 学科代码 */
	public void setMsubjCode(String msubjCode) {
		this.msubjCode = msubjCode;
	}
	
}
