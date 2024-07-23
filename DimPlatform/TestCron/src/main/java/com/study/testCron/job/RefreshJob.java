//package com.study.testCron.job;
//
//import com.example.cronservice.service.RefreshService;
//import org.quartz.Job;
//import org.quartz.JobDataMap;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class RefreshJob implements Job {
//
//    @Autowired
//    private RefreshService refreshService;
//
//    @Override
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
//        String dataType = dataMap.getString("dataType");
//
//        try {
//            refreshService.refreshDataType(dataType);
//        } catch (Exception e) {
//            throw new JobExecutionException("Failed to refresh data type: " + dataType, e);
//        }
//    }
//}
