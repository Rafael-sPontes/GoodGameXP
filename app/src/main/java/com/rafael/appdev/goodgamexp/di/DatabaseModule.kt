package com.rafael.appdev.goodgamexp.di

import android.content.Context
import androidx.room.Room
import com.rafael.appdev.goodgamexp.model.AppDatabase
import com.rafael.appdev.goodgamexp.model.GameDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideGameDao(db: AppDatabase): GameDao = db.gameDao()
}