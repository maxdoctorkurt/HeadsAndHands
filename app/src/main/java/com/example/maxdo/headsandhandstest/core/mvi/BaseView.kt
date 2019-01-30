package com.example.maxdo.headsandhandstest.core.mvi

import com.hannesdorfmann.mosby3.mvp.MvpView

interface BaseView<ViewStateClass>: MvpView {

    fun render(viewState: ViewStateClass)
}