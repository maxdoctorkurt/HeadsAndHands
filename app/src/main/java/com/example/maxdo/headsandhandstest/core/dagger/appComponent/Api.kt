package com.example.maxdo.headsandhandstest.core.dagger.appComponent

import com.example.maxdo.headsandhandstest.core.entities.Weather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("weather/")
    fun weatherSample(@Query("q") query: String = "London,uk"): Single<Weather>


}