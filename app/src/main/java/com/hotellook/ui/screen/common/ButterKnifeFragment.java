package com.hotellook.ui.screen.common;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class ButterKnifeFragment extends BaseFragment {
    private Unbinder unbinder;

    protected abstract View inflateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup);

    @Nullable
    @CallSuper
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflateView(inflater, container);
        this.unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
