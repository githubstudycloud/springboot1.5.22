package com.study.testCron.job;


import com.study.testCron.repository.JobExecutionRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SampleJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(SampleJob.class);

    @Autowired
    private JobExecutionRepository repository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("SampleJob is executing...");
        // Add your job logic here
        String jobId = context.getJobDetail().getKey().getName();
        if (repository.hasBeenExecuted(jobId)) {
            return;
        }

        // 执行任务逻辑
        // ...

        repository.markAsExecuted(jobId);
    }
}
