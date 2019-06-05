package cn.edustar.jitar.dao.hibernate;

import java.util.List;



import cn.edustar.jitar.dao.JitarColumnDao;
import cn.edustar.jitar.pojos.JitarColumn;
import cn.edustar.jitar.pojos.JitarColumnNews;

public class JitarColumnDaoHibernate extends BaseDaoHibernate implements
		JitarColumnDao {

	public JitarColumn getJitarColumnById(int columnId) {
		return (JitarColumn)this.getSession().get(JitarColumn.class, columnId);
	}

	@SuppressWarnings("unchecked")
	public List<JitarColumn> getJitarColumnList() {
		String queryString = "FROM JitarColumn Order By columnId";
		return (List<JitarColumn>) this.getSession().createQuery(queryString).list();
	}
	
	public void saveOrUpdateJitarColumnNews(JitarColumnNews jitarColumnNews)
	{
		this.getSession().saveOrUpdate(jitarColumnNews);
	}

	public JitarColumnNews getJitarColumnNewsById(int columnNewsId)
	{
		return (JitarColumnNews)this.getSession().get(JitarColumnNews.class, columnNewsId);
	}
	
	public void deleteJitarColumnNews(JitarColumnNews jitarColumnNews)
	{
		this.getSession().delete(jitarColumnNews);
		this.getSession().flush();
	}
}
