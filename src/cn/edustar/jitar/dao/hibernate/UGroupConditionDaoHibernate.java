package cn.edustar.jitar.dao.hibernate;
import java.util.List;

import cn.edustar.jitar.pojos.UGroupCondition;
import cn.edustar.jitar.dao.UGroupConditionDao;

public class UGroupConditionDaoHibernate  extends BaseDaoHibernate implements UGroupConditionDao{
	@SuppressWarnings("unchecked")
	public List<UGroupCondition> getUGroupCondition(){
		List<UGroupCondition> list = this.getSession().createQuery("FROM UGroupCondition Order By conditionId").list();
		return list;
	}
}
