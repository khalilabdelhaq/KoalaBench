package ma.ac.uhp.dataLoader.tables;

import ma.ac.uhp.dataLoader.types.AbstractTable;
import oracle.kv.table.Row;
import oracle.kv.table.Table;
import oracle.kv.table.TableAPI;

public class CustomerTable extends AbstractTable {
	private Long orderKey;
	private Long customerKey;
	private String name;
	private String address;
	private String nation_name;
	private String regionName;
	private String phone;
	private Double accountBalance;
	private String marketSegment;
	private final String TABLE_NAME = "lineItem.customer_dim";

	public CustomerTable(Long orderKey, Long customerKey, String name, String address, String nation_name,
			String regionName, String phone, Double accountBalance, String marketSegment) {
		super();
		this.orderKey = orderKey;
		this.customerKey = customerKey;
		this.name = name;
		this.address = address;
		this.nation_name = nation_name;
		this.regionName = regionName;
		this.phone = phone;
		this.accountBalance = accountBalance;
		this.marketSegment = marketSegment;
	}

	public CustomerTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(Long orderKey) {
		this.orderKey = orderKey;
	}

	public Long getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(Long customerKey) {
		this.customerKey = customerKey;
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

	public String getNation_name() {
		return nation_name;
	}

	public void setNation_name(String nation_name) {
		this.nation_name = nation_name;
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

	public String getMarketSegment() {
		return marketSegment;
	}

	public void setMarketSegment(String marketSegment) {
		this.marketSegment = marketSegment;
	}

	@Override
	public Row getRow(TableAPI tableAPI) {
		Table table = tableAPI.getTable(TABLE_NAME);
		final Row row = table.createRow();
		row.put("orderKey", getOrderKey());
		row.put("customerKey", getCustomerKey());
		row.put("name", getName());
		row.put("address", getAddress());
		row.put("nation_name", getNation_name());
		row.put("regionName", getRegionName());
		row.put("phone", getPhone());
		row.put("accountBalance", getAccountBalance());
		row.put("marketSegment", getMarketSegment());
		return row;
	}

	@Override
	public String toString() {
		return "CustomerTable [orderKey=" + orderKey + ", customerKey=" + customerKey + ", name=" + name + ", address="
				+ address + ", nation_name=" + nation_name + ", regionName=" + regionName + ", phone=" + phone
				+ ", accountBalance=" + accountBalance + ", marketSegment=" + marketSegment + ", TABLE_NAME="
				+ TABLE_NAME + "]";
	}
	

}
