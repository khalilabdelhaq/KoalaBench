package ma.ac.uhp.dataLoader.tables;

import ma.ac.uhp.dataLoader.types.AbstractTable;
import oracle.kv.table.Row;
import oracle.kv.table.Table;
import oracle.kv.table.TableAPI;

public class SupplierTable extends AbstractTable {
	private Long orderKey;
	private Long supplierKey;
	private String name;
	private String address;
	private String nationName;
	private String regionName;
	private String phone;
	private Double accountBalance;
	private String comment;
	private final String TABLE_NAME = "lineItem.supplier_dim";

	@Override
	public Row getRow(TableAPI tableAPI) {
		Table table = tableAPI.getTable(TABLE_NAME);
		final Row row = table.createRow();
		row.put("orderKey", getOrderKey());
		row.put("supplierKey", getSupplierKey());
		row.put("name", getName());
		row.put("address", getAddress());
		row.put("nationName", getName());
		row.put("regionName", getRegionName());
		row.put("phone", getPhone());
		row.put("accountBalance", getAccountBalance());
		row.put("comment", getComment());
		return row;
	}

	public SupplierTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SupplierTable(Long orderKey, Long supplierKey, String name, String address, String nationName,
			String regionName, String phone, Double accountBalance, String comment) {
		super();
		this.orderKey = orderKey;
		this.supplierKey = supplierKey;
		this.name = name;
		this.address = address;
		this.nationName = nationName;
		this.regionName = regionName;
		this.phone = phone;
		this.accountBalance = accountBalance;
		this.comment = comment;
	}

	public Long getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(Long orderKey) {
		this.orderKey = orderKey;
	}

	public Long getSupplierKey() {
		return supplierKey;
	}

	public void setSupplierKey(Long supplierKey) {
		this.supplierKey = supplierKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTABLE_NAME() {
		return TABLE_NAME;
	}

}
