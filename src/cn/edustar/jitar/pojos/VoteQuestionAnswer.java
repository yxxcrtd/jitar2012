package cn.edustar.jitar.pojos;

/**
 * PVoteQuestionAnswer entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VoteQuestionAnswer implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3047015172575865675L;
	private Integer voteQuestionAnswerId;
	private String answerContent;
	private int voteQuestionId;
	private int itemIndex;
	private int voteCount;
	
	// Constructors

	/** default constructor */
	public VoteQuestionAnswer() {
	}

	public Integer getVoteQuestionAnswerId() {
		return voteQuestionAnswerId;
	}

	public void setVoteQuestionAnswerId(Integer voteQuestionAnswerId) {
		this.voteQuestionAnswerId = voteQuestionAnswerId;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public int getVoteQuestionId() {
		return voteQuestionId;
	}

	public void setVoteQuestionId(int voteQuestionId) {
		this.voteQuestionId = voteQuestionId;
	}

	public int getItemIndex() {
		return itemIndex;
	}

	public void setItemIndex(int itemIndex) {
		this.itemIndex = itemIndex;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}


}