package com.hotellook.statistics.mixpanel;

import com.hotellook.search.ServerDateFormatter;
import com.hotellook.ui.screen.searchform.LaunchParams;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import me.zhanghai.android.materialprogressbar.C1759R;
import pl.charmas.android.reactivelocation.C1822R;

public class ParseLaunchParamsUtils {
    public static final String APPSFLYER_DATE_FORMAT = "ddMMyyyy";
    public static final String TYPE_CITY = "L";
    public static final String TYPE_HOTEL = "H";
    public static final String TYPE_IATA = "I";

    public static LaunchParams fromApsflyer(String params) throws ParseException {
        try {
            String[] splittedParams = params.split("-");
            String type = splittedParams[0];
            SimpleDateFormat appsflyerDateFormat = new SimpleDateFormat(APPSFLYER_DATE_FORMAT, Locale.US);
            ServerDateFormatter dateFormatter = new ServerDateFormatter();
            String checkInDate = dateFormatter.format(appsflyerDateFormat.parse(splittedParams[2]));
            String checkOutDate = dateFormatter.format(appsflyerDateFormat.parse(splittedParams[3]));
            int adults = Integer.parseInt(splittedParams[4]);
            Object obj = -1;
            switch (type.hashCode()) {
                case C1759R.styleable.AppCompatTheme_listPreferredItemHeightLarge /*72*/:
                    if (type.equals(TYPE_HOTEL)) {
                        obj = null;
                        break;
                    }
                    break;
                case C1759R.styleable.AppCompatTheme_listPreferredItemPaddingLeft /*73*/:
                    if (type.equals(TYPE_IATA)) {
                        obj = 2;
                        break;
                    }
                    break;
                case C1759R.styleable.AppCompatTheme_listPopupWindowStyle /*76*/:
                    if (type.equals(TYPE_CITY)) {
                        obj = 1;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case C1822R.styleable.SignInButton_buttonSize /*0*/:
                    return new LaunchParams(Long.parseLong(splittedParams[1]), checkInDate, checkOutDate, adults);
                case C1822R.styleable.SignInButton_colorScheme /*1*/:
                    return new LaunchParams(Integer.parseInt(splittedParams[1]), checkInDate, checkOutDate, adults);
                case C1822R.styleable.SignInButton_scopeUris /*2*/:
                    return new LaunchParams(splittedParams[1], checkInDate, checkOutDate, adults);
                default:
                    throw new ParseException("Unknown type " + type, 0);
            }
        } catch (Exception e) {
            throw new ParseException(e.getMessage(), 0);
        }
    }
}
