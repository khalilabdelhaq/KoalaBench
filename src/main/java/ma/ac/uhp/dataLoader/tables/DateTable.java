package ma.ac.uhp.dataLoader.tables;

import java.sql.Timestamp;

import ma.ac.uhp.dataLoader.types.AbstractTable;
import oracle.kv.table.Row;
import oracle.kv.table.Table;
import oracle.kv.table.TableAPI;

public class DateTable extends AbstractTable {

	Timestamp date;
	String month;
	Integer year;
	String yearMonthNum;
	String yearMonth;
	Integer dayNumInWeek;
	Integer dayNumInMonth;
	Integer dayNumInYear;
	Integer monthNumInYear;
	Integer weekNumInYear;
	private final String TABLE_NAME = "lineItem.date_dim";

	public DateTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getYearMonthNum() {
		return yearMonthNum;
	}

	public void setYearMonthNum(String yearMonthNum) {
		this.yearMonthNum = yearMonthNum;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Integer getDayNumInWeek() {
		return dayNumInWeek;
	}

	public void setDayNumInWeek(Integer dayNumInWeek) {
		this.dayNumInWeek = dayNumInWeek;
	}

	public Integer getDayNumInMonth() {
		return dayNumInMonth;
	}

	public void setDayNumInMonth(Integer dayNumInMonth) {
		this.dayNumInMonth = dayNumInMonth;
	}

	public Integer getDayNumInYear() {
		return dayNumInYear;
	}

	public void setDayNumInYear(Integer dayNumInYear) {
		this.dayNumInYear = dayNumInYear;
	}

	public Integer getMonthNumInYear() {
		return monthNumInYear;
	}

	public void setMonthNumInYear(Integer monthNumInYear) {
		this.monthNumInYear = monthNumInYear;
	}

	public Integer getWeekNumInYear() {
		return weekNumInYear;
	}

	public void setWeekNumInYear(Integer weekNumInYear) {
		this.weekNumInYear = weekNumInYear;
	}

	@Override
	public Row getRow(TableAPI tableAPI) {
		Table table = tableAPI.getTable(TABLE_NAME);
		final Row row = table.createRow();
		row.put("date", getDate());
		row.put("month", getMonth());
		row.put("year", getYear());
		row.put("yearMonthNum", getYearMonth());
		row.put("yearMonth", getYearMonth());
		row.put("dayNumInWeek", getDayNumInWeek());
		row.put("dayNumInMonth", getDayNumInMonth());
		row.put("dayNumInYear", getDayNumInYear());
		row.put("monthNumInYear", getMonthNumInYear());
		row.put("weekNumInYear", getWeekNumInYear());
		return row;
	}

	@Override
	public String toString() {
		return "DateTable [date=" + date + ", month=" + month + ", year=" + year + ", yearMonthNum=" + yearMonthNum
				+ ", yearMonth=" + yearMonth + ", dayNumInWeek=" + dayNumInWeek + ", dayNumInMonth=" + dayNumInMonth
				+ ", dayNumInYear=" + dayNumInYear + ", monthNumInYear=" + monthNumInYear + ", weekNumInYear="
				+ weekNumInYear + ", TABLE_NAME=" + TABLE_NAME + "]";
	}
	
}