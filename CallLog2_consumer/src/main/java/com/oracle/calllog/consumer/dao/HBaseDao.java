package com.oracle.calllog.consumer.dao;

import java.io.IOException;

import com.oracle.calllog.consumer.utils.HBaseUtil;
import com.oracle.calllog.consumer.vo.CallLog;

public class HBaseDao {

	public static void putCallingData(CallLog callLog) throws IOException {
		HBaseUtil.putCalling(callLog);
	}

	public static void putCalledData(CallLog callLog) throws IOException {
		HBaseUtil.putCalled(callLog);
	}
	
}
