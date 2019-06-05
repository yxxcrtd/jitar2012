package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.data.Pager;

/**
 * 模仿 asp command 对象.
 *
 *
 */
public class Command extends AbstractCommand {
	/**
	 * 缺省构造函数.
	 */
	public Command() {
		
	}
	
	/**
	 * 使用指定 hql 语句构造.
	 * @param hql
	 */
	public Command(String hql) {
		super(hql);
	}
	
	
	/**
	 * 打开结果集, 实际返回结果依据查询语句而异.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List open() {
		// 执行 Query 得到结果
		return getDatabase().executeQuery(this, -1, -1);
	}
	
	/**
	 * 返回结果集, 返回前 top 个结果.
	 * @param topNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List open(int topNum) {
		// 执行 Query 得到结果
		return getDatabase().executeQuery(this, -1, topNum);
	}
	
	/**
	 * 使用指定的分页参数进行数据查询.
	 * @param pager
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List open(Pager pager) {
		return getDatabase().executeQuery(this, pager.getStartRow(), pager.getPageSize());
	}
	
	/**
	 * 返回结果集, 返回从 firstResult 开始的 maxResults 个结果. 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List open(int firstResult, int maxResults) {
		// 执行 Query 得到结果
		return getDatabase().executeQuery(this, firstResult, maxResults);
	}

	/**
	 * 获得查询记录的第一行.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object first() {
		List list = open(1);
		if (list == null || list.size() == 0) return null;
		return list.get(0);
	}
	
	/**
	 * 执行更新语句, 并返回更新影响的记录数.
	 * @return
	 */
	public int update() {
		return getDatabase().executeUpdate(this);
	}

	@SuppressWarnings("unchecked")
	public List execute() {
		return getDatabase().executeSQLQuery(this);
	}

	
	/**
	 * 执行 SQL 语句, 并返回 object 型单一值.
	 * @return
	 */
	public Object scalar() {
		return getDatabase().uniqueResult(this);
	}
	
	/**
	 * 执行 SQL 语句, 并返回 int 型的单一值.
	 * @return
	 */
	public int int_scalar() {
		// 执行 Query 得到结果
		Object r = getDatabase().uniqueResult(this);
		if (r == null) return 0;
		if (r instanceof Number) return ((Number)r).intValue();
		return 0;
	}
}
