package com.damolks.ouxynext.di

import com.damolks.ouxynext.core.EventBus
import com.damolks.ouxynext.core.ModuleManager
import com.damolks.ouxynext.impl.DefaultEventBus
import com.damolks.ouxynext.impl.DefaultModuleManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindModuleManager(impl: DefaultModuleManager): ModuleManager

    @Binds
    @Singleton
    abstract fun bindEventBus(impl: DefaultEventBus): EventBus
}