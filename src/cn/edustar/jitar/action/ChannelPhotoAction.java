package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.pojos.ChannelPhoto;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ChannelPhotoQuery;
import cn.edustar.jitar.service.PhotoService;

/**
 * 自定义频道的图片管理
 * @author baimindong
 *
 */
public class ChannelPhotoAction extends BaseChannelManage{
	
	private static final long serialVersionUID = -6161914753166463172L;
	private CategoryService categoryService;
	private PhotoService photoService;
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
        get_list();
        return "list";
	}
	private void get_list(){
        String showState = param_util.safeGetStringParam("sshowState");
        ChannelPhotoQuery query = new ChannelPhotoQuery(" cp.channelPhotoId, cp.photoId, photo.title, photo.href, cp.viewCount, photo.createDate, cp.channelCate, user.trueName as userTrueName,user.loginName, unit.unitName,unit.unitTitle ");
        query.setRequest(request);
        query.channelId = channel.getChannelId();        
        String schannel = param_util.safeGetStringParam("schannel");
        if(schannel.length() >0){
            query.channelCate = schannel;
        }
        putChannelCategoryTree();
        
        //# 调用分页函数.
		Pager pager = param_util.createPager();
		pager.setItemName("图片");
		pager.setItemUnit("张");
		pager.setPageSize(10);
		pager.setTotalRows(query.count());
        
        //# 得到所有照片的列表.
        Object photoList = query.query_map(pager);
            
        //# 传给页面.
        request.setAttribute("photoList", photoList);
        request.setAttribute("pager", pager);
        request.setAttribute("channel", channel);
        request.setAttribute("schannel", schannel);
	}
	private void putChannelCategoryTree(){
		CategoryTreeModel channel_photo_categories = categoryService.getCategoryTree("channel_photo_" +channel.getChannelId());
        request.setAttribute("channel_photo_categories", channel_photo_categories);		
	}
	private void save_post(String cmd){
        List<Integer> guids = param_util.safeGetIntValues("guid");
        if("remove".equals(cmd)){
            for(Integer g : guids){
            	ChannelPhoto channelPhoto = channelPageService.getChannelPhoto(g);
                if(channelPhoto != null){
                    channelPageService.deleteChannelPhoto(channelPhoto);
                }
            }
        }else if("recate".equals(cmd)){
            String newCate = param_util.safeGetStringParam("newCate");
            if(newCate.length() == 0){
            	for(Integer guid: guids){
            		ChannelPhoto channelPhoto = channelPageService.getChannelPhoto(guid);
            		if(channelPhoto != null){
                    if(channelPhoto.getChannelCateId() != null){
                        channelPhoto.setChannelCate(null);
                        channelPhoto.setChannelCateId(null);
                        channelPageService.saveOrUpdateChannelPhoto(channelPhoto);
                    }
            		}
            	}
            }else{
                String[] newCateArray = newCate.split("/");
                if(newCateArray.length > 1){
                    String newCateId = newCateArray[newCateArray.length-1];
                    for(Integer guid: guids){
                    	ChannelPhoto channelPhoto = channelPageService.getChannelPhoto(guid);
                    	if(channelPhoto != null){
                        if(!(channelPhoto.getChannelCateId().equals(Integer.parseInt(newCateId)))){
                            channelPhoto.setChannelCate(newCate);
                            channelPhoto.setChannelCateId( Integer.parseInt(newCateId));
                            channelPageService.saveOrUpdateChannelPhoto(channelPhoto);
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

	public PhotoService getPhotoService() {
		return photoService;
	}

	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}
}
