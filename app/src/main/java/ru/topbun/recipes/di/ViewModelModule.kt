package ru.topbun.recipes.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.topbun.recipes.presentation.detail.DetailRecipeViewModel
import ru.topbun.recipes.presentation.main.MainViewModel


@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(impl: MainViewModel): androidx.lifecycle.ViewModel

    @IntoMap
    @ViewModelKey(DetailRecipeViewModel::class)
    @Binds
    fun bindDetailViewModel(impl: DetailRecipeViewModel): androidx.lifecycle.ViewModel

}