package com.example.maxdo.headsandhandstest.core.dagger;

import com.example.maxdo.headsandhandstest.core.dagger.appComponent.Api;
import retrofit2.Retrofit;

import javax.inject.Inject;

public class ApiCalls {

    private Api api;

    @Inject
    ApiCalls(Retrofit retrofit) {
        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }
}
