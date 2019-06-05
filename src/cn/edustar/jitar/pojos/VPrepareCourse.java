package cn.edustar.jitar.pojos;
import java.util.*;
public class VPrepareCourse implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2129933532444438840L;

	/** 备课标识 */
	private Integer prepareCourseId;
	
	/** 备课名称 */
	private String title;
	
	/** 备课发起人标识 */
	private Integer createUserId;
	
	/** 备课主备人标识 */
	private Integer leaderId;
	
	/** 备课创建时间 */
	private Date createDate;
	
	/** 备课开始时间 */
	private Date startDate;
	
	/** 备课结束时间 */
	private Date endDate;
	
	/** 备课所属学科 */
	private Integer metaSubjectId;
	
	/** 备课所属学段 */
	private Integer gradeId;
	
	/** 成员数 */
	private int memberCount;
	
	/** 文章数 */
	private int articleCount;
	
	/** 资源数 */
	private int resourceCount;
	
	/** 讨论主题回复数数 */
	private int topicReplyCount;
	
	/** 讨论主题数 */
	private int topicCount;
	
	/** 活动数 */
	private int actionCount;
	
	/** 查看数 */
	private int viewCount;
	
	/** 状态 0:正常，1：待审核，2：锁定，其他未知 */
	private int status;
	
	/** 是否生成了备课 */
	private Boolean prepareCourseGenerated;
	
	/** 显示顺序 */
	private Integer itemOrder;
	
	/** 是否推荐状态： false - 未推荐，true - 推荐 */
	private boolean recommendState;
	
	private Integer privateCount;
	private Integer editCount;

	private String createUserName;
	private String leaderUserName;
	private String createUserNickName;
	private String leaderUserNickName;
	private int createUserUnitId;
	private int leaderUserUnitId;
	private String createUserUnitTitle;
	private String leaderUserUnitTitle;
	public Integer getItemOrder() {
		return itemOrder;
	}

	public void setItemOrder(Integer itemOrder) {
		this.itemOrder = itemOrder;
	}

	/** default constructor */
	public VPrepareCourse() {
	}

	// Property accessors

	public Integer getPrepareCourseId() {
		return this.prepareCourseId;
	}

	public void setPrepareCourseId(Integer prepareCourseId) {
		this.prepareCourseId = prepareCourseId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getLeaderId() {
		return this.leaderId;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getMetaSubjectId() {
		return this.metaSubjectId;
	}

	public void setMetaSubjectId(Integer metaSubjectId) {
		this.metaSubjectId = metaSubjectId;
	}

	public Integer getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}


	public int getMemberCount() {
		return this.memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public int getArticleCount() {
		return this.articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public int getResourceCount() {
		return this.resourceCount;
	}

	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}

	public int getTopicReplyCount() {
		return this.topicReplyCount;
	}

	public void setTopicReplyCount(int topicReplyCount) {
		this.topicReplyCount = topicReplyCount;
	}

	public int getTopicCount() {
		return this.topicCount;
	}

	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}

	public int getActionCount() {
		return this.actionCount;
	}

	public void setActionCount(int actionCount) {
		this.actionCount = actionCount;
	}

	public int getViewCount() {
		return this.viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}




	public Boolean getPrepareCourseGenerated() {
		return prepareCourseGenerated;
	}

	public void setPrepareCourseGenerated(Boolean prepareCourseGenerated) {
		this.prepareCourseGenerated = prepareCourseGenerated;
	}
	/** 是否推荐状态： false - 未推荐，true - 推荐 */
	public boolean getRecommendState() {
		return this.recommendState;
	}

	/** 是否推荐状态： false - 未推荐，true - 推荐 */
	public void setRecommendState(boolean recommendState) {
		this.recommendState = recommendState;
	}

	public Integer getPrivateCount() {
		return privateCount;
	}

	public void setPrivateCount(Integer privateCount) {
		this.privateCount = privateCount;
	}

	public Integer getEditCount() {
		return editCount;
	}

	public void setEditCount(Integer editCount) {
		this.editCount = editCount;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getLeaderUserName() {
		return leaderUserName;
	}

	public void setLeaderUserName(String leaderUserName) {
		this.leaderUserName = leaderUserName;
	}

	public String getCreateUserNickName() {
		return createUserNickName;
	}

	public void setCreateUserNickName(String createUserNickName) {
		this.createUserNickName = createUserNickName;
	}

	public String getLeaderUserNickName() {
		return leaderUserNickName;
	}

	public void setLeaderUserNickName(String leaderUserNickName) {
		this.leaderUserNickName = leaderUserNickName;
	}

	public int getCreateUserUnitId() {
		return createUserUnitId;
	}

	public void setCreateUserUnitId(int createUserUnitId) {
		this.createUserUnitId = createUserUnitId;
	}

	public int getLeaderUserUnitId() {
		return leaderUserUnitId;
	}

	public void setLeaderUserUnitId(int leaderUserUnitId) {
		this.leaderUserUnitId = leaderUserUnitId;
	}

	public String getCreateUserUnitTitle() {
		return createUserUnitTitle;
	}

	public void setCreateUserUnitTitle(String createUserUnitTitle) {
		this.createUserUnitTitle = createUserUnitTitle;
	}

	public String getLeaderUserUnitTitle() {
		return leaderUserUnitTitle;
	}

	public void setLeaderUserUnitTitle(String leaderUserUnitTitle) {
		this.leaderUserUnitTitle = leaderUserUnitTitle;
	}


}