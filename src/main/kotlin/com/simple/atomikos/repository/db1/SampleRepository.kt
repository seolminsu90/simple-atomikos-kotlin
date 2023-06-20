package com.simple.atomikos.repository.db1

import com.simple.atomikos.entity.db1.Sample
import org.springframework.data.jpa.repository.JpaRepository

interface SampleRepository : JpaRepository<Sample, Long> {
}