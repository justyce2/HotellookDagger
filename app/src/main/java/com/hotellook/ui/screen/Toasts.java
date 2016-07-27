package com.hotellook.ui.screen;

import android.content.Context;
import android.widget.Toast;
import com.hotellook.C1178R;

public class Toasts {
    private static Toast lastToast;

    public static void showCalendarInvalidRange(Context context) {
        show(context, C1178R.string.calendar_invalid_range);
    }

    public static void showCurrencyAlertToast(Context context) {
        show(context, C1178R.string.currency_alert_message);
    }

    public static void showErrorFindLocation(Context context) {
        show(context, C1178R.string.error_find_location);
    }

    public static void showDeeplinkError(Context context) {
        show(context, C1178R.string.error_deeplink_loading);
    }

    public static void showHotelRemovedFromFavorites(Context context) {
        show(context, C1178R.string.hotel_removed_from_favorites_toast);
    }

    public static void showHotelAddedToFavorites(Context context) {
        show(context, C1178R.string.hotel_added_to_favorites_toast);
    }

    public static void showHotelSearchFailed(Context context) {
        show(context, C1178R.string.hotel_search_failed_toast);
    }

    public static void showHotelForTodayNoGps(Context context) {
        show(context, C1178R.string.hotel_for_today_no_gps_error);
    }

    public static void showErrorUpdatingLocation(Context context) {
        show(context, C1178R.string.sf_toast_error_updating_location);
    }

    private static void show(Context context, int textId) {
        lastToast = Toast.makeText(context, context.getString(textId), 0);
        lastToast.show();
    }

    public static void cancelLastToast() {
        if (lastToast != null) {
            lastToast.cancel();
        }
    }

    public static void showLocationPermissionDeniedToast(Context context) {
        show(context, C1178R.string.location_permission_denied_message);
    }

    public static void showInvalidLocationSettingsToast(Context context) {
        show(context, C1178R.string.location_invalid_settings_message);
    }

    public static void showNoPlayServicesToast(Context context) {
        show(context, C1178R.string.location_no_play_services_message);
    }
}
