package com.example.academyhomework.domain.features.simpleWordList

interface EditTextChangeListener {
    val action:(CharSequence?,Int,Int,Int) -> Unit
}