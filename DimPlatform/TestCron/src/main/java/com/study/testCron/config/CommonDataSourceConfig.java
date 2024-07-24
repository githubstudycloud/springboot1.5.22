package com.study.testCron.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.study.testCron.dao.common", sqlSessionTemplateRef = "commonSqlSessionTemplate")
public class CommonDataSourceConfig {

    @Primary
    @Bean(name = "commonDataSource")
    @ConfigurationProperties(prefix = "common.datasource")
    public DataSource commonDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "commonSqlSessionFactory")
    public SqlSessionFactory commonSqlSessionFactory(@Qualifier("commonDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/common/*.xml"));
        return bean.getObject();
    }

    @Primary
    @Bean(name = "commonTransactionManager")
    public DataSourceTransactionManager commonTransactionManager(@Qualifier("commonDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "commonSqlSessionTemplate")
    public SqlSessionTemplate commonSqlSessionTemplate(@Qualifier("commonSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}