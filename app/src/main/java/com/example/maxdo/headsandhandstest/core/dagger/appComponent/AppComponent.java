package com.example.maxdo.headsandhandstest.core.dagger.appComponent;

import com.example.maxdo.headsandhandstest.App;
import dagger.Component;

@Component(modules = {
        ContextModule.class,
        InterceptorModule.class,
        OkHttpClientModule.class,
        RetrofitModule.class,
        PreferencesModule.class})
public interface AppComponent {
    void inject(App app);
}
