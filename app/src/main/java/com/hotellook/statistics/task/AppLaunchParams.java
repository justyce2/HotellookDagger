package com.hotellook.statistics.task;

import android.content.Context;
import com.hotellook.HotellookApplication;
import com.hotellook.common.apps.AppsOnDevice;
import com.hotellook.db.DatabaseHelper;
import com.hotellook.statistics.Constants.AppsPackages;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.statistics.mixpanel.MixPanelParamsBuilder;
import com.hotellook.statistics.mixpanel.MixPanelSuperParamsBuilder;
import com.hotellook.statistics.mixpanel.MixpanelCurrentDayFactory;
import com.hotellook.utils.AndroidUtils;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AppLaunchParams implements MixPanelParamsBuilder, MixPanelSuperParamsBuilder {
    private final AppsOnDevice appsOnDevice;
    private final Context context;
    private String country;
    private String currency;
    private long favHotelsCount;
    private boolean hasAviasales;
    private boolean hasJetradar;
    private String lang;
    private String launchDate;
    private String locale;
    private final Map<String, String> mAppsFoCheckMixPanel;
    private final String referrer;

    /* renamed from: com.hotellook.statistics.task.AppLaunchParams.1 */
    class C12081 extends HashMap<String, String> {
        C12081() {
            put(MixPanelParams.HAS_BOOKING, AppsPackages.BOOKING);
            put(MixPanelParams.HAS_AGODA, AppsPackages.AGODA);
            put(MixPanelParams.HAS_EXPEDIA, AppsPackages.EXPEDIA);
            put(MixPanelParams.HAS_OSTROVOK, AppsPackages.OSTROVOK);
            put(MixPanelParams.HAS_OKTOGO, AppsPackages.OKTOGO);
            put(MixPanelParams.HAS_TRIVAGO, AppsPackages.TRIVAGO);
            put(MixPanelParams.HAS_ROOMGURU, AppsPackages.ROOMGURU);
            put(MixPanelParams.HAS_SKY_SCANNER_HOTELS, AppsPackages.SKYSKANNER_HOTELS);
            put(MixPanelParams.HAS_KAYAK, AppsPackages.KAYAK);
            put(MixPanelParams.HAS_AIR_BNB, AppsPackages.AIRBNB);
            put(MixPanelParams.HAS_HOTEL_TONIGHT, AppsPackages.HOTEL_TONIGHT);
            put(MixPanelParams.HAS_WE_GO, AppsPackages.WEGO);
        }
    }

    public AppLaunchParams(Context context, String referrer) {
        this.mAppsFoCheckMixPanel = new C12081();
        this.context = context;
        this.referrer = referrer;
        this.appsOnDevice = new AppsOnDevice(context);
    }

    private void prepareData() {
        DatabaseHelper dbHelper = HotellookApplication.getApp().getComponent().getDatabaseHelper();
        this.favHotelsCount = 0;
        try {
            this.favHotelsCount = dbHelper.getFavoriteHotelDataDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.hasAviasales = this.appsOnDevice.isInstalled(AppsPackages.AVIASALES);
        this.hasJetradar = this.appsOnDevice.isInstalled(AppsPackages.JETRADAR);
        this.launchDate = MixpanelCurrentDayFactory.create();
        this.locale = AndroidUtils.getLocale().toString();
        this.lang = AndroidUtils.getLanguage();
        this.country = AndroidUtils.getCountry();
        this.currency = HotellookApplication.from(this.context).getComponent().currencyRepository().currencyCode();
    }

    public Map<String, Object> buildParams() {
        prepareData();
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.LAUNCH_DATE, this.launchDate);
        params.put(MixPanelParams.LAUNCH_REFERRER, this.referrer);
        params.put(MixPanelParams.HAS_AVIASALES, Boolean.valueOf(this.hasAviasales));
        params.put(MixPanelParams.HAS_JET_RADAR, Boolean.valueOf(this.hasJetradar));
        params.put(MixPanelParams.SUPER_FAVORITES, Long.valueOf(this.favHotelsCount));
        params.put(MixPanelParams.LOCALE, this.locale);
        params.put(MixPanelParams.SYSTEM_COUNTRY, this.country);
        params.put(MixPanelParams.SYSTEM_LANGUAGE, this.lang);
        params.put(MixPanelParams.CURRENCY, this.currency);
        for (Entry<String, String> entry : this.mAppsFoCheckMixPanel.entrySet()) {
            params.put(entry.getKey(), Boolean.valueOf(this.appsOnDevice.isInstalled((String) entry.getValue())));
        }
        return params;
    }

    public Map<String, Object> buildSuperParams() {
        prepareData();
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.LAUNCH_DATE, this.launchDate);
        params.put(MixPanelParams.HAS_AVIASALES, Boolean.valueOf(this.hasAviasales));
        params.put(MixPanelParams.HAS_JET_RADAR, Boolean.valueOf(this.hasJetradar));
        params.put(MixPanelParams.SUPER_FAVORITES, Long.valueOf(this.favHotelsCount));
        params.put(MixPanelParams.LOCALE, this.locale);
        params.put(MixPanelParams.SYSTEM_COUNTRY, this.country);
        params.put(MixPanelParams.SYSTEM_LANGUAGE, this.lang);
        params.put(MixPanelParams.CURRENCY, this.currency);
        for (Entry<String, String> entry : this.mAppsFoCheckMixPanel.entrySet()) {
            params.put(entry.getKey(), Boolean.valueOf(this.appsOnDevice.isInstalled((String) entry.getValue())));
        }
        params.put(MixPanelParams.LOCATION_SERVICES, Boolean.valueOf(AndroidUtils.hasLocationPermission(this.context)));
        return params;
    }
}
