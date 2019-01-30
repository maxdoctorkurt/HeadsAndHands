package com.example.maxdo.headsandhandstest.core.mvi

import android.view.View
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object CustomRxBindings {

    fun viewClicks(view: View): Observable<View> {
        val subject = PublishSubject.create<View>()
        view.setOnClickListener {
            subject.onNext(it)
        }
        return subject
    }
}