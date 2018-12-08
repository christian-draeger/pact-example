package com.example.demo.messaging

import java.util.*

data class UserCreateEvent(
        val firstName: String,
        val lastName: String,
        val age: Int,
        val active: Boolean,
        val expiryDate: Date,
        val timestamp: Long,
        val ids: Identifiers
)

data class Identifiers(
        val id: Long,
        val uuid: UUID
)