package com.example.academyhomework.domain.features.englishToRussianCase

import com.leverx.android_modern_architecture_sample.testPlusGame.domain.features.englishToRussianCase.EnglishTranslate


interface AnswerHandler {
    val action: (EnglishTranslate.TranslateVariants, Int) -> Unit
    fun handle(ans: EnglishTranslate.TranslateVariants, adapterPosition: Int)
}