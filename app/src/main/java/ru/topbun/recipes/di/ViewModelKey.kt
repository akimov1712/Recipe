package ru.topbun.recipes.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)