package com.ongrin.android.grinbluetooth.discover

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.ongrin.android.grinbluetooth.R
import com.ongrin.presentation.discover.HomeScreenContract
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HomeScreenContract.View {

    @Inject
    lateinit var mPresenter: HomeScreenContract.Presenter

    private val REQUEST_CODE_ENABLE_BT = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        startBluetooth()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "BT Enabled!", Toast.LENGTH_SHORT).show()
            } else {
                disableBluetoothFeature()
            }
        }
    }

    override fun setPresenter(presenter: HomeScreenContract.Presenter) {
        this.mPresenter = presenter
    }

    private fun startBluetooth() {
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            disableBluetoothFeature()
            return
        }

        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_CODE_ENABLE_BT)
        }
    }

    private fun disableBluetoothFeature() {
        Snackbar.make(findViewById(R.id.coordinatorLayout), R.string.no_bluetooth, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.enable_bluetooth) { startBluetooth() }
                .show()
    }
}
