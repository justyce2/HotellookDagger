package com.hotellook.utils.map.clustering;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.hotellook.utils.map.clustering.algo.Algorithm;
import com.hotellook.utils.map.clustering.view.ClusterRenderer;
import com.hotellook.utils.map.clustering.view.ClusterRenderer.OnRenderingFinishedListener;
import com.hotellook.utils.threads.TaskExecutor;
import java.util.Collection;
import java.util.Set;
import timber.log.Timber;

public class ClusterManager<T extends ClusterItem> implements OnCameraChangeListener {
    private Algorithm<T> algorithm;
    private final TaskExecutor clusterTaskExecutor;
    private Set<? extends Cluster<T>> clustersWaitingForRendering;
    private GoogleMap map;
    private OnClusterClickListener<T> onClusterClickListener;
    private OnClusterInfoWindowClickListener<T> onClusterInfoWindowClickListener;
    private OnClusterItemClickListener<T> onClusterItemClickListener;
    private OnClusterItemInfoWindowClickListener<T> onClusterItemInfoWindowClickListener;
    private ClusterRenderer<T> renderer;
    private final Handler uIThreadHandler;

    public interface OnClusterClickListener<T extends ClusterItem> {
        boolean onClusterClick(Cluster<T> cluster);
    }

    /* renamed from: com.hotellook.utils.map.clustering.ClusterManager.1 */
    class C14591 implements OnRenderingFinishedListener {
        C14591() {
        }

        public void onRenderingFinished() {
            if (ClusterManager.this.clustersWaitingForRendering != null) {
                ClusterManager.this.renderClusters(ClusterManager.this.clustersWaitingForRendering);
                ClusterManager.this.clustersWaitingForRendering = null;
            }
        }
    }

    /* renamed from: com.hotellook.utils.map.clustering.ClusterManager.2 */
    class C14602 implements Runnable {
        final /* synthetic */ Set val$clusters;

        C14602(Set set) {
            this.val$clusters = set;
        }

        public void run() {
            try {
                ClusterManager.this.renderer.onClustersChanged(this.val$clusters);
            } catch (Exception e) {
                Timber.m751d("Catch exception: %s", e.getClass().getSimpleName());
            }
        }
    }

    private class ClusterTask implements Runnable {
        private final double zoom;

        private ClusterTask(double zoom) {
            this.zoom = zoom;
        }

        public void run() {
            ClusterManager.this.onNewClustersReady(ClusterManager.this.algorithm.getClusters(this.zoom));
        }
    }

    public interface OnClusterInfoWindowClickListener<T extends ClusterItem> {
        void onClusterInfoWindowClick(Cluster<T> cluster);
    }

    public interface OnClusterItemClickListener<T extends ClusterItem> {
        boolean onClusterItemClick(T t);
    }

    public interface OnClusterItemInfoWindowClickListener<T extends ClusterItem> {
        void onClusterItemInfoWindowClick(T t);
    }

    public ClusterManager(GoogleMap map) {
        this.clusterTaskExecutor = TaskExecutor.getTaskExecutor("CLUSTER_THREAD");
        this.uIThreadHandler = new Handler(Looper.getMainLooper());
        this.map = map;
    }

    public void setRenderer(ClusterRenderer<T> renderer) {
        this.renderer = renderer;
        this.renderer.setOnClusterClickListener(this.onClusterClickListener);
        this.renderer.setOnClusterInfoWindowClickListener(this.onClusterInfoWindowClickListener);
        this.renderer.setOnClusterItemClickListener(this.onClusterItemClickListener);
        this.renderer.setOnClusterItemInfoWindowClickListener(this.onClusterItemInfoWindowClickListener);
        this.renderer.setOnRenderingFinishedListener(new C14591());
    }

    public void setAlgorithm(Algorithm<T> algorithm) {
        this.algorithm = algorithm;
    }

    public void clearItems() {
        this.algorithm.clearItems();
    }

    public void addItems(Collection<T> items) {
        this.algorithm.addItems(items);
    }

    public void addItem(T myItem) {
        this.algorithm.addItem(myItem);
    }

    public void removeItem(T item) {
        this.algorithm.removeItem(item);
    }

    public void cluster() {
        this.clusterTaskExecutor.post(new ClusterTask((double) this.map.getCameraPosition().zoom, null));
    }

    public void onCameraChange(CameraPosition cameraPosition) {
        if (this.renderer instanceof OnCameraChangeListener) {
            ((OnCameraChangeListener) this.renderer).onCameraChange(cameraPosition);
        }
        cluster();
    }

    public void setOnClusterClickListener(OnClusterClickListener<T> listener) {
        this.onClusterClickListener = listener;
        this.renderer.setOnClusterClickListener(listener);
    }

    public void setOnClusterInfoWindowClickListener(OnClusterInfoWindowClickListener<T> listener) {
        this.onClusterInfoWindowClickListener = listener;
        this.renderer.setOnClusterInfoWindowClickListener(listener);
    }

    public void setOnClusterItemClickListener(OnClusterItemClickListener<T> listener) {
        this.onClusterItemClickListener = listener;
        this.renderer.setOnClusterItemClickListener(listener);
    }

    public void setOnClusterItemInfoWindowClickListener(OnClusterItemInfoWindowClickListener<T> listener) {
        this.onClusterItemInfoWindowClickListener = listener;
        this.renderer.setOnClusterItemInfoWindowClickListener(listener);
    }

    private void onNewClustersReady(Set<? extends Cluster<T>> clusters) {
        if (this.renderer.isReady()) {
            renderClusters(clusters);
        } else {
            this.clustersWaitingForRendering = clusters;
        }
    }

    private void renderClusters(Set<? extends Cluster<T>> clusters) {
        this.uIThreadHandler.post(new C14602(clusters));
    }

    public Marker getMarker(Cluster<T> cluster) {
        return this.renderer.getMarker(cluster);
    }
}
