package com.hotellook.ui.screen.filters.pois;

import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Action1;

final /* synthetic */ class DefaultDataCreator$$Lambda$12 implements Action1 {
    private final DefaultDataCreator arg$1;
    private final SeasonFilterTitles arg$2;
    private final CityInfo arg$3;

    private DefaultDataCreator$$Lambda$12(DefaultDataCreator defaultDataCreator, SeasonFilterTitles seasonFilterTitles, CityInfo cityInfo) {
        this.arg$1 = defaultDataCreator;
        this.arg$2 = seasonFilterTitles;
        this.arg$3 = cityInfo;
    }

    public static Action1 lambdaFactory$(DefaultDataCreator defaultDataCreator, SeasonFilterTitles seasonFilterTitles, CityInfo cityInfo) {
        return new DefaultDataCreator$$Lambda$12(defaultDataCreator, seasonFilterTitles, cityInfo);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$prepareSeasonPois$9(this.arg$2, this.arg$3, (List) obj);
    }
}
