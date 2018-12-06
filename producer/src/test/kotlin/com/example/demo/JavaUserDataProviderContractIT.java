package com.example.demo;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;

@RunWith(SpringRestPactRunner.class)
@Provider("user-data-provider")
@PactBroker(protocol = "http", host = "localhost", port = "80")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JavaUserDataProviderContractIT {

    @TestTarget
    public final Target target = new SpringBootHttpTarget();

    // only needed for Ajax Consumer
    @State("some user available")
    public void userAvailable() {
    }
}
