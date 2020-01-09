package com.example.demo.scheduled

import com.example.demo.tasks.EchoTask
import com.hazelcast.core.HazelcastInstance
import org.springframework.integration.hazelcast.leader.LeaderInitiator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class OnceInCluster(val hzInstance: HazelcastInstance, val initiator: LeaderInitiator) {

    @Scheduled(cron = "*/5 * * * * ?")
    fun echo() {
        if (!initiator.context.isLeader) return

        // this task is send from the leader, and distributed to cluster.
        val executorService = hzInstance.getExecutorService("exec")
        executorService.execute(EchoTask("""send from ${hzInstance.cluster.localMember.address}"""))
    }
}
