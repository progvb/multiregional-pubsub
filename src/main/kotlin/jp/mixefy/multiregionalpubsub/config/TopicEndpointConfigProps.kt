package jp.mixefy.multiregionalpubsub.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("publisher")
data class TopicEndpointConfigProps(val topicEndpoints: List<TopicEndpoint>)

data class TopicEndpoint(val topic: String, val endpoint: String)