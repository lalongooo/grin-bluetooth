package com.ongrin.presentation

/**
 *  Base Class to be used by any class that implements
 *  the View in the Model-View-Presenter pattern
 */
interface BaseView<in T : BasePresenter> {
    fun setPresenter(presenter: T)
}