package jp.mixefy.multiregionalpubsub

import com.google.cloud.pubsub.v1.Publisher
import com.google.protobuf.ByteString
import com.google.pubsub.v1.PubsubMessage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

interface IMultiRegionalPublisher {

    fun publishToMultipleRegions(message: String)
}

@Service
class MultiRegionalPublisher(private val publisher: List<Publisher>) : IMultiRegionalPublisher {

    override fun publishToMultipleRegions(message: String) {
        log.warn("Handling message: '$message'")
        val pubsubMessage =
            PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8(message)).build()

        val futures = publisher.map {
            log.warn("publishing to ${it.topicName}")
            it.publish(pubsubMessage)
        }

        val results: List<Result<String>> = futures.map { apiFuture ->
            apiFuture.runCatching { apiFuture.get() }
                .onSuccess { messageId -> log.warn("Successfully published message: messageId='$messageId'") }
                .onFailure { throwable -> log.warn("Failed to publish message with error: $throwable") }
        }

        log.warn("Success/Fail: ${results.count { it.isSuccess }}/${results.count { it.isFailure }} (total: ${results.count()})")
    }

    companion object {
        private val log = LoggerFactory.getLogger(MultiRegionalPublisher::class.java)
    }
}
