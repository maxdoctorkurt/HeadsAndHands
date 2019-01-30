package com.example.maxdo.headsandhandstest.core.dagger.appComponent;

import android.content.Context;
import android.content.SharedPreferences;
import dagger.Module;
import dagger.Provides;

@Module
public class PreferencesModule {
    @Provides
    SharedPreferences preferences(Context context) {
        return context.getSharedPreferences("that_app", Context.MODE_PRIVATE);
    }
}
