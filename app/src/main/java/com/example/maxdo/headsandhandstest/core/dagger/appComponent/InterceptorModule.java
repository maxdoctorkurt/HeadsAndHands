package com.example.maxdo.headsandhandstest.core.dagger.appComponent;

import androidx.annotation.NonNull;
import com.example.maxdo.headsandhandstest.BuildConfig;

import dagger.Module;
import dagger.Provides;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

@Module
public class InterceptorModule {
    @Provides
    Interceptor getInterceptor() {
        return new Interceptor() {

            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                okhttp3.Request request = chain.request();
//                Headers headers = request.headers().newBuilder().add("X-Api-Key", BuildConfig.WeatherApiKey).build();
//                request = request.newBuilder().headers(headers).build();
                return chain.proceed(request);
            }
        };
    }
}
