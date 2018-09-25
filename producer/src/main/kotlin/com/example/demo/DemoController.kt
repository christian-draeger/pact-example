package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.*

@RestController
class DataController {

    @GetMapping("/user")
    fun `allways return dummy data`() = UserModel()

}

data class UserModel(
    val firstName: String = "Christian",
    val lastName: String = "Dräger",
    val age: Int = 30,
    val currentDate: LocalDateTime = LocalDateTime.now(),
    val ids: AnotherModel = AnotherModel()
)

data class AnotherModel(
        val id: Int = 0,
        val uuid: String = UUID.randomUUID().toString()
)
