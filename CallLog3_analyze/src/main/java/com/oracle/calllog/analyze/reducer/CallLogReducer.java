package com.oracle.calllog.analyze.reducer;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.oracle.calllog.analyze.vo.LogBean;
import com.oracle.calllog.analyze.vo.ResultBean;

//会按照LogBean进行归并
public class CallLogReducer extends Reducer<LogBean,Text,LogBean, ResultBean>{
	
	ResultBean outValue = new ResultBean();
	@Override
	protected void reduce(LogBean key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		int oneKeySum = 0;
		int count = 0;
		for(Text value : values){
			int oneTime = Integer.valueOf(value.toString());
			oneKeySum = oneKeySum + oneTime;
			count ++;
		}
		outValue.setHoldTime(oneKeySum);
		outValue.setCallCount(count);
		
		context.write(key, outValue);
		
	}
	
}
