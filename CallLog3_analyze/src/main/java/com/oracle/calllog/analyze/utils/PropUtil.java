package com.oracle.calllog.analyze.utils;

import java.io.IOException;
import java.util.Properties;

public class PropUtil {

	static Properties prop;
	static{
		prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("conn.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key){
		return prop.getProperty(key);
	}
}
