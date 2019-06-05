package cn.edustar.jitar.pojos;

/**
 * PVoteUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VoteUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 743918970309053091L;
	private Integer voteUserId;
	private int voteId;
	private int userId;
	private String addIp;

	// Constructors

	/** default constructor */
	public VoteUser() {
	}

	public Integer getVoteUserId() {
		return voteUserId;
	}

	public void setVoteUserId(Integer voteUserId) {
		this.voteUserId = voteUserId;
	}

	public int getVoteId() {
		return voteId;
	}

	public void setVoteId(int voteId) {
		this.voteId = voteId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}


}