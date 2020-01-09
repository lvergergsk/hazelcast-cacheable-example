package com.example.demo.tasks

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.Serializable

class SlowEchoTask(private val msg: String) : Runnable, Serializable {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun run() {
        try {
            Thread.sleep(20000)
        } catch (e: InterruptedException) {
        }
        log.info("echo:$msg")
    }
}
