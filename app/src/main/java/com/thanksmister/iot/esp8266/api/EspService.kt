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


package com.thanksmister.iot.esp8266.api


import android.arch.lifecycle.LiveData
import com.thanksmister.iot.esp8266.vo.Message
import io.reactivex.Observable

import retrofit2.Call
import retrofit2.http.*

interface EspService {
    @GET("message/{message}")
    fun sendMessage(@Path("message") message:String): LiveData<ApiResponse<Message>>

    @GET("led/{state}")
    fun sendState(@Path("state") state:String): Observable<MessageResponse>
}