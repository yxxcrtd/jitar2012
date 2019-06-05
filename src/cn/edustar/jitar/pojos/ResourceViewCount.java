package cn.edustar.jitar.pojos;

/**
 * ResourceViewCount entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ResourceViewCount implements java.io.Serializable {

	// Fields

	/**  */
	private static final long serialVersionUID = 6256764397030221605L;
	private ResourceViewCountId id;

	// Constructors

	/** default constructor */
	public ResourceViewCount() {
	}

	/** full constructor */
	public ResourceViewCount(ResourceViewCountId id) {
		this.id = id;
	}

	// Property accessors

	public ResourceViewCountId getId() {
		return this.id;
	}

	public void setId(ResourceViewCountId id) {
		this.id = id;
	}

}