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

package com.thanksmister.iot.esp8266.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.Html
import android.text.Spanned
import android.widget.Toast

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun fromHtml(html: String?): Spanned {
    val result: Spanned
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        result = Html.fromHtml(html)
    }
    return result
}

fun broadcastReceiver(init: (Context, Intent?) -> Unit): BroadcastReceiver {
    return object : BroadcastReceiver() {
        public override fun onReceive(context: Context, intent: Intent?) {
            init(context, intent)
        }
    }
}