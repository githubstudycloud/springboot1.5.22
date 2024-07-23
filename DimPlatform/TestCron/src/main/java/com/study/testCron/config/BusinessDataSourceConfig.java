package com.study.testCron.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.study.testCron.repository",
        entityManagerFactoryRef = "businessEntityManagerFactory",
        transactionManagerRef = "businessTransactionManager"
)
public class BusinessDataSourceConfig {

    @Primary
    @Bean(name = "businessDataSource")
    @ConfigurationProperties(prefix = "business.datasource")
    public DataSource businessDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "businessEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean businessEntityManagerFactory(
            @Qualifier("businessDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.study.testCron.entity");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean(name = "businessTransactionManager")
    public PlatformTransactionManager businessTransactionManager(
            @Qualifier("businessEntityManagerFactory") LocalContainerEntityManagerFactoryBean businessEntityManagerFactory) {
        return new JpaTransactionManager(businessEntityManagerFactory.getObject());
    }
}