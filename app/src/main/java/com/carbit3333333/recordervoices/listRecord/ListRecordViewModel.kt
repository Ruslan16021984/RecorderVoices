package com.carbit3333333.recordervoices.listRecord

import androidx.lifecycle.ViewModel
import com.carbit3333333.recordervoices.database.RecordDataBaseDao

class ListRecordViewModel(dataSource: RecordDataBaseDao): ViewModel() {
    val database = dataSource
    val records = database.getAllRecord()

}