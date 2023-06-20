package com.simple.atomikos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleAtomikosKotlinApplication

fun main(args: Array<String>) {
    runApplication<SimpleAtomikosKotlinApplication>(*args)
}
