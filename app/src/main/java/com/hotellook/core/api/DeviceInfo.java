package com.hotellook.core.api;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

class DeviceInfo {
    private static final String NETWORK_MOBILE = "3g";
    private static final String NETWORK_WIFI = "wifi";
    private static final String OS_ANDROID = "android";
    private static final String PARAM_APPLICATION = "application";
    private static final String PARAM_APPLICATION_ID = "application_id";
    private static final String PARAM_CARRIER_NAME = "carrier_name";
    private static final String PARAM_DEVICE_ID = "device_id";
    private static final String PARAM_DEVICE_MODEL = "device_model";
    private static final String PARAM_HOST = "host";
    private static final String PARAM_MOBILE_COUNTRY_CODE = "mobile_country_code";
    private static final String PARAM_MOBILE_NETWORK_CODE = "mobile_network_code";
    private static final String PARAM_NETWORK = "network";
    private static final String PARAM_OS = "os";
    private static final String PARAM_OS_API_VERSION = "os_api_level";
    private static final String PARAM_OS_VERSION = "os_version";
    private static final String PARAM_SOURCE = "source";
    private static final String PARAM_TOKEN = "token";
    private static final String PARAM_TYPE = "type";
    private static final String PARAM_VERSION = "version";
    private static final String SOURCE_SEARCH = "search";
    private static final String TYPE_MOBILE = "mobile";
    final String appId;
    final String appName;
    final String appVersion;
    final String carrierName;
    private final Context context;
    final String deviceId;
    final String deviceModel;
    final String deviceType;
    final String host;
    final String mobileCountryCode;
    final String mobileNetworkCode;
    final String network;
    final String os;
    final String osApiLevel;
    final String osVersion;
    final String source;
    private final String string;
    final String token;

    static class ToStringBuilder {
        private final StringBuilder builder;

        ToStringBuilder() {
            this.builder = new StringBuilder();
        }

        ToStringBuilder append(String fieldName, String value) {
            String encodedValue = encode(value);
            if (encodedValue != null) {
                if (this.builder.length() > 0) {
                    this.builder.append("; ");
                }
                this.builder.append(fieldName).append(SimpleComparison.EQUAL_TO_OPERATION).append(encodedValue);
            }
            return this;
        }

        private String encode(String value) {
            if (value != null) {
                try {
                    return URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Log.e("DeviceInfo", "Can't encode the value: " + value, e);
                }
            }
            return null;
        }

        String build() {
            return this.builder.toString();
        }
    }

    static DeviceInfo from(Context context) {
        return new DeviceInfo(context);
    }

    private DeviceInfo(Context context) {
        this.context = context.getApplicationContext();
        this.deviceType = deviceType();
        this.source = source();
        this.appName = appName();
        this.appId = appId();
        this.appVersion = appVersion();
        this.host = host();
        this.token = token();
        this.deviceId = deviceId();
        this.os = os();
        this.osVersion = osVersion();
        this.osApiLevel = osApiLevel();
        this.deviceModel = deviceModel();
        this.network = network();
        this.carrierName = carrierName();
        this.mobileCountryCode = mobileCountryCode();
        this.mobileNetworkCode = mobileNetworkCode();
        this.string = _toString();
    }

    private String deviceType() {
        return TYPE_MOBILE;
    }

    private String source() {
        return SOURCE_SEARCH;
    }

    private String appName() {
        return this.context.getString(this.context.getApplicationInfo().labelRes);
    }

    private String appId() {
        return this.context.getPackageName();
    }

    private String appVersion() {
        try {
            return this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    private String host() {
        int screenLayoutSize = this.context.getResources().getConfiguration().screenLayout & 15;
        if (screenLayoutSize == 4 || screenLayoutSize == 3) {
            return HotellookService.HOST_TABLET;
        }
        return HotellookService.HOST_PHONE;
    }

    private String token() {
        Settings settings = Settings.from(this.context);
        String token = settings.getToken();
        if (token == null) {
            token = deviceId();
            if (token == null) {
                token = Build.SERIAL;
                if (token == null) {
                    token = new String(Hex.encodeHex(DigestUtils.md5(System.currentTimeMillis() + Build.MANUFACTURER + Build.MODEL)));
                }
            }
            settings.saveToken(token);
        }
        return token;
    }

    private String deviceId() {
        try {
            return Secure.getString(this.context.getContentResolver(), "android_id");
        } catch (Exception e) {
            return null;
        }
    }

    private String os() {
        return OS_ANDROID;
    }

    private String osVersion() {
        return VERSION.RELEASE;
    }

    private String osApiLevel() {
        return String.valueOf(VERSION.SDK_INT);
    }

    private String deviceModel() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        }
        return manufacturer + " " + model;
    }

    private String network() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return null;
        }
        State mobile;
        State wifi;
        if (connectivityManager.getNetworkInfo(0) != null) {
            mobile = connectivityManager.getNetworkInfo(0).getState();
        } else {
            mobile = State.DISCONNECTED;
        }
        if (connectivityManager.getNetworkInfo(1) != null) {
            wifi = connectivityManager.getNetworkInfo(1).getState();
        } else {
            wifi = State.DISCONNECTED;
        }
        if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
            return NETWORK_MOBILE;
        }
        if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
            return NETWORK_WIFI;
        }
        return null;
    }

    private String carrierName() {
        TelephonyManager telephonyManager = (TelephonyManager) this.context.getSystemService(MixPanelParams.PHONE);
        if (telephonyManager != null) {
            return telephonyManager.getNetworkOperatorName();
        }
        return null;
    }

    private String mobileCountryCode() {
        TelephonyManager telephonyManager = (TelephonyManager) this.context.getSystemService(MixPanelParams.PHONE);
        if (telephonyManager == null) {
            return null;
        }
        String networkOperator = telephonyManager.getNetworkOperator();
        if (TextUtils.isEmpty(networkOperator) || networkOperator.length() < 5) {
            return null;
        }
        return networkOperator.substring(0, 3);
    }

    private String mobileNetworkCode() {
        TelephonyManager telephonyManager = (TelephonyManager) this.context.getSystemService(MixPanelParams.PHONE);
        if (telephonyManager == null) {
            return null;
        }
        String networkOperator = telephonyManager.getNetworkOperator();
        if (TextUtils.isEmpty(networkOperator) || networkOperator.length() < 5) {
            return null;
        }
        return networkOperator.substring(3);
    }

    public String toString() {
        return this.string;
    }

    private String _toString() {
        return new ToStringBuilder().append(PARAM_TYPE, this.deviceType).append(PARAM_SOURCE, this.source).append(PARAM_APPLICATION, this.appName).append(PARAM_APPLICATION_ID, this.appId).append(PARAM_VERSION, this.appVersion).append(PARAM_HOST, this.host).append(PARAM_TOKEN, this.token).append(PARAM_DEVICE_ID, this.deviceId).append(PARAM_OS, this.os).append(PARAM_OS_VERSION, this.osVersion).append(PARAM_OS_API_VERSION, this.osApiLevel).append(PARAM_DEVICE_MODEL, this.deviceModel).append(PARAM_NETWORK, this.network).append(PARAM_CARRIER_NAME, this.carrierName).append(PARAM_MOBILE_COUNTRY_CODE, this.mobileCountryCode).append(PARAM_MOBILE_NETWORK_CODE, this.mobileNetworkCode).build();
    }
}
