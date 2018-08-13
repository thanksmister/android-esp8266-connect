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

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.thanksmister.iot.esp8266.BaseFragment
import com.thanksmister.iot.esp8266.R
import com.thanksmister.iot.esp8266.api.NetworkResponse
import com.thanksmister.iot.esp8266.api.Status
import com.thanksmister.iot.esp8266.util.DialogUtils
import com.thanksmister.iot.esp8266.viewmodel.TransmitViewModel
import kotlinx.android.synthetic.main.content_transmit.*
import javax.inject.Inject

class TransmitFragment : BaseFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: TransmitViewModel
    private var listener: OnFragmentInteractionListener? = null
    private var networkStatus: Status = Status.START

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transmit, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TransmitViewModel::class.java)
        observeViewModel(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonOn.setOnClickListener {
            val value = editOn.text.toString()
            if(TextUtils.isEmpty(value)) {
                Toast.makeText(activity!!, getString(R.string.toast_blak_value), Toast.LENGTH_SHORT).show()
            } else if (networkStatus != Status.LOADING){
                viewModel.sendMessage(value)
            }
        }

        buttonOff.setOnClickListener {
            val value = editOff.text.toString()
            if(TextUtils.isEmpty(value)) {
                Toast.makeText(activity!!, getString(R.string.toast_blak_value), Toast.LENGTH_SHORT).show()
            } else if (networkStatus != Status.LOADING) {
                viewModel.sendMessage(value)
            }
        }

        buttonDisconnect.setOnClickListener {
            listener?.disconnectFromWifi()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun disconnectFromWifi()
        fun wifiDisconnected()
    }

    private fun observeViewModel(viewModel: TransmitViewModel) {
        viewModel.networkResponse().observe(this, Observer {response -> processNetworkResponse(response)})
        viewModel.getToastMessage().observe(this, Observer {message -> Toast.makeText(activity!!, message, Toast.LENGTH_LONG).show()})
        viewModel.getAlertMessage().observe(this, Observer {message ->
            DialogUtils.dialogMessage(activity!!, getString(R.string.alert_title_error), message!!, DialogInterface.OnClickListener { _, _ -> SettingsActivity.start(activity!!) })
        })
    }

    private fun processNetworkResponse(response: NetworkResponse?) {
        if(response?.status != null) {
            networkStatus = response.status
            when (networkStatus) {
                Status.LOADING -> {
                    //
                }
                Status.SUCCESS -> {
                    //
                }
                Status.ERROR -> {
                    val message = response.error?.message.toString()
                    Snackbar.make(activity!!.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
                }
                else -> {
                }
            }
        }
    }

    companion object {
        fun newInstance(): TransmitFragment {
            return TransmitFragment()
        }
    }
}// Required empty public constructor
