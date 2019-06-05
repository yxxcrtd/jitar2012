package cn.edustar.jitar.pojos;

/**
 * 学科统计项目包括：学科ID，学科名称，工作室数，原创文章数、原创文章积分，转载文章数，转载文章积分，推荐文章数，推荐文章积分，
 * 			   资源数，资源积分，推荐资源数，推荐资源积分，评论数，评论积分，协作组数，集备数，活动数
 * @author dell
 *
 */
public class SubjectStat implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int subjectId;						//学科ID
	private String subjectName;					//学科名称
	private int userCount;						//工作室数
	private int originalArticleCount;			//原创文章数		typeState=0
	private float originalArticleScore;			//原创文章积分
	private int referencedArticleCount;			//转载文章数		typeState=1
	private float referencedArticleScore;		//转载文章积分
	private int recommendArticleCount;			//推荐文章数		recommendState=true
	private float recommendArticleScore;		//推荐文章积分
	private int resourceCount;					// 资源数
	private float  resourceScore;				//资源积分
	private int recommendResourceCount;			//推荐资源数
	private float  recommendResourceScore;		//推荐资源积分
	private int commentCount;					//评论数
	private float commentScore;					//评论积分
	private int groupCount;						//协作组数
	private int prepareCourseCountCount;		//协作组数
	private int actionCount;					//活动数
	
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public int getOriginalArticleCount() {
		return originalArticleCount;
	}
	public void setOriginalArticleCount(int originalArticleCount) {
		this.originalArticleCount = originalArticleCount;
	}
	public float getOriginalArticleScore() {
		return originalArticleScore;
	}
	public void setOriginalArticleScore(float originalArticleScore) {
		this.originalArticleScore = originalArticleScore;
	}
	public int getReferencedArticleCount() {
		return referencedArticleCount;
	}
	public void setReferencedArticleCount(int referencedArticleCount) {
		this.referencedArticleCount = referencedArticleCount;
	}
	public float getReferencedArticleScore() {
		return referencedArticleScore;
	}
	public void setReferencedArticleScore(float referencedArticleScore) {
		this.referencedArticleScore = referencedArticleScore;
	}
	public int getRecommendArticleCount() {
		return recommendArticleCount;
	}
	public void setRecommendArticleCount(int recommendArticleCount) {
		this.recommendArticleCount = recommendArticleCount;
	}
	public float getRecommendArticleScore() {
		return recommendArticleScore;
	}
	public void setRecommendArticleScore(float recommendArticleScore) {
		this.recommendArticleScore = recommendArticleScore;
	}
	public int getResourceCount() {
		return resourceCount;
	}
	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}
	public float getResourceScore() {
		return resourceScore;
	}
	public void setResourceScore(float resourceScore) {
		this.resourceScore = resourceScore;
	}
	public int getRecommendResourceCount() {
		return recommendResourceCount;
	}
	public void setRecommendResourceCount(int recommendResourceCount) {
		this.recommendResourceCount = recommendResourceCount;
	}
	public float getRecommendResourceScore() {
		return recommendResourceScore;
	}
	public void setRecommendResourceScore(float recommendResourceScore) {
		this.recommendResourceScore = recommendResourceScore;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public float getCommentScore() {
		return commentScore;
	}
	public void setCommentScore(float commentScore) {
		this.commentScore = commentScore;
	}
	public int getGroupCount() {
		return groupCount;
	}
	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}
	public int getPrepareCourseCountCount() {
		return prepareCourseCountCount;
	}
	public void setPrepareCourseCountCount(int prepareCourseCountCount) {
		this.prepareCourseCountCount = prepareCourseCountCount;
	}
	public int getActionCount() {
		return actionCount;
	}
	public void setActionCount(int actionCount) {
		this.actionCount = actionCount;
	}
	
}
