/*
 * Copyright (c) 2018 ThanksMister LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thanksmister.iot.esp8266

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.view.Menu
import android.view.MenuItem
import com.thanksmister.iot.esp8266.persistence.Preferences
import com.thanksmister.iot.esp8266.ui.ConnectionLiveData
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    private val REQUEST_PERMISSIONS = 88

    @Inject lateinit var preferences: Preferences

    private val inactivityHandler: Handler = Handler()
    private var hasNetwork = AtomicBoolean(true)
    val disposable = CompositeDisposable()

    private var userPresent: Boolean = false

    abstract fun getLayoutId(): Int

    private val inactivityCallback = Runnable {
        userPresent = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart(){
        super.onStart()
    }

    /*private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this@BaseActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this@BaseActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this@BaseActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA), REQUEST_PERMISSIONS)
                return
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                if (grantResults.isNotEmpty()) {
                    var permissionsDenied = false
                    for (permission in grantResults) {
                        if (permission != PackageManager.PERMISSION_GRANTED) {
                            permissionsDenied = true
                            break
                        }
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }*/

    override fun onDestroy() {
        super.onDestroy()
        inactivityHandler.removeCallbacks(inactivityCallback)
        disposable.dispose()
    }

    fun resetInactivityTimer() {
        inactivityHandler.removeCallbacks(inactivityCallback)
        inactivityHandler.postDelayed(inactivityCallback, preferences.inactivityTime())
    }

    override fun onUserInteraction() {
        Timber.d("onUserInteraction")
        userPresent = true
        resetInactivityTimer()
    }

    public override fun onStop() {
        super.onStop()
        inactivityHandler.removeCallbacks(inactivityCallback)
    }

    public override fun onResume() {
        super.onResume()
        //checkPermissions()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.itemId == android.R.id.home
    }
}