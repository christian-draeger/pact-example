package com.example.demo

import khttp.get
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class UserClient(
        @Value("\${producer.url}")
        val producerUrl: String
) {

    fun callProducer(): Map<String, String> {
        val producerResponse = get(producerUrl, timeout = 5.0)
        if (producerResponse.statusCode != 200) {
            throw RuntimeException("$producerUrl response was ${producerResponse.statusCode}")
        }
        return producerResponse.jsonObject.toMap()
    }
}

