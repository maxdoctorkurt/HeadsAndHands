package com.example.maxdo.headsandhandstest

class LoginViewState {
    var passwordError: EPasswordError? = null
    var mailError: Boolean = false
    var networkError: Boolean = false
    var weatherShortMessage: String? = null
    var loginButtonActive: Boolean = false
}

enum class EPasswordError {
    PASSWORD_AT_LEAST_6_SYMBOLS,
    PASSWORD_AT_LEAST_ONE_DIGIT,
    PASSWORD_AT_LEAST_ONE_CAPITAL,
    PASSWORD_AT_LEAST_ONE_LOWERCASE,
}


