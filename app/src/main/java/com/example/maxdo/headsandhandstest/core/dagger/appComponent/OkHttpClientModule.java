package com.example.maxdo.headsandhandstest.core.dagger.appComponent;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;

@Module
public class OkHttpClientModule {
    @Provides
    OkHttpClient getHttpClientModule(Interceptor interceptor) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String message) {
//                Timber.tag("OkHttp ***").d(message);
                System.out.println("OkHttp ***" + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);
        return client.build();
    }
}
