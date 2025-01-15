package com.damolks.ouxynext.di

import android.content.Context
import com.damolks.ouxynext.core.EventBus
import com.damolks.ouxynext.core.ModuleManager
import com.damolks.ouxynext.core.data.OuxyDatabase
import com.damolks.ouxynext.core.data.dao.EventLogDao
import com.damolks.ouxynext.core.data.dao.ModuleStateDao
import com.damolks.ouxynext.core.repository.EventLogRepository
import com.damolks.ouxynext.core.repository.ModuleStateRepository
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
    fun provideDatabase(@ApplicationContext context: Context): OuxyDatabase {
        return OuxyDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideModuleStateDao(database: OuxyDatabase): ModuleStateDao {
        return database.moduleStateDao()
    }

    @Provides
    @Singleton
    fun provideEventLogDao(database: OuxyDatabase): EventLogDao {
        return database.eventLogDao()
    }

    @Provides
    @Singleton
    fun provideModuleStateRepository(moduleStateDao: ModuleStateDao): ModuleStateRepository {
        return ModuleStateRepository(moduleStateDao)
    }

    @Provides
    @Singleton
    fun provideEventLogRepository(eventLogDao: EventLogDao): EventLogRepository {
        return EventLogRepository(eventLogDao)
    }

    @Provides
    @Singleton
    fun provideModuleManager(
        @ApplicationContext context: Context,
        moduleStateRepository: ModuleStateRepository,
        eventLogRepository: EventLogRepository
    ): ModuleManager {
        return ModuleManager(context, moduleStateRepository, eventLogRepository)
    }

    @Provides
    @Singleton
    fun provideEventBus(): EventBus {
        return EventBus()
    }
}