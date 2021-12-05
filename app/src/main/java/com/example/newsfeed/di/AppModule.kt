package com.example.newsfeed.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.newsfeed.api.NewsApi
import com.example.newsfeed.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

private const val TAG = "AppModule"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        Log.d(TAG, "provideRetrofit: providing...")
      return  Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsApi(retrofit: Retrofit) : NewsApi =
        retrofit.create(NewsApi::class.java)

    @Singleton
    @Provides
    fun provideDatabase(app: Application) : ArticleDatabase =
        Room.databaseBuilder(app, ArticleDatabase::class.java, "article_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideArticleDao(articleDatabase: ArticleDatabase) = articleDatabase.articleDao()

    @ApplicationScope
    @Singleton
    @Provides
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope