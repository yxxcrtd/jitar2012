package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.CommentDao;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.service.ArticleCommentQueryParam;
import cn.edustar.jitar.service.CommentQueryParam;

/**
 * 评论DAO的实现
 */
public class CommentDaoHibernate extends BaseDaoHibernate implements CommentDao {

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.CommentDao#getComment(int)
     */
    public Comment getComment(int id) {
        return (Comment) this.getSession().get(Comment.class, id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.iface.CommentDao#saveComment(cn.edustar.jitar.pojos
     * .Comment)
     */
    public void saveComment(Comment comment) {
        this.getSession().saveOrUpdate(comment);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.CommentDao#deleteComment(cn.edustar.jitar.pojos.
     * Comment)
     */
    public void deleteComment(Comment comment) {
        this.getSession().delete(comment);
        this.getSession().flush();
    }
    
    
    public void deleteCommentReply(Comment comment){
        String hql = "DELETE FROM Comment WHERE objType =:objType And objId=:objId";
        this.getSession().createQuery(hql).setInteger("objType", ObjectType.OBJECT_TYPE_COMMENT.getTypeId()).setInteger("objId", comment.getId()).executeUpdate();
        this.getSession().flush();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.CommentDao#updateCommentAuditState(cn.edustar.jitar
     * .pojos.Comment, boolean)
     */
    public void updateCommentAuditState(Comment comment, boolean audit) {
        String hql = "UPDATE Comment SET audit = :audit WHERE id = :id";
        this.getSession().createQuery(hql).setBoolean("audit", audit).setInteger("id", comment.getId()).executeUpdate();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.iface.CommentDao#getObjectCommentList(int, int,
     * cn.edustar.jitar.util.Pager)
     */
    @SuppressWarnings("unchecked")
    public List<Comment> getCommentList(CommentQueryParam param, Pager pager) {
        QueryHelper query = param.createQuery();
        if (pager == null)
            return query.queryData(this.getSession());
        else
            return query.queryDataAndTotalCount(this.getSession(), pager);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.CommentDao#getRecentCommentAboutUser(int, int)
     */
    @SuppressWarnings("unchecked")
    public List<Comment> getRecentCommentAboutUser(int userId, int count) {
        String hql = "FROM Comment WHERE aboutUserId = :aboutUserId AND audit = true ORDER BY id DESC";
        return this.getSession().createQuery(hql).setInteger("aboutUserId", userId).setMaxResults(count).setFirstResult(0).list();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.CommentDao#getUserArticleCommentList(int,
     * cn.edustar.data.Pager)
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> getUserArticleCommentList(int userId, Pager pager, boolean includeUnaudited) {
        // 创建查询辅助对象
        QueryHelper query = new QueryHelper();
        query.selectClause = "SELECT c, a ";
        query.fromClause = " FROM Comment c, Article a ";
        query.whereClause = " WHERE c.objType = :objType" + " AND c.objId = a.articleId " + " AND a.userId = :userId";
        if (includeUnaudited == false)
            query.addAndWhere(" c.audit = true ");
        query.orderClause = " ORDER BY c.id DESC ";

        query.setInteger("objType", ObjectType.OBJECT_TYPE_ARTICLE.getTypeId());
        query.setInteger("userId", userId);

        // 查询总量
        pager.setTotalRows(query.queryTotalCount(this.getSession()));
        return query.queryData(this.getSession());
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> getUserArticleCommentListEx(int userId, Pager pager, boolean includeUnaudited) {
        // 创建查询辅助对象
        QueryHelper query = new QueryHelper();
        query.selectClause = "SELECT c, a ";
        query.fromClause = " FROM Comment c, Article a ";
        query.whereClause = " WHERE c.objType = :objType" + " AND c.objId = a.articleId " + " AND c.userId = :userId";
        if (includeUnaudited == false)
            query.addAndWhere(" c.audit = true ");
        query.orderClause = " ORDER BY c.id DESC ";

        query.setInteger("objType", ObjectType.OBJECT_TYPE_ARTICLE.getTypeId());
        query.setInteger("userId", userId);

        // 查询总量
        pager.setTotalRows(query.queryTotalCount(this.getSession()));
        return query.queryData(this.getSession());
    }
    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.dao.CommentDao#deleteCommentByObject(int, int)
     */
    public int deleteCommentByObject(int objectType, int objectId) {
        String hql = "DELETE FROM Comment WHERE objType = :objType AND objId = :objId";
        int ret = this.getSession().createQuery(hql).setInteger("objType", objectType).setInteger("objId", objectId).executeUpdate();
        this.getSession().flush();
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.CommentDao#getUserResourceCommentList(cn.edustar
     * .jitar.service.CommentQueryParam, cn.edustar.data.Pager)
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> getUserResourceCommentList(CommentQueryParam param, Pager pager) {
        // 构造查询
        QueryHelper query = param.createQuery();

        // 根据需要修改查询
        query.selectClause = "SELECT c, r ";
        query.fromClause = " FROM Comment c, Resource r ";
        query.addAndWhere("c.objId = r.resourceId");

        // 查询数据
        return query.queryDataAndTotalCount(this.getSession(), pager);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.CommentDao#statCommentCountByUserAndObject(cn.edustar
     * .jitar.service.CommentQueryParam)
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> statCommentCountByUserAndObject(CommentQueryParam param) {
        // 构造查询
        QueryHelper query = param.createQuery();
        query.selectClause = "SELECT c.objId, COUNT(*) ";
        query.orderClause = "";
        query.groupbyClause = " GROUP BY c.objId";
        return query.queryData(this.getSession());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.CommentDao#getArticleCommentList(cn.edustar.jitar
     * .service.ArticleQueryParam, cn.edustar.jitar.service.CommentQueryParam,
     * cn.edustar.data.Pager)
     */
    @SuppressWarnings("unchecked")
    public List<Comment> getArticleCommentList(ArticleCommentQueryParam param, Pager pager) {
        QueryHelper query = param.createQuery();
        if (pager == null)
            return query.queryData(this.getSession(), -1, param.count);
        else
            return query.queryDataAndTotalCount(this.getSession(), pager);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.dao.CommentDao#getUserVideoCommentList(cn.edustar.jitar
     * .service.CommentQueryParam, cn.edustar.data.Pager)
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> getUserVideoCommentList(CommentQueryParam param, Pager pager) {
        QueryHelper query = param.createQuery();
        if (pager == null)
            return query.queryData(this.getSession(), -1, param.count);
        else
            return query.queryDataAndTotalCount(this.getSession(), pager);

        /*
         * QueryHelper query = param.createQuery(); query.selectClause =
         * "SELECT c, v "; query.fromClause = "FROM Comment c, Video v ";
         * query.whereClause = "WHERE c.objId = v.videoId"; return
         * query.queryDataAndTotalCount(this.getSession(), pager);
         */
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> getUserVideoCommentListEx(CommentQueryParam param, Pager pager) {
        QueryHelper query = param.createQueryEx();
        query.selectClause = "SELECT c, v ";
        return query.queryDataAndTotalCount(this.getSession(), pager);
    }

    @SuppressWarnings("unchecked")
    public List<Comment> getAllCommentByUserId(int userId) {
        String queryString = "FROM Comment Where audit = true And userId = :userId or aboutUserId = :aboutUserId";
        return (List<Comment>) this.getSession().createQuery(queryString).setInteger("userId", userId).setInteger("aboutUserId", userId).list();
    }

    
    
    /** 删除某个用户的所有相关评论 */
    public void deleteAllCommentByUserId(int userId) {
        String queryString = "DELETE FROM Comment Where audit = true And userId = :userId or aboutUserId = :aboutUserId";
        this.getSession().createQuery(queryString).setInteger("userId", userId).setInteger("aboutUserId", userId).executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
    public List<Comment> getCommentListByObject(int objType, int objId){
        String queryString = "FROM Comment Where audit = true And objType = :objType And objId = :objId ORDER BY id ASC";
        return (List<Comment>) this.getSession().createQuery(queryString).setInteger("objType", objType).setInteger("objId", objId).list();
    }

	@SuppressWarnings("unchecked")
    @Override
	public List<Comment> getAllPhotoCommentByPhotoId(Integer photoId) {
	   String queryString = "FROM Comment Where audit = true And objId =:objId And objType = " + ObjectType.OBJECT_TYPE_PHOTO.getTypeId() + " ORDER BY id DESC";
       return (List<Comment>) this.getSession().createQuery(queryString).setInteger("objId", photoId).list();
	}

    @Override
    public void evict(Object object) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub
        
    }

}
