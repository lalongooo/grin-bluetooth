package com.ongrin.android.grinbluetooth.di.module

import com.ongrin.android.grinbluetooth.di.scope.ActivityScope
import com.ongrin.android.grinbluetooth.discover.MainActivity
import com.ongrin.presentation.discover.HomeScreenContract
import com.ongrin.presentation.discover.HomeScreenPresenter
import dagger.Module
import dagger.Provides

@Module
open class MainActivityModule {

    @ActivityScope
    @Provides
    fun provideHomeScreenView(activity: MainActivity): HomeScreenContract.View {
        return activity
    }

    @ActivityScope
    @Provides
    fun provideHomeScreenPresenter(view: HomeScreenContract.View): HomeScreenContract.Presenter {
        return HomeScreenPresenter(view)
    }
}