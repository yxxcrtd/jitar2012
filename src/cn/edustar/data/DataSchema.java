package cn.edustar.data;

/**
 * 简单的集合类，支持通过名字找到对应的列
 * 
 * @author Yang XinXin
 */
public class DataSchema extends java.util.ArrayList<String> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6765343063450349820L;
	
	/**
	 * 空的 Schema 对象
	 */
	public static final DataSchema EMPTY_SCHEMA = new DataSchema();

	/**
	 * ColumnName - 列索引的快速映射
	 */
	private java.util.HashMap<String, Integer> name_map = null;
	
	/**
	 * Map 建立时候 super.ArrayList 的修改计数器，用于跟踪更新
	 */
	private int map_build_count = -1;
	
	/**
	 * 缺省构造函数
	 */
	public DataSchema() {
		super.modCount = 1;
	}
	
	/**
	 * 通过给出一个以 ',' 分隔的列字符串来构造一个 DataSchema 的新实例
	 * 使用 parseSelectFields() 函数解析 columns，参见该函数的说明
	 * 
	 * @param columns
	 */
	public DataSchema(String columns) {
		this(parseSelectFields(columns));
	}
	
	/**
	 * 分解一个 HQL 语句中的 SELECT 子句，并变成 String[] 返回，返回的数据可以用于产生 DataSchema
	 * 我们的分解算法不严格，所以写的时候一定要规则，识别的格式包括：
	 *   'title'			- title
	 *   'title Title'		- Title
	 *   'title as Title'	- Title
	 *   'I.title'			- title
	 *   'I.title Title' 	- Title
	 *   'I.title AS Title'	- Title
	 *   
	 * @param fields
	 * @return
	 */
	public static final String[] parseSelectFields(String fields) {
		if (fields == null || fields.trim().length() == 0) return new String[] {};
		if (fields.toUpperCase().startsWith("SELECT "))
			fields = fields.substring("SELECT ".length());
		fields = fields.trim();
		
		String[] cols = fields.split(",");
		for (int i = 0; i < cols.length; ++i) {
			cols[i] = parseSingleField(cols[i]);
		}
		return cols;
	}
	
	private static final String parseSingleField(String field) {
		if (null == field || field.trim().length() == 0)
			return "";
		
		field = field.trim();
		
		// 我们假定用户写的比较规则，不要使用特殊的东西，格式可能为：'title', 'alias.title', 'title as articleTitle', 'alias.title as articleTitle'
		
		// 分解为基本标记
		String[] tokens = field.split("\\s");
		
		if (tokens.length == 0)
			return "";
		
		if (tokens.length == 1)
			return parseFieldFinal(tokens[0]);
		
		if (tokens.length == 2)
			return parseFieldFinal(tokens[1]);	// 'title Title'
		
		// 大于2的时候，使用last作为别名
		return parseFieldFinal(tokens[tokens.length - 1]);
	}
	
	private static final String parseFieldFinal(String field) {
		if (null == field)
			return "";
		
		int index = field.lastIndexOf('.');
		
		if (index < 0)
			return field;
		
		return field.substring(index + 1);
	}
	
	/**
	 * 通过给定的 columns 构造列的集合
	 * 
	 * @param columns
	 */
	public DataSchema(String[] columns) {
		for (int i = 0; i < columns.length; i++) {
			this.add(columns[i]);
		}
	}
	
	/**
	 * 添加一个列
	 * 
	 * @param columnName
	 * @return
	 */
	public int addColumn(String columnName) {
		super.add(columnName);
		return super.size() - 1;
	}
	
	/**
	 * 返回列的数量
	 * 
	 * @return
	 */
	public int getColumnCount() {
		return super.size();
	}
	
	/**
	 * 返回指定索引的列的名字
	 * 
	 * @param index
	 * @return
	 */
	public String getColumnName(int index) {
		return super.get(index);
	}

	/**
	 * 获得指定列的索引，如果没有找到则返回：-1
	 * 
	 * @param column_name
	 * @return
	 */
	public int getColumnIndex(String column_name) {
		if (super.modCount != this.map_build_count) {
			// 重建 Hashmap
			java.util.HashMap<String, Integer> map = new java.util.HashMap<String, Integer>();
			for (int index = 0; index < super.size(); ++index) {
				map.put(super.get(index), new Integer(index));
			}
			this.map_build_count = super.modCount;
			this.name_map = map;
		}
		
		Integer column_index = this.name_map.get(column_name);
		if (column_index == null) return -1;
		return column_index.intValue();
	}
	
}
