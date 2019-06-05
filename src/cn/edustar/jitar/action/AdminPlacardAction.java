package cn.edustar.jitar.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Placard;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.PlacardQueryParam;
import cn.edustar.jitar.service.PlacardService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.util.PageContent;

/***
 * 系统公告管理
 * @author Yang Xinxin
 *
 */
public class AdminPlacardAction extends ManageBaseAction {

	/** serialVersionUID */
	private static final long serialVersionUID = 6034633977266798351L;

	/** 公告服务 */
	private PlacardService pla_svc;
	
	/** 学科/学段服务 */
	private SubjectService subj_svc;
	
	private AccessControlService accessControlService;
	
	/** 学科/学段服务 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}
	
	/** 公告服务 */
	public void setPlacardService(PlacardService pla_svc) {
		this.pla_svc = pla_svc;
	}
	
	public void setAccessControlService(AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		
		// 检查是否登陆及是否有管理权限.
		//管理权限：系统管理员、系统内容管理员		if (isUserLogined() == false) return LOGIN;

		if(placardAdmin()==false)
		{
			this.addActionError("没有管理系统公告的权限.");
			return ERROR;
		}
		
		if (cmd == null || cmd.length() == 0) cmd = "list";
		
		if("list".equalsIgnoreCase(cmd)) {
			return placardList();
		} else if("edit".equalsIgnoreCase(cmd)) {
			return placard_edit();
		} else if("del".equalsIgnoreCase(cmd)) {
			return placard_del();
		} else if("add".equalsIgnoreCase(cmd)) {
			return placard_add();
		} else if("save".equalsIgnoreCase(cmd)) {
			return placard_save();
		} else if ("hide".equalsIgnoreCase(cmd)) {
			return placard_hide();
		} else if ("show".equalsIgnoreCase(cmd)) {
			return placard_show();
		}
		return unknownCommand(cmd);
	}
	
	//本页面只提供供给系统管理员、系统内容管理员使用。
	private boolean placardAdmin()
	{
		User u = getLoginUser();
		if(u == null) return false;
		return (this.accessControlService.isSystemAdmin(u) || this.accessControlService.isSystemContentAdmin(u));
		
	}	
	/** 公告列表 */
	private String placardList() {
		
		/** 登陆验证 */
		if (!isUserLogined()) {
			return LOGIN;
		}
		
		//学科标识
		int subjectId = param_util.safeGetIntParam("subjectId");
		if(subjectId > 0) {
			Subject subject = subj_svc.getSubjectById(subjectId);
			setRequestAttribute("subject", subject);
			if(subject == null) {
				addActionError("未找到指定标识的公告");
				return ERROR;
			}
		}
		
		setRequestAttribute("subjectId", subjectId);
		
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("公告", "条");
		
		PlacardQueryParam param = new PlacardQueryParam();
		
		//subjectId == 0 则objType为系统类型, 若大于0, 则为学科类型
		if(subjectId == 0) {
			param.objType = ObjectType.OBJECT_TYPE_SYSTEM; 
		} else if(subjectId > 0) {
			param.objType = ObjectType.OBJECT_TYPE_SUBJECT;
		}
		if(subjectId<0)
		{
			param.objId = null;
		}
		else
		{
			param.objId = subjectId;
		}
		param.hideState=null;
		
		if(this.canAdmin())
		{
			//是管理员,不限制查询范围
			setRequestAttribute("admin", "1");
		}
		
		List<Placard> placard_list = pla_svc.getPlacardList(param, pager);
		
		setRequestAttribute("placard_list", placard_list);  
		setRequestAttribute("pager", pager);
		
		/** 学科列表, 供给模板使用 */
		putSubjectList();
		
		return "List_Success";
	}
	
	/* TODO:检查这里。。。。。。。调用基类中的putSubjectList了
	private void putSubjectList() {
		List<Subject> subject_list=null;
		if(this.canAdmin())
		{
			subject_list = subj_svc.getSubjectList();
			setRequestAttribute("admin", "1");
			setRequestAttribute("subject_list", subject_list);
		}
	}
	*/
	
	//修改系统公告
	private String placard_edit() {
		int placardId = param_util.safeGetIntParam("placardId");
		
		Integer subjectId = param_util.safeGetIntParam("subjectId"); 
		Placard placard = pla_svc.getPlacard(placardId);
		//System.out.print("placard.getObjType()="+placard.getObjType());
		if(placard.getObjType()==14)
		subjectId=placard.getObjId();
		
		/** 学科列表, 供给模板使用 */
		putSubjectList();
		setRequestAttribute("subjectId", subjectId);
		setRequestAttribute("__referer", getRefererHeader());
		setRequestAttribute("placard", placard);
		setRequestAttribute("placardId", placard);
		if(this.canAdmin()){
            setRequestAttribute("admin", "1");
        }
		
		return "placard_add_edit";
	}
	//添加公告 
	private String placard_add() {
		Placard placard = new Placard();
		
		putSubjectList();
		setRequestAttribute("placard", placard);
		setRequestAttribute("__referer", getRefererHeader());
		if(this.canAdmin()){
            setRequestAttribute("admin", "1");
        }
		return "placard_add_edit";
	}
	//删除公告
	private String placard_del() {
		List<Integer> ids = param_util.safeGetIntValues("placard");
		if(ids == null || ids.size() == 0) {
			addActionError("未选择要删除的公告");
			return ERROR;
		}
		int oper_count = 0;
		for(Integer id : ids) {
			Placard placard = pla_svc.getPlacard(id);
			if(placard == null) {
				addActionError("未找到标识为:" + id + "的公告");
				continue;
			}
			
			//TODO
			/*if(placard.getObjType() != ObjectType.OBJECT_TYPE_SYSTEM.getTypeId()
					|| placard.getObjId() != 0) {
				addActionError("不能删除别的对象的公告");
			}*/
					  
			pla_svc.deletePlacard(placard);
			addActionMessage("公告 " + placard.getTitle() + " 已删除");
			++oper_count;
		}
		addActionMessage("共删除了 " + oper_count + " 条公告");
		return SUCCESS;
	}
	//保存公告 
	@SuppressWarnings("null")
	private String placard_save() throws Exception {
		
		PrintWriter out = response.getWriter();
		//得到数据
		int placardId = param_util.getIntParam("placardId");
		String title =  param_util.safeGetStringParam("title");
		Integer subjectId = param_util.safeGetIntParam("subjectId");
		String content = param_util.safeGetStringParam("placardContent");
		
		
		//验证数据
		if(title == null || title.length() == 0) {
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>alert('   公告标题不能为空！');window.history.go(-1);</script>");
			out.flush();
			out.close();
			return NONE;
		}
		if(title != null && title.length() > 255) {
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>alert('   公告标题长度不能大于255！');window.history.go(-1);</script>");
			return NONE;
		}
		if(content == null && content.length() == 0) {
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>alert('   公告内容不能为空！');window.history.go(-1);</script>");
			return NONE;
		}
		
		Placard placard = new Placard();
		placard.setId(placardId);
		placard.setTitle(title);
		placard.setContent(content);
		placard.setCreateDate(new Date());
		if(subjectId > 0) {
			placard.setObjType(ObjectType.OBJECT_TYPE_SUBJECT.getTypeId());
			placard.setObjId(subjectId);
		} else {
			placard.setObjType(ObjectType.OBJECT_TYPE_SYSTEM.getTypeId());
			placard.setObjId(0); //系统类型:0
		}
		
		pla_svc.savePlacard(placard);
		addActionMessage("公告 '" + placard.getTitle() + "' 已保存");
		
		return SUCCESS;
	}
	
	//隐藏公告
	private String placard_hide() {
		List<Integer> ids = param_util.safeGetIntValues("placard");
		if(ids == null || ids.size() == 0) {
			addActionError("未选择要隐藏的公告");
			return ERROR;
		}
		
		int oper_count = 0;    
		for(Integer id : ids) {
			Placard placard = pla_svc.getPlacard(id);
			if(placard == null) {
				addActionError("未找到标识为 " + id + " 的公告");
				continue;
			}
			pla_svc.hidePlacard(placard);
			addActionMessage("公告" + placard.getTitle() + "已隐藏");
			++oper_count;
		}
		addActionMessage("共隐藏了" + oper_count + " 条公告");
		return SUCCESS;
		
	}
	//显示公告
	@SuppressWarnings("null")
	private String placard_show() {
		List<Integer>  ids = param_util.safeGetIntValues("placard");
		if(ids == null || ids.size() == 0) {
			addActionError("未选择要显示的公告");
			return ERROR;
		}
		
		int oper_count = 0;
		for(Integer id : ids ) {
			Placard placard = pla_svc.getPlacard(id);
			if(placard == null) {
				addActionError("未找到标识为" + placard.getTitle() + " 的公告");
				continue;
			}
			pla_svc.showPlacard(placard);
			addActionMessage("公告 " + placard.getTitle()+ "已显示");
			++oper_count;
		}
		addActionMessage("共显示了 " + oper_count + " 条公告");
		return SUCCESS;
	}
}	
