package com.oracle.calllog.analyze.vo;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class ResultBean implements WritableComparable<ResultBean> {

	private int holdTime;
	private int callCount;

	public ResultBean() {
		super();
	}

	public int getHoldTime() {
		return holdTime;
	}

	public void setHoldTime(int holdTime) {
		this.holdTime = holdTime;
	}

	public int getCallCount() {
		return callCount;
	}

	public void setCallCount(int callCount) {
		this.callCount = callCount;
	}

	@Override
	public String toString() {
		return holdTime + "_" + callCount;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(holdTime);
		out.writeInt(callCount);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		holdTime = in.readInt();
		callCount = in.readInt();
	}

	@Override
	public int compareTo(ResultBean o) {
		return 0;
	}

}
