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
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.thanksmister.iot.esp8266.BaseActivity
import com.thanksmister.iot.esp8266.R
import com.thanksmister.iot.esp8266.manager.WiFiReceiverManager
import com.thanksmister.iot.esp8266.manager.WiFiResponse
import com.thanksmister.iot.esp8266.manager.WiFiStatus
import com.thanksmister.iot.esp8266.util.IntentUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_settings.*
import timber.log.Timber


class LogActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())

        setSupportActionBar(settings_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.activity_logs)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.contentFrame, LogFragment.newInstance(), LOGS_FRAGMENT).commit()
        }
        resetInactivityTimer()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_logs
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val LOGS_FRAGMENT = "com.thanksmister.fragment.LOGS_FRAGMENT"
        fun start(activity: Activity) {
            activity.startActivity(newIntent(activity))
        }
        private fun newIntent(context: Context): Intent {
            return IntentUtils.newIntent(context, LogActivity::class.java)
        }
    }
}