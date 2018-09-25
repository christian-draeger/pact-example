package com.example.demo

import io.restassured.RestAssured.given
import org.hamcrest.Matchers.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [ProducerApplication::class], webEnvironment = RANDOM_PORT)
class DemoControllerTest {

    @LocalServerPort
    var port: Int = 0

    @Test
    fun canCallDemoController() {
        given().port(port)
        .`when`().get("/user")
        .then()
            .statusCode(200)
            .body("firstName", equalTo("Christian"))
            .body("lastName", equalTo("Dräger"))
            .body("age", equalTo(30))
            .body("ids.id", equalTo(0))
            .body("ids.uuid", not(isEmptyOrNullString()))
    }
}