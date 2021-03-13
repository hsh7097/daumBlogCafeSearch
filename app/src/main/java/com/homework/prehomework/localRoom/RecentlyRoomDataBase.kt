package com.homework.prehomework.localRoom

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.homework.prehomework.PreHomeworkApplication


@Database(
    version = 1,
    entities = [RecentlySearchWord::class],
    exportSchema = false
)
abstract class RecentlyRoomDataBase : RoomDatabase() {

    abstract fun recentlyDao(): RecentlySearchWordDao

    companion object {

        private var instance: RecentlyRoomDataBase? = null

        @Synchronized
        fun getInstance(): RecentlyRoomDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    PreHomeworkApplication.getPreHomeApplicationContext(),
                    RecentlyRoomDataBase::class.java, "recently.db"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }

            return instance!!
        }
    }

}