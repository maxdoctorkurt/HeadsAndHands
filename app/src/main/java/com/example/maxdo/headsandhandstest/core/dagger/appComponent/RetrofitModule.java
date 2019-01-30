package com.example.maxdo.headsandhandstest.core.dagger.appComponent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    @Provides
    Retrofit getRetrofit(OkHttpClient httpClient) {
        Retrofit.Builder builder = new Retrofit.Builder();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://samples.openweathermap.org/data/2.5/")
                .client(httpClient)
                .build();
    }
}
