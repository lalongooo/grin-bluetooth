package com.ongrin.android.grinbluetooth.di.module

import com.ongrin.android.grinbluetooth.MainActivity
import com.ongrin.android.grinbluetooth.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}