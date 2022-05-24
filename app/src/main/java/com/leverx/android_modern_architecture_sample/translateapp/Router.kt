package com.example.academyhomework

import android.view.View

interface Router {
    fun onClickToHide(view: View)
    fun onWordListClicked()
    fun onEnglishTranslateClicked(splashScreenfragment: SplashScreenfragment)
    fun onRussianTranslateClicked(splashScreenfragment: SplashScreenfragment)
}