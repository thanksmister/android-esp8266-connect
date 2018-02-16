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

package com.thanksmister.iot.esp8266.di

import android.arch.lifecycle.ViewModel
import com.thanksmister.iot.esp8266.BaseActivity
import com.thanksmister.iot.esp8266.BaseFragment
import com.thanksmister.iot.esp8266.ui.*
import com.thanksmister.iot.esp8266.viewmodel.MainViewModel
import com.thanksmister.iot.esp8266.viewmodel.MessageViewModel
import com.thanksmister.iot.esp8266.viewmodel.TransmitViewModel

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class AndroidBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransmitViewModel::class)
    abstract fun bindsTransmitViewModel(viewModel: TransmitViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MessageViewModel::class)
    abstract fun bindsMessageViewModel(viewModel: MessageViewModel): ViewModel

    @ContributesAndroidInjector
    internal abstract fun baseActivity(): BaseActivity

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun logActivity(): LogActivity

    @ContributesAndroidInjector
    internal abstract fun settingsActivity(): SettingsActivity

    @ContributesAndroidInjector
    internal abstract fun mainFragment(): MainFragment

    @ContributesAndroidInjector
    internal abstract fun transmitFragment(): TransmitFragment

    @ContributesAndroidInjector
    internal abstract fun baseFragment(): BaseFragment

    @ContributesAndroidInjector
    internal abstract fun logFragment(): LogFragment
}