package fr.boitakub.bogadex.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.boitakub.bogadex.BogadexDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesNoteDatabase(@ApplicationContext context: Context): BogadexDatabase {
        return Room.databaseBuilder(context, BogadexDatabase::class.java, BogadexDatabase.DB_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun providesBoardGameDao(noteDatabase: BogadexDatabase) = noteDatabase.boardGameDao()

    @Singleton
    @Provides
    fun providesBoardGameListDao(noteDatabase: BogadexDatabase) = noteDatabase.boardGameListDao()
}
