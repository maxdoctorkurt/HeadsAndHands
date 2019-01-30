package com.example.maxdo.headsandhandstest.core.dagger.appComponent;


import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Context context;

//    @Inject
    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
//    @Singleton
    Context getContext() {
        return context;
    }

}
