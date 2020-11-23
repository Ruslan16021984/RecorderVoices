package com.carbit3333333.recordervoices.listRecord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.carbit3333333.recordervoices.database.RecordDataBaseDao
import java.lang.IllegalArgumentException

class ListRecordsViewModelFactory(
    private val databaseDao: RecordDataBaseDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListRecordViewModel::class.java)){
            return ListRecordViewModel(databaseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}