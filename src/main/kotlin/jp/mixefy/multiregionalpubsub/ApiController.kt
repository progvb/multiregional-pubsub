package jp.mixefy.multiregionalpubsub

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController(val publisher: IMultiRegionalPublisher) {

    @PostMapping("/publish")
    fun publish(@RequestBody message: String) {
        publisher.publishToMultipleRegions(message)
    }
}