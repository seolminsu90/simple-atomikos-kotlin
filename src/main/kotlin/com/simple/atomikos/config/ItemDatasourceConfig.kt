package com.simple.atomikos.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
        basePackages = ["com.simple.atomikos.repository.db2"],
        entityManagerFactoryRef = "itemEntityManagerFactory",
        transactionManagerRef = "globalTxManager"
)
class ItemDatasourceConfig {

    @Bean("itemDataSource")
    fun itemDataSource(): DataSource {
        return XaDatatsourceUtil.createDatasource("sa", "", "jdbc:h2:mem:itemdb")
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
        entityManagerFactoryBean.setJpaPropertyMap(XaDatatsourceUtil.additionalProperties());

        return entityManagerFactoryBean
    }
}
