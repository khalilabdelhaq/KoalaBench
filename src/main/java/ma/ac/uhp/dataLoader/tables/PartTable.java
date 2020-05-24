package ma.ac.uhp.dataLoader.tables;

import ma.ac.uhp.dataLoader.types.AbstractTable;
import oracle.kv.table.Row;
import oracle.kv.table.Table;
import oracle.kv.table.TableAPI;

public class PartTable extends AbstractTable {
	private Long orderKey;
	private Long partKey;
	private String name;
	private String manufacturer;
	private String brand;
	private String type;
	private Integer size;
	private String container;
	private Double retailPrice;
	private String comment;
	private final String TABLE_NAME = "lineItem.part_dim";
	
	public PartTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PartTable(Long orderKey, Long partKey, String name, String manufacturer, String brand, String type,
			Integer size, String container, Double retailPrice, String comment) {
		super();
		this.orderKey = orderKey;
		this.partKey = partKey;
		this.name = name;
		this.manufacturer = manufacturer;
		this.brand = brand;
		this.type = type;
		this.size = size;
		this.container = container;
		this.retailPrice = retailPrice;
		this.comment = comment;
	}

	@Override
	public Row getRow(TableAPI tableAPI) {
		Table table = tableAPI.getTable(TABLE_NAME);
		final Row row = table.createRow();
		row.put("orderKey", getOrderKey());
		row.put("partKey", getPartKey());
		row.put("name", getName());
		row.put("manufacturer", getManufacturer());
		row.put("brand", getBrand());
		row.put("type", getType());
		row.put("size", getSize());
		row.put("container", getContainer());
		row.put("retailPrice", getRetailPrice());
		row.put("comment", getComment());
		return row;
	}

	public Long getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(Long orderKey) {
		this.orderKey = orderKey;
	}

	public Long getPartKey() {
		return partKey;
	}

	public void setPartKey(Long partKey) {
		this.partKey = partKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public Double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
