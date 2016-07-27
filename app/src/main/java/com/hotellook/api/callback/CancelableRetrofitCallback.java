package com.hotellook.api.callback;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class CancelableRetrofitCallback<T> implements Callback<T> {
    protected boolean canceled;
    protected boolean finished;

    public CancelableRetrofitCallback() {
        this.canceled = false;
        this.finished = false;
    }

    public void cancel() {
        this.canceled = true;
        onCancel();
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void onCancel() {
    }

    public void success(T t, Response response) {
        this.finished = true;
    }

    public void failure(RetrofitError error) {
        this.finished = true;
    }
}
