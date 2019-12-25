package com.example.demo.config

import com.example.demo.config.properties.HazelcastProperties
import com.hazelcast.config.*
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(HazelcastProperties::class)
@EnableCaching
class HazelcastConfig(val hazelcastProperties: HazelcastProperties, val serverProperties: ServerProperties) {
    @Bean
    fun hazelCastConfig(): Config {
        val tcpIpConfig = TcpIpConfig().setEnabled(true)

        for (member in hazelcastProperties.cluster) {
            tcpIpConfig.addMember(member)
        }

        val networkConfig =
                NetworkConfig().setPort(hazelcastProperties.port)
                        .setJoin(JoinConfig().setMulticastConfig(MulticastConfig().setEnabled(false))
                                .setTcpIpConfig(tcpIpConfig))

        val managementCenterConfig =
                ManagementCenterConfig().setEnabled(true)
                        .setUrl("http://172.28.1.4:8080/hazelcast-mancenter")

        return Config().setInstanceName("hazelcast-instance")
                .addMapConfig(MapConfig().setName("demo")
                        .setMaxSizeConfig(MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setTimeToLiveSeconds(20))
                .setManagementCenterConfig(managementCenterConfig)
                .setGroupConfig(GroupConfig().setName("hazelcast-group"))
                .setNetworkConfig(networkConfig)
    }
}
