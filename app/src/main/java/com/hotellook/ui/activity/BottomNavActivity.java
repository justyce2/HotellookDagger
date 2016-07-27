package com.hotellook.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.backstack.BackStackActivity;
import com.hotellook.backstack.SavableFragment;
import com.hotellook.backstack.SnapshotKeeper;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.events.OverlayClosedEvent;
import com.hotellook.ui.screen.favorite.view.FavoritesFragment;
import com.hotellook.ui.screen.hotel.HotelFragment;
import com.hotellook.ui.screen.information.ProfileFragment;
import com.hotellook.ui.screen.searchform.SearchFormFragment;
import com.hotellook.ui.view.bottomnavigation.BottomNavigationView;
import com.hotellook.ui.view.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.EventBus;
import com.hotellook.utils.Preconditions;
import java.util.List;
import me.zhanghai.android.materialprogressbar.C1759R;

public abstract class BottomNavActivity extends BackStackActivity implements OnNavigationItemSelectedListener {
    private static final int MAIN_BOTTOM_NAV_ITEM_ID = 2131690078;
    private static final String STATE_CURRENT_BOTTOM_NAV_ITEM_ID = "current_bottom_nav_item_id";
    private BottomNavigationView bottomNav;
    private int curBottomNavItemId;
    private Fragment curFragment;
    private EventBus eventBus;
    private SnapshotKeeper snapshotKeeper;

    public BottomNavActivity() {
        this.curBottomNavItemId = -1;
    }

    protected void onCreate(Bundle state) {
        if (state != null) {
            this.curBottomNavItemId = state.getInt(STATE_CURRENT_BOTTOM_NAV_ITEM_ID);
        }
        injectDependencies();
        super.onCreate(state);
        if (state == null) {
            removeFragmentsFromFragmentManager();
        }
        this.curFragment = getSupportFragmentManager().findFragmentById(C1178R.id.content_frame);
        if (this.curFragment instanceof SavableFragment) {
            ((SavableFragment) this.curFragment).setInitialSnapshot(this.snapshotKeeper.pop());
        }
    }

    private void injectDependencies() {
        HotellookComponent component = HotellookApplication.from(this).getComponent();
        this.eventBus = component.eventBus();
        this.snapshotKeeper = component.snapshotKeeper();
        this.backStackManager = component.backStackManager();
    }

    protected void onSaveInstanceState(Bundle outState) {
        if (this.curFragment instanceof SavableFragment) {
            this.snapshotKeeper.push(((SavableFragment) this.curFragment).takeSnapshot());
        }
        outState.putInt(STATE_CURRENT_BOTTOM_NAV_ITEM_ID, this.curBottomNavItemId);
        super.onSaveInstanceState(outState);
    }

    public void setContentView(@LayoutRes int layoutResId) {
        super.setContentView(layoutResId);
        setUpBottomNav();
    }

    private void setUpBottomNav() {
        this.bottomNav = (BottomNavigationView) findViewById(C1178R.id.bottom_nav);
        if (this.bottomNav == null) {
            throw new IllegalStateException("Failed to find BottomNavigationView");
        }
        AndroidUtils.addPaddingToOffsetNavBarBottom(this.bottomNav);
        this.bottomNav.setNavigationItemSelectedListener(this);
        if (this.curBottomNavItemId != -1) {
            this.bottomNav.select(this.curBottomNavItemId, false);
        }
    }

    @NonNull
    private Fragment rootBottomNavFragment(@IdRes int bottomNavItemId) {
        switch (bottomNavItemId) {
            case MAIN_BOTTOM_NAV_ITEM_ID /*2131690078*/:
                return new SearchFormFragment();
            case C1178R.id.bn_favorites /*2131690079*/:
                return new FavoritesFragment();
            case C1178R.id.bn_profile /*2131690080*/:
                return new ProfileFragment();
            default:
                throw new IllegalArgumentException();
        }
    }

    private boolean isRootBottomNavFragment(@NonNull Fragment fragment, @IdRes int bottomNavItemId) {
        return rootBottomNavFragment(bottomNavItemId).getClass() == fragment.getClass();
    }

    public void onBackPressed() {
        goBack();
    }

    public void onNavigationItemSelected(@IdRes int bottomNavItemId) {
        if (bottomNavItemId == this.curBottomNavItemId) {
            backToRoot();
        } else {
            switchTo(bottomNavItemId);
        }
    }

    protected void launchFromSearchForm() {
        this.bottomNav.select(MAIN_BOTTOM_NAV_ITEM_ID);
    }

    protected void launchFromSearchForm(@NonNull SearchFormFragment fragment) {
        launchFrom(fragment, MAIN_BOTTOM_NAV_ITEM_ID);
    }

    protected void launchFromHotel(@NonNull HotelFragment fragment) {
        launchFrom(fragment, MAIN_BOTTOM_NAV_ITEM_ID);
    }

    private void launchFrom(@NonNull Fragment fragment, @IdRes int bottomNavItemId) {
        this.bottomNav.select(bottomNavItemId, false);
        this.curBottomNavItemId = bottomNavItemId;
        showFragment(fragment);
    }

    public void showFragment(@NonNull Fragment fragment) {
        showFragment(fragment, true);
    }

    public void showFragment(@NonNull Fragment fragment, boolean addToBackStack) {
        if (this.curBottomNavItemId == -1) {
            throw new IllegalStateException("Unknown current navigation item id");
        }
        if (this.curFragment != null && addToBackStack) {
            pushFragmentToBackStack(this.curBottomNavItemId, this.curFragment);
        }
        replaceFragment(fragment);
    }

    private void switchTo(@IdRes int bottomNavItemId) {
        if (this.curFragment != null) {
            pushFragmentToBackStack(this.curBottomNavItemId, this.curFragment);
        }
        this.curBottomNavItemId = bottomNavItemId;
        Fragment fragment = popFragmentFromBackStack(this.curBottomNavItemId);
        if (fragment == null) {
            fragment = rootBottomNavFragment(this.curBottomNavItemId);
        }
        replaceFragment(fragment);
    }

    public void goBack() {
        if (isOverlayAttached()) {
            this.eventBus.post(new OverlayClosedEvent());
        }
        if (!getSupportFragmentManager().popBackStackImmediate()) {
            Pair<Integer, Fragment> pair = popFragmentFromBackStack();
            if (pair != null) {
                backTo(((Integer) pair.first).intValue(), (Fragment) pair.second);
            } else if (this.curBottomNavItemId == MAIN_BOTTOM_NAV_ITEM_ID && isRootBottomNavFragment(this.curFragment, MAIN_BOTTOM_NAV_ITEM_ID)) {
                finish();
            } else {
                backTo(MAIN_BOTTOM_NAV_ITEM_ID, rootBottomNavFragment(MAIN_BOTTOM_NAV_ITEM_ID));
            }
        }
    }

    private void backToRoot() {
        if (!isRootBottomNavFragment(this.curFragment, this.curBottomNavItemId)) {
            resetBackStackToRoot(this.curBottomNavItemId);
            backTo(this.curBottomNavItemId, (Fragment) Preconditions.checkNotNull(popFragmentFromBackStack(this.curBottomNavItemId)));
        }
    }

    private void backTo(@IdRes int bottomNavItemId, @NonNull Fragment fragment) {
        if (bottomNavItemId != this.curBottomNavItemId) {
            this.curBottomNavItemId = bottomNavItemId;
            this.bottomNav.select(this.curBottomNavItemId, false);
        }
        replaceFragment(fragment);
        getSupportFragmentManager().executePendingTransactions();
    }

    @SuppressLint({"PrivateResource"})
    private void replaceFragment(@NonNull Fragment fragment) {
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.setCustomAnimations(C1759R.anim.abc_fade_in, C1759R.anim.abc_fade_out, C1759R.anim.abc_fade_in, C1759R.anim.abc_fade_out);
        tr.replace(C1178R.id.content_frame, fragment);
        tr.commitAllowingStateLoss();
        this.curFragment = fragment;
    }

    private void resetBackStackToRoot(@IdRes int bottomNavItemId) {
        resetBackStackToRoot(bottomNavItemId, rootBottomNavFragment(bottomNavItemId));
    }

    protected void resetFavoritesBackStackIfNotCurrent() {
        if (this.curBottomNavItemId != C1178R.id.bn_favorites) {
            clearBackStack(C1178R.id.bn_favorites);
        }
    }

    @SuppressLint({"PrivateResource"})
    public void addFragment(@NonNull Fragment fragment) {
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.setCustomAnimations(C1759R.anim.abc_fade_in, C1759R.anim.abc_fade_out, C1759R.anim.abc_fade_in, C1759R.anim.abc_fade_out);
        tr.add((int) C1178R.id.content_frame, fragment);
        tr.addToBackStack(null);
        tr.commitAllowingStateLoss();
    }

    private void removeFragmentsFromFragmentManager() {
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        if (CollectionUtils.isNotEmpty(fragments)) {
            FragmentTransaction tr = fm.beginTransaction();
            for (Fragment fragment : fragments) {
                tr.remove(fragment);
            }
            tr.commitAllowingStateLoss();
        }
    }

    public void showOverlay(@NonNull Fragment fragment) {
        showOverlay(fragment, false);
    }

    @SuppressLint({"PrivateResource"})
    public void showOverlay(@NonNull Fragment fragment, boolean instantly) {
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        if (instantly) {
            tr.setCustomAnimations(C1178R.anim.instant_in, C1178R.anim.instant_out, C1178R.anim.instant_in, C1178R.anim.instant_out);
        } else {
            tr.setCustomAnimations(C1759R.anim.abc_fade_in, C1759R.anim.abc_fade_out, C1759R.anim.abc_fade_in, C1759R.anim.abc_fade_out);
        }
        tr.add((int) C1178R.id.overlay_frame, fragment);
        tr.addToBackStack(null);
        tr.commitAllowingStateLoss();
    }

    public boolean isOverlayAttached() {
        Fragment overlayFragment = getSupportFragmentManager().findFragmentById(C1178R.id.overlay_frame);
        return overlayFragment != null && overlayFragment.isAdded();
    }

    protected void goToSearchForm() {
        if (this.curBottomNavItemId == MAIN_BOTTOM_NAV_ITEM_ID) {
            backToRoot();
            return;
        }
        resetBackStackToRoot(MAIN_BOTTOM_NAV_ITEM_ID);
        this.bottomNav.select(MAIN_BOTTOM_NAV_ITEM_ID);
    }
}
