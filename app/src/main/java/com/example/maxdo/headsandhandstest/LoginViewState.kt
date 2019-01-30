package com.example.maxdo.headsandhandstest

class LoginViewState {
    var error: EError? = null
    var weatherShortMessage: String? = null
}

enum class EError {
    INVALID_EMAIL_FORMAT,
    PASSWORD_AT_LEAST_6_SYMBOLS,
    PASSWORD_AT_LEAST_ONE_DIGIT,
    PASSWORD_AT_LEAST_ONE_CAPITAL,
    PASSWORD_AT_LEAST_ONE_LOWERCASE,
    CHECK_INTERNET_CONNECTION,
    UNKNOWN_ERROR
}
