package com.example.demo.controller

import com.example.demo.service.DummyService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1.0/dummy")
class DummyController(private val dummyService: DummyService) {
    @GetMapping("time/now")
    fun getTimeNow(): String = dummyService.getTimeNow().toString()

    @GetMapping("time/yesterday")
    fun getTimeYesterday(): String = dummyService.getTimeYesterday().toString()

    @GetMapping("task/log")
    fun runTask(): String {
        dummyService.longRunningTask()
        return "OK"
    }
}
