package com.ongrin.android.grinbluetooth.discover

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ongrin.android.grinbluetooth.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }
}
