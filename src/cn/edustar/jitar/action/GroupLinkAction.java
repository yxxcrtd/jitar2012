package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Link;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.LinkQueryParam;
import cn.edustar.jitar.service.LinkService;


/***
 * 协作组链接管理.
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM  
 *
 */
public class GroupLinkAction  extends BaseGroupAction {
	/** serialVersionUID */
	private static final long serialVersionUID = 960747827761132972L;

	/** 链接服务 */
	private LinkService link_svc;
	
	/** 链接服务 */
	public void setLinkService(LinkService link_svc) {
		this.link_svc = link_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		// 判断用户登录.
		if (isUserLogined() == false) return LOGIN;
		// 必须有一个协作组且登录用户是协作组成员.
		if (hasCurrentGroupAndMember() == false) return ERROR;
		
		String uuid=group_svc.getGroupCateUuid(group_model);
		if(uuid.equals(CategoryService.GROUP_CATEGORY_GUID_KTYJ)){
			setRequestAttribute("isKtGroup", "1");
		}else{
			setRequestAttribute("isKtGroup", "0");
		}
		
		// 以下内容成员都可使用.
		if(cmd == null || cmd.length() == 0) cmd = "listLink";
		if ("listLink".equalsIgnoreCase(cmd))
			return list();

		// TODO: 以下需要有管理权限.
		
		
		if("write".equalsIgnoreCase(cmd)) {
			return write();
		} else if("saveLink".equalsIgnoreCase(cmd)) {
			return saveLink();
		} else if("editLink".equalsIgnoreCase(cmd)) {
			return editLink();
		} else if("deleteLink".equalsIgnoreCase(cmd)) {
			return deleteLink();
		}
		return unknownCommand(cmd);
	}
	
	/***
	 * 链接列表.
	 * @return
	 */
	private String list() {
		
		if(!isUserLogined()) {
			return LOGIN;
		}
		
		/** 构造查询参数和分页参数 */
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("主题", "个");

		Group group = super.group_model;
		LinkQueryParam param = new LinkQueryParam();
		
		param.groupId = group.getGroupId();
		List<Link> linkList = link_svc.getLinkList(param , pager);
		setRequestAttribute("linkList", linkList);
		setRequestAttribute("group", group_model);

			
		return "List_Link";
	}
	
	/***
	 * 添加链接.
	 * @return
	 */
	private String write() {
		int groupId = param_util.safeGetIntParam("groupId");
		Group group = super.group_model;
		
		Link link = new Link(); 
		setRequestAttribute("link", link);
		setRequestAttribute("groupId", groupId);
		setRequestAttribute("group", group);
		setRequestAttribute("__referer", getRefererHeader());
		return "Write_Success";
	}
	
	/***
	 * 保存一个链接.	 * @return
	 * @throws Exception
	 */
	private String saveLink() throws Exception {
		int groupId = param_util.safeGetIntParam("groupId");
		int linkId = param_util.safeGetIntParam("linkId");
		String linkName =  param_util.safeGetStringParam("title");
		String linkAddress = param_util.safeGetStringParam("address");
		int type = param_util.safeGetIntParam("type");		
		String description = param_util.safeGetStringParam("description");
		String linkIcon = param_util.safeGetStringParam("linkIcon");
		
		if(linkName == null || linkName.length() == 0) {
			addActionError(" 链接名称不能为空！");
			this.addActionLink("返回", "groupLink.action?cmd=write&groupId=" + groupId);
			return ERROR;
		}
		if(linkAddress == null || linkAddress.length() == 0 || linkAddress.toLowerCase().equalsIgnoreCase("http://")) {
			addActionError(" 链接地址不能为空！");
			this.addActionLink("返回", "groupLink.action?cmd=write&groupId=" + groupId);
			return ERROR;
		}
				
		Link link = new Link();
		link.setLinkId(linkId);
		link.setObjectType(ObjectType.OBJECT_TYPE_GROUP.getTypeId());
		link.setObjectId(groupId);
		link.setTitle(linkName);
		link.setLinkAddress(linkAddress);
		link.setLinkType(type);
		link.setDescription(description);
		link.setLinkIcon(linkIcon);
		
		//保存链接.
		link_svc.saveLink(link);
		addActionMessage("链接" + link.getTitle() + "已保存");
		this.addActionLink("返回", "groupLink.action?cmd=listLink&groupId=" + groupId);
		return SUCCESS;
	}
	
	/***
	 * 修改链接.
	 * @return
	 */
	private String editLink() {  
		
		int groupId = param_util.safeGetIntParam("groupId");
		int linkId = param_util.safeGetIntParam("linkId");
		
		Link link = link_svc.getLinkById(linkId);
		
		if(link == null) {
			addActionError("未找到标识为" + linkId + "的链接");
		}
		setRequestAttribute("link", link);
		setRequestAttribute("groupId", groupId);
		setRequestAttribute("__referer", getRefererHeader());
		return "Write_Success";
	}	
	
	/***
	 * 删除链接.
	 * @return
	 */
	private String deleteLink() {
		List<Integer> ids = param_util.safeGetIntValues("linkId");
		if(ids == null || ids.size() == 0) {
			addActionError("未选择要删除的公告");
			return ERROR;
		}
		int oper_count = 0;
		for(Integer id : ids) {
			Link link = link_svc.getLinkById(id);
			if(link == null) {
				addActionError("未找到标识为:" + id + "的链接");
				continue;
			}
			
			//删除链接
			link_svc.deleteLink(link);
			addActionMessage("链接" + link.toDisplayString() + "已删除");
			++oper_count;
		}
		addActionMessage("共删除了" + oper_count + "条链接");
		
		return SUCCESS;
	}
}
