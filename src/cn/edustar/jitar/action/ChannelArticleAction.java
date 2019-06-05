package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.pojos.ChannelArticle;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ChannelArticleQuery;

/**
 * 自定义频道的文章管理
 * @author baimindong
 *
 */
public class ChannelArticleAction  extends BaseChannelManage{

	private static final long serialVersionUID = 255965515143308409L;
	private CategoryService categoryService;
	private ArticleService articleService;
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
        article_list();
        return "list";
	}
	
	private void article_list(){
        String channelCate = param_util.safeGetStringParam("ss", "");
        String articleState = param_util.safeGetStringParam("sarticleState", "");
        request.setAttribute("sc", channelCate);
        request.setAttribute("articleState", articleState);

        ChannelArticleQuery qry = new ChannelArticleQuery("ca.channelArticleId, ca.articleId, ca.articleGuid, ca.title, ca.userId, ca.articleState, ca.typeState, ca.createDate,ca.loginName, ca.userTrueName,ca.channelCate, ca.channelCateId");
        qry.setRequest(request);
        qry.channelId = channel.getChannelId();
        if (channelCate.length() >0){
            qry.channelCate = channelCate;
        }
        if (articleState.equals("1")){
            qry.articleState = true;
        }else if(articleState.equals("0")){
            qry.articleState = false;
        }else{
            qry.articleState = null;
        }
		Pager pager = param_util.createPager();
		pager.setItemName("文章");
		pager.setItemUnit("篇");
		pager.setPageSize(25);
        pager.setTotalRows(qry.count());
        List article_list = (List)qry.query_map(pager);
        CategoryTreeModel article_categories = categoryService.getCategoryTree("channel_article_" + channel.getChannelId());        
        request.setAttribute("article_categories", article_categories);
        request.setAttribute("article_list", article_list);
        request.setAttribute("pager", pager);
        request.setAttribute("channel", channel);
        request.setAttribute("ss", channelCate);
   }
	
	private void save_post(String cmd){
        String[] guids = param_util.getRequestParamValues("guid");
        if(guids == null || guids.length == 0){
            return;  
        }
        if("remove".equals(cmd)){
            for(String guid : guids){
                channelPageService.deleteChannelArticleById(Integer.parseInt(guid));
            }
        }else if("recate".equals(cmd)){
            String newCate = param_util.safeGetStringParam("newCate");
            if(newCate.length() == 0){
            	for(String guid : guids){
            		ChannelArticle channelArticle = channelPageService.getChannelArticleById(Integer.parseInt(guid));
                    if(channelArticle != null && channelArticle.getChannelCateId() != null){
                        channelArticle.setChannelCate(null);
                        channelArticle.setChannelCateId(null);
                        channelPageService.saveOrUpdateChannelArticle(channelArticle);
                    }
            	}
            }else{
                String[] newCateArray = newCate.split("/");
                if(newCateArray.length > 1){
                    String newCateId = newCateArray[newCateArray.length-1];
            		for(String guid : guids){
            			ChannelArticle channelArticle = channelPageService.getChannelArticleById(Integer.parseInt(guid));
            			if (channelArticle != null){
                        if (!channelArticle.getChannelCateId().equals(Integer.parseInt(newCateId))){
                            channelArticle.setChannelCate(newCate);
                            channelArticle.setChannelCateId(Integer.parseInt(newCateId));
                            channelPageService.saveOrUpdateChannelArticle(channelArticle);
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

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
}
