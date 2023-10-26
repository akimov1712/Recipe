package ru.topbun.recipes.di

import android.app.Application
import ru.topbun.recipes.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import ru.topbun.recipes.presentation.SplashActivity
import ru.topbun.recipes.presentation.detail.DetailRecipeActivity
import ru.topbun.recipes.presentation.detail.IngredientsFragment
import ru.topbun.recipes.presentation.detail.StepRecipeFragment
import ru.topbun.recipes.presentation.FavoriteRecipeActivity

@ApplicationScope
@Component(
    modules = [
        DomainModule::class,
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: SplashActivity)
    fun inject(activity: DetailRecipeActivity)
    fun inject(activity: FavoriteRecipeActivity)
    fun inject(fragment: IngredientsFragment)
    fun inject(fragment: StepRecipeFragment)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}