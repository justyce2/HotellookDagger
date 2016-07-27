package com.hotellook.ui.screen.searchresults.map;

import com.hotellook.search.Discounts;
import com.hotellook.search.Highlights;
import com.hotellook.utils.map.clustering.Cluster;
import com.hotellook.utils.map.clustering.ClusterManager.OnClusterClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ResultsMapView$$Lambda$6 implements OnClusterClickListener {
    private final ResultsMapView arg$1;
    private final Discounts arg$2;
    private final Highlights arg$3;

    private ResultsMapView$$Lambda$6(ResultsMapView resultsMapView, Discounts discounts, Highlights highlights) {
        this.arg$1 = resultsMapView;
        this.arg$2 = discounts;
        this.arg$3 = highlights;
    }

    public static OnClusterClickListener lambdaFactory$(ResultsMapView resultsMapView, Discounts discounts, Highlights highlights) {
        return new ResultsMapView$$Lambda$6(resultsMapView, discounts, highlights);
    }

    @Hidden
    public boolean onClusterClick(Cluster cluster) {
        return this.arg$1.lambda$null$1(this.arg$2, this.arg$3, cluster);
    }
}
