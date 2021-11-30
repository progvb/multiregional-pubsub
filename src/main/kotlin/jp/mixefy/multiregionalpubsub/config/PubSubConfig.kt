package jp.mixefy.multiregionalpubsub.config

import com.google.api.gax.grpc.GrpcTransportChannel
import com.google.api.gax.rpc.FixedTransportChannelProvider
import com.google.cloud.pubsub.v1.Publisher
import io.grpc.ManagedChannelBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
data class PubSubConfig(val props: TopicEndpointConfigProps) {

    @Bean
    fun publishers() = props.topicEndpoints.map {
        Publisher.newBuilder(it.topic)
            .setChannelProvider(createChannelProvider(it.endpoint))
            .build()
    }

    companion object {
        private fun createChannelProvider(endpoint: String) =
            FixedTransportChannelProvider.create(
                GrpcTransportChannel.create(
                    ManagedChannelBuilder.forTarget(endpoint).build()
                )
            )
    }
}
