package com.example.demo.config

import com.example.demo.properties.ApplicationProperties
import com.hazelcast.core.HazelcastInstance
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.hazelcast.leader.LeaderInitiator
import org.springframework.integration.leader.Candidate
import org.springframework.integration.leader.Context
import org.springframework.integration.leader.DefaultCandidate

@Configuration
class LeadershipConfig(val hzInstance: HazelcastInstance, val applicationProperties: ApplicationProperties) {
    @Bean
    fun nodeServiceCandidate(): Candidate {
        return NodeCandidate(hzInstance.cluster.localMember.address.toString(), applicationProperties.clustering.role)
    }

    @Bean
    fun initiator(): LeaderInitiator {
        return LeaderInitiator(hzInstance, nodeServiceCandidate())
    }
}

class NodeCandidate(nodeId: String, role: String) : DefaultCandidate(nodeId, role) {
    override fun onGranted(ctx: Context) {
        super.onGranted(ctx);
        println(">>> Leader granted to: $ctx")
        // do some configuration.
    }

    override fun onRevoked(ctx: Context) {
        super.onGranted(ctx);
        println(">>> Leader revoked to: $ctx");
        // do some configuration.
    }
}
