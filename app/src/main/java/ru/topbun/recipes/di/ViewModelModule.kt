package ru.topbun.recipes.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.topbun.recipes.presentation.ViewModel


@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(ViewModel::class)
    @Binds
    fun bindAdsViewModel(impl: ViewModel): androidx.lifecycle.ViewModel

}