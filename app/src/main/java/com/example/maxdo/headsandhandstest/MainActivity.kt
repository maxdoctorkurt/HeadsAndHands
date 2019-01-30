package com.example.maxdo.headsandhandstest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.example.maxdo.headsandhandstest.core.mvi.CustomRxBindings
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.mosby3.mvi.MviActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar_login.*

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

        if (viewState.passwordError != null) {
            passwordInputLayout.error = when(viewState.passwordError) {
                EPasswordError.PASSWORD_AT_LEAST_ONE_LOWERCASE -> getString(R.string.password_lowercase)
                EPasswordError.PASSWORD_AT_LEAST_ONE_DIGIT -> getString(R.string.password_digit)
                EPasswordError.PASSWORD_AT_LEAST_ONE_CAPITAL -> getString(R.string.password_capital)
                EPasswordError.PASSWORD_AT_LEAST_6_SYMBOLS -> getString(R.string.password_too_short)
                else -> {
                    ""
                }
            }
        }
        else {
            passwordInputLayout.error = ""
        }

        loginLayout.error = if (viewState.mailError) getString(R.string.invalid_email_format) else ""

        if(viewState.networkError) Snackbar.make(loginRootView, getString(R.string.check_internet), Snackbar.LENGTH_SHORT).show()
        if(viewState.weatherShortMessage != null) Snackbar.make(loginRootView, viewState.weatherShortMessage!!, Snackbar.LENGTH_SHORT).show()

        setLoginAndCreateButtonState(viewState.loginButtonActive)

    }

    private fun setLoginAndCreateButtonState(clickable: Boolean) {
        loginButton.isClickable = clickable
        loginButton.isEnabled = clickable

        newAccountCreateButton.isClickable = clickable
        newAccountCreateButton.isEnabled = clickable

        loginButton.alpha = if(clickable) 1.0f else 0.5f
        newAccountCreateButton.alpha = if(clickable) 1.0f else 0.5f
    }

    override fun createPresenter(): LoginPresenter {
        return LoginPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val passwordEndHelpDrawable = ContextCompat.getDrawable(this, R.drawable.help)
        passwordEt.setCompoundDrawablesWithIntrinsicBounds(null, null, passwordEndHelpDrawable, null)
        toolbar_arrow_back.setOnClickListener { onBackPressed() }

        passwordHelpClick.setOnClickListener {
            val md = MaterialDialog(this)
            md.title(R.string.help)
            md.message(R.string.password_help_dialog_message)
            md.positiveButton(R.string.ok)
            md.show()
        }

        newAccountCreateButton.setOnClickListener {
            Snackbar.make(loginRootView, getString(R.string.new_user_created_fake), Snackbar.LENGTH_SHORT).show()
        }
    }


}
