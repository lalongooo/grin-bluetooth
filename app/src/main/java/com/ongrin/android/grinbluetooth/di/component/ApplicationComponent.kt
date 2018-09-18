package com.ongrin.android.grinbluetooth.di.component

import android.app.Application
import com.ongrin.android.grinbluetooth.GrinApplication
import com.ongrin.android.grinbluetooth.di.module.ActivityBindingModule
import com.ongrin.android.grinbluetooth.di.module.ApplicationModule
import com.ongrin.android.grinbluetooth.di.scope.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(
        modules = [
            (ActivityBindingModule::class),
            (ApplicationModule::class),
            (AndroidSupportInjectionModule::class)
        ])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: GrinApplication)
}
