package com.example.intervaltimer.di

import android.content.Context
import androidx.room.Room
import com.example.intervaltimer.IntervalTimerDatabase
import com.example.intervaltimer.data.IntervalTimerDao
import com.example.intervaltimer.repository.IntervalTimerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): IntervalTimerDatabase =
        Room.databaseBuilder(
            context,
            IntervalTimerDatabase::class.java,
            "interval_timer_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()


    @Provides
    @Singleton
    fun provideIntervalTimerRepository(intervalTimerDao: IntervalTimerDao): IntervalTimerRepository =
        IntervalTimerRepository(intervalTimerDao)

    @Provides
    @Singleton
    fun provideIntervalTimerDao(intervalTimerDatabase: IntervalTimerDatabase): IntervalTimerDao =
        intervalTimerDatabase.intervalTimerDao()
}