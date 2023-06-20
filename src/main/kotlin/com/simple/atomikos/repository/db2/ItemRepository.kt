package com.simple.atomikos.repository.db2

import com.simple.atomikos.entity.db2.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository : JpaRepository<Item, Long> {
}