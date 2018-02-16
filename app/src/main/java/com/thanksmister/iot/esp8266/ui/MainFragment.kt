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

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thanksmister.iot.esp8266.BaseFragment
import com.thanksmister.iot.esp8266.R
import com.thanksmister.iot.esp8266.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.content_main.*
import timber.log.Timber
import javax.inject.Inject

class MainFragment : BaseFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: MainViewModel
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        observeViewModel(viewModel)
    }

    /*Snackbar.make(activity!!.findViewById(android.R.id.content), "Replace with your own action", Snackbar.LENGTH_LONG)
                  .setAction("Action", null).show()*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonConnect.setOnClickListener {
            Timber.d("WiFi Connect")
            listener?.connectWifi()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun observeViewModel(viewModel: MainViewModel) {

    }

    interface OnFragmentInteractionListener {
        fun wifiConnected()
        fun connectWifi()
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}// Required empty public constructor
