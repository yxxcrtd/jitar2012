package cn.edustar.jitar.service;

import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.SiteStat;
import cn.edustar.jitar.pojos.Topic;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.UserOnLineStat;

/**
 * 延迟写入统计数据的服务，例如用户博客访问量，站点访问量等
 * 
 *
 */
public interface StatService {

	/**
	 * 得到当前站点统计信息，得到的是一个副本
	 *
	 * @return
	 */
	public SiteStat getSiteStat();

	public void init();
	
	public void updateSiteStat(SiteStat siteStat);
	
	/**
	 * 增加一次用户博客访问量计数
	 *
	 * @param user
	 */
	public void incUserVisitCount(User user);

	/**
	 * 增加一次群组访问量计数
	 *
	 * @param group
	 */
	public void incGroupVisitCount(Group group);

	/**
	 * 增加资源访问计数
	 *
	 * @param resource
	 */
	public void incResourceVisitCount(Resource resource);

	/**
	 * 增加资源下载计数
	 *
	 * @param resource
	 */
	public void incResourceDownloadCount(Resource resource);
	
	/**
	 * 增加一次资源评论的计数
	 *
	 * @param resource
	 */
	public void incResourceCommentCount(Resource resource);
	 
	/**
	 * 增加/减少因为变更了 user 的统计数
	 * 
	 * @param user
	 * @param count
	 */
	public void incUserCount(User user, int count);
	
	/**
	 * 增加/减少因为变更了 group 的统计数
	 * 
	 * @param group
	 * @param count
	 */
	public void incGroupCount(Group group, int count);
	
	/**
	 * 增加/减少指定文章的统计数，包括所有要统计的项。当前有:
	 *   1，站点级文章计数
	 *   2，学科级文章计数
	 *   3，用户级文章计数
	 *   
	 * @param article
	 * @param count
	 */
	public void incArticleCount(Article article, int count);

	/**
	 * 增加/减少指定资源的统计数，包括所有要统计的项。当前有:
	 *   1，站点级资源计数
	 *   2，学科级资源计数
	 *   3，用户级资源计数
	 *   
	 * @param resource
	 * @param count
	 */
	public void incResourceCount(Resource resource, int count);

	/**
	 * 增加/减少指定图片的统计数，包括所有要统计的项
	 * 
	 * @param photo
	 * @param count
	 */
	public void incPhotoCount(Photo photo, int count);
	
	/**
	 * 增加/减少指定主题的统计数，包括所有要统计的项
	 * 
	 * @param topic
	 * @param count
	 */
	public void incTopicCount(Topic topic, int count);

	/**
	 * 增加/减少指定评论的统计数，包括所有要统计的项
	 * 
	 * @param comment
	 * @param count
	 */
	public void incCommentCount(Comment comment, int count);
	
	/**
	 * 学科统计
	 *
	 * @param subjectId
	 * @param gradeId
	 */
	public void subjectStat(int subjectId, int beginGradeId, int endGradeId);
	
	/**
	 * 产品信息
	 *
	 * @param productID
	 * @param productName
	 * @param installDate
	 */
	public void InsertIntoProductInfo(String productID,String productName,String installDate);

	/**
	 * 
	 *
	 * @param dateCount
	 */
	public void InsertIntoStateDateCount(String dateCount);
	
	/**
	 * 得到最高在线人数
	 * 
	 * @return
	 */
	public UserOnLineStat getUserOnLineStat();
	
	/**
	 * 更新用户在线统计表
	 * 
	 * @param userOnLineStat
	 */
	public void updateUserOnLineStat(UserOnLineStat userOnLineStat);
	
}
