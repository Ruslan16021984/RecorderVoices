package com.carbit3333333.recordervoices.removeDialog

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.carbit3333333.recordervoices.database.RecordDataBaseDao
import java.lang.IllegalArgumentException

class RemoveViewModelFactory(private val dataBaseDao: RecordDataBaseDao,
                             private val application: Application):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RemoveDialogViewModel::class.java)){
            return RemoveDialogViewModel(dataBaseDao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}