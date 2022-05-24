package com.leverx.android_modern_architecture_sample.testPlusGame.domain.features.englishToRussianCase

import android.view.View
import com.example.academyhomework.utils.Utils
import com.google.android.material.button.MaterialButton
import com.leverx.android_modern_architecture_sample.R
import kotlin.random.Random

data class EnglishTranslate(
    val engWord: String = "",
    val translateRus: String = "",
    val hint: String = Utils.makeHint(translateRus)
) {

    fun setAnswers(listW: List<EnglishTranslate>, view: View) {
        val b1: MaterialButton = view.findViewById(R.id.b_translate_1)
        val b2: MaterialButton = view.findViewById(R.id.b_translate_2)
        val b3: MaterialButton = view.findViewById(R.id.b_translate_3)
        val b4: MaterialButton = view.findViewById(R.id.b_translate_4)

        var list: List<String> = listOf(
            translateRus,
            listW[Random.nextInt(0, listW.size)].translateRus,
            listW[Random.nextInt(0, listW.size)].translateRus,
            listW[Random.nextInt(0, listW.size)].translateRus
        )
        list = list.shuffled()
        b1.text = list[0]
        b2.text = list[1]
        b3.text = list[2]
        b4.text = list[3]
    }

    enum class TranslateVariants {
        GOOD,
        WRONG
    }
}
