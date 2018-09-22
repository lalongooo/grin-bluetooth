package com.ongrin.presentation.discover

import javax.inject.Inject

class HomeScreenPresenter @Inject constructor(val homeScreenView: HomeScreenContract.View) : HomeScreenContract.Presenter {
    override fun start() {
    }

    override fun stop() {
    }
}