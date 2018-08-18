package com.oracle.calllog.consumer.vo;

public class CallLog {

	private String callingNumber;
	private String callingName;
	private String calledNumber;
	private String calledName;
	private String callTime;
	private String holdTime;
	private String timestamp;
	private String flag;

	public CallLog() {
		super();
	}

	public CallLog(String callingNumber, String callingName, String calledNumber, String calledName, String callTime,
			String holdTime, String timestamp, String flag) {
		super();
		this.callingNumber = callingNumber;
		this.callingName = callingName;
		this.calledNumber = calledNumber;
		this.calledName = calledName;
		this.callTime = callTime;
		this.holdTime = holdTime;
		this.timestamp = timestamp;
		this.flag = flag;
	}

	public String getCallingNumber() {
		return callingNumber;
	}

	public void setCallingNumber(String callingNumber) {
		this.callingNumber = callingNumber;
	}

	public String getCallingName() {
		return callingName;
	}

	public void setCallingName(String callingName) {
		this.callingName = callingName;
	}

	public String getCalledNumber() {
		return calledNumber;
	}

	public void setCalledNumber(String calledNumber) {
		this.calledNumber = calledNumber;
	}

	public String getCalledName() {
		return calledName;
	}

	public void setCalledName(String calledName) {
		this.calledName = calledName;
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	public String getHoldTime() {
		return holdTime;
	}

	public void setHoldTime(String holdTime) {
		this.holdTime = holdTime;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return callingNumber + "\t" + callingName + "\t" + calledNumber + "\t" + calledName + "\t" + callTime + "\t"
				+ holdTime + "\t" + timestamp + "\t" + flag;
	}

}
