package cn.edustar.jitar.pojos;

import java.io.Serializable;

public class JitarColumn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1054323336427396205L;

	public JitarColumn() {
	}

	private int columnId;
	private String columnName;

	public int getColumnId() {
		return columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
