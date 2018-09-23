package com.example.demo

import au.com.dius.pact.consumer.ConsumerPactTestMk2
import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.PactDslJsonBody
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.model.RequestResponsePact
import org.assertj.core.api.KotlinAssertions.assertThat


class ContractTest : ConsumerPactTestMk2() {

    override fun providerName(): String = "user-data-provider"
    override fun consumerName(): String = "user-data-cli"

    override fun createPact(builder: PactDslWithProvider): RequestResponsePact {

        val body = PactDslJsonBody()
                .stringType("firstName")
                .stringType("lastName")
                .numberType("age")
                .`object`("ids", PactDslJsonBody()
                        .integerType("id")
                        .uuid("uuid"))

        return builder.uponReceiving("can get user data from user data provider")
                .path("/user")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(body)
                .toPact()
    }

    override fun runTest(mockServer: MockServer) {
        val expectedKeys = listOf("firstName", "lastName", "ids", "age")
        val result = UserClient("${mockServer.getUrl()}/user").callProducer()
        assertThat(result.keys).containsAll(expectedKeys)
    }
}