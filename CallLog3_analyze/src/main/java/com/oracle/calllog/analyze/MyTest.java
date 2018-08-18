package com.oracle.calllog.analyze;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.oracle.calllog.analyze.utils.DBTool;


public class MyTest {

	
	@Test
	public void myTest4(){
		
		String sql = "123456**";
		Object[] objs = {sql,1289};
		
		System.out.println(objs[0]+""+objs[1]+"");
	}
	
	
	
	@Test
	public void myTest3(){
		
//		0_13091075826__0_2018__	520105_145
//		0_13091075826__0_2018_01_	91074_22
//		0_13091075826__0_2018_01_04	1649_1
//		0_13091075826__0_2018_01_06	10198_2
		

		
		DBTool tool = new DBTool();
		
		
		String number = "13091075826";
		String year = "2018";
		String month = "01";
		String day = "-1";
		
		
		
		List<Map<String, Object>> list = 
				tool.queryByPrepared("select id from tb_dimension_date where year = ? and month = ? and day =? ",year,month,day);
		
		int id_date_dimension = Integer.valueOf(list.get(0).get("id")+"");
		System.out.println(list.get(0).get("id"));
		
		List<Map<String, Object>> list2 = 
				tool.queryByPrepared("select id from tb_contacts where telephone = ?",number);
		
		int id_contact = Integer.valueOf(list2.get(0).get("id")+"");
		System.out.println();
		
		String id_date_contact = id_date_dimension+"_"+id_contact;
  
		int call_sum = 520105;
		int call_duration_sum = 22;
		
		String sql = "insert into tb_call "
				+ "(id_date_contact,id_date_dimension ,id_contact,call_sum,call_duration_sum) values (?,?,?,?,?)";
		tool.updateByPrepared(sql,id_date_contact,id_date_dimension,id_contact,call_sum,call_duration_sum);
		
		
	}
	@Test
	public void myTest2(){
		
		DBTool tool = new DBTool();
		List<Map<String, Object>> list = tool.queryByPrepared("select * from t_test");
		
		System.out.println(list);
	}
	
	
	
	@Test
	public void myTest(){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//		00_13091075826_1520039247000_14604235595_3426_1
		String[] strs = "00_13091075826_1530100884000_14604235595_3426_1".split("_");
		
		String time = format.format(new Date(Long.valueOf(strs[2]))).replaceAll("[\\D]+","");
		System.out.println(time);
		System.out.println(time.substring(0, 4));
		System.out.println(time.substring(0, 6));
		System.out.println(time.substring(0, 8));
	}
	
	
}

