package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import au.com.dius.pact.consumer.ConsumerPactTestMk2;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

public class JavaContractTest extends ConsumerPactTestMk2 {

    @Override
    protected String providerName() {
        return "user-data-provider";
    }

    @Override
    protected String consumerName() {
        return "user-data-cli";
    }

    @Override
    protected RequestResponsePact createPact(PactDslWithProvider builder) {
        PactDslJsonBody body = new PactDslJsonBody()
                .stringType("firstName")
                .stringType("lastName")
                .numberType("age")
                .object("ids", new PactDslJsonBody()
                    .integerType("id")
                    .uuid("uuid")
                );

        return builder.uponReceiving("can get user data from user data provider")
                .path("/user")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(body)
                .toPact();
    }

    @Override
    protected void runTest(MockServer mockServer) {
        List<String> expectedKeys = Arrays.asList("firstName", "lastName", "ids", "age");
        Map<String, String> result = new UserClient(mockServer.getUrl() + "/user").callProducer();
        assertThat(result.keySet()).containsAll(expectedKeys);
    }
}
