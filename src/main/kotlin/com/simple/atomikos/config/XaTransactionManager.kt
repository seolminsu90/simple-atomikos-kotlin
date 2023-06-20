package com.simple.atomikos.config

import com.atomikos.icatch.jta.UserTransactionManager
import jakarta.transaction.UserTransaction
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
        return XaDatatsourceUtil.userTransaction()
    }

    @Bean(name = ["userTransactionManager"])
    @Throws(Throwable::class)
    fun userTransactionManager(): UserTransactionManager {
        return XaDatatsourceUtil.userTransactionManager()
    }

    @Bean(name = ["globalTxManager"])
    @DependsOn(*["userTransaction", "userTransactionManager"])
    @Throws(Throwable::class)
    fun transactionManager(userTransaction: UserTransaction, userTransactionManager: UserTransactionManager): PlatformTransactionManager {
        return JtaTransactionManager(userTransaction, userTransactionManager)
    }
}
