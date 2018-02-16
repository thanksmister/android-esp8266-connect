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

package com.thanksmister.iot.esp8266.manager

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.*
import android.text.TextUtils

import timber.log.Timber
import com.thanksmister.iot.esp8266.viewmodel.SnackbarMessage
import com.thanksmister.iot.esp8266.viewmodel.ToastMessage

/*
https://gist.github.com/JosiasSena/100de74192ca3024da8494c1ca428294
 */
class WiFiReceiverManager(private val application: Application, lifecycle: Lifecycle) : LifecycleObserver {

    private val wifiManager: WifiManager = application.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private val toastText = ToastMessage()
    private val snackbarText = SnackbarMessage()
    private val wifiResponse = MutableLiveData<WiFiResponse>()

    fun wifiResponse(): MutableLiveData<WiFiResponse> {
        return wifiResponse
    }

    fun getToastMessage(): ToastMessage {
        return toastText
    }

    fun getSnackbarMessage(): SnackbarMessage {
        return snackbarText
    }

    init {
        lifecycle.addObserver(this)
    }

    private fun showSnackbarMessage(message: Int?) {
        snackbarText.value = message
    }

    private fun showToastMessage(message: String?) {
        toastText.value = message
    }

    private val intentFilterForWifiConnectionReceiver: IntentFilter
        get() {
            val randomIntentFilter = IntentFilter(WIFI_STATE_CHANGED_ACTION)
            randomIntentFilter.addAction(NETWORK_STATE_CHANGED_ACTION)
            randomIntentFilter.addAction(SUPPLICANT_STATE_CHANGED_ACTION)
            return randomIntentFilter
        }

    private val wifiConnectionReceiver = object : BroadcastReceiver() {
        override fun onReceive(c: Context, intent: Intent) {
            //Timber.d("onReceive() called with: intent = [$intent]")
            val action = intent.action
            if (!TextUtils.isEmpty(action)) {
                Timber.d("action: " + action)
                when (action) {
                    WIFI_STATE_CHANGED_ACTION,
                    SUPPLICANT_STATE_CHANGED_ACTION -> {
                        val wifiInfo = wifiManager.connectionInfo
                        var wirelessNetworkName = wifiInfo.ssid
                        wirelessNetworkName = wirelessNetworkName.replace("\"", "");
                        Timber.d("WiFi Info current SSID " + wirelessNetworkName)
                        Timber.d("WiFi Info Hidden " + wifiInfo.hiddenSSID )
                        Timber.d("WiFi Connect To networkSSID " + networkSSID )
                        Timber.d("WiFi previous NetworkSSID " + previousNetworkSSID )
                        Timber.d("WiFi Names Equal " + (networkSSID == wirelessNetworkName))
                        if(networkSSID == wirelessNetworkName && !disconnecting) {
                            Timber.d("WiFi connected to " + networkSSID)
                            wifiResponse.value = WiFiResponse.connected()
                        } else if(networkSSID != wirelessNetworkName && disconnecting) {
                            wifiResponse.value = WiFiResponse.disconnected()
                        }
                    }
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    internal fun registerYourReceiver() {
        application.registerReceiver(wifiConnectionReceiver, intentFilterForWifiConnectionReceiver)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    internal fun unRegisterYourReceiver() {
        try {
            application.unregisterReceiver(wifiConnectionReceiver)
        } catch (e : IllegalArgumentException) {
            Timber.e(e.message)
        }
    }

    private fun getNetworkId(networkSSID: String): Int {
        val list = wifiManager.configuredNetworks
        for (i in list) {
            if (i.SSID != null && i.SSID == "\"" + networkSSID + "\"") {
                return i.networkId
            }
        }
        return -1
    }
    /**
     * Connect to the specified wifi network.
     *
     * @param networkSSID     - The wifi network SSID
     * @param networkPassword - the wifi password
     */
    fun connectWifi(ssid: String, password: String) {

        if (TextUtils.isEmpty(ssid) || TextUtils.isEmpty(password)) {
            Timber.d("onReceive: cannot use connection without passing in a proper wifi SSID and password.")
            return
        }

        networkSSID = ssid
        networkPassword = password

        wifiResponse.value = WiFiResponse.connecting()
        if (!wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = true
        }

        val wifiInfo = wifiManager.connectionInfo
        if(wifiInfo.ssid == networkSSID) {
            wifiResponse.value = WiFiResponse.connected()
            return
        }
        previousNetworkSSID = wifiInfo.ssid
        disconnecting = false
        connectToWifi()
    }

    private fun connectToWifi() {
        Timber.d("WiFi connectToWifi")
        val conf = WifiConfiguration()
        conf.SSID = String.format("\"%s\"", networkSSID);
        conf.preSharedKey = String.format("\"%s\"", networkPassword);
        conf.status = WifiConfiguration.Status.ENABLED;
        conf.hiddenSSID = true;

        networkId = wifiManager.addNetwork(conf)
        if(networkId == -1) {
            networkId = getNetworkId(networkSSID)
        }

        wifiManager.disconnect()
        wifiManager.enableNetwork(networkId, true)
        wifiManager.reconnect()
    }

    fun disconnectFromWifi() {
        Timber.d("WiFi disconnectFromWifi")
        disconnecting = true
        wifiResponse.value = WiFiResponse.disconnecting()
        wifiManager.disconnect()
        wifiManager.disableNetwork(networkId)
        wifiManager.removeNetwork(networkId)
        wifiManager.reconnect() // reconnect to previous network
    }

    companion object {
        var disconnecting: Boolean = false
        var networkSSID: String = ""
        var networkPassword: String = ""
        var previousNetworkSSID: String = ""
        var networkId:Int = -1
    }
}