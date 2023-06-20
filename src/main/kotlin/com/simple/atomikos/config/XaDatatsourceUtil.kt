package com.simple.atomikos.config

import com.atomikos.icatch.jta.UserTransactionImp
import com.atomikos.icatch.jta.UserTransactionManager
import com.atomikos.spring.AtomikosDataSourceBean
import jakarta.transaction.UserTransaction
import java.util.*
import javax.sql.DataSource

object XaDatatsourceUtil {
    fun createDatasource(user: String, pwd: String, url: String): DataSource {
        val dataSource = AtomikosDataSourceBean()
        val properties = Properties()
        properties.setProperty("user", user)
        properties.setProperty("password", pwd)
        properties.setProperty("url", url)

        dataSource.xaDataSourceClassName = "org.h2.jdbcx.JdbcDataSource" // 각 DB가 지원하는 XA Datasource로 설정해야함.
        dataSource.xaProperties = properties
        dataSource.uniqueResourceName = "unique_$url" // 관리 유니크 명명
        dataSource.borrowConnectionTimeout = 600 // 커넥션 풀 대기 타임아웃 시간
        dataSource.maxIdleTime = 60 // Idle 상태인 커넥션 풀 자동 반환 시간
        dataSource.minPoolSize = 10 // 커넥션 풀 min/max 개수
        dataSource.maxPoolSize = 20 // 커넥션 풀 min/max 개수

        // 기타 추가 옵션은 com.atomikos.jdbc.AtomikosDataSourceBean::doInit() 확인
        return dataSource
    }

    fun additionalProperties(): HashMap<String, String> {
        val properties: HashMap<String, String> = HashMap()
        properties["hibernate.hbm2ddl.auto"] = "create-drop" // create create-drop validate none
        properties["hibernate.dialect"] = "org.hibernate.dialect.H2Dialect" //"org.hibernate.dialect.MySQL8Dialect"
        properties["hibernate.show_sql"] = "true"
        properties["hibernate.transaction.jta.platform"] = "com.simple.atomikos.config.AtomikosJtaPlatform" // 증요
        properties["javax.persistence.transactionType"] = "JTA"
        properties["jakarta.persistence.transactionType"] = "JTA" // 둘중 하나 쓰면 되는데 우선 확인 안해봤음.
        //properties.put("hibernate.format_sql", "true");
        //properties.put("hibernate.use_sql_comments", "true");
        return properties
    }

    fun userTransactionManager(): UserTransactionManager {
        return UserTransactionManager().apply {
            forceShutdown = false
        }
    }

    fun userTransaction(): UserTransaction {
        return UserTransactionImp().apply {
            setTransactionTimeout(1000)
        }
    }
}