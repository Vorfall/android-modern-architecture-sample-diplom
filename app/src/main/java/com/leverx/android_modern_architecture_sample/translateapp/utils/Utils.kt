package com.example.academyhomework.utils

import java.util.stream.Collectors.toList

object Utils {

    fun stringWordsDivider(string: String): List<String> {
        var list: List<String> = string.trim()
            .replace(" — ", "$", false)
            .replace(" ", "$", false)
            .split("$")
        return list
    }

    fun makeHint(word: String): String {
        var value: String = ""
        for (item in word.toList().shuffled()) {
            value = value.plus(item)
        }
        return value
    }
}
