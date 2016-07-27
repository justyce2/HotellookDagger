package com.hotellook.search;

import com.hotellook.api.RequestFlags;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func8;

final /* synthetic */ class SearchEngine$$Lambda$4 implements Func8 {
    private final SearchParams arg$1;
    private final RequestFlags arg$2;

    private SearchEngine$$Lambda$4(SearchParams searchParams, RequestFlags requestFlags) {
        this.arg$1 = searchParams;
        this.arg$2 = requestFlags;
    }

    public static Func8 lambdaFactory$(SearchParams searchParams, RequestFlags requestFlags) {
        return new SearchEngine$$Lambda$4(searchParams, requestFlags);
    }

    @Hidden
    public Object call(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8) {
        return new SearchData(this.arg$1, this.arg$2, (Locations) obj, ((LocationDumps) obj2).hotels(), (Offers) obj3, (RoomTypes) obj4, ((LocationDumps) obj2).pois(), ((LocationDumps) obj2).seasons(), ((LocationDumps) obj2).districts(), ((Boolean) obj6).booleanValue(), (Discounts) obj7, (Highlights) obj8);
    }
}
