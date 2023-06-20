package com.simple.atomikos.entity.db3

import jakarta.persistence.*

@Table(name="tb_second")
@Entity
class Second(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    var name: String
)