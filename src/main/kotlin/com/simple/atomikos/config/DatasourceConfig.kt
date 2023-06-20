package com.simple.atomikos.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.simple.atomikos.repository.db1"],
    entityManagerFactoryRef = "sampleEntityManagerFactory",
    transactionManagerRef = "transactionManager"
)
class SampleDatasourceConfig {

    @Primary
    @Bean("sampleDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.sample")
    fun sampleDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Primary
    @Bean("sampleDataSource")
    fun sampleDataSource(): DataSource {
        return sampleDataSourceProperties().initializeDataSourceBuilder().build()
    }

    @Primary
    @Bean("sampleEntityManagerFactory")
    fun sampleEntityManagerFactory(
        @Qualifier("sampleDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        val entityManagerFactoryBean = LocalContainerEntityManagerFactoryBean()
        entityManagerFactoryBean.dataSource = dataSource
        entityManagerFactoryBean.setPackagesToScan("com.simple.atomikos.entity.db1")
        entityManagerFactoryBean.persistenceUnitName = "db1"

        val vendorAdapter: JpaVendorAdapter = HibernateJpaVendorAdapter()
        entityManagerFactoryBean.jpaVendorAdapter = vendorAdapter
        entityManagerFactoryBean.setJpaPropertyMap(additionalProperties());

        return entityManagerFactoryBean
    }

    private fun additionalProperties(): HashMap<String, String> {
        val properties: HashMap<String, String> = HashMap()
        properties["hibernate.hbm2ddl.auto"] = "create-drop" // create create-drop validate none
        properties["hibernate.dialect"] = "org.hibernate.dialect.H2Dialect" //"org.hibernate.dialect.MySQL8Dialect"
        properties["hibernate.show_sql"] = "true"
        //properties.put("hibernate.format_sql", "true");
        //properties.put("hibernate.use_sql_comments", "true");
        return properties
    }
}

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.simple.atomikos.repository.db2"],
    entityManagerFactoryRef = "itemEntityManagerFactory",
    transactionManagerRef = "transactionManager"
)
class ItemDatasourceConfig {

    @Bean("itemDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.item")
    fun itemDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean("itemDataSource")
    fun itemDataSource(): DataSource {
        return itemDataSourceProperties().initializeDataSourceBuilder().build()
    }

    @Bean("itemEntityManagerFactory")
    fun itemEntityManagerFactory(
        @Qualifier("itemDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        val entityManagerFactoryBean = LocalContainerEntityManagerFactoryBean()
        entityManagerFactoryBean.dataSource = dataSource
        entityManagerFactoryBean.setPackagesToScan("com.simple.atomikos.entity.db2")
        entityManagerFactoryBean.persistenceUnitName = "db2"

        val vendorAdapter: JpaVendorAdapter = HibernateJpaVendorAdapter()
        entityManagerFactoryBean.jpaVendorAdapter = vendorAdapter
        entityManagerFactoryBean.setJpaPropertyMap(additionalProperties());

        return entityManagerFactoryBean
    }

    private fun additionalProperties(): HashMap<String, String> {
        val properties: HashMap<String, String> = HashMap()
        properties["hibernate.hbm2ddl.auto"] = "create-drop" // create create-drop validate none
        properties["hibernate.dialect"] = "org.hibernate.dialect.H2Dialect" //"org.hibernate.dialect.MySQL8Dialect"
        properties["hibernate.show_sql"] = "true"
        //properties.put("hibernate.format_sql", "true");
        //properties.put("hibernate.use_sql_comments", "true");
        return properties
    }
}