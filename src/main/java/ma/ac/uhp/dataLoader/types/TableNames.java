package ma.ac.uhp.dataLoader.types;

public enum TableNames {
	LINEITEM("lineItem"), SUPPLIER("lineItem.supplier_dim"), CUSTOMER("lineItem.customer_dim"),
	PART("lineItem.part_dim");

	private String tableName;

	private TableNames(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public static TableNames fromString(String text) {
		for (TableNames table : TableNames.values()) {
			if (table.tableName.equalsIgnoreCase(text)) {
				return table;
			}
		}
		return null;
	}
}
