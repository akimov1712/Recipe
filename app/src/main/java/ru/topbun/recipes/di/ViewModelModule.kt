package ru.topbun.recipes.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.topbun.recipes.presentation.detail.DetailRecipeViewModel
import ru.topbun.recipes.presentation.main.category.CategoryViewModel
import ru.topbun.recipes.presentation.main.favorite.FavoriteViewModel
import ru.topbun.recipes.presentation.main.home.HomeViewModel
import ru.topbun.recipes.presentation.main.search.SearchViewModel


@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    @Binds
    fun bindMainViewModel(impl: HomeViewModel): ViewModel

    @IntoMap
    @ViewModelKey(DetailRecipeViewModel::class)
    @Binds
    fun bindDetailViewModel(impl: DetailRecipeViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    @Binds
    fun bindCategoryViewModel(impl: CategoryViewModel): ViewModel

    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    @Binds
    fun bindFavoriteViewModel(impl: FavoriteViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    @Binds
    fun bindSearchViewModel(impl: SearchViewModel): ViewModel

}