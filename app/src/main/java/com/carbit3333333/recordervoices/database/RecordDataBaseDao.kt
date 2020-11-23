package com.carbit3333333.recordervoices.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecordDataBaseDao {
    @Insert
    fun insert(record: RecordingItem)
    @Update
    fun update(record: RecordingItem)
    @Query("SELECT * from recording_table WHERE id =:key")
    fun getRecord(key: Long?): RecordingItem?
    @Query("DELETE from recording_table")
    fun clearAll()
    @Query("DELETE from recording_table WHERE id =:key")
    fun removeREcord(key: Long?)
    @Query("SELECT * from recording_table ORDER BY id DESC")
    fun getAllRecord(): LiveData<MutableList<RecordingItem>>
    @Query("SELECT COUNT(*) FROM recording_table")
    fun getCount():LiveData<Int>
}