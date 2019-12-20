package megakit.miranda.movietest.di

import android.app.Application
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import megakit.miranda.movietest.MovieApp
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        ActivityBuilderModule::class,
        ViewModelModule::class]
)
interface AppComponent : AndroidInjector<MovieApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MovieApp>()

    fun inject(app: Application)
}