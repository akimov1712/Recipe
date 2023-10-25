package ru.topbun.recipes.di

import android.app.Application
import ru.topbun.recipes.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import ru.topbun.recipes.presentation.detail.DetailRecipeActivity

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
    fun inject(activity: DetailRecipeActivity)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}