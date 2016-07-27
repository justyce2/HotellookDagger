package com.hotellook.ui.screen;

import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import com.hotellook.C1178R;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.dialog.DismissDialogListener;
import com.hotellook.ui.dialog.LoadingDialogContent;
import com.hotellook.ui.dialog.LoadingDialogFactory;
import com.hotellook.ui.dialog.OneButtonDialogContent;
import com.hotellook.ui.dialog.OneButtonDialogFactory;
import com.hotellook.ui.dialog.TwoButtonDialogContent;
import com.hotellook.ui.dialog.TwoButtonDialogFactory;
import com.hotellook.ui.dialog.error.NetworkErrorDialogContentFactory;
import com.hotellook.ui.screen.browser.BookingLoadingDialogFragment;
import com.hotellook.ui.screen.hotel.HotelFragment;
import com.hotellook.ui.screen.information.RateDialogFragment;
import com.hotellook.ui.screen.information.RateDialogFragment.Source;
import com.hotellook.ui.screen.searchform.SearchFormFragment;
import retrofit.RetrofitError;
import timber.log.Timber;

public class Dialogs {
    public static final String TAG_ERROR_DIALOG = "ERROR_DIALOG";

    @NonNull
    public static AlertDialog showCurrencyChangedDialog(Context context) {
        DismissDialogListener dialogListener = new DismissDialogListener();
        AlertDialog dialog = new OneButtonDialogFactory(context).createDialog(new OneButtonDialogContent(C1178R.string.currency_changed, C1178R.string.currency_alert_message, C1178R.string.ok_understood, dialogListener));
        dialogListener.setDialog(dialog);
        dialog.show();
        return dialog;
    }

    public static void showLocationErrorDialog(FragmentActivity context, DismissDialogListener repeatListener) {
        DismissDialogListener dismissListener = new DismissDialogListener();
        AlertDialog alertDialog = new TwoButtonDialogFactory(context).createDialog(new TwoButtonDialogContent(C1178R.string.location_error, C1178R.string.location_error_message, C1178R.string.dialog_cancel, C1178R.string.dialog_repeat, dismissListener, repeatListener));
        dismissListener.setDialog(alertDialog);
        repeatListener.setDialog(alertDialog);
        show(AlertDialogFragment.create(alertDialog), context.getSupportFragmentManager(), SearchFormFragment.LOCATION_ERROR_DIALOG_TAG);
    }

    public static void showLocationPermissionDialog(FragmentActivity activity, DismissDialogListener dismissListener, DismissDialogListener openSettingsListener) {
        AlertDialog alertDialog = new TwoButtonDialogFactory(activity).createDialog(new TwoButtonDialogContent(C1178R.string.your_location, C1178R.string.location_permission_message, C1178R.string.dialog_cancel, C1178R.string.dialog_to_settings, dismissListener, openSettingsListener));
        dismissListener.setDialog(alertDialog);
        openSettingsListener.setDialog(alertDialog);
        show(AlertDialogFragment.create(alertDialog), activity.getSupportFragmentManager(), MainActivity.REQUEST_LOCATION_PERMISSION_DIALOG_TAG);
    }

    public static AlertDialog showLoadingNearestCityDialog(FragmentActivity activity, OnDismissListener dismissListener) {
        AlertDialog locationLoadingDialog = new LoadingDialogFactory(activity).createDialog(new LoadingDialogContent(C1178R.string.location_updating_dialog_message));
        locationLoadingDialog.setCanceledOnTouchOutside(false);
        locationLoadingDialog.setOnDismissListener(dismissListener);
        show(AlertDialogFragment.create(locationLoadingDialog), activity.getSupportFragmentManager(), SearchFormFragment.NEAREST_LOCATION_LOADING_DIALOG_TAG);
        return locationLoadingDialog;
    }

    public static AlertDialog showDestinationLoadingDialog(FragmentActivity activity) {
        AlertDialog destinationLoadingDialog = new LoadingDialogFactory(activity).createDialog(new LoadingDialogContent(C1178R.string.destination_loading_dialog_message));
        destinationLoadingDialog.setCanceledOnTouchOutside(false);
        show(AlertDialogFragment.create(destinationLoadingDialog), activity.getSupportFragmentManager(), SearchFormFragment.DESTINATION_LOADING_DIALOG_TAG);
        return destinationLoadingDialog;
    }

    public static AlertDialogFragment showNetworkErrorDialog(FragmentActivity activity, RetrofitError error, DismissDialogListener dialogListener, OnKeyListener onKeyListener) {
        AlertDialog dialog = new OneButtonDialogFactory(activity).createDialog(new NetworkErrorDialogContentFactory().getDialogContent(error, dialogListener));
        dialogListener.setDialog(dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(onKeyListener);
        AlertDialogFragment errorDialog = AlertDialogFragment.create(dialog);
        show(errorDialog, activity.getSupportFragmentManager(), TAG_ERROR_DIALOG);
        return errorDialog;
    }

    public static BookingLoadingDialogFragment showBrowserLoadingDialog(FragmentActivity activity, String gateName) {
        BookingLoadingDialogFragment dialog = BookingLoadingDialogFragment.create(gateName);
        show(dialog, activity.getSupportFragmentManager(), BookingLoadingDialogFragment.TAG);
        return dialog;
    }

    public static void showOutOfDateDialog(FragmentActivity activity, DismissDialogListener newSearchListener) {
        DismissDialogListener dismissListener = new DismissDialogListener();
        AlertDialog alertDialog = new TwoButtonDialogFactory(activity).createDialog(new TwoButtonDialogContent(C1178R.string.out_of_date_dialog_title, C1178R.string.out_of_date_dialog_message, C1178R.string.out_of_date_dialog_cancel_btn, C1178R.string.out_of_date_dialog_new_search_btn, dismissListener, newSearchListener, Integer.valueOf(ContextCompat.getColor(activity, C1178R.color.green_AADB92))));
        dismissListener.setDialog(alertDialog);
        newSearchListener.setDialog(alertDialog);
        show(AlertDialogFragment.create(alertDialog), activity.getSupportFragmentManager(), HotelFragment.OUT_OF_DATE_DIALOG_TAG);
    }

    public static AlertDialog showLoadingIntentErrorDialog(FragmentActivity activity, DismissDialogListener repeatListener, DismissDialogListener cancelListener) {
        AlertDialog loadingFromIntentErrorAlertDialog = new TwoButtonDialogFactory(activity).createDialog(new TwoButtonDialogContent(C1178R.string.loading_from_intent_error_dialog_title, C1178R.string.loading_from_intent_error_dialog_message, C1178R.string.loading_from_intent_error_dialog_repeat, C1178R.string.loading_from_intent_error_dialog_new_search_btn, repeatListener, cancelListener));
        repeatListener.setDialog(loadingFromIntentErrorAlertDialog);
        cancelListener.setDialog(loadingFromIntentErrorAlertDialog);
        loadingFromIntentErrorAlertDialog.setCancelable(false);
        show(AlertDialogFragment.create(loadingFromIntentErrorAlertDialog), activity.getSupportFragmentManager(), HotelFragment.LOADING_FROM_INTENT_DIALOG_TAG);
        return loadingFromIntentErrorAlertDialog;
    }

    public static void showRateDialog(FragmentActivity activity, Source source) {
        RateDialogFragment rateDialogFragment = new RateDialogFragment();
        rateDialogFragment.setSource(source);
        show(rateDialogFragment, activity.getSupportFragmentManager(), RateDialogFragment.TAG);
    }

    private static void show(DialogFragment dialog, FragmentManager supportFragmentManager, String tag) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.add((Fragment) dialog, tag);
        try {
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            Timber.m751d("Cant open dialog %s", dialog.getClass().getSimpleName());
        }
    }

    public static void showEnGatesDialog(FragmentActivity activity, DismissDialogListener confirmListener) {
        DismissDialogListener cancelListener = new DismissDialogListener();
        AlertDialog alertDialog = new TwoButtonDialogFactory(activity).createDialog(TwoButtonDialogContent.newBuilder().title(C1178R.string.restart_search).message(C1178R.string.restart_search_for_en_gates).firstBtnTxtId(C1178R.string.later).secondBtnTxtId(C1178R.string.restart).firstBtnClickListener(cancelListener).secondBtnClickListener(confirmListener).build());
        cancelListener.setDialog(alertDialog);
        confirmListener.setDialog(alertDialog);
        show(AlertDialogFragment.create(alertDialog), activity.getSupportFragmentManager(), HotelFragment.RESTART_SEARCH_DIALOG_TAG);
    }
}
