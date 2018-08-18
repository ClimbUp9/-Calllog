package com.oracle.calllog.consumer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.oracle.calllog.consumer.dao.HBaseDao;
import com.oracle.calllog.consumer.utils.HBaseUtil;
import com.oracle.calllog.consumer.utils.PropertiesUtil;
import com.oracle.calllog.consumer.vo.CallLog;

public class HBaseConsumer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws ParseException, IOException {
		Properties props = new Properties();
		props.put("bootstrap.servers", PropertiesUtil.getProperty("bootstrap.servers"));
		props.put("group.id", PropertiesUtil.getProperty("group.id")); 
		props.put("enable.auto.commit", PropertiesUtil.getProperty("enable.auto.commit"));
		props.put("auto.commit.interval.ms", PropertiesUtil.getProperty("auto.commit.interval.ms"));
		props.put("key.deserializer", PropertiesUtil.getProperty("key.deserializer"));
		props.put("value.deserializer", PropertiesUtil.getProperty("value.deserializer"));

		//����������
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		//�����߶���topic,��ͬʱ���Ķ��
		consumer.subscribe(Arrays.asList("second"));
		
		CallLog callLog = new CallLog();
		HBaseUtil.createTable();
		while(true){
			//��ȡ���ݣ���ȡ�ĳ�ʱʱ��Ϊ100ms
			ConsumerRecords<String, String> records = consumer.poll(100);
			for(ConsumerRecord<String, String> record : records){
				String[] strs = record.value().toString().split(",");
				//�ֻ����� ��� ���� Ȼ������hashcode
				callLog.setCallingNumber(strs[0]);
				callLog.setCallingName(strs[1]);
				callLog.setCalledNumber(strs[2]);
				callLog.setCalledName(strs[3]);
				callLog.setCallTime(strs[4]);
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
				callLog.setTimestamp(format.parse(strs[4]).getTime()+"");
				callLog.setHoldTime(strs[5]);
				callLog.setFlag("1");
				//��HBase��������ݣ������˵�
				HBaseDao.putCallingData(callLog);
				
				System.out.println("*************************************");
//				System.out.println(HBaseUtil.getPartitionNumber(callLog));
//				System.out.println(HBaseUtil.getRowKey(callLog));
				System.out.println(callLog);
				System.out.println("-------------------------------------");
				
				
				//��HBase��������ݣ������˵�
				callLog.setFlag("2");
				HBaseDao.putCalledData(callLog);
//				System.out.println(HBaseUtil.getPartitionNumber2(callLog));
//				System.out.println(HBaseUtil.getRowKey2(callLog));
				System.out.println(callLog);
				System.out.println("*************************************");
				
				
			}
			
		}
		
	}

}
