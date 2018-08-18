package com.oracle.calllog.analyze.runner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.oracle.calllog.analyze.mapper.CallLogMapper;
import com.oracle.calllog.analyze.outputformat.MySqlOutPutFormat;
import com.oracle.calllog.analyze.reducer.CallLogReducer;
import com.oracle.calllog.analyze.vo.LogBean;
import com.oracle.calllog.analyze.vo.ResultBean;

public class CallLogApp {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
		
		Configuration conf = HBaseConfiguration.create();
		Job job = Job.getInstance(conf);
		FileSystem fs = FileSystem.get(new URI("hdfs://node121:8020"),conf, "bduser");
		
		//4
		Scan scan = new Scan();
		TableMapReduceUtil.initTableMapperJob("myns1:calllog", scan, CallLogMapper.class, LogBean.class,Text.class, job);
		job.setOutputKeyClass(LogBean.class);
		job.setOutputValueClass(ResultBean.class);
		
		//3
		job.setJarByClass(CallLogApp.class);
		job.setReducerClass(CallLogReducer.class);
		//2
		Path outPath = new Path("hdfs://node121:8020/user/bduser/resulttmp/calllog");
		if(fs.exists(outPath)){
			fs.delete(outPath, true);
		}
		FileOutputFormat.setOutputPath(job, outPath);
		
		//设置自定义的输出类型,设置
		job.setOutputFormatClass(MySqlOutPutFormat.class);

		//1
		boolean result = job.waitForCompletion(true);
		System.exit(result?0:1);
	}

}
