package com.appsdeviser.tracker_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appsdeviser.tracker_data.local.entity.TrackedFoodEntity

@Database(
    entities = [TrackedFoodEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TrackerDatabase : RoomDatabase() {

    abstract val dao: TrackerDao

    companion object {
        const val DATABASE_NAME ="tracker_db"
    }
}