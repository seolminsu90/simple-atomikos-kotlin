package com.simple.atomikos.entity.db2

import jakarta.persistence.*

@Table(name="tb_item")
@Entity
class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    var name: String
)