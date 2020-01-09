package com.example.demo.properties

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.NestedConfigurationProperty
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@ConstructorBinding
@ConfigurationProperties(prefix = "application")
@Validated
data class ApplicationProperties(@NestedConfigurationProperty val hazelcast: HazelcastProperties,@NestedConfigurationProperty val clustering: ClusteringProperties) {
    data class ClusteringProperties(@NotBlank val role: String)

    data class HazelcastProperties(@Min(1024) @Max(49151) val port: Int,
                                   @Size(min = 1) val cluster: List<String>)
}
