package com.simple.atomikos.entity.db1

import jakarta.persistence.*

@Table(name="tb_sample")
@Entity
class Sample(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    var name: String
)