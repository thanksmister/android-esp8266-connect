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

import android.util.Log

import com.crashlytics.android.Crashlytics

import timber.log.Timber

/**
 * A logging implementation which reports 'info', 'warning', and 'error' logs to Crashlytics.
 */
class CrashlyticsTree : Timber.Tree() {

    override fun d(message: String?, vararg args: Any) {
        if (BuildConfig.DEBUG) {
            Log.println(Log.DEBUG, "AlarmPanel", message)
        }
    }

    override fun i(message: String?, vararg args: Any) {
        if (BuildConfig.DEBUG) {
            logMessage(Log.INFO, message, *args)
        }
    }

    override fun i(t: Throwable, message: String?, vararg args: Any) {
        if (BuildConfig.DEBUG) {
            logMessage(Log.INFO, message, *args)
        }
        // NOTE: We are explicitly not sending the exception to Crashlytics here.
    }

    override fun w(message: String?, vararg args: Any) {
        if (BuildConfig.DEBUG) {
            logMessage(Log.WARN, message, *args)
        }
    }

    override fun w(t: Throwable, message: String?, vararg args: Any) {
        if (BuildConfig.DEBUG) {
            logMessage(Log.WARN, message, *args)
        }
        // NOTE: We are explicitly not sending the exception to Crashlytics here.
    }

    override fun e(message: String?, vararg args: Any) {
        if (BuildConfig.DEBUG) {
            logMessage(Log.ERROR, message, *args)
        }
    }

    override fun e(t: Throwable, message: String?, vararg args: Any) {
        logMessage(Log.ERROR, message, *args)
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable) {
        if (priority == Log.ERROR) {
            try {
                if (tag != null && tag.length > 0) {
                    Crashlytics.log(priority, tag, String.format(message, tag))
                } else {
                    Crashlytics.log(priority, tag, message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun logMessage(priority: Int, message: String?, vararg args: Any) {
        try {
            if (args.size > 0 && priority == Log.ERROR) {
                Crashlytics.logException(Throwable(String.format(message!!, *args)))
            } else if (priority == Log.ERROR) {
                Crashlytics.logException(Throwable(message))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
