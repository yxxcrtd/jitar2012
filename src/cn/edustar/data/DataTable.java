package cn.edustar.data;

import java.util.List;

/**
 * DataTable 中动态值计算接口.
 * 数据表，实现了数据的集合（数据行、数据列） 
 * 原实现在发布系统中，现在为了公共化，提取到了 .data.tree 包里面.
 * 可以通过记录集来构造，也可以默认的构造.
 * 数据表必须含有一个数据方案(数据列的集合).
 * 它所引用的数据方案是一个拷贝版本，原先的数据方案不再对现在新的数据表产生影响.
 * 如果给定了数据方案，而且数据列的集合不为空，将使用给定的数据方案.
 * 
 *
 */
@SuppressWarnings("rawtypes")
public class DataTable extends java.util.ArrayList<DataRow> {
	private static final long serialVersionUID = 5629892660702860948L;

	public static final DataTable EMPTY_DATATABLE = new DataTable(
			DataSchema.EMPTY_SCHEMA);

	public static interface FieldCalculator {
		/**
		 * 计算指定表格指定行，指定名字字段的值.
		 * 
		 * @param table-数据表格对象.
		 * @param row-数据行对象.
		 * @param name-字段名字.
		 * @return 返回 name 名字字段的计算出来的值.
		 */
		public Object calcValue(DataTable table, DataRow row, String name);
	}

	/** 数据架构，包含数据列的集合. */
	private DataSchema schema;

	/** 可计算的字段集合. */
	private java.util.HashMap<String, FieldCalculator> calc_fields = new java.util.HashMap<String, FieldCalculator>();

	/** 数据表的注释. */
	private String comment;

	/**
	 * 默认的构造方法.
	 */
	public DataTable(DataSchema schema) {
		this.schema = schema;
	}

	/**
	 * 使用指定的架构和数据构造一个 DataTable.
	 * 
	 * @param schema
	 * @param list-数据，每个数据项必须是一个Object[]数组.
	 */
	public DataTable(DataSchema schema, List list) {
		this.schema = schema;
		this.addList(list);
	}

	/**
	 * 获得记录数量，bean 模式的访问，用于 template 中.
	 * 
	 * @return
	 */
	public int getSize() {
		return this.size();
	}

	public boolean getIsEmpty() {
		return this.isEmpty();
	}

	/**
	 * 创建一个符合表结构的数据行对象.
	 * 
	 * @return 拥有结构的数据行对象.
	 */
	public DataRow newRow() {
		return new DataRow(this);
	}

	/**
	 * 创建一个符合表结构的数据行对象，并用指定值进行初始化.
	 * 
	 * @param values
	 * @return
	 */
	public DataRow newRow(Object[] values) {
		return new DataRow(this, values);
	}

	/**
	 * 增加符合表结构的数据行对象.
	 * 
	 * @param dataRow-符合表结构的数据行对象.
	 */
	public boolean addRow(DataRow dataRow) {
		return super.add(dataRow);
	}

	/**
	 * 增加符合数据表结构的数据行对象到指定位置.
	 * 
	 * @param dataRow-数据行对象.
	 * @param index-数据行在表中的索引位置.
	 */
	public void addRow(int index, DataRow dataRow) {
		if (dataRow.getDataTable() != this) {
			throw new IllegalArgumentException(
					"addRow(int, DataRow)，增加的数据行与当前数据表的结构不一致。");
		} else {
			// ???: dataRow.setRowID(index);
			super.add(index, dataRow);
		}
	}

	/**
	 * 添加符合数据表结构的 List 到数据表.
	 * 
	 * @param list数据，必须是List&lt;Object[]&gt;格式的.
	 */
	public void addList(List list) {
		if (list == null || list.size() == 0)
			return;
		for (int i = 0; i < list.size(); ++i) {
			Object[] row = (Object[]) list.get(i);
			this.addRow(this.newRow(row));
		}
	}

	/**
	 * 得到数据表中数据列的集合.
	 * 
	 * @return 数据列的集合.
	 * 
	 */
	public DataSchema getSchema() {
		return this.schema;
	}

	/**
	 * 设置 Schema.
	 * @param schema
	 */
	public void setSchema(DataSchema schema) {
		this.schema = schema;
	}
	
	/**
	 * 设置 schema 字符串.
	 * @param schema_string
	 */
	public void setSchemaString(String schema_string) {
		setSchema(new DataSchema(schema_string));
	}
	
	/**
	 * 得到数据表的注释信息.
	 * 
	 * @return 注释信息.
	 * 
	 */
	public String getComment() {
		return this.comment;
	}

	/**
	 * 设置数据表的注释信息.
	 * 
	 * @param comment-注释信息.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * 查找一个字段计算子，如果没有则返回 null.
	 * 
	 * @param name字段名字
	 * @return
	 */
	public FieldCalculator findFieldCalcor(String name) {
		return this.calc_fields.get(name);
	}

	/**
	 * 添加一个可计算字段，通过 row.get(name) 可以访问该计算域.
	 * 
	 * @param name字段名字.
	 * @param calc计算的接口.
	 */
	public void addCalcField(String name, FieldCalculator calc) {
		this.calc_fields.put(name, calc);
	}

	/**
	 * 调试用，输出所有内容.
	 */
	public void dump() {
		System.out.println("=== Dump DataTable ==========================");
		if (schema != null) {
			for (int index = 0; index < schema.size(); ++index) {
				String prop = schema.get(index);
				System.out.println("Schema[" + index + "] = " + prop);
			}
		}

		for (int index = 0; index < super.size(); ++index) {
			DataRow row = super.get(index);
			System.out.print("row[" + index + "]=");
			for (int j = 0; j < row.size(); j++) {
				System.out.print(((j > 0) ? ", " : "") + row.get(j));
			}
			System.out.println();
		}
	}
}
