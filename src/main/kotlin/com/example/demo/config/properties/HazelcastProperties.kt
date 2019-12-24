package com.example.demo.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "application.hazelcast")
data class HazelcastProperties(val port: Int,
                               val cluster: List<String>)
