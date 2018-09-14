package com.example.demo

import org.json.JSONObject

fun jsonToMap(json: JSONObject): Map<String, String> {
    val map: MutableMap<String, String> = linkedMapOf()
    val keys = json.keys()
    while (keys.hasNext()) {
        val key = keys.next()
        map.put(key, json[key].toString())
    }
    return map
}