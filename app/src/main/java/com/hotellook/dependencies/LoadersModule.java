package com.hotellook.dependencies;

import com.hotellook.api.dataloaders.MinPricesLoader;
import com.hotellook.api.dataloaders.StreetViewCheckLoader;
import com.hotellook.core.api.HotellookService;
import com.hotellook.currency.CurrencyRepository;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;

@Module
public class LoadersModule {
    @Singleton
    @Provides
    public MinPricesLoader provideMinPricesLoader(HotellookService service, CurrencyRepository currencyRepository) {
        return new MinPricesLoader(service, currencyRepository);
    }

    @Provides
    public StreetViewCheckLoader provideStreetViewLoader(OkHttpClient okHttpClient) {
        return new StreetViewCheckLoader(okHttpClient);
    }
}
