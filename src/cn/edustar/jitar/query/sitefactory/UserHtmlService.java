package cn.edustar.jitar.query.sitefactory;

import java.util.List;

import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.ArticleUser;
import cn.edustar.jitar.pojos.HtmlTimer;
import cn.edustar.jitar.pojos.User;

/**
 * 生成用户静态文件的服务接口
 * 生成用户静态采用请求触发的方法，而不采用定时器，采用定时器，可能很多程序需要进行修改。
 * 调用生成用户静态页面的执行者，可以是用户自己，也可能是管理员、第三方用户触发执行。
 * 
 * 
 * @author admin
 *
 */
public interface UserHtmlService {
	/**
	 * 生成指定用户的首页
	 * @param user
	 */
	public void GenUserIndex(User user);
	
	/**
	 * 生成指定用户的文章列表页面
	 * @param user
	 */
	public void GenUserArticleList(User user);
	
	/**
	 * 生成指定用户的个人档案页面
	 * @param user
	 */
	public void GenUserProfile(User user);
	
	/**
	 * 生成指定用户的资源列表页面
	 * @param user
	 */
	public void GenUserResourceList(User user);
	
	/**
	 * 生成用户的视频列表页面
	 * @param user
	 */
	public void GenUserVideoList(User user);
	
	/**
	 * 生成指定用户的图片列表页面
	 * @param user
	 */
	public void GenUserPhotoList(User user);
	
	/**
	 * 生成用户创建的活动列表
	 * @param user
	 */
	public void GenUserCreateActionList(User user);
	
	/**
	 * 深沉指定用户参与的活动列表
	 * @param user
	 */
	public void GenUserJoinedActionList(User user);
	
	/**
	 * 生成指定用户的发起的集备
	 * @param user
	 */
	public void GenUserCreatePrepareCourseList(User user);
	
	/**
	 * 生成指定用户加入的集备
	 * @param user
	 */
	public void GenUserJoinedPrepareCourseList(User user);
	
	/**
	 * 生成指定用户的问题与解答列表
	 * @param user
	 */
	public void GenUserQuestionList(User user);
	
	/**
	 * 生成指定用户的投票列表
	 * @param user
	 */
	public void GenUserVoteList(User user);
	
	/**
	 * 生成指定用户加入的协作组列表
	 * @param user
	 */
	public void GenUserGroupList(User user);
	
	/**
	 * 生成指定用户的留言列表
	 * @param user
	 */
	public void GenUserLeaveWordList(User user);
	
	/**
	 * 生成指定用户的好友列表
	 * @param user
	 */
	public void GenUserFriendList(User user);
	
	/**
	 * 生成指定用户的专题讨论页面
	 * @param user
	 */
	public void GenUserSpecialTopic(User user);	
	
	public void saveOrUpdate(HtmlTimer htmlTimer);
	
	public HtmlTimer getHtmlTimer(int objectId, int objectType);
	
	public void GenUserArticle(User user, Article article) throws Exception;
	public List<Article> getSimilarArticle(Article article);
	
	//测试
	public List<ArticleUser> getArticleUser(final int userId);
}
