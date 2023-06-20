package com.simple.atomikos.controller

import com.simple.atomikos.service.SampleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleController(
    private val sampleService: SampleService
) {

    @GetMapping("/test")
    fun transactionTest(@RequestParam error: String?) : String{
        sampleService.insertSample(error)
        return "Success"
    }
}