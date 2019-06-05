package cn.edustar.jitar.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.VideoDao;
import cn.edustar.jitar.pojos.Video;
import cn.edustar.jitar.query.VideoQueryParam;

/**
 * 视频DAO的实现
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 20, 2009 4:08:20 PM
 */
public class VideoDaoHibernate extends BaseDaoHibernate implements VideoDao {
	
	/** 增加视频播放次数 */
	private static final String INCRESE_VIDEO_VIEW_COUNT = "UPDATE Video SET viewCount = viewCount + 1 WHERE (videoId = ?)";
	
	/** 增加视频评论次数 */
	private static final String INCRESE_VIDEO_COMMENT_COUNT = "UPDATE Video SET commentCount = commentCount + 1 WHERE (videoId = ?)";
	
	/** 更新视频状态 */
	private static final String UPDATE_VIDEO_STATUS = "UPDATE Video SET status = ? WHERE (videoId = ?)";
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.VideoDao#save(cn.edustar.jitar.pojos.Video)
	 */
	public void save(Video video) {
		this.getSession().save(video);
		this.getSession().flush();
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.VideoDao#getVideoList(cn.edustar.jitar.query.VideoQueryParam, cn.edustar.data.Pager)
	 */
	@SuppressWarnings("unchecked")
	public List<Video> getVideoList(VideoQueryParam param, Pager pager) {
		QueryHelper query = param.createQuery();
		if (pager == null) {
			return query.queryData(getSession(), -1, param.count);
		} else {
			return query.queryDataAndTotalCount(getSession(), pager);
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.VideoDao#findById(int)
	 */
	public Video findById(int videoId) {
		return (Video) this.getSession().get(Video.class, videoId);
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.VideoDao#delVideo(cn.edustar.jitar.pojos.Video)
	 */
	public void delVideo(Video video) {
		this.getSession().delete(video);
	}
	
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.dao.VideoDao#increseViewCount(int)
	 */
	public void increaseViewCount(int videoId) {
		this.getSession().createQuery(INCRESE_VIDEO_VIEW_COUNT).setInteger(0, videoId).executeUpdate();
	}
	public void increaseViewCount(int videoId, int count){
	    String hql = "UPDATE Video SET viewCount = viewCount + :viewCount WHERE videoId = :videoId";
	    this.getSession().createQuery(hql).setInteger("viewCount", count).setInteger("videoId", videoId).executeUpdate();
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.VideoDao#increseCommentCount(int)
	 */
	public void increseCommentCount(int videoId) {
		this.getSession().createQuery(INCRESE_VIDEO_COMMENT_COUNT).setInteger(0, videoId).executeUpdate();
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.VideoDao#updateAllVideoCommentCount()
	 */
	@SuppressWarnings("unchecked")
	public void updateAllVideoCommentCount() {
		String hql = "UPDATE Video Set commentCount=0";
		this.getSession().createQuery(hql).executeUpdate();
		hql = "SELECT a.videoId ,Count(b.objId) FROM Video a, Comment b WHERE a.videoId=b.objId and b.objType=17 group by a.videoId";
		List list = this.getSession().createQuery(hql).list();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			int _videoId = Integer.parseInt(obj[0].toString());
			int count = Integer.parseInt(obj[1].toString());
			hql = "UPDATE Video Set commentCount=? Where videoId=?";
			this.getSession().createQuery(hql).setInteger(0, count).setInteger(1, _videoId).executeUpdate();

		}
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.VideoDao#updateVideoCommentCount(int)
	 */
	@SuppressWarnings("unchecked")
	public void updateVideoCommentCount(int userId) {
		String hql = "UPDATE Video Set commentCount=0 Where userId=?";
		this.getSession().createQuery(hql).setInteger(0, userId).executeUpdate();
		hql = "SELECT a.videoId ,Count(b.objId) from Video a, Comment b Where a.videoId=b.objId and b.objType=17 and a.userId=? group by a.videoId";
		List list = this.getSession().createQuery(hql).setInteger(0, userId).list();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			int _videoId = Integer.parseInt(obj[0].toString());
			int count = Integer.parseInt(obj[1].toString());
			hql = "UPDATE Video Set commentCount=? Where videoId=?";
			this.getSession().createQuery(hql).setInteger(0, count).setInteger(1, _videoId).executeUpdate();

		}
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.VideoDao#getVideoCategoryList()
	 */
	@SuppressWarnings("unchecked")
	public List getVideoCategoryList() {
		String hql = "SELECT DISTINCT staple FROM Video";
		List list = getSession().createQuery(hql).list();
		return list;

	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.VideoDao#updateAuditState(cn.edustar.jitar.pojos.Video, boolean)
	 */
	public void updateAuditState(Video v, boolean state) {
		String hql = "UPDATE Video SET auditState = ? WHERE videoId = ?";
		short stateValue = 0;
		if (state) {
			stateValue = 0;
		} else {
			stateValue = 1;
		}
		getSession().createQuery(hql).setShort(0, stateValue).setInteger(1, v.getVideoId() ).executeUpdate();
	}
	
	public void updateVideo(Video video)
	{
		this.getSession().update(video);
	}
	
	public void batchClearVideoUserCategory(int userCateId)
	{
		String hql = "UPDATE Video SET userCateId = NULL WHERE userCateId = ? ";
		getSession().createQuery(hql).setInteger(0,userCateId).executeUpdate();		
	}

	@Override
	public void evict(Object object) {
		this.getSession().evict(object);
	}

	@Override
	public void flush() {
		this.getSession().flush();
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.dao.VideoDao#updateVideoStatus(int)
	 */
	public void updateVideoStatus(int videoId, int status) {
		this.getSession().createQuery(UPDATE_VIDEO_STATUS).setInteger(0, status).setInteger(1, videoId).executeUpdate();
	}
	
}
