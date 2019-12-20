package megakit.miranda.movietest

import android.app.Activity
import android.app.Application
import androidx.multidex.MultiDexApplication
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import megakit.miranda.movietest.di.AppInjector
import javax.inject.Inject

class MovieApp : MultiDexApplication(), HasActivityInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = androidInjector

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }
}