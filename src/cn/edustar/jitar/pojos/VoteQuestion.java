package cn.edustar.jitar.pojos;

/**
 * PVoteQuestion entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VoteQuestion implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5585914158340544578L;
	private Integer voteQuestionId;
	private String title;
	private Byte questionType;
	private int maxSelectCount;
	private int voteId;
	private int itemIndex;
	// Constructors

	/** default constructor */
	public VoteQuestion() {
	}

	public Integer getVoteQuestionId() {
		return voteQuestionId;
	}

	public void setVoteQuestionId(Integer voteQuestionId) {
		this.voteQuestionId = voteQuestionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Byte getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Byte questionType) {
		this.questionType = questionType;
	}

	public int getMaxSelectCount() {
		return maxSelectCount;
	}

	public void setMaxSelectCount(int maxSelectCount) {
		this.maxSelectCount = maxSelectCount;
	}

	public int getVoteId() {
		return voteId;
	}

	public void setVoteId(int voteId) {
		this.voteId = voteId;
	}

	public int getItemIndex() {
		return itemIndex;
	}

	public void setItemIndex(int itemIndex) {
		this.itemIndex = itemIndex;
	}

	// Property accessors


}