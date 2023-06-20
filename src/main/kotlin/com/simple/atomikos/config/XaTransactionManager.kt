package com.simple.atomikos.config

import com.atomikos.icatch.jta.UserTransactionImp
import com.atomikos.icatch.jta.UserTransactionManager
import jakarta.transaction.UserTransaction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionManager
import org.springframework.transaction.jta.JtaTransactionManager


@Configuration
class XaTransactionManager {

    @Bean
    @Throws(Throwable::class)
    fun userTransaction(): UserTransactionImp {
        val userTransaction = UserTransactionImp()
        userTransaction.setTransactionTimeout(300)

        return userTransaction
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    fun userTransactionManager(): UserTransactionManager {
        val transactionManager = UserTransactionManager()
        transactionManager.setForceShutdown(false)

        return transactionManager
    }

    @Bean
    fun transactionManager(
        userTransactionManager: UserTransactionManager,
        userTransaction: UserTransactionImp
    ): PlatformTransactionManager {
        var transactionManager = JtaTransactionManager()
        transactionManager.userTransaction = userTransaction
        transactionManager.transactionManager = userTransactionManager
        transactionManager.setTransactionSynchronizationName("SYNCHRONIZATION_ON_ACTUAL_TRANSACTION")

        return transactionManager
    }

}