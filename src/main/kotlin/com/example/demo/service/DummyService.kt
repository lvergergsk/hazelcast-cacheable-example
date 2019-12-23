package com.example.demo.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class DummyService {
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
}
