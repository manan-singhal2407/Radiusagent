package com.radiusagent.assignment.domain.di

import android.content.Context
import com.radiusagent.assignment.domain.Security
import com.radiusagent.assignment.domain.Radiusagent
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext application: Context): Radiusagent =
        application as Radiusagent

    @Singleton
    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context): Context = context

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideSecurity(): Security = Security()
}