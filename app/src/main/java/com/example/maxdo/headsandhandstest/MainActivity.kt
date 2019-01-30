package com.example.maxdo.headsandhandstest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import com.example.maxdo.headsandhandstest.core.mvi.CustomRxBindings
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.mosby3.mvi.MviActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MviActivity<LoginView, LoginPresenter>(), LoginView {


    override fun getEmailChangeIntent(): Observable<String> {

        val subject = PublishSubject.create<String>()

        loginEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                subject.onNext(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        return subject
    }

    override fun getPasswordChangeIntent(): Observable<String> {

        val subject = PublishSubject.create<String>()


        passwordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                subject.onNext(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        return subject
    }

    override fun getLoginButtonPressIntent(): Observable<Boolean> {
        return CustomRxBindings.viewClicks(loginButton).map { true }
    }


    override fun render(viewState: LoginViewState) {

        if (viewState.error != null) {

            setLoginButtonState(false)

            val errorText: String = when(viewState.error) {
                EError.UNKNOWN_ERROR -> getString(R.string.unknown_error)
                EError.CHECK_INTERNET_CONNECTION -> {
                    setLoginButtonState(true)

                    getString(R.string.check_internet)
                }
                EError.INVALID_EMAIL_FORMAT -> getString(R.string.invalid_email_fromat)
                EError.PASSWORD_AT_LEAST_ONE_LOWERCASE -> getString(R.string.password_lowercase)
                EError.PASSWORD_AT_LEAST_ONE_DIGIT -> getString(R.string.password_digit)
                EError.PASSWORD_AT_LEAST_ONE_CAPITAL -> getString(R.string.password_capital)
                EError.PASSWORD_AT_LEAST_6_SYMBOLS -> getString(R.string.password_too_short)
                else -> {
                    ""
                }
            }

            val snackbar = Snackbar
                .make(loginRootView, errorText, Snackbar.LENGTH_SHORT)

            snackbar.show()


        }
        else {

            val snackbar = Snackbar
                .make(loginRootView, viewState.weatherShortMessage ?: "12345", Snackbar.LENGTH_SHORT)

            snackbar.show()

            setLoginButtonState(true)
        }

    }

    private fun setLoginButtonState(clickable: Boolean) {
        loginButton.isClickable = clickable
        loginButton.isEnabled = clickable
    }

    override fun createPresenter(): LoginPresenter {
        return LoginPresenter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val passwordEndHelpDrawable = ContextCompat.getDrawable(this, R.drawable.help)
        passwordEt.setCompoundDrawablesWithIntrinsicBounds(null, null, passwordEndHelpDrawable, null)


    }
}
