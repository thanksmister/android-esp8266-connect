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

package com.thanksmister.iot.esp8266.persistence

import net.grandcentrix.tray.AppPreferences
import javax.inject.Inject

/**
 * Store preferences
 */
class Preferences @Inject
constructor(private val preferences: AppPreferences) {

    fun inactivityTime():Long {
        return this.preferences.getLong(PREF_INACTIVITY_TIME, 300000)
    }

    fun inactivityTime(value:Long) {
        this.preferences.put(PREF_INACTIVITY_TIME, value)
    }

    fun address(): String? {
        return this.preferences.getString(PREF_SSID, "192.168.4.1")
    }

    fun address(value:String) {
        this.preferences.put(PREF_SSID, value)
    }

    fun ssID():String? {
        return this.preferences.getString(PREF_SSID, "Esp8266TestNet")
    }

    fun ssId(value:String) {
        this.preferences.put(PREF_SSID, value)
    }

    fun password():String? {
        return this.preferences.getString(PREF_PASSWORD, "Esp8266Test")
    }

    fun password(value:String) {
        this.preferences.put(PREF_PASSWORD, value)
    }

    /**
     * Reset the `SharedPreferences` and database
     */
    fun reset() {
        preferences.clear()
    }

    companion object {
        @JvmField val PREF_SSID = "pref_ssid"
        @JvmField val PREF_PASSWORD = "pref_password"
        @JvmField val PREF_INACTIVITY_TIME = "pref_inactivity_time"
    }
}