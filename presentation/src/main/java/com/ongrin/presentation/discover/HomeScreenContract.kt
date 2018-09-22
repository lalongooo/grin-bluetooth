package com.ongrin.presentation.discover

import com.ongrin.presentation.BasePresenter
import com.ongrin.presentation.BaseView

interface HomeScreenContract {
    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter {
    }
}