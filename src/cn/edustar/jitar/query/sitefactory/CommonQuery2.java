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
public class CommonQuery2 {
	
	private Session session;
	private String whereClause = "";
	private String orderByClause = "";
	private String hql;
	private int startRow = 0;
	private int maxRow = 20;

	public CommonQuery2(Session session) {
		this.session = session;
	}

	@SuppressWarnings("rawtypes")
	public List getQueryList() {
		if(!this.whereClause.equals(""))
		{
			hql += " Where " + this.whereClause;
		}
		if(!this.orderByClause.equals(""))
		{
			hql += " Order By " + this.orderByClause;
		}	
		if(startRow<0) startRow = 0;
		if(startRow>maxRow) startRow = maxRow;
		final int size1= maxRow;  
        final int startR = startRow;         
        Query query = session.createQuery(hql);  
        query.setFirstResult(startR);  
        query.setMaxResults(size1);  
        return query.list();  
       
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
