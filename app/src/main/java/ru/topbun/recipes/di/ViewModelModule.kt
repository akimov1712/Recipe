package ru.topbun.recipes.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.topbun.recipes.presentation.main.MainViewModel


@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindAdsViewModel(impl: MainViewModel): androidx.lifecycle.ViewModel

}