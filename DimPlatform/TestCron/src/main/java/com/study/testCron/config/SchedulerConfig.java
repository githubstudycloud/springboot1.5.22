package com.study.testCron.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class SchedulerConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("quartzDataSource")
    private DataSource quartzDataSource;

    @Autowired
    @Qualifier("commonTransactionManager")
    private PlatformTransactionManager transactionManager;


    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(quartzDataSource);
        schedulerFactoryBean.setTransactionManager(transactionManager);
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setSchedulerName("ClusterScheduler");
        schedulerFactoryBean.setJobFactory(springBeanJobFactory());
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        return schedulerFactoryBean;
    }

    @Bean
    public Properties quartzProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        Properties properties = null;
        try {
            propertiesFactoryBean.afterPropertiesSet();
            properties = propertiesFactoryBean.getObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}