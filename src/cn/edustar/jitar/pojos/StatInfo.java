package cn.edustar.jitar.pojos;

/**
 * 简单的统计信息, 可以用于给用户、协作组等存储其统计信息.
 *
 *
 */
public class StatInfo {
	/** 用户标识, 依据不同使用者是否使用其值. */
	public int userId;
	
	/** 协作组标识, 依据不同使用者是否使用其值. */
	public int groupId;
	
	/** 文章数, 依据不同使用者是否使用其值. */
	public int articleCount;
	
	/** 资源数, 依据不同使用者是否使用其值. */
	public int resourceCount;
	
	/** 网课数, 依据不同使用者是否使用其值. */
	public int courseCount;
	
	/** 图片数, 依据不同使用者是否使用其值. */
	public int photoCount;
	
	/** 主题数, 依据不同使用者是否使用其值. */
	public int topicCount;
	
	/** 回复数, 依据不同使用者是否使用其值. */
	public int replyCount;
	
	/** 评论数, 依据不同使用者是否使用其值. */
	public int commentCount;
	
	/** 活动数, 依据不同使用者是否使用其值. */
	public int actionCount;
}
