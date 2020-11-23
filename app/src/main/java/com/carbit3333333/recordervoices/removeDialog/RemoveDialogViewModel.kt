package com.carbit3333333.recordervoices.removeDialog

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.carbit3333333.recordervoices.R
import com.carbit3333333.recordervoices.database.RecordDataBase
import com.carbit3333333.recordervoices.database.RecordDataBaseDao
import kotlinx.coroutines.*
import java.io.File

class RemoveDialogViewModel(private var dataBaseDao: RecordDataBaseDao,
                            private val application: Application) : ViewModel() {
    private var job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    fun removeItem(itemId: Long){
        dataBaseDao = RecordDataBase.getInstance(application).recordDataBaseDao
        try {
            uiScope.launch {
                withContext(Dispatchers.IO){
                    dataBaseDao.removeREcord(itemId)
                }
            }
        }catch (e: Exception){
            Log.e("removeItem", "exception", e)
        }
    }
    fun removeFile(path: String){
        val file = File(path)
        if (file.exists()){
            file.delete()
            Toast.makeText(application, R.string.file_deleted_text, Toast.LENGTH_LONG).show()
        }
    }
}
