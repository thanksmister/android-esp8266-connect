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

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle

class IntentUtils private constructor() {

    init {
        throw IllegalStateException("No instances")
    }

    companion object {

        fun share(text: String): Intent {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, text)
            sendIntent.type = "text/plain"

            return sendIntent
        }

        fun showUri(context: Context, uri: String) {
            showUri(context, Uri.parse(uri))
        }

        fun showUri(context: Context, uri: Uri) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri

            context.startActivity(intent)
        }

        fun startActivity(context: Context, kls: Class<*>) {
            context.startActivity(newIntent(context, kls))
        }

        fun startActivity(context: Context, kls: Class<*>, bundle: Bundle) {
            context.startActivity(newIntent(context, kls, bundle))
        }

        @JvmOverloads
        fun newIntent(context: Context, kls: Class<*>, bundle: Bundle = Bundle()): Intent {
            val intent = Intent(context, kls)
            intent.putExtras(bundle)

            return intent
        }
    }
}
