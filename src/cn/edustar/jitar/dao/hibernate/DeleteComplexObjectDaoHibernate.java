package cn.edustar.jitar.dao.hibernate;

import cn.edustar.jitar.dao.DeleteComplexObjectDao;
import cn.edustar.jitar.pojos.Unit;

public class DeleteComplexObjectDaoHibernate implements DeleteComplexObjectDao {

	public void removeArticleUnitPathInfo(Unit unit) {
		if(unit == null) return;
		//String queryString;
		//queryString = "UPDATE Article Set "

	}

	public void removeResourceUnitPathInfo(Unit unit) {
		if(unit == null) return;

	}

	public void removeUserUnitPathInfo(Unit unit) {
		if(unit == null) return;

	}

}
