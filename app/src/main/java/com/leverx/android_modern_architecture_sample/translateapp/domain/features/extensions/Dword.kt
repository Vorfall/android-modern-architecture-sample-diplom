package com.example.academyhomework.domain.features.extensions

import com.example.academyhomework.domain.features.simpleWordList.Dword
import com.leverx.android_modern_architecture_sample.translateapp.domain.features.englishToRussianCase.EnglishTranslate
import com.example.academyhomework.domain.features.russianToEnglishCase.RussianTranslate
import com.example.academyhomework.utils.Utils

fun Dword.toRussianTranslate(): RussianTranslate {
    return RussianTranslate(
        rusWord = translate,
        translateEng = word,
        hint = Utils.makeHint(word)
    )
}

fun List<Dword>.toEnglishTranslateList():List<EnglishTranslate>{

    var newList:MutableList<EnglishTranslate> = mutableListOf(EnglishTranslate("nope","nope"))
    forEach { newList?.add(it.toEnglishTranslate()) }


    return newList
}

fun Dword.toEnglishTranslate(): EnglishTranslate {
    return EnglishTranslate(
        engWord = word,
        translateRus = translate,
        hint = Utils.makeHint(translate)
    )
}