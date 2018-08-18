package com.oracle.calllog.analyze.vo;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class LogBean implements WritableComparable<LogBean> {

	private int info_id = 0;
	private String phoneNumber;
	private String name = "";
	private int date_id = 0;
	private String year = "-1";
	private String month = "-1";
	private String day = "-1";

	public LogBean() {
		super();
	}

	public int getInfo_id() {
		return info_id;
	}

	public void setInfo_id(int info_id) {
		this.info_id = info_id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDate_id() {
		return date_id;
	}

	public void setDate_id(int date_id) {
		this.date_id = date_id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

//	@Override
//	public String toString() {
//		return info_id + "\t" + phoneNumber + "\t" + name + "\t" + date_id + "\t" + year + "\t" + month + "\t" + day;
//	}
	@Override
	public String toString() {
		return info_id + "_" + phoneNumber + "_" + name + "_" + date_id + "_" + year + "_" + month + "_" + day;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(info_id);
		out.writeUTF(phoneNumber);
		out.writeUTF(name);
		out.writeInt(date_id);
		out.writeUTF(year);
		out.writeUTF(month);
		out.writeUTF(day);

	}

	@Override
	public void readFields(DataInput in) throws IOException {
		info_id = in.readInt();
		phoneNumber = in.readUTF();
		name = in.readUTF();
		date_id = in.readInt();
		year = in.readUTF();
		month = in.readUTF();
		day = in.readUTF();
	}

	@Override
	public int compareTo(LogBean o) {
		if(this.phoneNumber.compareTo(o.getPhoneNumber())==0){
			if(this.year.compareTo(o.getYear())==0){
				if(this.month.compareTo(o.getMonth()) == 0){
					return this.day.compareTo(o.getDay());
				}
				return this.month.compareTo(o.getMonth());
			}
			return this.year.compareTo(o.getYear());
		}
		return this.phoneNumber.compareTo(o.getPhoneNumber());
	}

}
