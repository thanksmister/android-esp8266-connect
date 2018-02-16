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

import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.support.annotation.NonNull
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

import com.thanksmister.iot.esp8266.R
import com.thanksmister.iot.esp8266.ui.views.DialogTextView
import android.content.DialogInterface
import android.widget.EditText



class DialogUtils(base: Context) : ContextWrapper(base) {
    
    companion object {

        fun dialogMessage(context: Context, title: String, message: String) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(android.R.string.ok, null)
            builder.create().show();
        }

        fun dialogMessage(context: Context, title: String, message: String, listener: DialogInterface.OnClickListener) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(android.R.string.ok, listener)
            builder.create().show();
        }

        fun dialogMessage(context: Context, message: String?) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(message)
            builder.setPositiveButton(android.R.string.ok, null)
            builder.create().show();
        }

        fun dialogMessageHtml(context: Context, title: String, message: String) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(fromHtml(message))
            builder.setPositiveButton(android.R.string.ok, null)
            builder.create().show();
        }

        fun dialogEditText(context: Context, title:String, value: String, listener: DialogTextView.ViewListener) : AlertDialog {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.dialog_text_view, null, false)
            val dialogView = view.findViewById<DialogTextView>(R.id.dialog_text_view)
            dialogView.setListener(listener)
            val valueText = view.findViewById<TextView>(R.id.value_text)
            valueText.text = value;
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle(title)
            dialog.setView(view)
            dialog.setPositiveButton(android.R.string.ok) { _, _ ->
                listener.onTextChange(dialogView.getValue())
            }
            dialog.setNegativeButton(android.R.string.cancel) { _, _ ->
                listener.onCancel()
            }
            dialog.setOnDismissListener({ listener.onCancel() })
            return dialog.show()
        }

        fun dialogPasswordText(context: Context, title:String, value: String, listener: DialogTextView.ViewListener) : AlertDialog {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.dialog_password_view, null, false)
            val dialogView = view.findViewById<DialogTextView>(R.id.dialog_text_view)
            dialogView.setListener(listener)
            val valueText = view.findViewById<TextView>(R.id.value_text)
            valueText.text = value;
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle(title)
            dialog.setView(view)
            dialog.setPositiveButton(android.R.string.ok) { _, _ ->
                listener.onTextChange(dialogView.getValue())
            }
            dialog.setNegativeButton(android.R.string.cancel) { _, _ ->
                listener.onCancel()
            }
            dialog.setOnDismissListener({ listener.onCancel() })
            return dialog.show()
        }

        /**
         * Generate a dismissible <code>SnackBar</code> component.
         */
        fun createSnackBar(context: Context, message: String, retry: Boolean, @NonNull view: View, listener: View.OnClickListener): Snackbar {
            return when {
                retry -> {
                    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                            .setAction(context.getString(R.string.button_retry), listener)
                    val textView = snackBar.view.findViewById<TextView>(android.support.design.R.id.snackbar_text) as TextView
                    textView.setTextColor(context.resources.getColor(R.color.white))
                    snackBar
                }
                else -> {
                    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                    val textView = snackBar.view.findViewById<TextView>(android.support.design.R.id.snackbar_text) as TextView
                    textView.setTextColor(context.resources.getColor(R.color.white))
                    snackBar
                }
            }
        }
    }
}