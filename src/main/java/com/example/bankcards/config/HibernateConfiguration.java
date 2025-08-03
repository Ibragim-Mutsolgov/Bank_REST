package com.example.bankcards.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class HibernateConfiguration {

    @Value("${hibernate.data.driver}")
    private String driver;

    @Value("${hibernate.data.username}")
    private String username;

    @Value("${hibernate.data.password}")
    private String password;

    @Value("${hibernate.data.url}")
    private String url;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        return dataSource;
    }

    @Bean
    public static PersistenceExceptionTranslationPostProcessor postProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com.example.bankcards.entity");
        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setJpaProperties(properties());
        return factoryBean;
    }

    public Properties properties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.use_sql_comments", false);
        properties.put("hibernate.show_sql", false);
        return properties;
    }
}
