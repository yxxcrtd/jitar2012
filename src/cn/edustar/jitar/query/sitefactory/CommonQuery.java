package cn.edustar.jitar.query.sitefactory;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * 
 * @author admin
 *
 *	文章的查询，供生成静态页面使用
 */
public class CommonQuery {

	private Session session;
	private String whereClause = "";
	private String orderByClause = "";
	private String hql;
	private int startRow = 0;
	private int maxRow = 20;

	public CommonQuery(Session session) {
		this.session = session;
	}

	@SuppressWarnings("rawtypes")
	public List getQueryList() {
		try
		{
			if(!this.whereClause.equals(""))
			{
				hql += " Where " + this.whereClause;
			}
			if(!this.orderByClause.equals(""))
			{
				hql += " Order By " + this.orderByClause;
			}		
	
			Query query = session.createQuery(hql);
			if(startRow<0) startRow = 0;
			if(maxRow<0) maxRow = 20;
			if(startRow>maxRow) startRow = maxRow;
			query.setFirstResult(startRow);
			if(this.maxRow > -1 ) query.setMaxResults(maxRow);
			List lst = query.list();
			return lst;
		}
		catch(Exception ex)
		{
			System.out.println("执行查询时出现错误：" + hql);
			return null;
		}
	}

	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getMaxRow() {
		return maxRow;
	}

	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}
}
