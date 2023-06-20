package com.simple.atomikos.config

import jakarta.transaction.TransactionManager
import jakarta.transaction.UserTransaction
import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform

class AtomikosJtaPlatform() : AbstractJtaPlatform() {

    override fun locateTransactionManager(): TransactionManager {
        return XaDatatsourceUtil.userTransactionManager()
    }

    override fun locateUserTransaction(): UserTransaction {
        return XaDatatsourceUtil.userTransaction()
    }
}
