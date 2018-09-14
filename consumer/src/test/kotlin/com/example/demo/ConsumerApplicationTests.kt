package com.example.demo

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class ConsumerApplicationTests {

    @Test
    fun `will call producer on application context loads`() {
    }
}
