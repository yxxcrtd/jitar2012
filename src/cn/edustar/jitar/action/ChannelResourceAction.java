package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.pojos.ChannelResource;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ChannelResourceQuery;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.ResourceService;

/**
 * 自定义频道的资源管理
 * @author baimindong
 *
 */
public class ChannelResourceAction extends BaseChannelManage{
	private static final long serialVersionUID = -3600732307153006392L;
	private CategoryService categoryService;
	private GroupService groupService;
	private ResourceService resourceService;
	private Channel channel = null;
	protected String execute(String cmd) throws Exception{
        Integer channelId = param_util.safeGetIntParam("channelId");
        this.channel = channelPageService.getChannel(channelId);
        if (this.channel == null){
            addActionError("不能加载频道对象。");
            return ERROR;
        }
        if(isSystemAdmin() == false && isChannelSystemAdmin(channel) == false && isChannelContentAdmin(channel) == false){
            addActionError("你无权管理本频道。");
            return ERROR;		
        }		
        if("POST".equals(request.getMethod())){
            save_post(cmd);
        }
        resource_list();
        return "list";
	}
	private void resource_list(){
        String schannel = param_util.safeGetStringParam("schannel", "");
        request.setAttribute("schannel", schannel);
        ChannelResourceQuery qry = new ChannelResourceQuery(" cr.channelResourceId, cr.resourceId, cr.userId, resource.title, resource.createDate,user.loginName, user.trueName as userTrueName, resource.href, resource.fsize, cr.channelCateId, unit.unitName,unit.unitTitle ");
        qry.setRequest(request);
        qry.channelId = channel.getChannelId();
        if (schannel.length() >0 ){
            qry.channelCate = schannel;
        }
        
		Pager pager = param_util.createPager();
		pager.setItemName("资源");
		pager.setItemUnit("个");
		pager.setPageSize(25);
        pager.setTotalRows(qry.count());
        List resource_list = (List)qry.query_map(pager);
        request.setAttribute("channel_resource_categories", categoryService.getCategoryTree("channel_resource_" + channel.getChannelId()));
        request.setAttribute("resource_list", resource_list);
        request.setAttribute("pager", pager);
        request.setAttribute("channel", channel);	
    }
	private void save_post(String cmd){
        List<Integer> guids = param_util.safeGetIntValues("guid");
        if("remove".equals(cmd)){
            for(Integer guid : guids){
            	ChannelResource r = channelPageService.getChannelResource(guid);
                if(r != null){
                    channelPageService.deleteChannelResource(r);
                }
            }
        }else if("recate".equals(cmd)){
            String newCate = param_util.safeGetStringParam("newCate");
            if(newCate.length() == 0){
            	for(Integer guid : guids){
            		ChannelResource channelResource = channelPageService.getChannelResource(guid);
            		if(channelResource != null){
	                    if(channelResource.getChannelCateId() !=null){
	                        channelResource.setChannelCate(null);
	                        channelResource.setChannelCateId(null);
	                        channelPageService.saveOrUpdateChannelResource(channelResource);
	                    }
            		}
            	}
            }else{
                String[] newCateArray = newCate.split("/");
                if(newCateArray.length > 1){
                    String newCateId = newCateArray[newCateArray.length-1];
                    for(Integer guid : guids){
                    	ChannelResource channelResource = channelPageService.getChannelResource(guid);
                    	if(channelResource != null){
	                        //if(!newCate.equals(channelResource.getChannelCate())){                            
                    		if(!(channelResource.getChannelCateId().equals(Integer.parseInt(newCateId)))){
	                            channelResource.setChannelCate(newCate);
	                            channelResource.setChannelCateId(Integer.parseInt(newCateId));
	                            channelPageService.saveOrUpdateChannelResource(channelResource);
	                        }
                    	}
                    }
                }
            }
        }
    }
	
	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
}
