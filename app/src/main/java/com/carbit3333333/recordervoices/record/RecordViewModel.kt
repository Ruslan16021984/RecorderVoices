package com.carbit3333333.recordervoices.record

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class RecordViewModel(private val app: Application): AndroidViewModel(app) {
    private val TRIGGER_TIME = "TRIGGER_AT"
    private val second: Long = 1_000L
    private var prefs = app.getSharedPreferences("com.gmail.carbit3333333.voicerecord", Context.MODE_PRIVATE)
    private val _elapsedTime = MutableLiveData<String>()

    val elapsedTime: LiveData<String>
    get() = _elapsedTime

    private lateinit var timer: CountDownTimer
    init {
        createTimer()
    }
    fun timeFormatter(time: Long): String{
        return String.format(
            "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(time)%60,
            TimeUnit.MILLISECONDS.toMinutes(time)%60,
            TimeUnit.MILLISECONDS.toSeconds(time)%60
            )
    }
    fun stopTimer(){
        if (this::timer.isInitialized){

            timer.cancel()
        }
        resetTimer()
    }
    fun startTimer(){
        val triggerTime = SystemClock.elapsedRealtime()
        viewModelScope.launch {
         saveTime(triggerTime)
          createTimer()
        }
    }
    fun resetTimer(){
        _elapsedTime.value = timeFormatter(0)
        viewModelScope.launch {
            saveTime(0)
        }
    }
    fun createTimer(){
        viewModelScope.launch {
            val triggerTime = loadTime()
            timer = object : CountDownTimer(triggerTime, second){
                override fun onFinish() {
                    resetTimer()
                }

                override fun onTick(millisUntilFinished: Long) {
                    _elapsedTime.value = timeFormatter(SystemClock.elapsedRealtime() - triggerTime)
                }
            }
            timer.start()
        }
    }
    private suspend fun saveTime(triggerTime: Long){
        withContext(Dispatchers.IO){
            prefs.edit().putLong(TRIGGER_TIME, triggerTime).apply()
        }
    }
    private suspend fun loadTime():Long =
        withContext(Dispatchers.IO){
            prefs.getLong(TRIGGER_TIME, 0)
        }
}