package megakit.miranda.movietest.di



import dagger.Module
import dagger.android.ContributesAndroidInjector
import megakit.miranda.movietest.ui.MainActivity

@Suppress("unused")
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}