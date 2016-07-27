package com.hotellook.ui.screen.hotel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.backstack.Savable;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.screen.hotel.HotelInfoLoadableLayout.LCE;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.utils.AndroidUtils;
import pl.charmas.android.reactivelocation.C1822R;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class HotelInfoBaseListFragment extends BaseFragment implements Savable {
    private Adapter adapter;
    private View errorView;
    private HotelDataSource hotelDataSource;
    private View layout;
    private HotelInfoLoadableLayout loadableLayout;
    private View loadingView;
    private RecyclerView recyclerView;
    private View scrollView;

    /* renamed from: com.hotellook.ui.screen.hotel.HotelInfoBaseListFragment.1 */
    static /* synthetic */ class C13071 {
        static final /* synthetic */ int[] f728x491420ea;

        static {
            f728x491420ea = new int[LCE.values().length];
            try {
                f728x491420ea[LCE.CONTENT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    protected static class Snapshot {
        private final HotelDataSource hotelDataSource;

        public Snapshot(HotelDataSource hotelDataSource) {
            this.hotelDataSource = hotelDataSource;
        }
    }

    protected abstract Adapter createAdapter(HotelDataSource hotelDataSource);

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.layout = inflater.inflate(C1178R.layout.fragment_hotel_screen_base_list, container, false);
        this.loadableLayout = (HotelInfoLoadableLayout) this.layout.findViewById(C1178R.id.loadable_layout);
        this.recyclerView = (RecyclerView) this.layout.findViewById(C1178R.id.recycler_view);
        setUpRecyclerView();
        setUpRecyclerViewPaddings();
        this.errorView = this.loadableLayout.findViewById(C1178R.id.embedded_error_view);
        this.loadingView = this.loadableLayout.findViewById(C1178R.id.embedded_loading_view);
        this.scrollView = this.layout.findViewById(C1178R.id.scroll_view);
        subscribeToLoadableLayoutStates();
        HotellookApplication.eventBus().register(this);
        return this.layout;
    }

    @SuppressLint({"RxSubscribeOnError"})
    private void subscribeToLoadableLayoutStates() {
        keepUntilDestroyView(this.loadableLayout.observeState(this.hotelDataSource).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(HotelInfoBaseListFragment$$Lambda$1.lambdaFactory$(this)));
    }

    /* synthetic */ void lambda$subscribeToLoadableLayoutStates$0(LCE lce) {
        switch (C13071.f728x491420ea[lce.ordinal()]) {
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                setUpRecyclerViewAdapter();
                onShowContent();
            default:
        }
    }

    private void setUpRecyclerView() {
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(createLayoutManager());
    }

    @NonNull
    protected LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    protected void setUpRecyclerViewPaddings() {
        AndroidUtils.addBottomPadding(this.recyclerView, getStandardOffset());
    }

    protected void setUpRecyclerViewAdapter() {
        if (this.hotelDataSource.hasAllHotelData()) {
            this.adapter = createAdapter(this.hotelDataSource);
            this.recyclerView.setAdapter(this.adapter);
        }
    }

    public void onDestroyView() {
        HotellookApplication.eventBus().unregister(this);
        super.onDestroyView();
    }

    protected RecyclerView getRecyclerView() {
        return this.recyclerView;
    }

    public Adapter getAdapter() {
        return this.adapter;
    }

    protected void onShowContent() {
        this.recyclerView.setVisibility(0);
        this.scrollView.setVisibility(8);
    }

    public void setHotelDataSource(HotelDataSource hotelDataSource) {
        this.hotelDataSource = hotelDataSource;
    }

    @NonNull
    public Object saveState() {
        return new Snapshot(this.hotelDataSource);
    }

    public void restoreState(@NonNull Object state) {
        this.hotelDataSource = ((Snapshot) state).hotelDataSource;
    }
}
