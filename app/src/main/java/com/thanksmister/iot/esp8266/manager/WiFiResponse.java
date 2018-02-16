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

package com.thanksmister.iot.esp8266.manager;


import android.support.annotation.NonNull;

import javax.annotation.Nullable;

import static com.thanksmister.iot.esp8266.manager.WiFiStatus.CONNECTED;
import static com.thanksmister.iot.esp8266.manager.WiFiStatus.CONNECTING;
import static com.thanksmister.iot.esp8266.manager.WiFiStatus.DISCONNECTED;
import static com.thanksmister.iot.esp8266.manager.WiFiStatus.DISCONNECTING;
import static com.thanksmister.iot.esp8266.manager.WiFiStatus.ERROR;


/**
 * Response holder provided to the UI
 * https://proandroiddev.com/mvvm-architecture-using-livedata-rxjava-and-new-dagger-android-injection-639837b1eb6c
 */
public class WiFiResponse {

    public WiFiStatus status;

    @Nullable
    public final String data;

    @Nullable
    public final Throwable error;

    private WiFiResponse(WiFiStatus status, @Nullable String data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static WiFiResponse connecting() {
        return new WiFiResponse(CONNECTING, null, null);
    }

    public static WiFiResponse connected() {
        return new WiFiResponse(CONNECTED, null, null);
    }

    public static WiFiResponse disconnected() {
        return new WiFiResponse(DISCONNECTED, null, null);
    }

    public static WiFiResponse disconnecting() {
        return new WiFiResponse(DISCONNECTING, null, null);
    }

    public static WiFiResponse error(@NonNull Throwable error) {
        return new WiFiResponse(ERROR, null, error);
    }
}