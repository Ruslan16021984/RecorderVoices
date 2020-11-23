package com.carbit3333333.recordervoices

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.carbit3333333.recordervoices.database.RecordDataBase
import com.carbit3333333.recordervoices.database.RecordDataBaseDao
import com.carbit3333333.recordervoices.database.RecordingItem
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RecordDataBaseTest {
    private lateinit var recordDataBaseDao: RecordDataBaseDao
    private lateinit var dataBase: RecordDataBase

    @Before
    fun createDb(){
       val context = InstrumentationRegistry.getInstrumentation().targetContext
        dataBase = Room.inMemoryDatabaseBuilder(context, RecordDataBase::class.java).allowMainThreadQueries().build()
        recordDataBaseDao = dataBase.recordDataBaseDao
    }
    @After
    @Throws(IOException::class)
    fun closeDb(){
        dataBase.close()
    }
    @Test
    @Throws(Exception::class)
    fun testDataBase(){
        recordDataBaseDao.insert(RecordingItem())
        val getCount = recordDataBaseDao.getCount()
        assertEquals(getCount, 1)
    }


}