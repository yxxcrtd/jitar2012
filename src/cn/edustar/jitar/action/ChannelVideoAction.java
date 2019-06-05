package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.pojos.ChannelVideo;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ChannelVideoQuery;
import cn.edustar.jitar.service.VideoService;

/**
 * 自定义频道的视频管理
 * @author baimindong
 *
 */
public class ChannelVideoAction extends BaseChannelManage{
	private static final long serialVersionUID = -3154876189876802118L;
	private CategoryService categoryService;
	private VideoService videoService;
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
		ChannelVideoQuery query = new ChannelVideoQuery(" cv.channelVideoId, cv.videoId, video.title, video.createDate, cv.channelCate, cv.viewCount, video.flvThumbNailHref, user.loginName,user.trueName, unit.unitName,unit.unitTitle ");
		query.setRequest(request);
        query.channelId = channel.getChannelId();
        String schannel = param_util.safeGetStringParam("schannel");
        if(schannel.length() > 0){
            query.channelCate = schannel;
        }
        query.k = param_util.safeGetStringParam("k");
        query.f = param_util.safeGetStringParam("f");
        CategoryTreeModel channel_video_categories = categoryService.getCategoryTree("channel_video_" + channel.getChannelId());
        request.setAttribute("channel_video_categories", channel_video_categories);
        
        //# 调用分页函数.
		Pager pager = param_util.createPager();
		pager.setItemName("视频");
		pager.setItemUnit("个");
		pager.setPageSize(10);
		pager.setTotalRows(query.count());
		
        //# 得到列表.
        Object videoList = query.query_map(pager);
            
        //# 传给页面.
        request.setAttribute("videoList", videoList);
        request.setAttribute("pager", pager);
        request.setAttribute("k", query.k);
        request.setAttribute("f", query.f);
        request.setAttribute("channel", channel);
        request.setAttribute("schannel", schannel);
	}
	private void save_post(String cmd){
        List<Integer> guids = param_util.safeGetIntValues("guid");
        if("remove".equals(cmd)){
            for(Integer guid : guids){
            	ChannelVideo r = channelPageService.getChannelVideo(guid);
                if(r != null){
                    channelPageService.deleteChannelVideo(r);
                }
            }
        }else if("recate".equals(cmd)){
            String newCate = param_util.safeGetStringParam("newCate");            
            if(newCate.length() == 0){
            	for(Integer guid : guids){
            		ChannelVideo channelVideo = channelPageService.getChannelVideo(guid);
            		if(channelVideo != null){
                    if(channelVideo.getChannelCateId() != null){
                        channelVideo.setChannelCate(null);
                        channelVideo.setChannelCateId(null);
                        channelPageService.saveOrUpdateChannelVideo(channelVideo);
                    }
            		}
            	}
            }else{
                String[] newCateArray = newCate.split("/");
                if (newCateArray.length > 1){
                    String newCateId = newCateArray[newCateArray.length-1];
                    for(Integer guid : guids){
                    	ChannelVideo channelVideo = channelPageService.getChannelVideo(guid);
                    	if(channelVideo != null ){
                        if(!(channelVideo.getChannelCate().equals(Integer.parseInt(newCateId)))){                            
                            channelVideo.setChannelCate(newCate);
                            channelVideo.setChannelCateId(Integer.parseInt(newCateId));
                            channelPageService.saveOrUpdateChannelVideo(channelVideo);
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

	public VideoService getVideoService() {
		return videoService;
	}

	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}
}
