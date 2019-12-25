package com.example.demo.service

import com.example.demo.tasks.EchoTask
import com.hazelcast.core.HazelcastInstance
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class DummyService(val hzInstance: HazelcastInstance) {
    @Cacheable(cacheNames = ["demo"], key = "'getTimeNow'")
    fun getTimeNow(): LocalDateTime {
        println(">>> getTimeNow called.")
        return LocalDateTime.now()
    }

    @Cacheable(cacheNames = ["demo"], key = "'getTimeYesterday'")
    fun getTimeYesterday(): LocalDateTime {
        println(">>> getTimeYesterday called.")
        return LocalDateTime.now().minusDays(1)
    }

    fun longRunningTask() {
        val executorService = hzInstance.getExecutorService("exec")
        executorService.execute(EchoTask(hzInstance.name))
    }
}
