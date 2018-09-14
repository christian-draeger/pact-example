package com.example.demo

import khttp.get
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConsumerApplication : CommandLineRunner {

    @Value("\${producer.url}")
    lateinit var producerUrl: String

    val logger = KotlinLogging.logger {}

    fun main(args: Array<String>) {
        runApplication<ConsumerApplication>(*args)
    }
    override fun run(vararg args: String?) {
        val dataFromProducer = callProducer()
        logger.warn { dataFromProducer.forEach{key, value -> println("\n$key is $value\n")} }
    }

    fun callProducer(): Map<String, String> {
        logger.info { "try to call $producerUrl" }
        val producerResponse = get("$producerUrl/someThing", timeout = 5.0)
        if (producerResponse.statusCode == 200) {
            return jsonToMap(producerResponse.jsonObject)
        }
        return emptyMap()
    }
}
