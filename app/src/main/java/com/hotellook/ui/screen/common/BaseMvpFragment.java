package com.hotellook.ui.screen.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hotellook.HotellookApplication;
import com.hotellook.backstack.SavableFragment;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.ui.activity.MainActivity;

public abstract class BaseMvpFragment<V extends MvpView, P extends MvpPresenter<V>> extends MvpFragment<V, P> implements SavableFragment {
    private Object initialSnapshot;
    private Unbinder unbinder;

    protected abstract View inflateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup);

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflateView(inflater, container);
        this.unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void onDestroyView() {
        this.unbinder.unbind();
        super.onDestroyView();
    }

    protected MainActivity mainActivity() {
        return (MainActivity) getActivity();
    }

    protected HotellookComponent appComponent() {
        return application().getComponent();
    }

    private HotellookApplication application() {
        return HotellookApplication.getApp();
    }

    @Nullable
    public Object takeSnapshot() {
        return null;
    }

    public void setInitialSnapshot(@Nullable Object snapshot) {
        this.initialSnapshot = snapshot;
    }

    public boolean hasInitialSnapshot() {
        return this.initialSnapshot != null;
    }

    @Nullable
    public Object initialSnapshot() {
        return this.initialSnapshot;
    }
}
