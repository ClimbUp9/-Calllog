package com.oracle.calllog.analyze.outputformat;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.oracle.calllog.analyze.vo.LogBean;
import com.oracle.calllog.analyze.vo.ResultBean;

public class MySqlOutPutFormat extends OutputFormat<LogBean, ResultBean> {

	private FileOutputCommitter committer = null;
	public static final String OUTDIR = "mapreduce.output.fileoutputformat.outputdir";
	
	@Override
	public RecordWriter<LogBean, ResultBean> getRecordWriter(TaskAttemptContext job)
			throws IOException, InterruptedException {
		return new MyRecordWriter();
	}

	@Override
	public void checkOutputSpecs(JobContext arg0) throws IOException, InterruptedException {
		
	}

	public synchronized OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
		if (committer == null) {
			Path output = getOutputPath(context);
			committer = new FileOutputCommitter(output, context);
		}
		return committer;
	}

	public static Path getOutputPath(JobContext job) {
		String name = job.getConfiguration().get(FileOutputFormat.OUTDIR);
		return name == null ? null : new Path(name);
	}

}
