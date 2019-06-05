package cn.edustar.data;

/**
 * 数据行，实现了数据表中的行记录 * 
 * @author Yang XinXin
 * @version
 */
public class DataRow extends java.util.ArrayList<Object> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4070168044706929996L;

	/**
	 * 所属的数据表
	 */
	private DataTable table;

	/**
	 * 构造方法，指定数据行所属的数据表	 * 
	 * @param table
	 */
	public DataRow(DataTable table) {
		this.table = table;
	}

	/**
	 * 使用指定的 DataTable 和值构造一个 DataRow 的实例	 * 
	 * @param table
	 * @param values
	 */
	public DataRow(DataTable table, Object[] values) {
		this(table);
		
		if (values == null)
			return;
		
		DataSchema schema = table.getSchema();
		int col_num = (schema.size() < values.length) ? schema.size() : values.length;
		for (int i = 0; i < col_num; ++i) {
			super.add(values[i]);
		}
	}

	/**
	 * 返回数据项个数
	 * 
	 * @return
	 */
	public int size() {
		return this.table.getSchema().size();
	}

	/**
	 * 得到数据行的编号
	 * 
	 * @return 数据行的编号
	 */
	public int getRowID() {
		return this.table.indexOf(this);
	}

	/**
	 * 得到数据列的集合
	 * 
	 * @return 数据列的集合
	 */
	public DataSchema getSchema() {
		return this.table.getSchema();
	}

	/**
	 * 得到当前行所属的数据表对象
	 * 
	 * @return 数据表对象
	 */
	public DataTable getDataTable() {
		return this.table;
	}

	/**
	 * 得到指定列的值	 * 
	 * @param columnIndex列索引，从0开始算起	 * @return 值	 */
	@Override
	public Object get(int columnIndex) {
		if (columnIndex < super.size() && columnIndex >= 0)
			return super.get(columnIndex);
		else if (columnIndex < table.getSchema().size() && columnIndex >= 0)
			return null;
		else
			return null;
		/*
		 * throw new IndexOutOfBoundsException("columnIndex == " + columnIndex + " out of bound " + table.getSchema().size());
		 */
	}

	/**
	 * 得到指定列的值
	 * 
	 * @param columnName-列的名称
	 * @return
	 * @exception IndexOutOfBoundsException-所给的字段名不在此表格中	 */
	public Object get(String column_name) {
		int column_index = this.table.getSchema().getColumnIndex(column_name);

		if (column_index < 0) {
			// 如果有此名字的字段计算器，则使用字段计算器计算			DataTable.FieldCalculator calcor = this.table.findFieldCalcor(column_name);
			if (calcor != null)
				return calcor.calcValue(table, this, column_name);
			return null;
		}
		return get(column_index);
	}

	public Object __getattr__(String column_name) {
		return this.get(column_name);
	}
	
	/**
	 * 在指定行的位置增加值	 * 
	 * @param columnIndex-列索引，从0开始算起	 * @param value-值	 */
	@Override
	public Object set(int columnIndex, Object value) {
		DataSchema schema = table.getSchema();
		if (super.size() < schema.size()) {
			super.ensureCapacity(schema.size());
			while (super.size() < schema.size()) {
				super.add(null);
			}
		}
		return super.set(columnIndex, value);
	}

	/**
	 * 在指定列增加值	 * 
	 * @param columnName-列名
	 * @param value-值	 * @exception IndexOutOfBoundsException-所给的字段名不在此表格中	 */
	public Object set(String columnName, Object value) {
		int column_index = this.table.getSchema().getColumnIndex(columnName);

		if (column_index < 0)
			throw new IndexOutOfBoundsException("列名：'" + columnName + "'，没有找到！");

		return this.set(column_index, value);
	}
	
}
