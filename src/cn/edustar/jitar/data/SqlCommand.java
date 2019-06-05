package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.data.DataSchema;
import cn.edustar.data.DataTable;

/**
 * 执行 Hibernate SQLQuery.
 *
 *
 */
public class SqlCommand extends AbstractCommand {
	/**
	 * 缺省构造函数.
	 */
	public SqlCommand() {
		
	}
	
	/**
	 * 使用指定 sql 语句构造.
	 * @param sql
	 */
	public SqlCommand(String sql) {
		super(sql);
	}
	
	
	/**
	 * 打开结果集, 实际返回 DataTable.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object open() {
		// 执行 Query 得到结果
		List list = (List)getDatabase().executeSQLQuery(this);
		
		// 包装为 DataTable 返回, 实质为增强的 List<Object[]>.
		String schema = calcSchemaString();
		DataTable dt = new DataTable(new DataSchema(schema));
		dt.addList(list);
		
		return dt;
	}

}
