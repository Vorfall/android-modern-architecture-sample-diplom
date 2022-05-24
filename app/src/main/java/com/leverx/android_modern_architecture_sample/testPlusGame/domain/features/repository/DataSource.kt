package com.leverx.android_modern_architecture_sample.testPlusGame.domain.features.repository

import android.content.Context
import com.example.academyhomework.domain.features.simpleWordList.Dword
import com.example.academyhomework.utils.Utils.stringWordsDivider
import com.leverx.android_modern_architecture_sample.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class DataSource {
    private var scope = CoroutineScope(Dispatchers.IO)

    suspend fun loadWords(context: Context): List<Dword> {


        val value = scope.async {
            var wordList: List<String> = stringWordsDivider(
                context.resources.getString(R.string.word_collection)
            )

            var dwordList = mutableListOf<Dword>(Dword())
            for (item in wordList) {
                when {
                    dwordList.last().word == "nope" -> dwordList.add(Dword(item))
                    dwordList.last().translate == "nope" -> dwordList.last().translate = item
                    else -> dwordList.add(Dword(item))
                }
            }
            dwordList

        }
        return value.await()
    }
}

