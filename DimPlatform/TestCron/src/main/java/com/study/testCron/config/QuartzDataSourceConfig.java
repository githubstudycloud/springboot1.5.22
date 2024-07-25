package com.study.testCron.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class QuartzDataSourceConfig {

    @Bean(name = "quartzDataSource")
    @ConfigurationProperties(prefix = "quartz.datasource")
    public DataSource quartzDataSource() {
        return DataSourceBuilder.create().build();
    }
}

