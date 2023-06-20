package com.simple.atomikos.repository.db3

import com.simple.atomikos.entity.db1.Sample
import com.simple.atomikos.entity.db3.Second
import org.springframework.data.jpa.repository.JpaRepository

interface SecondRepository : JpaRepository<Second, Long> {
}