package com.leverx.android_modern_architecture_sample.data.di

import android.content.Context
import androidx.room.Room
import com.leverx.android_modern_architecture_sample.BuildConfig
import com.leverx.android_modern_architecture_sample.data.network.NewsService
import com.leverx.android_modern_architecture_sample.data.storage.ArticleRoomDatabase
import com.leverx.android_modern_architecture_sample.util.idlingResource.Idling
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        Idling.startProcess()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java).also { Idling.endProcess() }
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ArticleRoomDatabase {
        return Room.databaseBuilder(
            context,
            ArticleRoomDatabase::class.java,
            "article_table"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }
}
