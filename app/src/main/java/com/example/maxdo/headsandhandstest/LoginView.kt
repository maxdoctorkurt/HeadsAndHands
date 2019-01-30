package com.example.maxdo.headsandhandstest

import com.example.maxdo.headsandhandstest.core.mvi.BaseView
import io.reactivex.Observable

interface LoginView : BaseView<LoginViewState> {
    fun getEmailChangeIntent(): Observable<String>
    fun getPasswordChangeIntent(): Observable<String>
    fun getLoginButtonPressIntent(): Observable<Boolean>
}
