package com.oracle.calllog.consumer.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	static Properties prop;
	static{
		prop = new Properties();
		
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hbase_kafka_consumer.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key){
		return prop.getProperty(key);
	}
	
}
