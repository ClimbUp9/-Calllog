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

		//定义消费者
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		//消费者订阅topic,课同时订阅多个
		consumer.subscribe(Arrays.asList("second"));
		
		CallLog callLog = new CallLog();
		HBaseUtil.createTable();
		while(true){
			//读取数据，读取的超时时间为100ms
			ConsumerRecords<String, String> records = consumer.poll(100);
			for(ConsumerRecord<String, String> record : records){
				String[] strs = record.value().toString().split(",");
				//手机号码 亦或 年月 然后在求hashcode
				callLog.setCallingNumber(strs[0]);
				callLog.setCallingName(strs[1]);
				callLog.setCalledNumber(strs[2]);
				callLog.setCalledName(strs[3]);
				callLog.setCallTime(strs[4]);
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
				callLog.setTimestamp(format.parse(strs[4]).getTime()+"");
				callLog.setHoldTime(strs[5]);
				callLog.setFlag("1");
				//往HBase中添加数据，主叫人的
				HBaseDao.putCallingData(callLog);
				
				System.out.println("*************************************");
//				System.out.println(HBaseUtil.getPartitionNumber(callLog));
//				System.out.println(HBaseUtil.getRowKey(callLog));
				System.out.println(callLog);
				System.out.println("-------------------------------------");
				
				
				//往HBase中添加数据，被叫人的
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
