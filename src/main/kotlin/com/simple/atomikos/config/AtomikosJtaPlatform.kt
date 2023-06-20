package com.simple.atomikos.config

import com.atomikos.icatch.jta.UserTransactionImp
import com.atomikos.icatch.jta.UserTransactionManager
import jakarta.transaction.TransactionManager
import jakarta.transaction.UserTransaction
import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform

class AtomikosJtaPlatform() : AbstractJtaPlatform() {
    private val userTransactionManager: UserTransactionManager by lazy { UserTransactionManager() }
    private val userTransaction: UserTransaction by lazy { UserTransactionImp() }

    override fun locateTransactionManager(): TransactionManager {
        return userTransactionManager
    }

    override fun locateUserTransaction(): UserTransaction {
        return userTransaction
    }
}
