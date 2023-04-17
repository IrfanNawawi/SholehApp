package id.heycoding.sholehapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.heycoding.sholehapp.data.local.dao.SholehAppDatabase
import id.heycoding.sholehapp.data.local.dao.SholehDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DatabaseName = "SholehDB"

    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): SholehAppDatabase {
        return Room.databaseBuilder(
            context,
            SholehAppDatabase::class.java,
            DatabaseName
        ).build()
    }

    @Provides
    fun providerSholehDao(sholehAppDatabase: SholehAppDatabase): SholehDao {
        return sholehAppDatabase.sholehDao()
    }
}