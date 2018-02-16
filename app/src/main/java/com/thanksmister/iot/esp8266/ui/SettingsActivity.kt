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

package com.thanksmister.iot.esp8266.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.MenuItem
import com.thanksmister.iot.esp8266.BaseActivity
import com.thanksmister.iot.esp8266.R
import com.thanksmister.iot.esp8266.ui.views.DialogTextView
import com.thanksmister.iot.esp8266.util.DialogUtils
import com.thanksmister.iot.esp8266.util.IntentUtils
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity() {

    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())

        setSupportActionBar(settings_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.title_settings)

        if(TextUtils.isEmpty(preferences.address())) {
            api_text.text = getString(R.string.pref_address_description)
        } else {
            api_text.text = preferences.address()
        }
        api_button.setOnClickListener {
            val address : String? = preferences.address()
            dialog = DialogUtils.dialogEditText(this@SettingsActivity, getString(R.string.pref_address), address!!,
                    object : DialogTextView.ViewListener {
                        override fun onTextChange(value: String?) {
                            if(!TextUtils.isEmpty(value)) {
                                preferences.address(value!!)
                                api_text.text = preferences.address()
                            }
                        }
                        override fun onCancel() {
                            dialog?.dismiss()
                        }
                    })
        }
        password_button.setOnClickListener {
            val password : String? = preferences.password()
            dialog = DialogUtils.dialogPasswordText(this@SettingsActivity, getString(R.string.pref_password), password!!,
                    object : DialogTextView.ViewListener {
                        override fun onTextChange(value: String?) {
                            if(!TextUtils.isEmpty(value)) {
                                preferences.password(value!!)
                            }
                        }
                        override fun onCancel() {
                            dialog?.dismiss()
                        }
                    })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
        dialog = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_settings
    }

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(newIntent(activity))
        }
        private fun newIntent(context: Context): Intent {
            return IntentUtils.newIntent(context, SettingsActivity::class.java)
        }
    }
}