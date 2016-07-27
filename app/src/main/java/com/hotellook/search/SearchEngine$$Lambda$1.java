package com.hotellook.search;

import com.hotellook.api.RequestFlags;
import java.lang.invoke.LambdaForm.Hidden;
import rx.Observable;
import rx.functions.Func1;

final /* synthetic */ class SearchEngine$$Lambda$1 implements Func1 {
    private final SearchEngine arg$1;
    private final Observable arg$10;
    private final Observable arg$11;
    private final Observable arg$2;
    private final Observable arg$3;
    private final Observable arg$4;
    private final Observable arg$5;
    private final Observable arg$6;
    private final SearchParams arg$7;
    private final RequestFlags arg$8;
    private final Observable arg$9;

    private SearchEngine$$Lambda$1(SearchEngine searchEngine, Observable observable, Observable observable2, Observable observable3, Observable observable4, Observable observable5, SearchParams searchParams, RequestFlags requestFlags, Observable observable6, Observable observable7, Observable observable8) {
        this.arg$1 = searchEngine;
        this.arg$2 = observable;
        this.arg$3 = observable2;
        this.arg$4 = observable3;
        this.arg$5 = observable4;
        this.arg$6 = observable5;
        this.arg$7 = searchParams;
        this.arg$8 = requestFlags;
        this.arg$9 = observable6;
        this.arg$10 = observable7;
        this.arg$11 = observable8;
    }

    public static Func1 lambdaFactory$(SearchEngine searchEngine, Observable observable, Observable observable2, Observable observable3, Observable observable4, Observable observable5, SearchParams searchParams, RequestFlags requestFlags, Observable observable6, Observable observable7, Observable observable8) {
        return new SearchEngine$$Lambda$1(searchEngine, observable, observable2, observable3, observable4, observable5, searchParams, requestFlags, observable6, observable7, observable8);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$makeSearch$0(this.arg$2, this.arg$3, this.arg$4, this.arg$5, this.arg$6, this.arg$7, this.arg$8, this.arg$9, this.arg$10, this.arg$11, (Locations) obj);
    }
}
