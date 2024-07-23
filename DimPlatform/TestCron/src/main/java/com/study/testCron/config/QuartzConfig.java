//package com.study.testCron.config;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//import org.springframework.scheduling.quartz.SpringBeanJobFactory;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.util.Properties;
//
//@Configuration
//public class QuartzConfig {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    private PlatformTransactionManager transactionManager;
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    @Bean
//    public SpringBeanJobFactory springBeanJobFactory() {
//        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
//        jobFactory.setApplicationContext(applicationContext);
//        return jobFactory;
//    }
//
//    @Bean
//    public SchedulerFactoryBean scheduler() throws IOException {
//        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
//        schedulerFactory.setDataSource(dataSource);
//        schedulerFactory.setTransactionManager(transactionManager);
//        schedulerFactory.setOverwriteExistingJobs(true);
//        schedulerFactory.setSchedulerName("ClusterScheduler");
//        schedulerFactory.setJobFactory(springBeanJobFactory());
//
//        Properties properties = new Properties();
//        properties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
//        properties.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
//        properties.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
//        properties.setProperty("org.quartz.jobStore.useProperties", "false");
//        properties.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");
//        properties.setProperty("org.quartz.jobStore.isClustered", "true");
//        properties.setProperty("org.quartz.jobStore.clusterCheckinInterval", "20000");
//
//        schedulerFactory.setQuartzProperties(properties);
//        schedulerFactory.setConfigLocation(new ClassPathResource("/quartz.properties"));
//
//        return schedulerFactory;
//    }
//}