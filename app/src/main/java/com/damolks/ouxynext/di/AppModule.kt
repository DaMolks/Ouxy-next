package com.damolks.ouxynext.di

import com.damolks.ouxynext.core.EventBus
import com.damolks.ouxynext.core.ModuleManager
import com.damolks.ouxynext.impl.DefaultEventBus
import com.damolks.ouxynext.impl.DefaultModuleManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideModuleManager(): ModuleManager = DefaultModuleManager()

    @Provides
    @Singleton
    fun provideEventBus(): EventBus = DefaultEventBus()
}