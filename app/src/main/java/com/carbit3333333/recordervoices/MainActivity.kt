package com.carbit3333333.recordervoices

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavigationUI.setupWithNavController(
            bottom_navigation,
            Navigation.findNavController(this, R.id.nav_host_fragment_container)
        )
    }

    @SuppressLint("ServiceCast")
    fun isServiceRunning(): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if ("com.carbit3333333.recordervoices.record.RecordService" == service.service.className) {
                return true
            }
        }
        return false
    }
}
