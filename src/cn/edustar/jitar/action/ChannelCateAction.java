package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.jitar.data.Command;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.service.CategoryQuery;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.util.CommonUtil;
/**
 * 频道分类管理(文章分类 资源分类 图片分类 视频分类)
 * @author baimindong
 *
 */
public class ChannelCateAction extends BaseChannelManage {
	
	private static final long serialVersionUID = -6641107732491273977L;
	private CategoryService categoryService;
	private Category currentCategory = null;
	private Integer channelId= 0; 
	protected String execute(String cmd) throws Exception{
		if(!this.isUserLogined()){
			addActionError("请先登录！");
			addActionLink("登录", getSiteUrl()+"login.jsp","_self");
			return ERROR;
		}
        String itemType = param_util.safeGetStringParam("type");
        Integer parentId = param_util.getIntParamZeroAsNull("parentId");
        
        if(cmd == null || cmd.length() == 0){ cmd = "list";}
        
        channelId = param_util.safeGetIntParam("channelId");
        Channel channel = channelPageService.getChannel(channelId);
        if (channel == null){
            addActionError("不能加载频道对象。");
            return ERROR;
        }
        if(isSystemAdmin() == false && isChannelSystemAdmin(channel) == false){
            addActionError("你无权管理本频道。");
            return ERROR;
        }
        request.setAttribute("parentId", parentId);
        request.setAttribute("channel", channel);
        request.setAttribute("type", itemType);
        if(cmd.equals("list")){
            return list(itemType, parentId);
        }else if(cmd.equals("add")){
            return add(itemType, parentId);
        }else if(cmd.equals("edit")){
            return edit(itemType);
        }else if(cmd.equals("save")){
            return save(itemType, parentId);
        }else if(cmd.equals("delete")){
            return delete(itemType);
        }else if(cmd.equals("orderlist")){
            return orderlist(itemType,parentId);
        }else if(cmd.equals("ordersave")){
            return ordersave();
        }
        addActionError("未知命令 : " + cmd);//
        return ERROR;		
	}
	private String ordersave(){
        List<Integer> cate_ids = param_util.getIdList("cateid");
        List<Integer> nos = param_util.getIdList("orderNo");
        if(cate_ids == null || cate_ids.size() == 0){
            addActionError("没有选择要操作的分类.");
            return ERROR;
        }
        if(nos == null || nos.size() == 0){
            addActionError("没有选择要操作的分类序号.");
            return ERROR;
        }
        int iIndex = -1;
        for(Integer id : cate_ids){
            iIndex = iIndex + 1;
            Integer no = nos.get(iIndex);
            Category category = categoryService.getCategory(id);
            if(category == null){
                addActionError("未能找到标识为 " + id + " 的分类");
                return ERROR;
            }else{  
                categoryService.setCategoryOrder(id, no);
            }
        }
        Integer parentId = param_util.getIntParamZeroAsNull("parentId");
        String itemType = param_util.getStringParam("type");
        String link = "channelcate.action?cmd=list&type=" + itemType + "&channelId=" + channelId + "&parentId=";
        if (parentId != null){
            link += parentId;
        }
        addActionLink("返回", link);
        return SUCCESS;
	}
	private String orderlist(String itemType,Integer parentId){
        //# 文章的分类
        if(itemType.startsWith("channel_article_")){ 
            String cateId = itemType.substring(("channel_article_").length());
            if(!cateId.equals(""+channelId)){
                addActionError("不正确的分类对象。");
                return ERROR;
            }
            CategoryQuery query = new CategoryQuery(" cat.categoryId, cat.name, cat.itemType, cat.parentId, cat.parentPath,cat.childNum, cat.orderNum, cat.description ");
            query.itemType = itemType;
            query.parentId = parentId;
            List cateList = query.query_map(query.count());
            request.setAttribute("category_list", cateList);
            //# 将参数放到查询的页面.
            request.setAttribute("parentId", parentId);
            request.setAttribute("itemType", itemType);
            request.setAttribute("type", itemType);
            
            if(parentId == null){
                request.setAttribute("parentparentId", "");
            }else{
            	Category pcategory = categoryService.getCategory(parentId);
                if(pcategory == null){
                    request.setAttribute("parentparentId", "");
                }else{    
                    request.setAttribute("parentparentId", pcategory.getParentId());
                }
            }
            return "orderlist";
        }else if(itemType.startsWith("channel_resource_")){ 
            String cateId = itemType.substring(("channel_resource_").length());
            if(!cateId.equals(""+channelId)){
                addActionError("不正确的分类对象。");
                return ERROR;
            }
            CategoryQuery query = new CategoryQuery(" cat.categoryId, cat.name, cat.itemType, cat.parentId, cat.parentPath,cat.childNum, cat.orderNum, cat.description ");
            query.itemType = itemType;
            query.parentId = parentId ;           
            List cateList = query.query_map(query.count());
            request.setAttribute("category_list", cateList);
            //# 将参数放到查询的页面.
            request.setAttribute("parentId", query.parentId);
            request.setAttribute("itemType", query.itemType);
            request.setAttribute("type", query.itemType);
            
            if (query.parentId == null){
                request.setAttribute("parentparentId", "");
            }else{
            	Category pcategory = categoryService.getCategory(query.parentId);
                if(pcategory == null){
                    request.setAttribute("parentparentId", "");
                }else{    
                    request.setAttribute("parentparentId", pcategory.getParentId());
                }
            }
            return "orderlist";
        }else if(itemType.startsWith("channel_photo_")){ 
            String cateId = itemType.substring("channel_photo_".length());
            if(!cateId.equals(""+channelId)){
                addActionError("不正确的分类对象。");
                return ERROR;
            }
            CategoryQuery query = new CategoryQuery(" cat.categoryId, cat.name, cat.itemType, cat.parentId, cat.parentPath,cat.childNum, cat.orderNum, cat.description ");
            query.itemType = itemType;
            query.parentId = parentId;           
            List cateList = query.query_map(query.count());
            request.setAttribute("category_list", cateList);
            //# 将参数放到查询的页面.
            request.setAttribute("parentId", query.parentId);
            request.setAttribute("itemType", query.itemType);
            request.setAttribute("type", query.itemType);
            if (query.parentId == null){
                request.setAttribute("parentparentId", "");
            }else{
            	Category pcategory = categoryService.getCategory(query.parentId);
                if(pcategory == null){
                    request.setAttribute("parentparentId", "");
                }else{    
                    request.setAttribute("parentparentId", pcategory.getParentId());
                }
            }
            return "orderlist";
        }else if(itemType.startsWith("channel_video_")){ 
            String cateId = itemType.substring("channel_video_".length());
            if(!cateId.equals(""+channelId)){
                addActionError("不正确的分类对象。");
                return ERROR;
            }
            CategoryQuery query = new CategoryQuery(" cat.categoryId, cat.name, cat.itemType, cat.parentId, cat.parentPath,cat.childNum, cat.orderNum, cat.description ");
            query.itemType = itemType;
            query.parentId = parentId;           
            List cateList = query.query_map(query.count());
            request.setAttribute("category_list", cateList);
            //# 将参数放到查询的页面.
            request.setAttribute("parentId", query.parentId);
            request.setAttribute("itemType", query.itemType);
            request.setAttribute("type", query.itemType);
            if (query.parentId == null){
                request.setAttribute("parentparentId", "");
            }else{
            	Category pcategory = categoryService.getCategory(query.parentId);
                if(pcategory == null){
                    request.setAttribute("parentparentId", "");
                }else{    
                    request.setAttribute("parentparentId", pcategory.getParentId());
                }
            }
            return "orderlist";
        }else{
            addActionError("无效的分类对象。");
            return ERROR;
        }
	}
	/**
	 * 删除所选择的分类
	 * @return
	 */
	private String delete(String itemType){
        //# 得到要删除的分类对象.
        if(getCurrentCategory(itemType) == false){
        	return ERROR;
        }
        Category category = currentCategory;
        //# 验证其是否有子分类, 有子分类的必须要先删除子分类才能删除分类.
        if(hasChildCategories(category)){
        	addActionError("分类 " + category.getName() + " 有子分类, 必须先删除其所有子分类才能删除该分类.");
        	return ERROR;
        }
        //# 设置 文章.groupCateId 都为 null.
        //# 这里现在没有放在事务里面执行.
        if(itemType.startsWith("channel_article_")){
            deleteChannelCategory("ChannelArticle",category);
        }else if(itemType.startsWith("channel_resource_")){
            //deleteChannelCategory("Resource",category);  //py中有错？？？？？TODO:修改检查测试PY？？？
        	deleteChannelCategory("ChannelResource",category); 
        }else if(itemType.startsWith("channel_photo_")){
            //deleteChannelCategory("Photo",category);  //py中有错？？？？？TODO:修改检查测试PY？？？
        	deleteChannelCategory("ChannelPhoto",category);
        }else if(itemType.startsWith("channel_video_")){
            //deleteChannelCategory("Video",category); //py中有错？？？？？TODO:修改检查测试PY？？？
        	deleteChannelCategory("ChannelVideo",category);
        }else{
            addActionError("无效的分类");
            return ERROR;
        }
        //# 执行业务.
        categoryService.deleteCategory(category);
        
        addActionMessage("分类 " + category.getName() + " 已经成功删除.");
        return SUCCESS; 		
	}
	
	private void deleteChannelCategory(String resType,Category category){
        if (category == null){
        	return;
        }
        Command cmd = new Command(" UPDATE " + resType + " SET channelCate = NULL,channelCateId = NULL WHERE channelId = :channelId AND channelCateId = :channelCateId ");
        cmd.setInteger("channelId", channelId);
        cmd.setInteger("channelCateId", category.getCategoryId());
        int count = cmd.update();	
	}
    /**
     *判断指定的分类是否具有子分类. 
     * @param category
     * @return
     */
    private boolean hasChildCategories(Category category){
        int childCount = categoryService.getChildrenCount(category.getCategoryId());
        return childCount > 0;	
    }
	private String save(String itemType, Integer parentId){
        //# 获得和验证父分类参数.
        if(getParentCategory(itemType) == false){
            return ERROR;
        }
        
        //# 从提交数据中组装出 category 对象.
        Category category = new Category();
        category.setCategoryId(param_util.getIntParam("categoryId"));
        category.setName(param_util.getStringParam("name"));
        category.setItemType(itemType);
        category.setParentId(parentId);
        category.setDescription(param_util.getStringParam("description"));
        
        // 简单验证.
        if(category.getName() == null || category.getName().length() == 0 ){
            addActionError("未填写分类名字.");
            return ERROR;
        }
        if(category.getCategoryId() == 0){
            //# 创建该分类.
            categoryService.createCategory(category);
            addActionMessage("分类 " + category.getName() + " 创建成功.");
        }else{
            //# 更新/移动分类.
            categoryService.updateCategory(category);
            //# 修改文章、资源、图片、视频的分类标识 
            if(itemType.startsWith("channel_article_")){
                updateChannelCateId("ChannelArticle", category);
            }else if(itemType.startsWith("channel_resource_")){
                //updateChannelCateId("Resource", category); //TODO：PY也不对？？？？？测试
            	updateChannelCateId("ChannelResource", category);
            }else if(itemType.startsWith("channel_photo_")){
                //updateChannelCateId("Photo", category);//TODO：PY也不对？？？？？测试
            	updateChannelCateId("ChannelPhoto", category);
            }else if(itemType.startsWith("channel_video_")){
                //updateChannelCateId("Video", category);//TODO：PY也不对？？？？？测试
            	updateChannelCateId("ChannelVideo", category);
            }
            addActionMessage("分类 " + category.getName() + " 修改/移动操作成功完成.");
		}
        String link = "?cmd=list&channelId=" + channelId + "&type=" + itemType;
        addActionLink("返回", link);
        return SUCCESS;
	}
	
    private void updateChannelCateId(String resType,Category category){
        //# 得到该分类及其下级所有分类
    	List<Category> cateList = categoryService.getCategories(resType);
        for(Category cate : cateList){
            if(cate != null){
                String catePath = CommonUtil.convertIntFrom36To10(cate.getParentPath()) + cate.getCategoryId() + "/";
                Command cmd = new Command(" UPDATE " + resType + " SET channelCate = :channelCate WHERE channelCateId = :channelCateId ");
                cmd.setString("channelCate", catePath);
                cmd.setInteger("channelCateId", cate.getCategoryId());
                int count = cmd.update();
            }
        }
    }
	private String edit(String itemType){
        //# 得到要编辑的分类对象.
        if (getCurrentCategory(itemType) == false){
            return ERROR;
        }
        request.setAttribute("category", currentCategory);
    
        //# 得到整个分类树.
        CategoryTreeModel category_tree = categoryService.getCategoryTree(itemType);
        request.setAttribute("category_tree", category_tree);
        return "edit";	
	}
	
    /**
	 * 得到当前要操作的分类对象, 并验证其存在, 以及 itemType 匹配.
	 * 返回 False 表示失败; True 表示成功.
	 * 如果返回 True 则 category 中存放着拿出来的分类对象.
     * 
     * @return
     */
    private boolean getCurrentCategory(String itemType){
        Integer categoryId = param_util.safeGetIntParam("categoryId");
        if(categoryId == 0){
            addActionError("未给出要操作的分类.");
            return false;
        }
        Category category = categoryService.getCategory(categoryId);
        if(category == null){
            addActionError("未找到指定标识为 " + categoryId + " 的分类.");
            return false;
        }
        //# 验证分类类型必须匹配.
        if(!category.getItemType().equals(itemType)){
            addActionError("不匹配的分类类型.");
            return false;
        }
        currentCategory = category;
        return true;
    }
    
	private String add(String itemType,Integer parentId){
        if(getParentCategory(itemType) == false){
            return ERROR;
		}
        //# 得到整个分类树.
        CategoryTreeModel category_tree = categoryService.getCategoryTree(itemType);
        request.setAttribute("category_tree", category_tree);
        
        //# 构造一个新分类.
        Category category = new Category();
        category.setItemType(itemType);
        category.setParentId(parentId);
        request.setAttribute("category", category);
        return "add";
	}
	private String list(String itemType, Integer parentId){
		List<Category> category_list = categoryService.getChildCategories(itemType, parentId);
        request.setAttribute("category_list", category_list);
        return "list";
	}
	
    /**
     *得到父分类标识参数及父分类对象, 并进行 itemType 验证. 
     * @return
     */
    private boolean getParentCategory(String itemType){
        Integer parentId = param_util.getIntParamZeroAsNull("parentId");
        request.setAttribute("parentId", parentId);
        if (parentId == null){	//认为是根分类.
            return true;
        }
        
        Category parentCategory = categoryService.getCategory(parentId);
        request.setAttribute("parentCategory", parentCategory);

        if (parentCategory == null){
            addActionError("未能找到指定标识的父分类, 请确定您是从有效的链接点击进入的.");
            return false;
        }
        if (!parentCategory.getItemType().equals(itemType)){
            addActionError("不匹配的父分类类型: " + parentCategory.getItemType());
            return false;
        }
        return true;
    }  
    
	public CategoryService getCategoryService() {
		return categoryService;
	}
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
}
