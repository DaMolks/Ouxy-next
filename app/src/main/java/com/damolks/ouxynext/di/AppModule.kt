package com.damolks.ouxynext.di

import android.content.Context
import com.damolks.ouxynext.core.EventBus
import com.damolks.ouxynext.core.ModuleManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideModuleManager(
        @ApplicationContext context: Context
    ): ModuleManager {
        return ModuleManager(context)
    }

    @Provides
    @Singleton
    fun provideEventBus(): EventBus {
        return EventBus()
    }
}