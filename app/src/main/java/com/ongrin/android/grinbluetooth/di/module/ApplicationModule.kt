package com.ongrin.android.grinbluetooth.di.module

import android.app.Application
import android.content.Context
import com.ongrin.android.grinbluetooth.UiThread
import com.ongrin.android.grinbluetooth.di.scope.ApplicationScope
import com.ongrin.data.executor.JobExecutor
import com.ongrin.domain.executor.PostExecutionThread
import com.ongrin.domain.executor.ThreadExecutor
import dagger.Module
import dagger.Provides

/**
 * Module used to provide dependencies at an application-level.
 */
@Module
open class ApplicationModule {

    @Provides
    @ApplicationScope
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @ApplicationScope
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Provides
    @ApplicationScope
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }
}