package com.hotellook.api.data;

import android.content.res.Resources;
import android.location.Location;
import com.hotellook.C1178R;
import com.hotellook.common.locale.Constants.Region;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.DateUtils;
import com.hotellook.utils.LocationUtils;
import java.util.Calendar;
import me.zhanghai.android.materialprogressbar.C1759R;
import pl.charmas.android.reactivelocation.C1822R;

public class DefaultSearchFormData {
    public final Calendar checkInCal;
    public final Calendar checkOutCal;
    public final String city;
    public final int cityId;
    public final String country;
    public final int destinationType;
    public final int hotelsNum;
    public final String latinCityName;
    public final Location location;

    private DefaultSearchFormData(String city, String country, int hotelsNum, int cityId, int destinationType, Location location, Calendar checkInCal, Calendar checkOutCal, String latinCityName) {
        this.city = city;
        this.country = country;
        this.hotelsNum = hotelsNum;
        this.cityId = cityId;
        this.destinationType = destinationType;
        this.location = location;
        this.checkInCal = checkInCal;
        this.checkOutCal = checkOutCal;
        this.latinCityName = latinCityName;
    }

    public static DefaultSearchFormData create(Resources res) {
        String city;
        String country;
        int cityId;
        int hotelsNum;
        Location location;
        String latinCityName = "DEFAULT";
        String systemCountry = AndroidUtils.getCountry().toUpperCase();
        Object obj = -1;
        switch (systemCountry.hashCode()) {
            case 2100:
                if (systemCountry.equals(Region.AUSTRALIA)) {
                    obj = null;
                    break;
                }
                break;
            case 2128:
                if (systemCountry.equals(Region.BRAZIL)) {
                    obj = 16;
                    break;
                }
                break;
            case 2135:
                if (systemCountry.equals(Region.BELARUS)) {
                    obj = 12;
                    break;
                }
                break;
            case 2142:
                if (systemCountry.equals(Region.CANADA)) {
                    obj = 2;
                    break;
                }
                break;
            case 2155:
                if (systemCountry.equals(Region.CHINA)) {
                    obj = 14;
                    break;
                }
                break;
            case 2177:
                if (systemCountry.equals(Region.GERMANY)) {
                    obj = 6;
                    break;
                }
                break;
            case 2222:
                if (systemCountry.equals(Region.SPAIN)) {
                    obj = 8;
                    break;
                }
                break;
            case 2252:
                if (systemCountry.equals(Region.FRANCE)) {
                    obj = 5;
                    break;
                }
                break;
            case 2267:
                if (systemCountry.equals(Region.GREAT_BRITAIN)) {
                    obj = 1;
                    break;
                }
                break;
            case 2307:
                if (systemCountry.equals(Region.HONG_KONG)) {
                    obj = 13;
                    break;
                }
                break;
            case 2347:
                if (systemCountry.equals(Region.ITALIA)) {
                    obj = 7;
                    break;
                }
                break;
            case 2374:
                if (systemCountry.equals(Region.JAPAN)) {
                    obj = 18;
                    break;
                }
                break;
            case 2407:
                if (systemCountry.equals(Region.SOUTH_KOREA)) {
                    obj = 17;
                    break;
                }
                break;
            case 2415:
                if (systemCountry.equals(Region.KAZAKHSTAN)) {
                    obj = 11;
                    break;
                }
                break;
            case 2564:
                if (systemCountry.equals(Region.PORTUGAL)) {
                    obj = 3;
                    break;
                }
                break;
            case 2627:
                if (systemCountry.equals(Region.RUSSIA)) {
                    obj = 9;
                    break;
                }
                break;
            case 2676:
                if (systemCountry.equals(Region.THAILAND)) {
                    obj = 4;
                    break;
                }
                break;
            case 2691:
                if (systemCountry.equals(Region.TAIWAN)) {
                    obj = 15;
                    break;
                }
                break;
            case 2700:
                if (systemCountry.equals(Region.UKRAINE)) {
                    obj = 10;
                    break;
                }
                break;
            case 2718:
                if (systemCountry.equals(Region.UNITED_STATES)) {
                    obj = 19;
                    break;
                }
                break;
        }
        switch (obj) {
            case C1822R.styleable.SignInButton_buttonSize /*0*/:
                city = res.getString(C1178R.string.en_au_city_name);
                country = res.getString(C1178R.string.en_au_country_name);
                cityId = 5630;
                hotelsNum = 958;
                location = LocationUtils.from(-33.86785d, 151.207323d);
                break;
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                city = res.getString(C1178R.string.en_gb_city_name);
                country = res.getString(C1178R.string.en_gb_country_name);
                cityId = 7896;
                hotelsNum = 2301;
                location = LocationUtils.from(51.500729d, -0.124627d);
                break;
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                city = res.getString(C1178R.string.en_ca_city_name);
                country = res.getString(C1178R.string.en_ca_country_name);
                cityId = 21357;
                hotelsNum = 317;
                location = LocationUtils.from(45.423456d, -75.697689d);
                break;
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                city = res.getString(C1178R.string.pt_pt_city_name);
                country = res.getString(C1178R.string.pt_pt_country_name);
                cityId = 4806;
                hotelsNum = 9863;
                location = LocationUtils.from(38.71667d, -9.13333d);
                break;
            case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
                city = res.getString(C1178R.string.th_th_city_name);
                country = res.getString(C1178R.string.th_th_country_name);
                cityId = 25949;
                hotelsNum = 6726;
                location = LocationUtils.from(13.758879d, 100.497358d);
                break;
            case C1822R.styleable.MapAttrs_cameraZoom /*5*/:
                city = res.getString(C1178R.string.fr_fr_city_name);
                country = res.getString(C1178R.string.fr_fr_country_name);
                cityId = 15542;
                hotelsNum = 23553;
                location = LocationUtils.from(48.85634d, 2.342587d);
                break;
            case C1822R.styleable.MapAttrs_liteMode /*6*/:
                city = res.getString(C1178R.string.de_de_city_name);
                country = res.getString(C1178R.string.de_de_country_name);
                cityId = 9510;
                hotelsNum = 12467;
                location = LocationUtils.from(52.520603d, 13.408907d);
                break;
            case C1822R.styleable.MapAttrs_uiCompass /*7*/:
                city = res.getString(C1178R.string.it_it_city_name);
                country = res.getString(C1178R.string.it_it_country_name);
                cityId = 13559;
                hotelsNum = 22373;
                location = LocationUtils.from(41.890202d, 12.492214d);
                break;
            case C1822R.styleable.MapAttrs_uiRotateGestures /*8*/:
                city = res.getString(C1178R.string.es_es_city_name);
                country = res.getString(C1178R.string.es_es_country_name);
                cityId = 3683;
                hotelsNum = 6117;
                location = LocationUtils.from(40.416876d, -3.704255d);
                break;
            case C1822R.styleable.MapAttrs_uiScrollGestures /*9*/:
                city = res.getString(C1178R.string.ru_ru_city_name);
                country = res.getString(C1178R.string.ru_ru_country_name);
                cityId = 12153;
                hotelsNum = 6551;
                location = LocationUtils.from(55.752041d, 37.617508d);
                break;
            case C1822R.styleable.MapAttrs_uiTiltGestures /*10*/:
                city = res.getString(C1178R.string.uk_ua_city_name);
                country = res.getString(C1178R.string.uk_ua_country_name);
                cityId = 6614;
                hotelsNum = 3161;
                location = LocationUtils.from(50.450963d, 30.522604d);
                break;
            case C1822R.styleable.MapAttrs_uiZoomControls /*11*/:
                city = res.getString(C1178R.string.kz_kz_city_name);
                country = res.getString(C1178R.string.kz_kz_country_name);
                cityId = 1990;
                hotelsNum = 544;
                location = LocationUtils.from(51.155881d, 71.431818d);
                break;
            case C1822R.styleable.MapAttrs_uiZoomGestures /*12*/:
                city = res.getString(C1178R.string.be_be_city_name);
                country = res.getString(C1178R.string.be_be_country_name);
                cityId = 6202;
                hotelsNum = 1349;
                location = LocationUtils.from(53.900052d, 27.564904d);
                break;
            case C1822R.styleable.MapAttrs_useViewLifecycle /*13*/:
                city = res.getString(C1178R.string.zh_hk_city_name);
                country = res.getString(C1178R.string.zh_hk_country_name);
                cityId = 4525;
                hotelsNum = 825;
                location = LocationUtils.from(22.283065d, 114.17319d);
                break;
            case C1822R.styleable.MapAttrs_zOrderOnTop /*14*/:
                city = res.getString(C1178R.string.zh_cn_city_name);
                country = res.getString(C1178R.string.zh_cn_country_name);
                cityId = 6679;
                hotelsNum = 4863;
                location = LocationUtils.from(39.916322d, 116.390278d);
                break;
            case C1822R.styleable.MapAttrs_uiMapToolbar /*15*/:
                city = res.getString(C1178R.string.zh_tw_city_name);
                country = res.getString(C1178R.string.zh_tw_country_name);
                cityId = 5810;
                hotelsNum = 2752;
                location = LocationUtils.from(25.04776d, 121.53185d);
                break;
            case C1822R.styleable.MapAttrs_ambientEnabled /*16*/:
                city = res.getString(C1178R.string.pt_br_city_name);
                country = res.getString(C1178R.string.pt_br_country_name);
                cityId = 6337;
                hotelsNum = 414;
                location = LocationUtils.from(-15.779722d, -47.929722d);
                break;
            case C1759R.styleable.Toolbar_titleMarginTop /*17*/:
                city = res.getString(C1178R.string.ko_kr_city_name);
                country = res.getString(C1178R.string.ko_kr_country_name);
                cityId = 5789;
                hotelsNum = 5707;
                location = LocationUtils.from(37.569042d, 127.006433d);
                break;
            case C1759R.styleable.Toolbar_titleMarginBottom /*18*/:
                city = res.getString(C1178R.string.ja_jp_city_name);
                country = res.getString(C1178R.string.ja_jp_country_name);
                cityId = 25666;
                hotelsNum = 6195;
                location = LocationUtils.from(139.728789d, 35.692707d);
                break;
            default:
                city = res.getString(C1178R.string.en_us_city_name);
                country = res.getString(C1178R.string.en_us_country_name);
                cityId = 20857;
                hotelsNum = 9227;
                location = LocationUtils.from(40.75603d, -73.986956d);
                break;
        }
        Calendar checkInCal = DateUtils.getTodayCalendar();
        Calendar checkOutCal = DateUtils.getTodayCalendar();
        checkOutCal.add(5, 1);
        return new DefaultSearchFormData(city, country, hotelsNum, cityId, DestinationType.CITY, location, checkInCal, checkOutCal, latinCityName);
    }
}
