package com.hotellook.api.dataloaders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.hotellook.api.callback.CallbackWithTimeout;
import com.hotellook.core.api.HotellookService;
import java.util.Collection;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class InfoByIdLoader<T> extends BaseLoader<T> {
    public static final int INVALID_INFO_ID = -1;
    private static final long TIMEOUT = 120000;
    private final HotellookService api;
    private com.hotellook.api.dataloaders.InfoByIdLoader$com.hotellook.api.dataloaders.InfoByIdLoader.LoadCallback currentCallback;
    private T data;
    private long infoId;
    @Nullable
    private LoadListener loadListener;

    private class LoadCallback extends CallbackWithTimeout<T> {
        protected LoadCallback() {
            super(InfoByIdLoader.TIMEOUT);
        }

        public void success(T data, Response response) {
            super.success(data, response);
            if (!isCanceled()) {
                InfoByIdLoader.this.data = data;
                InfoByIdLoader.this.state = State.LOADED;
                if (InfoByIdLoader.this.loadListener != null) {
                    InfoByIdLoader.this.loadListener.onSuccess();
                }
            }
        }

        public void failure(RetrofitError error) {
            super.failure(error);
            if (equals(InfoByIdLoader.this.currentCallback)) {
                InfoByIdLoader.this.currentCallback = null;
                InfoByIdLoader.this.infoId = -1;
            }
            if (!isCanceled()) {
                InfoByIdLoader.this.state = State.FAILED;
                if (InfoByIdLoader.this.loadListener != null) {
                    InfoByIdLoader.this.loadListener.onFail(error);
                }
            }
        }
    }

    public interface LoadListener {
        void onFail(RetrofitError retrofitError);

        void onSuccess();
    }

    protected abstract void loadData(HotellookService hotellookService, CallbackWithTimeout<T> callbackWithTimeout, long j);

    public InfoByIdLoader(@NonNull HotellookService api) {
        this.infoId = -1;
        this.api = api;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void loadData(long infoId) {
        if (infoId != this.infoId || this.data == null || (this.currentCallback != null && this.currentCallback.isCanceled())) {
            this.state = State.LOADING;
            this.infoId = infoId;
            this.data = null;
            if (this.currentCallback != null) {
                this.currentCallback.cancel();
            }
            this.currentCallback = new LoadCallback();
            loadData(this.api, this.currentCallback, infoId);
        }
    }

    public boolean hasData() {
        return this.data != null;
    }

    public void cancel() {
        this.state = State.IDLE;
        this.data = null;
        if (this.currentCallback != null) {
            this.currentCallback.cancel();
        }
    }

    public void setLoadListener(@Nullable LoadListener loadListener) {
        this.loadListener = loadListener;
    }

    public boolean isLoading() {
        return (this.currentCallback == null || this.currentCallback.isFinished() || this.currentCallback.isCanceled()) ? false : true;
    }

    public boolean loadedEmpty(long id) {
        return id == this.infoId && this.state.equals(State.LOADED) && (this.data == null || ((this.data instanceof Collection) && ((Collection) this.data).isEmpty()));
    }

    public boolean loadingOrLoaded() {
        return this.state.equals(State.LOADING) || this.state.equals(State.LOADED);
    }

    public boolean loadingFinished() {
        return this.state.equals(State.LOADED);
    }

    public boolean hasFailed() {
        return this.state.equals(State.FAILED);
    }

    protected void setLoaded() {
        this.state = State.LOADED;
    }
}
