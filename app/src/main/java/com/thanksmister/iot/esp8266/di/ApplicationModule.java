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

package com.thanksmister.iot.esp8266.di;

import android.app.Application;
import android.content.Context;


import com.thanksmister.iot.esp8266.BaseApplication;
import com.thanksmister.iot.esp8266.persistence.AppDatabase;
import com.thanksmister.iot.esp8266.persistence.MessageDao;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
abstract class ApplicationModule {

    @Binds
    abstract Application application(BaseApplication baseApplication);

    @Provides
    @Singleton
    static Context provideContext(Application application) {
        return application;
    }

    @Singleton
    @Provides
    static AppDatabase provideDatabase(Application app) {
        return AppDatabase.getInstance(app);
    }

    @Singleton
    @Provides
    static MessageDao provideMessageDao(AppDatabase database) {
        return database.messageDao();
    }
}