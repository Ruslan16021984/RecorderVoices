package com.carbit3333333.recordervoices.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [RecordingItem::class], version = 1, exportSchema = false)
abstract class RecordDataBase: RoomDatabase() {
    abstract val recordDataBaseDao: RecordDataBaseDao
    companion object{
        private var INSTANCE: RecordDataBase? = null
        fun getInstance(context: Context): RecordDataBase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext, RecordDataBase::class.java,"record_app_database"
                        ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}