package com.hotellook.ui.screen.searchresults.map.clustering;

import com.google.android.gms.maps.GoogleMap;
import com.hotellook.ui.view.appbar.ToolbarScrollingBehavior;
import com.hotellook.utils.map.clustering.algo.NonHierarchicalDistanceBasedAlgorithm;
import com.hotellook.utils.map.geometry.Bounds;

public class HotelClusteringAlgorithm extends NonHierarchicalDistanceBasedAlgorithm {
    public HotelClusteringAlgorithm() {
        super(ToolbarScrollingBehavior.ANIMATION_DURATION, 1);
    }

    public float findZoomWhichSplitsCluster(GoogleMap map, HotelCluster cluster) {
        float zoom = map.getCameraPosition().zoom;
        for (int i = (int) zoom; ((float) i) <= map.getMaxZoomLevel(); i++) {
            if (i != ((int) zoom)) {
                Bounds bounds = createBoundsFromSpan(PROJECTION.toPoint(cluster.getPosition()), getZoomSpecificSpan((double) i));
                int outOfBoundsCounter = 0;
                for (HotelClusterItem item : cluster.getItems()) {
                    if (!bounds.contains(PROJECTION.toPoint(item.getPosition()))) {
                        outOfBoundsCounter++;
                    }
                }
                if (outOfBoundsCounter >= cluster.getSize() / 2) {
                    return (float) i;
                }
            }
        }
        return 0.0f;
    }
}
