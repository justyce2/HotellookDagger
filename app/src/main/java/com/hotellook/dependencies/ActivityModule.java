package com.hotellook.dependencies;

import android.content.Context;
import com.hotellook.ui.activity.MainActivity;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final MainActivity activity;

    public ActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    public MainActivity provideMainActivity() {
        return this.activity;
    }

    @Provides
    public Context provideContext() {
        return this.activity;
    }
}
