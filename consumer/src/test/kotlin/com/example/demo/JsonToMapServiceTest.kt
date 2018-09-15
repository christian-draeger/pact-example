package com.example.demo

import org.assertj.core.api.KotlinAssertions
import org.json.JSONObject
import org.junit.Test

class JsonToMapServiceTest {

    @Test
    fun `can parse json with string values`() {
        val sud = JSONObject("""{"key": "value", "number": "4711"}""").toMap()
        KotlinAssertions.assertThat(sud).isEqualTo(mapOf("number" to "4711", "key" to "value"))
    }

    @Test
    fun `can parse json with number values`() {
        val sud = JSONObject("""{"number": 1234, "otherNumber": 4711}""").toMap()
        KotlinAssertions.assertThat(sud).isEqualTo(mapOf("number" to "1234", "otherNumber" to "4711"))
    }

    @Test
    fun `can parse json with mixed values`() {
        val sud = JSONObject("""{"key": "value", "number": 4711}""").toMap()
        KotlinAssertions.assertThat(sud).isEqualTo(mapOf("number" to "4711", "key" to "value"))
    }
}
