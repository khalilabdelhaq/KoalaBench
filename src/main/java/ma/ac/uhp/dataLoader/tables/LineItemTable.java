package ma.ac.uhp.dataLoader.tables;

import java.sql.Timestamp;

import ma.ac.uhp.dataLoader.types.AbstractTable;
import oracle.kv.table.Row;
import oracle.kv.table.Table;
import oracle.kv.table.TableAPI;

public class LineItemTable extends AbstractTable {
	private Long orderKey;
	private Integer quantity;
	private Double extendedPrice;
	private Double discount;
	private Double tax;
	private String returnFlag;
	private String status;
	private Timestamp receiptDate;
	private String shipInstructions;
	private String shipMode;
	private String comment;
	private final String TABLE_NAME = "LineItem";
	
	

	public LineItemTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LineItemTable(Long orderKey, Integer quantity, Double extendedPrice, Double discount, Double tax,
			String returnFlag, String status, Timestamp receiptDate, String shipInstructions, String shipMode,
			String comment) {
		super();
		this.orderKey = orderKey;
		this.quantity = quantity;
		this.extendedPrice = extendedPrice;
		this.discount = discount;
		this.tax = tax;
		this.returnFlag = returnFlag;
		this.status = status;
		this.receiptDate = receiptDate;
		this.shipInstructions = shipInstructions;
		this.shipMode = shipMode;
		this.comment = comment;
	}

	public Long getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(Long orderKey) {
		this.orderKey = orderKey;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getExtendedPrice() {
		return extendedPrice;
	}

	public void setExtendedPrice(Double extendedPrice) {
		this.extendedPrice = extendedPrice;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public String getReturnFlag() {
		return returnFlag;
	}

	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
	}

	public Timestamp getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Timestamp receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShipInstructions() {
		return shipInstructions;
	}

	public void setShipInstructions(String shipInstructions) {
		this.shipInstructions = shipInstructions;
	}

	public String getShipMode() {
		return shipMode;
	}

	public void setShipMode(String shipMode) {
		this.shipMode = shipMode;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public Row getRow(TableAPI tableAPI) {
		Table table = tableAPI.getTable(TABLE_NAME);
		final Row row = table.createRow();
		row.put("orderKey", this.getOrderKey());
		row.put("quantity", this.getQuantity());
		row.put("extendedPrice", this.getExtendedPrice());
		row.put("discount", this.getDiscount());
		row.put("tax", this.getTax());
		row.putEnum("returnFlag", this.getReturnFlag());
		row.putEnum("status", this.getStatus());
		row.put("receiptDate", this.getReceiptDate());
		row.put("shipInstructions", this.getShipInstructions());
		row.put("shipMode", this.getShipMode());
		row.put("comment", this.getComment());
		return row;
	}

	@Override
	public String toString() {
		return "LineItemTable [orderKey=" + orderKey + ", quantity=" + quantity + ", extendedPrice=" + extendedPrice
				+ ", discount=" + discount + ", tax=" + tax + ", returnFlag=" + returnFlag + ", status=" + status
				+ ", receiptDate=" + receiptDate + ", shipInstructions=" + shipInstructions + ", shipMode=" + shipMode
				+ ", comment=" + comment + ", TABLE_NAME=" + TABLE_NAME + "]";
	}

}
