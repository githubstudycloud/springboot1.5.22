//package com.study.testCron;
//
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.quartz.QuartzJobBean;
//
//import java.util.List;
//
//public class RefreshJob extends QuartzJobBean {
//
//    @Autowired
//    private DataTypeConfigRepository dataTypeConfigRepository;
//
//    @Autowired
//    private RefreshService refreshService;
//
//    @Override
//    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
//        String dataType = context.getJobDetail().getJobDataMap().getString("dataType");
//        List<DataTypeConfig> configs = dataTypeConfigRepository.findByDataType(dataType);
//
//        for (DataTypeConfig config : configs) {
//            try {
//                refreshService.refreshData(config);
//            } catch (Exception e) {
//                throw new JobExecutionException("Refresh failed for data type: " + dataType, e);
//            }
//        }
//    }
//}