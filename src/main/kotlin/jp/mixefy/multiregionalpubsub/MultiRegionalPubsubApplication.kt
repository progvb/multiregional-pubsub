package jp.mixefy.multiregionalpubsub

import jp.mixefy.multiregionalpubsub.config.TopicEndpointConfigProps
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(TopicEndpointConfigProps::class)
class MultiregionalPubsubApplication

fun main(args: Array<String>) {
    runApplication<MultiregionalPubsubApplication>(*args)
}
