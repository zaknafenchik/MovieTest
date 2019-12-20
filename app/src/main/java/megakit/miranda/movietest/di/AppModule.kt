package megakit.miranda.movietest.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import megakit.miranda.movietest.MovieApp
import megakit.miranda.movietest.data.api.MovieApi
import megakit.miranda.movietest.data.repo.Repo
import megakit.miranda.movietest.data.repo.RepoImpl
import megakit.miranda.movietest.di.qualifier.ApplicationContext
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @ApplicationContext
    @Singleton
    fun provideContext(app: MovieApp): Context = app

    @Provides
    @Singleton
    fun provideRepo(api:MovieApi):Repo = RepoImpl(api)
}