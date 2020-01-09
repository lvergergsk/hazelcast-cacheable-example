package com.example.demo.config

import com.example.demo.properties.ApplicationProperties
import com.hazelcast.config.*
import com.hazelcast.config.cp.CPSubsystemConfig
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(ApplicationProperties::class)
@EnableCaching
class HazelcastConfig(val applicationProperties: ApplicationProperties) {

    @Bean
    fun hazelCastConfig(): Config {
        val tcpIpConfig = TcpIpConfig().setEnabled(true)

        for (member in applicationProperties.hazelcast.cluster) {
            tcpIpConfig.addMember(member)
        }

        val networkConfig =
                NetworkConfig().setPort(applicationProperties.hazelcast.port)
                        .setJoin(JoinConfig().setMulticastConfig(MulticastConfig().setEnabled(false))
                                .setTcpIpConfig(tcpIpConfig))

        val managementCenterConfig =
                ManagementCenterConfig().setEnabled(true)
                        .setUrl("http://172.28.1.4:8080/hazelcast-mancenter")

        val demoCache = MapConfig().setName("demo")
                .setMaxSizeConfig(MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                .setEvictionPolicy(EvictionPolicy.LRU)
                .setTimeToLiveSeconds(20)

        val executorConfig = ExecutorConfig().setName("exec").setPoolSize(5).setQueueCapacity(100)

        // CPMemberCount need to be equal or more than 3
        val cpSubsystemConfig = CPSubsystemConfig().setCPMemberCount(3)

        return Config().setInstanceName("hazelcast-instance")
                .addMapConfig(demoCache)
                .addExecutorConfig(executorConfig)
                .setManagementCenterConfig(managementCenterConfig)
                .setGroupConfig(GroupConfig().setName("hazelcast-group"))
                .setNetworkConfig(networkConfig)
                .setCPSubsystemConfig(cpSubsystemConfig)
    }
}

