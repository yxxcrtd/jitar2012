package cn.edustar.jitar.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.CategoryModel;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Browsing;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.pojos.Praise;
import cn.edustar.jitar.pojos.Report;
import cn.edustar.jitar.pojos.UFavorites;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.BrowsingService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.service.PhotoQuery;
import cn.edustar.jitar.service.PhotoService;
import cn.edustar.jitar.service.PraiseQuery;
import cn.edustar.jitar.service.PraiseService;
import cn.edustar.jitar.service.ReportService;
import cn.edustar.jitar.service.UFavoritesService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.ParamUtil;

import com.alibaba.fastjson.JSONObject;

public class PhotosAction extends AbstractBasePageAction {

    private static final long serialVersionUID = -443014581816277400L;
    private CategoryService categoryService;
    /** 赞服务 */
    private PraiseService praiseService;
    private Integer photoId = null;
    /** 当前登录用户 */
    private User loginUser;
    private Photo photo = null;
    /** 举报服务 */
    private ReportService reportService;
    /** 收藏夹服务 */
    private UFavoritesService favoritesService;

    private CommentService commentService;

    /** 图片服务 */
    private PhotoService photoService;

    /** 浏览历史服务 */
    private BrowsingService browsingService;

    private UserService userService;

    @Override
    public String execute(String cmd) throws Exception {

        loginUser = super.getLoginUser();

        // 最新图片.
        get_new_photo_list();
        // 图片点击排行.
        get_hot_photo_list();
        request.setAttribute("head_nav", "gallery");
        switch (cmd) {
            case "praise" : // 赞
                return praise();
            case "unpraise" : // 取消赞
                return unpraise();
            case "report" : // 举报
                return report();
            case "favorite" : // 收藏图片
                return favoritePhoto();
            case "unfavorite" : // 取消收藏图片
                return unFavoritePhoto();
            case "comment" :
                return commentPhoto();
            case "replyCmt" :
                return replyComment();
            case "deleteCmt" :
                return deleteComment();
            case "detail" :
                return show();
            case "detail_comment" :
                return show_1();
            default :
                // getPhoto();
                get_photo_category();
                return "success";
        }
    }

    private String show_1() {
        getPhoto();
        // 读取赞的数量
        // request.setAttribute("article", article);

        if (null != loginUser) {
            Browsing browsing = browsingService.getBrowsing(ObjectType.OBJECT_TYPE_PHOTO.getTypeId(), photo.getPhotoId(), loginUser.getUserId());
            if (null != browsing) {
                this.browsingService.deleteBrowsing(browsing);
            }
            browsing = new Browsing(ObjectType.OBJECT_TYPE_PHOTO.getTypeId(), photo.getPhotoId(), loginUser.getUserId());
            this.browsingService.saveBrowsing(browsing);
        }
        if (photo.getUserStaple() != null) {
            request.setAttribute("UserCate", this.categoryService.getCategory(photo.getUserStaple()));
        }
        if (photo.getSysCateId() != null) {
            request.setAttribute("SysCate", this.categoryService.getCategory(photo.getSysCateId()));
            // 得到本分类的文章数

        }

        // this.photoService.increaseViewCount(photo.getPhotoId(), 1);
        request.setAttribute("photoAuthor", this.userService.getUserById(photo.getUserId(), true));

        this.getBrowsingHistory();
        this.getPhotoComment();
        this.getPraiseList();
        this.getPhotoFavorated();
        this.getReportTypeList();
        return "detail_comment";
    }

    private String getPhoto() {
        // 通过photoId查询photo
        photoId = null != params.safeGetStringParam("photoId") ? params.safeGetIntParam("photoId") : null;
        if (photoId != null) {
            get_photosAndSysList(photoId);
        } else {
            return ERROR;
        }
        if (null == photo) {
            this.addActionError("无法加载图片对象。");
            this.addActionLink("返回首页", request.getContextPath() + "/");
            return ERROR;
        }
        if (photo.getDelState() || photo.getAuditState() != Photo.AUDIT_STATE_OK) {
            this.addActionError("图片设置为待删除、或者未审核，无法显示此图片。");
            this.addActionLink("返回首页", request.getContextPath() + "/");
            return ERROR;
        }
        return ERROR;
    }

    private String show() throws IOException {
        getPhoto();

        // 读取赞的数量

        // request.setAttribute("article", article);

        if (null != loginUser) {
            Browsing browsing = browsingService.getBrowsing(ObjectType.OBJECT_TYPE_PHOTO.getTypeId(), photo.getPhotoId(), loginUser.getUserId());
            if (null != browsing) {
                this.browsingService.deleteBrowsing(browsing);
            }
            browsing = new Browsing(ObjectType.OBJECT_TYPE_PHOTO.getTypeId(), photo.getPhotoId(), loginUser.getUserId());
            this.browsingService.saveBrowsing(browsing);
        }
        PhotoQuery qry = new PhotoQuery("p.photoId");
        qry.userId = photo.getUserId();
        if (photo.getUserStaple() != null && photo.getUserStaple() > 0) {
            request.setAttribute("UserCate", this.categoryService.getCategory(photo.getUserStaple()));          
            qry.userStapleId = photo.getUserStaple();
        }
       else{
           qry.userStapleId = -1;
        }
        request.setAttribute("UserCateCount", qry.count());
        
        /*if (photo.getSysCateId() != null) {
            request.setAttribute("SysCate", this.categoryService.getCategory(photo.getSysCateId()));
            // 得到本分类的文章数

        }*/

        this.photoService.increaseViewCount(photo.getPhotoId(), 1);
        request.setAttribute("photoAuthor", this.userService.getUserById(photo.getUserId(), true));

        // 页面导航高亮为 'gallery'
        request.setAttribute("head_nav", "gallery");
        this.getBrowsingHistory();
        this.getPhotoComment();
        this.getPraiseList();
        this.getPhotoFavorated();
        this.getReportTypeList();
        return "detailphoto";
    }

    private void getBrowsingHistory() {
        List<Browsing> browsingHistory = this.browsingService.getBrowsingTopList(24, ObjectType.OBJECT_TYPE_PHOTO.getTypeId(), this.photoId);
        List<User> browsingUser = new ArrayList<User>();
        if(browsingHistory != null && browsingHistory.size() > 0){
            for (Browsing b : browsingHistory) {
                browsingUser.add(this.userService.getUserById(b.getUserId(), true));
            }
            request.setAttribute("browsingUser", browsingUser);
        }
    }

    // 检查是否收藏过
    private void getPhotoFavorated() {
        if (null == loginUser) {
            return;
        }
        UFavorites favorate = this.favoritesService.getFavorites(ObjectType.OBJECT_TYPE_PHOTO.getTypeId(), this.photoId, loginUser.getUserId());
        if (favorate != null) {
            request.setAttribute("favorate", "1");
        }
    }

    /**
     * 赞列表
     */
    private void getPraiseList() {
        List<Praise> praiseList = this.praiseService.getPraiseByObjectType(this.photoId, ObjectType.OBJECT_TYPE_PHOTO.getTypeId());
        request.setAttribute("praiseList", praiseList);

        if (praiseList == null || praiseList.size() == 0) {
            request.setAttribute("praiseCount", 0);
        } else {
            request.setAttribute("praiseCount", praiseList.size());
        }
        // 当前用户是否赞过
        if (loginUser != null) {
            Praise p = this.praiseService.getPraiseByTypeAndUserId(this.photoId, ObjectType.OBJECT_TYPE_PHOTO.getTypeId(), loginUser.getUserId());
            request.setAttribute("praise", p);

            if (p != null && praiseList != null && praiseList.size() == 1) {
                if (p.getUserId() == praiseList.get(0).getUserId()) {
                    request.setAttribute("praiseCount", -1);
                }
            }
        }
    }

    /**
     * 得到图片评论
     */
    private void getPhotoComment() {
        List<Comment> cmt_list = this.commentService.getAllPhotoCommentByPhotoId(photoId);
        for (Comment c : cmt_list) {
            List<Comment> reply_list = this.commentService.getCommentListByObject(ObjectType.OBJECT_TYPE_COMMENT.getTypeId(), c.getId());
            if (null != reply_list && reply_list.size() > 0) {
                c.setReplyList(reply_list);
            }
        }
        request.setAttribute("cmt_list", cmt_list);
    }

    private String deleteComment() throws IOException {
        int cmtId = this.params.safeGetIntParam("cmtId", 0);
        response.setContentType("application/json");
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }

        Comment comment = this.commentService.getComment(cmtId);
        if (null == comment) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载评论对象，该评论可能已被删除。"));
            return NONE;
        }
        if (comment.getUserId() != loginUser.getUserId()) {
            response.getWriter().print(this.makeJsonReturn(3, "请不要删除不是自己发布的内容。"));
            return NONE;
        }
        // 先删除评论的回复
        this.commentService.deleteCommentReply(comment);
        this.commentService.deleteComment(comment);
        response.getWriter().print(this.makeJsonReturn(0, ""));
        return NONE;
    }

    private String replyComment() throws IOException {
        int parentId = this.params.safeGetIntParam("parentId", 0);
        int cmtId = this.params.safeGetIntParam("cmtId", 0);
        String content = this.params.safeGetStringParam("content");
        response.setContentType("application/json");
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        if (null == content || content.length() == 0) {
            response.getWriter().print(this.makeJsonReturn(3, "请输入内容。"));
            return NONE;
        }
        if (content.length() > 300) {
            response.getWriter().print(this.makeJsonReturn(3, "输入的内容不能多于300字。"));
            return NONE;
        }

        Comment comment = this.commentService.getComment(cmtId);
        if (null == comment) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载评论对象，该评论可能已被删除。"));
            return NONE;
        }

        Comment cmt = new Comment();
        cmt.setAboutUserId(comment.getUserId());
        cmt.setAudit(true);
        cmt.setContent(content);
        cmt.setCreateDate(new Date());
        cmt.setIp(CommonUtil.getClientIP(request));
        if (parentId == 0) {
            cmt.setObjId(comment.getId());
        } else {
            cmt.setObjId(parentId);
        }
        cmt.setObjType(ObjectType.OBJECT_TYPE_COMMENT.getTypeId());
        cmt.setStar(0);
        cmt.setTitle("回复");
        cmt.setUserId(loginUser.getUserId());
        cmt.setUserName(loginUser.getTrueName());
        this.commentService.saveComment(cmt);

        // 动态显示回复
        User aboutUser = this.userService.getUserById(cmt.getAboutUserId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer sb = new StringBuffer();
        sb.append("<dl id='r" + cmt.getId() + "'>");
        sb.append("<dt><a href='" + request.getContextPath() + "/go.action?loginName=" + loginUser.getLoginName() + "' target='_blank'><img src='"
                + this.SSOServerURL1 + "/upload/" + loginUser.getUserIcon() + "' width='50' border='0' onerror=\"this.src='"
                + request.getContextPath() + "/images/default.gif'\"/></a></dt>");
        sb.append("<dd>");
        sb.append("  <h4><em class='emDate'>" + sdf.format(cmt.getCreateDate()) + "</em><a target='_blank' href='" + request.getContextPath()
                + "/go.action?loginName=" + loginUser.getLoginName() + "'>" + loginUser.getTrueName() + "</a></h4>");
        sb.append("  <p class='mt10 hardBreak'>");
        sb.append(cmt.getContent());
        sb.append("</p>");
        sb.append("  <p class='clearfix'>");
        sb.append("    <a href='javascript:void(0);' class='replyBtn' id='rb2' onclick='replay(" + cmt.getId() + "," + cmt.getObjId() + ")'>回复</a>");
        sb.append("    <a href='javascript:void(0);' class='removeBtn' commentid='" + cmt.getId() + "' isreply='1' xxxonclick='deleteComment("
                + cmt.getId() + ",1);return false;'>删除</a>");
        if (aboutUser != null) {
            sb.append("    <span class='replyText'>回复给</span> <a target='_blank' href='" + request.getContextPath() + "/go.action?loginName="
                    + aboutUser.getLoginName() + "' class='replyYou'>" + aboutUser.getTrueName() + "</a>");
        }
        sb.append("  </p>");
        sb.append("</dd>");
        sb.append("<span class='replyArrow'></span>");
        sb.append("</dl>");

        response.getWriter().print(this.makeJsonReturn(0, sb.toString(), cmt.getId()));
        sb = null;
        return NONE;
    }

    private String commentPhoto() throws IOException {
        getPhoto();
        String content = this.params.safeGetStringParam("content");
        response.setContentType("application/json");
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        if (null == content || content.length() == 0) {
            response.getWriter().print(this.makeJsonReturn(3, "请输入内容。"));
            return NONE;
        }

        if (null == photo) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载图片对象。"));
            return NONE;
        }

        Comment cmt = new Comment();
        cmt.setAboutUserId(photo.getUserId());
        cmt.setAudit(true);
        cmt.setContent(content);
        cmt.setCreateDate(new Date());
        cmt.setIp(CommonUtil.getClientIP(request));
        cmt.setObjId(photo.getPhotoId());
        cmt.setObjType(ObjectType.OBJECT_TYPE_PHOTO.getTypeId());
        cmt.setStar(0);
        cmt.setTitle(photo.getTitle());
        cmt.setUserId(loginUser.getUserId());
        cmt.setUserName(loginUser.getTrueName());
        this.commentService.saveComment(cmt);
        this.photoService.incPhotoCommentCount(cmt, 1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer sb = new StringBuffer();
        sb.append("<dl>")
                .append("<dt><a target='_blank' href='" + request.getContextPath() + "/go.action?loginName=" + loginUser.getLoginName()
                        + "'><img src='" + this.SSOServerURL1 + "/upload/" + loginUser.getUserIcon() + "' width='50' onerror=\"this.src='"
                        + request.getContextPath() + "/images/default.gif'\"/></a></dt>")
                .append("<dd>")
                .append("<div class='replyContent' id='replyContent" + cmt.getId() + "'>")
                .append("<div id='r" + cmt.getId() + "'>")
                .append("<h4><em class='emDate'>" + sdf.format(cmt.getCreateDate()) + "</em><a target='_blank' href='" + request.getContextPath()
                        + "/go.action?loginName=" + loginUser.getLoginName() + "'>" + loginUser.getTrueName() + "</a></h4>")
                .append("<p class='mt10'>")
                .append(cmt.getContent())
                .append("</p>")
                .append("<p class='clearfix'>")
                .append(" <a href='javascript:void(0);' class='replyBtn' onclick='replay(" + cmt.getId() + ", " + cmt.getId() + ")'>回复</a>")
                .append("<a href='javascript:void(0);' class='removeBtn' commentid='" + cmt.getId() + "' isreply='0' xxxxonclick='deleteComment("
                        + cmt.getId() + "," + cmt.getId() + ");return false;'>删除</a>").append("</p>").append("</div>").append("</div>")
                .append("</dd>").append("</dl>");
        response.getWriter().print(this.makeJsonReturn(0, sb.toString()));
        sb = null;

        return NONE;
    }

    // 取消收藏
    private String unFavoritePhoto() throws IOException {
        response.setContentType("application/json");
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        photoId = null != params.safeGetStringParam("photoId") ? params.safeGetIntParam("photoId") : null;
        UFavorites favorate = this.favoritesService.getFavorites(ObjectType.OBJECT_TYPE_PHOTO.getTypeId(), this.photoId, loginUser.getUserId());
        if (favorate != null) {
            this.favoritesService.Del(favorate);
        }
        response.getWriter().print(this.makeJsonReturn(0, ""));
        return NONE;
    }

    private String favoritePhoto() throws IOException {
        response.setContentType("application/json");
        photoId = null != params.safeGetStringParam("photoId") ? params.safeGetIntParam("photoId") : null;
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        if (null == photoId) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载文章对象。"));
            return NONE;
        }
        Photo photo = photoService.findById(photoId);
        UFavorites favorate = new UFavorites();
        favorate.setFavHref(CommonUtil.getSiteUrl(request) + "photos.action?photoId=" + photoId);
        favorate.setFavTitle(photo.getTitle());
        favorate.setFavUser(loginUser.getUserId());
        favorate.setFavTypeId(0);
        favorate.setObjectId(photoId);
        favorate.setObjectType(ObjectType.OBJECT_TYPE_PHOTO.getTypeId());
        favorate.setFavDate(new Date());
        this.favoritesService.Save(favorate);
        response.getWriter().print(this.makeJsonReturn(0, "收藏完毕。"));
        return NONE;
    }

    private String report() throws IOException {
        photoId = null != params.safeGetStringParam("photoId") ? params.safeGetIntParam("photoId") : null;
        String reportContent = this.params.safeGetStringParam("reportContent");
        response.setContentType("application/json");
        photo = photoService.findById(photoId);
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        if (null == photo) {
            response.getWriter().print(this.makeJsonReturn(2, "无法加载文章对象。"));
            return NONE;
        }
        Report report = new Report();
        report.setUserId(loginUser.getUserId());
        report.setCreateDate(new Date());
        report.setObjId(this.photoId);
        report.setObjType(ObjectType.OBJECT_TYPE_PHOTO.getTypeId());
        String photoTitle = photo.getTitle();
        if (photoTitle.length() > 124) {
            photoTitle = photoTitle.substring(0, 124) + "...";
        }
        report.setObjTitle(photoTitle);
        report.setReportType(reportContent);
        if (reportContent.length() > 0) {
            report.setReportContent(reportContent);
        }
        this.reportService.saveReport(report);
        response.getWriter().print(this.makeJsonReturn(0, "举报成功。"));
        return NONE;
    }

    private String unpraise() throws IOException {
        response.setContentType("application/json");
        if (null == loginUser) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        photoId = null != params.safeGetStringParam("photoId") ? params.safeGetIntParam("photoId") : null;
        Praise praise = this.praiseService.getPraiseByTypeAndUserId(photoId, ObjectType.OBJECT_TYPE_PHOTO.getTypeId(), loginUser.getUserId());
        if (praise != null) {
            this.praiseService.deletePraise(praise);
        }
        // 得到赞的数量
        PraiseQuery qry = new PraiseQuery("p.praiseId, u.loginName, u.trueName, u.userIcon");
        qry.objId = this.photoId;
        qry.objType = ObjectType.OBJECT_TYPE_PHOTO.getTypeId();
        @SuppressWarnings("rawtypes")
        List praise_list = qry.query_map(20);
        if (praise_list == null || praise_list.size() == 0) {
            response.getWriter().print(this.makeJsonReturn(0, ""));
        } else {
            response.getWriter().print(this.makeJsonReturn(0, "" + qry.count(), praise_list));
        }
        return NONE;
    }

    /**
     * 实现赞的功能
     * 
     * <p>
     * 注意：此功能需要用户登录。可以参与的用户是整个用户管理系统中的用户，目前可以仅限制在本地系统中。
     * </p>
     * 
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    private String praise() throws IOException {
        response.setContentType("application/json");
        if (null == getLoginUser()) {
            response.getWriter().print(this.makeJsonReturn(1, "请先登录。"));
            return NONE;
        }
        photoId = null != params.safeGetStringParam("photoId") ? params.safeGetIntParam("photoId") : null;
        Praise praise = praiseService.getPraiseByTypeAndUserId(photoId, ObjectType.OBJECT_TYPE_PHOTO.getTypeId(), this.loginUser.getUserId());
        if (praise == null) {
            praise = new Praise();
            praise.setObjType(ObjectType.OBJECT_TYPE_PHOTO.getTypeId());
            praise.setObjId(this.photoId);
            praise.setUserId(this.loginUser.getUserId());
            this.praiseService.savePraise(praise);
            // 得到赞的数量
            // int count =
            // this.praiseService.getPraiseCountByObjectType(articleId,
            // ObjectType.OBJECT_TYPE_ARTICLE.getTypeId());
            PraiseQuery qry = new PraiseQuery("p.praiseId, u.loginName, u.trueName, u.userIcon");
            qry.objId = this.photoId;
            qry.objType = ObjectType.OBJECT_TYPE_PHOTO.getTypeId();
            List praise_list = qry.query_map(20);
            if (praise_list == null || praise_list.size() == 0) {
                response.getWriter().print(this.makeJsonReturn(0, ""));
            } else {
                response.getWriter().print(this.makeJsonReturn(0, "" + qry.count(), praise_list));
            }
        } else {
            response.getWriter().print(this.makeJsonReturn(2, "你已经赞过啦。"));
        }
        return NONE;
    }

    // 获取当前图片的title,loginName 及 该图片的系统分类下的前10张图片,如果该图片不属于
    @SuppressWarnings("unchecked")
    private void get_photosAndSysList(Integer photoId) {

        PhotoQuery photosQuery = new PhotoQuery(
                "p.photoId, p.title,u.loginName,p.sysCateId,p.userStaple,p.createDate,p.userId,p.commentCount,p.href,p.viewCount,p.summary,p.tags");
        photosQuery.setPhotoId(photoId);
        Object obj = photosQuery.query_map().get(0);
        Map<String, Object> map = null != obj ? (HashMap<String, Object>) obj : null;
        photo = new Photo();
        if (null != map && map.size() != 0) {
            photo.setTitle(map.get("title").toString());
            photo.setUserStaple((Integer) map.get("userStaple"));
            photo.setSysCateId((Integer) map.get("sysCateId"));
            photo.setUserId((Integer) map.get("userId"));
            photo.setCreateDate((Date) map.get("createDate"));
            photo.setCommentCount((Integer) map.get("commentCount"));
            photo.setPhotoId((Integer) map.get("photoId"));
            photo.setHref((String) map.get("href"));
            photo.setViewCount((Integer) map.get("viewCount"));
            photo.setSummary((String) map.get("summary"));
            photo.setTags((String) map.get("tags"));
        } else {
            photo = null;
        }
        request.setAttribute("loginName", map.get("loginName"));
        request.setAttribute("photo", photo);
        photosQuery.setPhotoId(null);
        if (null != photo.getSysCateId()) {
            PhotoQuery photosQuery1 = new PhotoQuery("p.photoId, p.title,u.loginName,p.href,p.createDate");
            photosQuery1.setSysCateId(photo.getSysCateId());
            photosQuery1.setRemovePhotoId(photoId);
            photosQuery1.setMaxResults(10);
            request.setAttribute("photo_details", photosQuery1.query_map());
            photosQuery1.setSysCateId(null);
        } else if (null != photo.getUserStaple()) {
            PhotoQuery photosQuery2 = new PhotoQuery("p.photoId, p.title,u.loginName,p.href,p.createDate");
            photosQuery2.setUserStapleId(photo.getUserStaple());
            photosQuery2.setRemovePhotoId(photoId);
            photosQuery2.setMaxResults(10);
            request.setAttribute("photo_details", photosQuery2.query_map());
            photosQuery2.setUserStapleId(null);
        } else {
            PhotoQuery photosQuery3 = new PhotoQuery("p.photoId, p.title,u.loginName,p.href,p.createDate");
            photosQuery3.setUserStapleId(-1);
            photosQuery3.setSysCateId(-1);
            photosQuery3.setMaxResults(10);
            request.setAttribute("photo_details", photosQuery3.query_map());
        }
    }

    private void get_new_photo_list() {
        PhotoQuery photosQuery = new PhotoQuery("p.photoId, p.title, p.createDate, p.href, u.userId, u.loginName, u.nickName, p.summary");
        photosQuery.orderType = PhotoQuery.ORDER_TYPE_PHOTOID_DESC;
        Object obj = photosQuery.query_map(18);
        request.setAttribute("new_photo_list", obj);
    }

    private void get_hot_photo_list() {
        PhotoQuery photosQuery = new PhotoQuery("p.photoId, p.title,u.loginName,p.createDate,p.href");
        photosQuery.orderType = PhotoQuery.ORDER_TYPE_VIEWCOUNT_DESC;
        Object obj = photosQuery.query_map(20);
        request.setAttribute("hot_photo_list", obj);
    }

    private void get_photo_category() {
        // 得到根一级图片分类.
        List<JSONObject> photo_cates = get_photo_root_category();
        PhotoQuery photosQuery = new PhotoQuery("p.photoId, p.title, p.href, u.loginName");
        if(photo_cates == null || photo_cates.size() == 0){
            return;
        }        
        for (JSONObject c : photo_cates) {
            photosQuery.setSysCateId((Integer) c.get("categoryId"));
            c.put("photo_list", photosQuery.query_map(10));
        }
        if (photo_cates.size() == 0) {
            Pager pager = new ParamUtil(request).createPager();
            pager.setItemName("图片");
            pager.setItemUnit("张");
            pager.setPageSize(24);
            photosQuery = new PhotoQuery("p.photoId, p.title, p.createDate, p.href, u.userId, u.loginName, u.nickName, p.summary");
            photosQuery.orderType = 0;
            pager.setTotalRows(photosQuery.count());
            request.setAttribute("photo_list_all", photosQuery.query_map(pager));
            request.setAttribute("pager", pager);
        }
        request.setAttribute("photo_cates", photo_cates);
        photosQuery.setSysCateId(null);
    }

    @SuppressWarnings("unchecked")
    private List<JSONObject> get_photo_root_category() {
        String cacheKey = "photo_root_cate";
        List<JSONObject> root_cates = (List<JSONObject>)this.cacheService.get(cacheKey);
        if(root_cates != null){
            return root_cates;
        }
        
        CategoryTreeModel photo_categories = categoryService.getCategoryTree("photo");
        root_cates = new ArrayList<JSONObject>();
        if(photo_categories == null || photo_categories.getAll() == null || photo_categories.getAll().size() == 0){
            return null;
        }
        for (CategoryModel c : photo_categories.getAll()) {
            JSONObject root_cate = new JSONObject();
            root_cate.put("categoryId", c.getCategoryId());
            root_cate.put("categoryName", c.getName());
            root_cates.add(root_cate);
        }
        this.cacheService.put(cacheKey, root_cates);
        return root_cates;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setPraiseService(PraiseService praiseService) {
        this.praiseService = praiseService;
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    public void setFavoritesService(UFavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    public void setPhotoService(PhotoService photoService) {
        this.photoService = photoService;
    }

    public void setBrowsingService(BrowsingService browsingService) {
        this.browsingService = browsingService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
