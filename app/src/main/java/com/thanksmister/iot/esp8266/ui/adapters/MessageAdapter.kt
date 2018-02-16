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

package com.thanksmister.iot.esp8266.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thanksmister.iot.esp8266.R
import com.thanksmister.iot.esp8266.util.DateUtils
import com.thanksmister.iot.esp8266.vo.Message
import kotlinx.android.synthetic.main.adapter_logs.view.*

class MessageAdapter(private val items: List<Message>?) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_logs, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        if (items == null) return 0
        return if (items.isNotEmpty()) items.size else 0
    }

    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        holder.bindItems(items!![position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: Message) {
            itemView.textMessage.text = item.message
            itemView.textValue.text = item.value
            itemView.textDate.text = DateUtils.parseCreatedAtDate(item.createdAt)
        }
    }
}