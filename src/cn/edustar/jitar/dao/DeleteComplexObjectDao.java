package cn.edustar.jitar.dao;

import cn.edustar.jitar.pojos.Unit;

public interface DeleteComplexObjectDao {

	public void removeArticleUnitPathInfo(Unit unit);
	public void removeResourceUnitPathInfo(Unit unit);
	public void removeUserUnitPathInfo(Unit unit);
}
