package com.radiusagent.assignment.domain.di

import android.content.Context
import androidx.room.Room
import com.radiusagent.assignment.data.cache.database.LocalDatabase
import com.radiusagent.assignment.data.cache.database.dao.FacilityDao
import com.radiusagent.assignment.data.cache.datastore.AppSettings
import com.radiusagent.assignment.domain.Radiusagent
import com.radiusagent.assignment.domain.Security
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import net.sqlcipher.database.SupportFactory

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideAppSettings(@ApplicationContext context: Context, security: Security): AppSettings {
        return AppSettings(context, security)
    }

    @Singleton
    @Provides
    fun provideMasterKey(appSettings: AppSettings): String {
        return appSettings.getMasterKey().toString(Charsets.ISO_8859_1)
    }

    @Singleton
    @Provides
    fun provideDb(radiusAgent: Radiusagent, masterKey: String): LocalDatabase {
        return Room
            .databaseBuilder(
                radiusAgent,
                LocalDatabase::class.java,
                LocalDatabase.DATABASE_NAME
            )
            .openHelperFactory(SupportFactory(masterKey.toByteArray(Charsets.ISO_8859_1)))
            .build()
    }

    @Singleton
    @Provides
    fun provideFacilityDao(db: LocalDatabase): FacilityDao = db.facilityDao()
}