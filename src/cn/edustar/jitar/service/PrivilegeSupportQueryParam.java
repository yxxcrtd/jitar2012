package cn.edustar.jitar.service;

/**
 * 支持限制区域/学科条件的查询参数要实现的接口.
 *
 *
 */
public interface PrivilegeSupportQueryParam {
	/**
	 * 是否支持指定的区域/学科条件模式? 暂时未使用.
	 * @param mode
	 * @return
	 */
	public boolean isModeSupport(int mode);
	
	/**
	 * 权限拒绝, 表示没有任何用户/内容被允许查看.
	 */
	public void denyCrtieria();
	
	/**
	 * 添加 学科(Subject) 限定条件.
	 * @param subjectId
	 */
	public void addSubjectCriteria(Integer subjectId);

	/**
	 * 添加 学段(Grade) 限定条件.
	 * @param gradeId
	 */
	public void addGradeCriteria(Integer gradeId);
	
	/**
	 * 添加 省/直辖市(Province) 限定条件.
	 * @param provinceId
	 */
	public void addProvinceCriteria(Integer provinceId);
	
	/**
	 * 添加 地区/市(City) 限定条件.
	 * @param cityId
	 */
	public void addCityCriteria(Integer cityId);
	
	
	/**
	 * 添加 机构/公司(UnitId) 限定条件.
	 * @param unitId
	 */
	public void addUnitCriteria(Integer unitId);
}
