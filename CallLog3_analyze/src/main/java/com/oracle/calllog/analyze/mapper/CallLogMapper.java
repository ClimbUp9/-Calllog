package com.oracle.calllog.analyze.mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

import com.oracle.calllog.analyze.vo.LogBean;

public class CallLogMapper extends TableMapper<LogBean, Text>{

	private LogBean outKey1 = new LogBean();
	private LogBean outKey2 = new LogBean();
	private LogBean outKey3 = new LogBean();
	private LogBean outKey4 = new LogBean();
	private LogBean outKey5 = new LogBean();
	private LogBean outKey6 = new LogBean();
	private Text outValue = new Text();
	@Override
	protected void map(ImmutableBytesWritable key, Result value,Context context)
			throws IOException, InterruptedException {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//		00_13091075826_1520039247000_14604235595_3426_1
		String[] strs = Bytes.toString(value.getRow()).split("_");
		String time = format.format(new Date(Long.valueOf(strs[2]))).replaceAll("[\\D]+","");
		
		if(strs[5].equals("1")){
			//18812345678_2018
			outKey1.setPhoneNumber(strs[1]);outKey1.setYear(time.substring(0, 4));outValue.set(strs[4]);
			context.write(outKey1, outValue);
			//18812345678_201808
			outKey2.setPhoneNumber(strs[1]);outKey2.setYear(time.substring(0, 4));outKey2.setMonth(time.substring(4, 6));outValue.set(strs[4]);
			context.write(outKey2, outValue);
			//18812345678_20180816
			outKey3.setPhoneNumber(strs[1]);outKey3.setYear(time.substring(0, 4));outKey3.setMonth(time.substring(4, 6));outKey3.setDay(time.substring(6, 8));outValue.set(strs[4]);
			context.write(outKey3, outValue);			
		}
		if(strs[5].equals("2")){
			//13912345678_2018
			outKey4.setPhoneNumber(strs[1]);outKey4.setYear(time.substring(0, 4));outValue.set(strs[4]);
			context.write(outKey4, outValue);
			//13912345678_201808
			outKey5.setPhoneNumber(strs[1]);outKey5.setYear(time.substring(0, 4));outKey5.setMonth(time.substring(4, 6));outValue.set(strs[4]);
			context.write(outKey5, outValue);
			//13912345678_20180816
			outKey6.setPhoneNumber(strs[1]);outKey6.setYear(time.substring(0, 4));outKey6.setMonth(time.substring(4, 6));outKey6.setDay(time.substring(6, 8));outValue.set(strs[4]);
			context.write(outKey6, outValue);
			
		}
		
	}
}
