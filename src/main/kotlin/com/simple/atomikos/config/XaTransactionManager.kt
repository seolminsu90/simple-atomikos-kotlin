package com.simple.atomikos.config

import com.atomikos.icatch.jta.UserTransactionImp
import com.atomikos.icatch.jta.UserTransactionManager
import jakarta.transaction.TransactionManager
import jakarta.transaction.UserTransaction
import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.jta.JtaTransactionManager

@Configuration
class XaTransactionManager {
    @Bean(name = ["userTransaction"])
    @Throws(Throwable::class)
    fun userTransaction(): UserTransaction {
        val userTransactionImp = UserTransactionImp()
        userTransactionImp.setTransactionTimeout(10000)
        return userTransactionImp
    }

    @Bean(name = ["atomikosTransactionManager"])
    @Throws(Throwable::class)
    fun atomikosTransactionManager(): UserTransactionManager {
        val userTransactionManager = UserTransactionManager()
        userTransactionManager.forceShutdown = false
        return userTransactionManager
    }

    @Bean(name = ["globalTxManager"])
    @DependsOn(*["userTransaction", "atomikosTransactionManager"])
    @Throws(Throwable::class)
    fun transactionManager(userTransaction: UserTransaction, atomikosTransactionManager: UserTransactionManager): PlatformTransactionManager {
        return JtaTransactionManager(userTransaction, atomikosTransactionManager)
    }
}
