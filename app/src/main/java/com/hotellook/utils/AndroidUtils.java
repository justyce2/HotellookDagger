package com.hotellook.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;
import com.google.android.gms.common.GoogleApiAvailability;
import com.hotellook.C1178R;
import com.hotellook.common.click.DoubleClickPreventer;
import com.hotellook.common.locale.Constants.Region;
import com.hotellook.ui.activity.MainActivity;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import java.lang.reflect.Method;
import java.util.Locale;
import me.zhanghai.android.materialprogressbar.BuildConfig;
import me.zhanghai.android.materialprogressbar.C1759R;

public class AndroidUtils {
    private static final double LARGE_PHONE_DIAGONAL = 4.2d;
    private static final int PHONE = 1;
    private static final String SHOW_NAV_BAR_RES_NAME = "config_showNavigationBar";
    private static final int TABLET_LARGE = 2;
    private static final int TABLET_XLARGE = 3;
    private static Integer deviceType;
    private static final DoubleClickPreventer sClickPreventer;
    private static int sNavBarBottomHeight;
    private static String sNavBarOverride;
    private static int sNavBarRightWidth;

    static {
        sClickPreventer = new DoubleClickPreventer(300);
        deviceType = null;
        sNavBarOverride = BuildConfig.FLAVOR;
        sNavBarBottomHeight = 0;
        sNavBarRightWidth = 0;
        try {
            Class[] clsArr = new Class[PHONE];
            clsArr[0] = String.class;
            Method m = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", clsArr);
            m.setAccessible(true);
            Object[] objArr = new Object[PHONE];
            objArr[0] = "qemu.hw.mainkeys";
            sNavBarOverride = (String) m.invoke(null, objArr);
        } catch (Throwable th) {
        }
    }

    @Nullable
    public static Activity from(View view) {
        for (Context context = view.getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
        }
        return null;
    }

    public static boolean isMetricUnits() {
        boolean isImperialUnits;
        String countryCode = getLocale().getCountry();
        if (Region.UNITED_STATES.equals(countryCode) || Region.LIBERIA.equals(countryCode) || Region.BURMA.equals(countryCode)) {
            isImperialUnits = true;
        } else {
            isImperialUnits = false;
        }
        if (isImperialUnits) {
            return false;
        }
        return true;
    }

    public static int convertDpToPx(Context context, int dpValue) {
        return (int) TypedValue.applyDimension(PHONE, (float) dpValue, context.getResources().getDisplayMetrics());
    }

    public static void removeOnGlobalLayoutListener(View view, OnGlobalLayoutListener listener) {
        if (VERSION.SDK_INT >= 16) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        } else {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        }
    }

    public static Point getDisplaySize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static Point getDisplaySize(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return new Point(metrics.widthPixels, metrics.heightPixels);
    }

    @TargetApi(14)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(SHOW_NAV_BAR_RES_NAME, "bool", AbstractSpiCall.ANDROID_CLIENT_TYPE);
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            if ("1".equals(sNavBarOverride)) {
                return false;
            }
            if ("0".equals(sNavBarOverride)) {
                return true;
            }
            return hasNav;
        }
        return !ViewConfiguration.get(context).hasPermanentMenuKey();
    }

    @Deprecated
    public static int getNavBarHeight(Context context) {
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(4);
        if (hasMenuKey || hasBackKey) {
            return 0;
        }
        int resourceId;
        Resources resources = context.getResources();
        int orientation = context.getResources().getConfiguration().orientation;
        if (isTablet(context)) {
            resourceId = resources.getIdentifier(orientation == PHONE ? "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", AbstractSpiCall.ANDROID_CLIENT_TYPE);
        } else {
            resourceId = resources.getIdentifier(orientation == PHONE ? "navigation_bar_height" : "navigation_bar_width", "dimen", AbstractSpiCall.ANDROID_CLIENT_TYPE);
        }
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static boolean isTablet(Context context) {
        return getDeviceType(context) != PHONE && isLarge(context);
    }

    public static boolean isLarge(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= TABLET_XLARGE;
    }

    public static int getStatusBarHeight(Context context) {
        if (VERSION.SDK_INT < 21) {
            return 0;
        }
        return getStatusBarHeight(context.getResources());
    }

    public static int getStatusBarHeight(Resources res) {
        int resourceId = res.getIdentifier("status_bar_height", "dimen", AbstractSpiCall.ANDROID_CLIENT_TYPE);
        if (resourceId > 0) {
            return res.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static void addPaddingToOffsetStatusBar(View view) {
        if (VERSION.SDK_INT >= 21) {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(view.getContext()), view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    public static void addNegativeToolbarPadding(View view) {
        if (VERSION.SDK_INT >= 21) {
            MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
            params.setMargins(params.leftMargin, params.topMargin - getToolbarHeight(view.getContext()), params.rightMargin, params.bottomMargin);
        }
    }

    public static void setUpViewBelowStatusBar(View view) {
        if (VERSION.SDK_INT >= 21 && view != null) {
            MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
            params.setMargins(params.leftMargin, getStatusBarHeight(view.getContext()), params.rightMargin, params.bottomMargin);
        }
    }

    private static int getDeviceType(Context context) {
        if (deviceType == null) {
            if ((context.getResources().getConfiguration().screenLayout & 15) == 4) {
                deviceType = Integer.valueOf(TABLET_XLARGE);
            } else if ((context.getResources().getConfiguration().screenLayout & 15) == TABLET_XLARGE) {
                deviceType = Integer.valueOf(TABLET_LARGE);
            } else {
                deviceType = Integer.valueOf(PHONE);
            }
        }
        return deviceType.intValue();
    }

    public static boolean isPhone(Context context) {
        return getDeviceType(context) == PHONE;
    }

    public static int getToolbarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(C1759R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return context.getResources().getDimensionPixelSize(C1759R.dimen.abc_action_bar_default_height_material);
    }

    public static void addMarginToPlaceViewBelowToolbar(View view) {
        MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
        params.setMargins(params.leftMargin, params.topMargin + getToolbarHeight(view.getContext()), params.rightMargin, params.bottomMargin);
    }

    public static void addPaddingToOffsetToolbar(@NonNull View view) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getToolbarHeight(view.getContext()), view.getPaddingRight(), view.getPaddingBottom());
    }

    public static void addMarginToPlaceViewBelowStatusBar(View view) {
        int marginTop = 0;
        if (VERSION.SDK_INT >= 21) {
            marginTop = 0 + getStatusBarHeight(view.getContext());
        }
        MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
        params.setMargins(params.leftMargin, params.topMargin + marginTop, params.rightMargin, params.bottomMargin);
    }

    public static void hideKeyboard(MainActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean preventDoubleClick() {
        return sClickPreventer.preventDoubleClick();
    }

    public static void forceHideSpinners(Spinner spinner) {
        if (spinner != null) {
            try {
                Method method = Spinner.class.getDeclaredMethod("onDetachedFromWindow", new Class[0]);
                method.setAccessible(true);
                method.invoke(spinner, new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getLanguage() {
        return getLocale().getLanguage().toLowerCase();
    }

    public static String getCountry() {
        return getLocale().getCountry().toLowerCase();
    }

    public static Locale getLocale() {
        return Locale.getDefault();
    }

    public static boolean smallDensity(Context context) {
        int density = context.getResources().getDisplayMetrics().densityDpi;
        return density == 120 || density == 160 || density == 240;
    }

    public static boolean isGoogleServicesAvailable(Context context) {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == 0;
    }

    @SuppressLint({"NewApi"})
    public static void addPaddingToOffsetNavBarBottom(View view) {
        if (VERSION.SDK_INT >= 21) {
            addBottomPadding(view, sNavBarBottomHeight);
        }
    }

    @SuppressLint({"NewApi"})
    public static void addMarginsToOffsetNavBar(View view) {
        if (VERSION.SDK_INT >= 21) {
            MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin + sNavBarRightWidth, params.bottomMargin + sNavBarBottomHeight);
        }
    }

    @SuppressLint({"NewApi"})
    public static void addMarginToOffsetNavBarRight(View view) {
        if (VERSION.SDK_INT >= 21) {
            MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin + sNavBarRightWidth, params.bottomMargin);
        }
    }

    @SuppressLint({"NewApi"})
    public static void addMarginToOffsetNavBarBottom(View view) {
        if (VERSION.SDK_INT >= 21) {
            addBottomMargin(view, sNavBarBottomHeight);
        }
    }

    public static void addPaddingToOffsetBottomNav(@NonNull View view) {
        addBottomPadding(view, sNavBarBottomHeight + getBottomNavHeight(view.getContext()));
    }

    public static void addMarginToOffsetBottomNav(@NonNull View view) {
        addBottomMargin(view, sNavBarBottomHeight + getBottomNavHeight(view.getContext()));
    }

    public static void addBottomPadding(@NonNull View view, int padding) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom() + padding);
    }

    public static void addBottomMargin(@NonNull View view, int padding) {
        MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
        params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, params.bottomMargin + padding);
    }

    public static void cacheNavBarSizes(Activity activity) {
        if (VERSION.SDK_INT >= 21) {
            Rect visibleRect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(visibleRect);
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics realMetrics = new DisplayMetrics();
            display.getRealMetrics(realMetrics);
            int realWidth = realMetrics.widthPixels;
            sNavBarBottomHeight = (realMetrics.heightPixels - visibleRect.bottom) - visibleRect.top;
            sNavBarRightWidth = (realWidth - visibleRect.right) - visibleRect.left;
            if (sNavBarBottomHeight < 0) {
                sNavBarBottomHeight = 0;
            }
            if (sNavBarRightWidth < 0) {
                sNavBarRightWidth = 0;
            }
        }
    }

    public static int getBottomNavHeight(@NonNull Context context) {
        return context.getResources().getDimensionPixelSize(C1178R.dimen.bottom_nav_height);
    }

    public static int getNavBarBottomHeight() {
        return sNavBarBottomHeight;
    }

    public static int getNavBarRightWidth() {
        return sNavBarRightWidth;
    }

    public static int getViewYLocationOnScreen(@NonNull View view) {
        int[] viewLocation = new int[TABLET_LARGE];
        view.getLocationOnScreen(viewLocation);
        return viewLocation[PHONE];
    }

    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == TABLET_LARGE;
    }

    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == PHONE;
    }

    public static boolean isLargePhone(Context context) {
        return isPhone(context) && getDiagonalInInches(context) > LARGE_PHONE_DIAGONAL;
    }

    private static double getDiagonalInInches(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        double density = (double) (dm.density * 160.0f);
        return Math.sqrt(Math.pow(((double) dm.widthPixels) / density, 2.0d) + Math.pow(((double) dm.heightPixels) / density, 2.0d));
    }

    public static boolean hasLocationPermission(@NonNull Context context) {
        return ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0;
    }

    public static boolean isPhoneLandscape(Context context) {
        return isLandscape(context) && isPhone(context);
    }
}
