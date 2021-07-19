package com.diferencedb.test.config;

import lombok.SneakyThrows;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("application.properties")
@ComponentScan("com.diferencedb.test")
public class DataBaseConfiguration {
    @Resource
    private Environment environment;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("first") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.diferencedb.test.model");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(getProperties());
        return em;
    }

    @Bean("second")
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("first")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.first")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().build();
    }

    @SneakyThrows
    private Properties getProperties() {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("hibernate_mysql.properties");
        properties.load(inputStream);
        return properties;
    }

}
