package ru.topbun.recipes

import android.app.Application
import ru.topbun.recipes.di.DaggerApplicationComponent

class App: Application() {

    val component by lazy{
        DaggerApplicationComponent.factory().create(this)
    }

}