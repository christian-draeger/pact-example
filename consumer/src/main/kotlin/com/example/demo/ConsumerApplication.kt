package com.example.demo

import mu.KotlinLogging
import org.json.JSONObject
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.collections.set

@SpringBootApplication
class ConsumerApplication(
        val userClient: UserClient
) : CommandLineRunner {

    val logger = KotlinLogging.logger {}

    fun main(args: Array<String>) {
        runApplication<ConsumerApplication>(*args)
    }
    override fun run(vararg args: String?) {
        val dataFromProducer = userClient.callProducer()
        logger.warn { dataFromProducer.forEach{key, value -> println("\n$key is $value\n")} }
    }
}

fun JSONObject.toMap(): Map<String, String> {
    val map: MutableMap<String, String> = linkedMapOf()
    val keys = this.keys()
    keys.forEach { key -> map[key] = this[key].toString() }
    return map
}
