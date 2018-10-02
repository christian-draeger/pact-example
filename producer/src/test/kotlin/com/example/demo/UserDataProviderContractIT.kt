package com.example.demo

import au.com.dius.pact.provider.junit.Provider
import au.com.dius.pact.provider.junit.loader.PactBroker
import au.com.dius.pact.provider.junit.target.Target
import au.com.dius.pact.provider.junit.target.TestTarget
import au.com.dius.pact.provider.spring.SpringRestPactRunner
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget
import org.junit.Ignore
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest

@Ignore("https://github.com/DiUS/pact-jvm/issues/784")
@RunWith(SpringRestPactRunner::class)
@Provider("user-data-provider")
@PactBroker(protocol = "http", host = "localhost", port = "8080")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserDataProviderContractIT {
    @TestTarget
    var target: Target = SpringBootHttpTarget()
}
