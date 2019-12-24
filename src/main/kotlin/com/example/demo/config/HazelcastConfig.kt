package com.example.demo.config

import com.hazelcast.config.*
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class HazelcastConfig {
    @Bean
    fun hazelCastConfig(): Config {
        val tcpIpConfig = TcpIpConfig()
                .setEnabled(true)
                .addMember("127.0.0.1:9701")
                .addMember("127.0.0.1:9702")

        val networkConfig = NetworkConfig().setPort(9701)
                .setJoin(JoinConfig()
                        .setMulticastConfig(MulticastConfig().setEnabled(false))
                        .setTcpIpConfig(tcpIpConfig))

        return Config().setInstanceName("hazelcast-instance")
                .addMapConfig(MapConfig().setName("demo")
                        .setMaxSizeConfig(MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setTimeToLiveSeconds(20))
                .setGroupConfig(GroupConfig().setName("hazelcast-group"))
                .setNetworkConfig(networkConfig)
    }
}
