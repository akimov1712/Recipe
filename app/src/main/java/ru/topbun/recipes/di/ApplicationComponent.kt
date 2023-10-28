package ru.topbun.recipes.di

import android.app.Application
import ru.topbun.recipes.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component
import ru.topbun.recipes.presentation.SplashActivity
import ru.topbun.recipes.presentation.detail.DetailRecipeActivity
import ru.topbun.recipes.presentation.detail.IngredientsFragment
import ru.topbun.recipes.presentation.detail.StepRecipeFragment
import ru.topbun.recipes.presentation.main.category.CategoryFragment
import ru.topbun.recipes.presentation.main.favorite.FavoriteFragment
import ru.topbun.recipes.presentation.main.home.HomeFragment
import ru.topbun.recipes.presentation.main.search.SearchFragment

@ApplicationScope
@Component(
    modules = [
        DomainModule::class,
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: SplashActivity)
    fun inject(activity: DetailRecipeActivity)
    fun inject(fragment: IngredientsFragment)
    fun inject(fragment: StepRecipeFragment)
    fun inject(fragment: FavoriteFragment)
    fun inject(fragment: CategoryFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: HomeFragment)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}