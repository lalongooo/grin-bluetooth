package com.ongrin.presentation

/**
 *  Base Class to be used by any class that implements
 *  the Presenter in the Model-View-Presenter pattern
 */
interface BasePresenter {

    fun start()

    fun stop()
}