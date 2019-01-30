package com.example.maxdo.headsandhandstest

import com.example.maxdo.headsandhandstest.core.mvi.BasePresenter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class LoginPresenter : BasePresenter<LoginView, LoginViewState, LoginPartialState>() {

    val DEBOUNCE_MILLIS = 300L

    val api = App.instance?.apiCalls?.api

    override fun bindIntents() {

        val initialState = LoginViewState()

        val partialMail: Observable<LoginPartialState> = intent(LoginView::getEmailChangeIntent)
            .debounce(DEBOUNCE_MILLIS, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .map {
                LoginPartialState.Login(if (checkMail(it)) null else EError.INVALID_EMAIL_FORMAT)
            }

        val partialPassword: Observable<LoginPartialState> = intent(LoginView::getPasswordChangeIntent)
            .debounce(DEBOUNCE_MILLIS, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .map {
                LoginPartialState.Password(checkPassword(it))
            }

        val partialLoginPress: Observable<LoginPartialState> = intent(LoginView::getLoginButtonPressIntent)
            .debounce(DEBOUNCE_MILLIS, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .flatMap {
                getSampleWeather().toObservable()
            }

        val all: Observable<LoginViewState> =
            partialMail
                .mergeWith(partialPassword)
                .mergeWith(partialLoginPress)
                .scan(initialState, this::stateReducer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(all, LoginView::render)
    }

    override fun stateReducer(viewState: LoginViewState, partialState: LoginPartialState): LoginViewState {


        if(partialState is LoginPartialState.Login) {
            viewState.error = partialState.error
            return viewState
        }

        if(partialState is LoginPartialState.Password) {
            viewState.error = partialState.error
            return viewState
        }

        if(partialState is LoginPartialState.WeatherShortDescription) {
            viewState.error = partialState.error
            viewState.weatherShortMessage = partialState.weather
            return viewState
        }

        throw IllegalStateException("Unknown Partial")

    }

    private fun checkMail(email: String): Boolean {
        val mailNotEmpty = email.isNotEmpty()
        val emailRegExp = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return emailRegExp.toRegex().matches(email) && mailNotEmpty
    }


    private fun checkPassword(password: String): EError? {
        return when {
            password.length < 6 -> EError.PASSWORD_AT_LEAST_6_SYMBOLS
            !password.contains("[A-ZА-ЯЁ]".toRegex()) -> EError.PASSWORD_AT_LEAST_ONE_CAPITAL
            !password.contains("[0-9]".toRegex()) -> EError.PASSWORD_AT_LEAST_ONE_DIGIT
            !password.contains("[a-zа-яё]".toRegex()) -> EError.PASSWORD_AT_LEAST_ONE_LOWERCASE
            else -> {
                // OK!
                null
            }
        }
    }

    private fun getSampleWeather(): Single<LoginPartialState> {
        val weather = api?.weatherSample()

        lateinit var result: Single<LoginPartialState>

        if (weather != null) {
            result = weather.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).cache().flatMap {
                return@flatMap Single.just(LoginPartialState.WeatherShortDescription("${it.name} - ${it.weather[0]}") as LoginPartialState)
            }
                .onErrorResumeNext {
                    Single.just(
                        LoginPartialState.WeatherShortDescription(
                            null,
                            EError.CHECK_INTERNET_CONNECTION
                        )
                    )
                }
            return result
        }
        return Single.just(LoginPartialState.WeatherShortDescription(null, EError.UNKNOWN_ERROR))
    }

}

sealed class LoginPartialState {
    class Login(val error: EError?) : LoginPartialState()
    class Password(val error: EError?) : LoginPartialState()
    class WeatherShortDescription(val weather: String?, val error: EError? = null) : LoginPartialState()
}
