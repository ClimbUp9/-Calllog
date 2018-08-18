package com.oracle.calllog.consumer.utils;

import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.oracle.calllog.consumer.vo.CallLog;

public class HBaseUtil {

	private static Configuration conf;
	private static Connection conn;
	private static Admin admin;

	
	static{
		try {
			conf = HBaseConfiguration.create();
			conn = ConnectionFactory.createConnection(conf);
			admin = conn.getAdmin();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//�жϱ��Ƿ����
	//������
	//		Ԥ����
	//		HTableDescriptor tdes = new HTableDescriptor(TableName.valueOf("ns:student"));
	//		admin.createTable(desc, splitKeys);
	
	public static void createTable() throws IOException{
		TableName tableName = TableName.valueOf(
				PropertiesUtil.getProperty("hbase.namespace.name")
				+":"+
				PropertiesUtil.getProperty("hbase.table.name"));
		HTableDescriptor tableDesc = new HTableDescriptor(tableName);
		if(!admin.tableExists(tableName)){
			HColumnDescriptor family1 = new HColumnDescriptor("info1");
			tableDesc.addFamily(family1);
			HColumnDescriptor family2 = new HColumnDescriptor("info2");
			tableDesc.addFamily(family2);
			
			tableDesc.setRegionReplication(6);
			//�����ʱ��ָ�������߽�
			admin.createTable(tableDesc, getPartitionBorder());			
		}else{
			System.out.println("���Ѿ�����");
		}
		
	}
	/**
	 * ��ȡ�����߽�
	 * @return
	 */
	private static byte[][] getPartitionBorder(){
		byte[][] byte2s = new byte[6][];
		byte2s[0] = Bytes.toBytes("00|");
		byte2s[1] = Bytes.toBytes("01|");
		byte2s[2] = Bytes.toBytes("02|");
		byte2s[3] = Bytes.toBytes("03|");
		byte2s[4] = Bytes.toBytes("04|");
		byte2s[5] = Bytes.toBytes("05|");
		return byte2s;
	}
	
	//����Ԥ�����߽�ķ���,����һ��byte[][]   �ַ����ı�ʾӦ����[Bytes.toBytes("00|"),"01|","02|","03|","04|","05|"]
	//���ɷ����ŵķ���    ���� 01
	//����RowKey�ķ���   ����regioncode_callingno_ts_calledno_duration_flag
	//put����
//	17053018400,�����,18664015893,�ȹ���,2018/02/23 02:54:18,4606

	public static String getPartitionNumber(CallLog callLog){
		String callTime  = callLog.getCallTime();
		String[] strs = callTime.split("/");
		String yearMonth = strs[0]+strs[1];
		String phoneNumber = callLog.getCallingNumber();
		long ym = Long.parseLong(yearMonth);
		long pn = Long.parseLong(phoneNumber);
		Long result = ym^pn;
		int result2 = Math.abs((int) (result.hashCode()%6));
		DecimalFormat format = new DecimalFormat("00");
		return format.format(result2);
		
	}
	
	public static String getRowKey(CallLog callLog) {
		return getPartitionNumber(callLog)+"_"+
				callLog.getCallingNumber()+"_"+
				callLog.getTimestamp()+"_"+
				callLog.getCalledNumber()+"_"+
				callLog.getHoldTime()+"_"+
				callLog.getFlag();
	}
	
	public static void putCalling(CallLog callLog) throws IOException{	
		//��ȡtable
		Table hTable = conn.getTable(TableName.valueOf(
				PropertiesUtil.getProperty("hbase.namespace.name")
				+":"+
				PropertiesUtil.getProperty("hbase.table.name")
				));
		Put p = new Put(Bytes.toBytes(getRowKey(callLog)));
		//���壬�У�ֵ
		p.addColumn(Bytes.toBytes("info1"),Bytes.toBytes("callingNumber"),Bytes.toBytes(callLog.getCallingNumber()));
		p.addColumn(Bytes.toBytes("info1"),Bytes.toBytes("callingName"),Bytes.toBytes(callLog.getCallingName()));
		p.addColumn(Bytes.toBytes("info1"),Bytes.toBytes("calledNumber"),Bytes.toBytes(callLog.getCalledNumber()));
		p.addColumn(Bytes.toBytes("info1"),Bytes.toBytes("calledName"),Bytes.toBytes(callLog.getCalledName()));
		p.addColumn(Bytes.toBytes("info1"),Bytes.toBytes("callTime"),Bytes.toBytes(callLog.getCallTime()));
		p.addColumn(Bytes.toBytes("info1"),Bytes.toBytes("holdTime"),Bytes.toBytes(callLog.getHoldTime()));
		p.addColumn(Bytes.toBytes("info1"),Bytes.toBytes("timestamp"),Bytes.toBytes(callLog.getTimestamp()));
		p.addColumn(Bytes.toBytes("info1"),Bytes.toBytes("flag"),Bytes.toBytes(callLog.getFlag()));
		//��table�����
		hTable.put(p);
		System.out.println("����ɹ�");	
	}
	

	
	public static String getPartitionNumber2(CallLog callLog){
		String callTime  = callLog.getCallTime();
		String[] strs = callTime.split("/");
		String yearMonth = strs[0]+strs[1];
		String phoneNumber = callLog.getCalledNumber();
		long ym = Long.parseLong(yearMonth);
		long pn = Long.parseLong(phoneNumber);
		Long result = ym^pn;
		int result2 = Math.abs((int) (result.hashCode()%6));
		DecimalFormat format = new DecimalFormat("00");
		return format.format(result2);
		
	}
	
	public static String getRowKey2(CallLog callLog) {
		return getPartitionNumber2(callLog)+"_"+
				callLog.getCalledNumber()+"_"+
				callLog.getTimestamp()+"_"+
				callLog.getCallingNumber()+"_"+
				callLog.getHoldTime()+"_"+
				callLog.getFlag();
	}
	
	public static void putCalled(CallLog callLog) throws IOException{	

		//��ȡtable
		Table hTable = conn.getTable(TableName.valueOf(
				PropertiesUtil.getProperty("hbase.namespace.name")
				+":"+
				PropertiesUtil.getProperty("hbase.table.name")
				));
		Put p = new Put(Bytes.toBytes(getRowKey2(callLog)));
		//���壬�У�ֵ
		p.addColumn(Bytes.toBytes("info2"),Bytes.toBytes("callingNumber"),Bytes.toBytes(callLog.getCallingNumber()));
		p.addColumn(Bytes.toBytes("info2"),Bytes.toBytes("callingName"),Bytes.toBytes(callLog.getCallingName()));
		p.addColumn(Bytes.toBytes("info2"),Bytes.toBytes("calledNumber"),Bytes.toBytes(callLog.getCalledNumber()));
		p.addColumn(Bytes.toBytes("info2"),Bytes.toBytes("calledName"),Bytes.toBytes(callLog.getCalledName()));
		p.addColumn(Bytes.toBytes("info2"),Bytes.toBytes("callTime"),Bytes.toBytes(callLog.getCallTime()));
		p.addColumn(Bytes.toBytes("info2"),Bytes.toBytes("holdTime"),Bytes.toBytes(callLog.getHoldTime()));
		p.addColumn(Bytes.toBytes("info2"),Bytes.toBytes("timestamp"),Bytes.toBytes(callLog.getTimestamp()));
		p.addColumn(Bytes.toBytes("info2"),Bytes.toBytes("flag"),Bytes.toBytes(callLog.getFlag()));
		//��table�����
		hTable.put(p);
		System.out.println("����ɹ�");	
	}

	
	//���������ռ�
	
}
