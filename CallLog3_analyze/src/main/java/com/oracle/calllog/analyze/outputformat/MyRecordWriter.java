package com.oracle.calllog.analyze.outputformat;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import com.oracle.calllog.analyze.utils.DBTool;
import com.oracle.calllog.analyze.vo.LogBean;
import com.oracle.calllog.analyze.vo.ResultBean;

public class MyRecordWriter extends RecordWriter<LogBean, ResultBean> {

	public MyRecordWriter() {
		//加载驱动
		
		//建立连接
		
		//生成处理对象
		
		//sql
		
		//关闭
		
	}

	@Override
	public void close(TaskAttemptContext arg0) throws IOException, InterruptedException {

	}

	@Override
	public void write(LogBean key, ResultBean value) throws IOException, InterruptedException {
		int count = 0;
		DBTool tool = new DBTool();
		
		String year = key.getYear();
		String month = key.getMonth();
		String day = key.getDay();
		String number = key.getPhoneNumber();
		
		List<Map<String, Object>> list = 
				tool.queryByPrepared("select id from tb_dimension_date where year = ? and month = ? and day =? ",year,month,day);
		List<Map<String, Object>> list2 = 
				tool.queryByPrepared("select id from tb_contacts where telephone = ?",number);
		
		int id_date_dimension = Integer.valueOf(list.get(0).get("id")+"");
		int id_contact = Integer.valueOf(list2.get(0).get("id")+"");
		String id_date_contact = id_date_dimension+"_"+id_contact;
		int call_sum = value.getHoldTime();
		int call_duration_sum = value.getCallCount();
		
		String sql = "insert into tb_call "
				+ "(id_date_contact,id_date_dimension ,id_contact,call_sum,call_duration_sum) values (?,?,?,?,?)"
				+ "";
		tool.updateByPrepared(sql,id_date_contact,id_date_dimension,id_contact,call_sum,call_duration_sum);
		
//		Object[] os = {id_date_contact,id_date_dimension,id_contact,call_sum,call_duration_sum};
//		List<Object[]> list_os = new ArrayList<>();
//		list_os.add(os);
		count++;
		if(count==200){
			count=0;
//			tool.updateByPstmtBatch(sql,list_os);			
		}
		
	}

}
