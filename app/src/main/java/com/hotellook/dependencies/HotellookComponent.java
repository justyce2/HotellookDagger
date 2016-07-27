package com.hotellook.dependencies;

import com.hotellook.HotellookApplication;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component
public interface HotellookComponent extends BaseHotellookComponent {

    public static final class Initializer {
        public static HotellookComponent init(HotellookApplication app) {
            return DaggerHotellookComponent.builder().appModule(new AppModule(app)).build();
        }
    }
}
