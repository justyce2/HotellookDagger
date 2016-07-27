package com.hotellook.ui.screen.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.backstack.SavableFragment;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.searchresults.SearchResultsFragment;
import rx.Observable;
import rx.Observable.Transformer;
import rx.Subscription;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

public class BaseFragment extends Fragment implements SavableFragment {
    private final BehaviorSubject<Boolean> destroyViewSubject;
    private Object initialSnapshot;
    private boolean paused;
    private CompositeSubscription subscriptions;

    /* renamed from: com.hotellook.ui.screen.common.BaseFragment.1 */
    class C12191 extends AnimatorListenerAdapter {
        final /* synthetic */ MenuItem[] val$menuItems;

        C12191(MenuItem[] menuItemArr) {
            this.val$menuItems = menuItemArr;
        }

        public void onAnimationStart(Animator animation) {
            for (MenuItem menuItem : this.val$menuItems) {
                menuItem.setVisible(true);
                menuItem.getIcon().setAlpha(0);
            }
        }
    }

    public BaseFragment() {
        this.subscriptions = new CompositeSubscription();
        this.destroyViewSubject = BehaviorSubject.create();
    }

    protected <T> Transformer<T, T> subscribeOnIoObserveOnMain() {
        return BaseFragment$$Lambda$1.lambdaFactory$();
    }

    protected <T> Transformer<T, T> bindUntilDestroyView() {
        return BaseFragment$$Lambda$2.lambdaFactory$(this);
    }

    /* synthetic */ Observable lambda$bindUntilDestroyView$1(Observable observable) {
        return observable.takeUntil(this.destroyViewSubject.first());
    }

    public void onDestroyView() {
        this.subscriptions.clear();
        this.destroyViewSubject.onNext(Boolean.valueOf(true));
        super.onDestroyView();
    }

    public ActionBar getSupportActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    @Nullable
    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    protected HotellookApplication getApplication() {
        return HotellookApplication.getApp();
    }

    protected void showMenuItemsWithDelay(int delay, MenuItem... menuItems) {
        for (MenuItem menuItem : menuItems) {
            menuItem.setVisible(false);
        }
        if (getView() != null) {
            getView().postDelayed(BaseFragment$$Lambda$3.lambdaFactory$(this, menuItems), (long) delay);
        }
    }

    /* synthetic */ void lambda$showMenuItemsWithDelay$3(MenuItem[] menuItems) {
        ValueAnimator animator = ValueAnimator.ofInt(new int[]{0, 255});
        animator.addListener(new C12191(menuItems));
        animator.addUpdateListener(BaseFragment$$Lambda$4.lambdaFactory$(menuItems));
        animator.setDuration(100);
        animator.start();
    }

    static /* synthetic */ void lambda$null$2(MenuItem[] menuItems, ValueAnimator animation) {
        for (MenuItem menuItem : menuItems) {
            menuItem.getIcon().setAlpha(((Integer) animation.getAnimatedValue()).intValue());
        }
    }

    protected void showMenuItemsWithStandardDelay(MenuItem... menuItems) {
        showMenuItemsWithDelay(SearchResultsFragment.TOOLBAR_ANIMATION_DURATION, menuItems);
    }

    protected void showMenuItems(MenuItem... menuItems) {
        showMenuItemsWithDelay(0, menuItems);
    }

    public void onPause() {
        super.onPause();
        this.paused = true;
    }

    public void onResume() {
        super.onResume();
        this.paused = false;
    }

    protected boolean isAfterOnPause() {
        return this.paused;
    }

    public ViewGroup getToolbar() {
        return getMainActivity().getToolbarManager().getToolbar();
    }

    protected int getStandardOffset() {
        return getResources().getDimensionPixelSize(C1178R.dimen.standard_offset);
    }

    protected HotellookComponent getComponent() {
        return HotellookApplication.getApp().getComponent();
    }

    protected void keepUntilDestroyView(@NonNull Subscription subscription) {
        this.subscriptions.add(subscription);
    }

    public void finish() {
        if (!isAfterOnPause()) {
            getActivity().onBackPressed();
        }
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
