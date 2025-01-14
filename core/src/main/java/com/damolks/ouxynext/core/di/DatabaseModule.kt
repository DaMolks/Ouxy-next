package com.damolks.ouxynext.core.di

import android.content.Context
import com.damolks.ouxynext.core.data.OuxyDatabase
import com.damolks.ouxynext.core.data.dao.EventLogDao
import com.damolks.ouxynext.core.data.dao.ModuleStateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): OuxyDatabase {
        return OuxyDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideModuleStateDao(database: OuxyDatabase): ModuleStateDao {
        return database.moduleStateDao()
    }

    @Singleton
    @Provides
    fun provideEventLogDao(database: OuxyDatabase): EventLogDao {
        return database.eventLogDao()
    }
}