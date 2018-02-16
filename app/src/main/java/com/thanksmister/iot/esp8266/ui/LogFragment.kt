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
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thanksmister.iot.esp8266.BaseFragment
import com.thanksmister.iot.esp8266.R
import com.thanksmister.iot.esp8266.ui.adapters.MessageAdapter
import com.thanksmister.iot.esp8266.viewmodel.MessageViewModel
import com.thanksmister.iot.esp8266.vo.Message
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_logs.*
import timber.log.Timber
import javax.inject.Inject

class LogFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    public lateinit var viewModel: MessageViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MessageViewModel::class.java)
        observeViewModel(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)
            view.adapter =  MessageAdapter(ArrayList<Message>())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_logs, container, false)
    }

    override fun onDetach() {
        super.onDetach()
        disposable.dispose()
    }

    private fun observeViewModel(viewModel: MessageViewModel) {
        disposable.add(viewModel.getMessages()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe({messages ->
                   list.adapter = MessageAdapter(messages)
                   list.invalidate()
               }, { error -> Timber.e("Unable to get messages: " + error)}))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         */
        fun newInstance(): LogFragment {
            return LogFragment()
        }
    }
}