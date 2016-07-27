package com.hotellook.statistics;

import com.hotellook.statistics.mixpanel.LaunchSource;

public interface Constants {

    public interface Actions {
        public static final String HOTEL = "hotel";
        public static final String PURCHASE = "purchase";
        public static final String SEARCH = "search";
    }

    public interface AppsPackages {
        public static final String AGODA = "com.agoda.mobile.consumer";
        public static final String AIRBNB = "com.airbnb.android";
        public static final String AVIASALES = "ru.aviasales";
        public static final String BOOKING = "com.booking";
        public static final String EXPEDIA = "com.expedia.bookings";
        public static final String HOTEL_TONIGHT = "com.hoteltonight.android.prod";
        public static final String JETRADAR = "com.jetradar";
        public static final String KAYAK = "com.kayak.android";
        public static final String OKTOGO = "ru.oktogo";
        public static final String OSTROVOK = "ru.ostrovok.android";
        public static final String ROOMGURU = "com.hotelscombined.mobile";
        public static final String SKYSKANNER_HOTELS = "net.skyscanner.hotels.main";
        public static final String TRIVAGO = "com.trivago";
        public static final String WEGO = "com.wego.android";
    }

    public enum Bool {
        YES,
        NO,
        NO_INFO
    }

    public interface FlurryEvents {
        public static final String CLICK = "Click";
        public static final String LAUNCH = "Launch";
        public static final String PREDICTION = "Prediction";
        public static final String RESULTS = "Results";
        public static final String SEARCH = "Search";
        public static final String VIEW = "View";
    }

    public interface MixPanelEvents {
        public static final String COMPETITORS = "Competitors";
        public static final String CURRENCY_CHANGED = "Currency Changed";
        public static final String EASTER_EGG = "Easter Egg";
        public static final String EMAIL_CONTACT = "Email Contact";
        public static final String ERROR_CONNECTION = "Error Connection";
        public static final String ERROR_CONTENT = "Error Content";
        public static final String ERROR_MIXPANEL = "Error MixPanel";
        public static final String ERROR_PARCING = "Error Parcing";
        public static final String ERROR_SERVER = "Error Server";
        public static final String ERROR_UNEXPECTED = "Error Unexpected";
        public static final String FAVORITES_OPENED = "Favorites Opened";
        public static final String FILTER_DISTANCE_CLOSED = "Filter Distance Closed";
        public static final String FILTER_ENGLISH_CLOSED = "Filter English Closed";
        public static final String FILTER_REFERRER = "Filter Referrer";
        public static final String FIRST_LAUNCH = "First Launch";
        public static final String FRAME_CLOSED = "Frame Closed";
        public static final String FRAME_OPENED = "Frame Opened";
        public static final String HOTEL_BOOKED = "Hotel Booked";
        public static final String HOTEL_CANCELLED = "Hotel Cancelled";
        public static final String HOTEL_CLOSED = "Hotel Closed";
        public static final String HOTEL_LIKED = "Hotel Liked";
        public static final String HOTEL_LIKED_UNDO = "Hotel Liked Undo";
        public static final String HOTEL_OPENED = "Hotel Opened";
        public static final String HOTEL_PAID = "Hotel Paid";
        public static final String HOTEL_PREDICTED = "Hotel Predicted";
        public static final String HOTEL_SHARED = "Hotel Shared";
        public static final String INFORMATION_OPENED = "Information Opened";
        public static final String INSTALL = "Install";
        public static final String LAUNCH = "Launch";
        public static final String LICENCE_OPENED = "Licence Opened";
        public static final String LOCATION_REQUESTED = "Location Requested";
        public static final String RATE_US = "Rate Us";
        public static final String RATE_US_CANCELLED = "Rate Us Cancelled";
        public static final String RATE_US_COMPLETED = "Rate Us Completed";
        public static final String RESULTS_FILTERED = "Results Filtered";
        public static final String RESULTS_LIST = "Results List";
        public static final String RESULTS_SORTED = "Results Sorted";
        public static final String SEARCH_ACTION = "Search Action";
        public static final String SEARCH_CANCELED = "Search Can\u0441elled";
        public static final String SEARCH_FORM = "Search Form";
        public static final String SOCIAL_NETWORKS_MENU = "Social Networks Menu";
        public static final String SOCIAL_NETWORKS_OUT = "Social Networks Out";
        public static final String TUTORIAL = "Tutorial";
    }

    public interface MixPanelParams {
        public static final String ACTION_BUTTON = "action-button";
        public static final String AF_INSTALL_TIME = "install_time";
        public static final String AF_STATUS = "af_status";
        public static final String AF_SUB1 = "af_sub1";
        public static final String AF_SUB5 = "af_sub5";
        public static final String AGENCY = "agency";
        public static final String ALL = "all";
        public static final String ALLOW_EN_GATES = "English OTA";
        public static final String AUTO = "auto";
        public static final String BEACH = "beach";
        public static final String BEST = "best";
        public static final String BOOKED_COUNT = "Booked Count";
        public static final String BOOKED_HOTEL_I_D = "Booked Hotel ID";
        public static final String BOOKED_OFFER_I_D = "Booked Offer ID";
        public static final String BUILD_TYPE = "Build Type";
        public static final String CANCELLED_HOTEL_I_D = "Cancelled Hotel ID";
        public static final String CANCELLED_OFFER_I_D = "Cancelled Offer ID";
        public static final String CENTRE = "centre";
        public static final String CITY = "city";
        public static final String COMPAIGN = "campaign";
        public static final String CONNECTION_TYPE = "Connection Type";
        public static final String CURRENCY = "Currency";
        public static final String DAYS_30 = "30-days";
        public static final String DEEPLINK = "deeplink";
        public static final String DEVICE_TYPE = "Device Type";
        public static final String DISCOUNT = "discount";
        public static final String DISTANCE = "distance";
        public static final String DISTANCE_TO_CENTRE = "distance-to-centre";
        public static final String DISTANCE_TO_LOCATION = "distance-to-location";
        public static final String DISTANCE_TO_ME = "distance-to-me";
        public static final String DISTANCE_TO_POI = "distance-to-poi";
        public static final String ERROR_CODE = "Error Code";
        public static final String ERROR_REFERRER = "Error Referrer";
        public static final String ERROR_TYPE = "Error Type";
        public static final String EVENT = "Event";
        public static final String FACEBOOK = "facebook";
        public static final String FAVORITES = "Favorites";
        public static final String FILTER_BANK_CARD = "Filter Bank Card";
        public static final String FILTER_BREAKFAST = "Filter Breakfast";
        public static final String FILTER_CANCELLATION = "Filter Cancellation";
        public static final String FILTER_COUNT = "Filter Count";
        public static final String FILTER_DISTANCE = "Filter Distance";
        public static final String FILTER_DISTRICT = "Filter District";
        public static final String FILTER_HOSTEL = "Filter Hostel";
        public static final String FILTER_HOTELS_BEFORE = "Filter Hotels Before";
        public static final String FILTER_HOTEL_AMENITIES = "Filter Hotel Amenities";
        public static final String FILTER_MISC = "Filter Misc";
        public static final String FILTER_NO_ROOMS = "Filter NoRooms";
        public static final String FILTER_OTA = "Filter OTA";
        public static final String FILTER_PAY_LATER = "Pay Later";
        public static final String FILTER_PAY_NOW = "Pay Now";
        public static final String FILTER_PRICE = "Filter Price";
        public static final String FILTER_PRICE_MAX = "Filter Price Max";
        public static final String FILTER_PRICE_MIN = "Filter Price Min";
        public static final String FILTER_RATING = "Filter Rating";
        public static final String FILTER_REFERRER = "Filter Referrer";
        public static final String FILTER_ROOMS_AMENITIES = "Filter Rooms Amenities";
        public static final String FILTER_SMOKING = "Filter Smoking";
        public static final String FILTER_STARS = "Filter Stars";
        public static final String FILTER_TYPE = "Filter Type";
        public static final String FILTER_VIEW = "Filter View";
        public static final String FIRST_LAUNCH_DATE = "First Launch Date";
        public static final String FORM = "form";
        public static final String FORM_REFERRER = "Form Referrer";
        public static final String FRAME_BADGE = "Frame Badge";
        public static final String FRAME_CITY = "Frame City";
        public static final String FRAME_COUNT = "Frame Count";
        public static final String FRAME_DAY_PRICE = "Frame Day Price";
        public static final String FRAME_DEPTH = "Frame Depth";
        public static final String FRAME_DISCOUNT = "Frame Discount";
        public static final String FRAME_DURATION = "Frame Duration";
        public static final String FRAME_FAVOURITE = "Frame Favourite";
        public static final String FRAME_FILTERED = "Frame Filtered";
        public static final String FRAME_GATE_ID = "Frame Gate ID";
        public static final String FRAME_HOTEL_ID = "Frame Hotel ID";
        public static final String FRAME_HOTEL_REFERRER = "Frame Hotel Referrer";
        public static final String FRAME_HOTEL_TYPE = "Frame Hotel Type";
        public static final String FRAME_LAUNCH_REFERRER = "Frame Launch Referrer";
        public static final String FRAME_LENGTH = "Frame Length";
        public static final String FRAME_MAP_VIEWED = "Frame Map Viewed";
        public static final String FRAME_OFFER_HIGHLIGHTED = "Frame Offer Highlighted";
        public static final String FRAME_OFFER_PRICE = "Frame Offer Price";
        public static final String FRAME_PARTNER = "Frame Partner";
        public static final String FRAME_PHOTOS = "Frame Photos";
        public static final String FRAME_PHOTOS_BEFORE = "Frame Photos Before";
        public static final String FRAME_PRIVATE_FARE = "Frame Private Fare";
        public static final String FRAME_RATES_VIEWED = "Frame Rates Viewed";
        public static final String FRAME_RATING = "Frame Rating";
        public static final String FRAME_RATING_VIEWED = "Frame Rating Viewed";
        public static final String FRAME_RECOMMEDED = "Frame Recommended";
        public static final String FRAME_RECOMMENDED = "Frame Recommended";
        public static final String FRAME_REFERRER = "Frame Referrer";
        public static final String FRAME_RENTALS = "Frame Rentals";
        public static final String FRAME_REVIEWS_VIEWED = "Frame Reviews Viewed";
        public static final String FRAME_ROOM_ID = "Frame Room ID";
        public static final String FRAME_SEARCH_REFERRER = "Frame Search Referrer";
        public static final String FRAME_SORTED = "Frame Sorted";
        public static final String FRAME_SORTED_TYPE = "Frame Sorted Type";
        public static final String FRAME_STARS = "Frame Stars";
        public static final String FRAME_TAB_REFRERRER = "Frame Tab Referrer";
        public static final String GENERAL = "general";
        public static final String GOOGLE_PLAY_SERVICES = "Google Play Services";
        public static final String GOOGLE_PLUS = "googleplay";
        public static final String HAS_AGODA = "Has Agoda";
        public static final String HAS_AIR_BNB = "Has AirBnB";
        public static final String HAS_AVIASALES = "Has Aviasales";
        public static final String HAS_BOOKING = "Has Booking";
        public static final String HAS_EXPEDIA = "Has Expedia";
        public static final String HAS_HOTEL_TONIGHT = "Has HotelTonight";
        public static final String HAS_JET_RADAR = "Has JetRadar";
        public static final String HAS_KAYAK = "Has Kayak";
        public static final String HAS_OKTOGO = "Has Oktogo";
        public static final String HAS_OSTROVOK = "Has Ostrovok";
        public static final String HAS_RESULTS_DISCOUNT = "Has Results Discount";
        public static final String HAS_ROOMGURU = "Has Roomguru";
        public static final String HAS_SKY_SCANNER_HOTELS = "Has SkyScanner Hotels";
        public static final String HAS_TRIVAGO = "Has Trivago";
        public static final String HAS_WE_GO = "Has WeGo";
        public static final String HLS = "HLS";
        public static final String HOTEL = "hotel";
        public static final String HOTEL_BADGE = "Hotel Badge";
        public static final String HOTEL_BADGE_LIST = "Hotel Badge List";
        public static final String HOTEL_CITY = "Hotel City";
        public static final String HOTEL_CITY_NAME = "Hotel City Name";
        public static final String HOTEL_COUNT = "Hotel Count";
        public static final String HOTEL_COUNTRY = "Hotel Country";
        public static final String HOTEL_COUNTRY_NAME = "Hotel Country Name";
        public static final String HOTEL_DAY_PRICE = "Hotel Day Price";
        public static final String HOTEL_DEPTH = "Hotel Depth";
        public static final String HOTEL_DISCOUNT = "Hotel Discount";
        public static final String HOTEL_FAVORITE = "Hotel Favorite";
        public static final String HOTEL_FRAMED = "Hotel Framed";
        public static final String HOTEL_GALLERY_VIEWED = "Hotel Gallery Viewed";
        public static final String HOTEL_HIGHLIGHTED = "Hotel Highlighted";
        public static final String HOTEL_ID = "Hotel ID";
        public static final String HOTEL_LIKED_REFERRER = "Hotel Liked Referrer";
        public static final String HOTEL_MAP_VIEWED = "Hotel Map Viewed";
        public static final String HOTEL_NAME = "Hotel Name";
        public static final String HOTEL_NIGHTS = "Hotel Length";
        public static final String HOTEL_NON_SCROLLED = "Hotel Non-Scrolled";
        public static final String HOTEL_OFFERS = "Hotel Offers";
        public static final String HOTEL_OFFER_PRICE = "Hotel Offer Price";
        public static final String HOTEL_PARTNERS = "Hotel Partners";
        public static final String HOTEL_PHOTOS = "Hotel Photos";
        public static final String HOTEL_PHOTOS_BEFORE = "Hotel Photos Before";
        public static final String HOTEL_POSITION = "Hotel Position";
        public static final String HOTEL_RATES_SHOWN = "Hotel Rates Shown";
        public static final String HOTEL_RATES_VIEWED = "Hotel Rates Viewed";
        public static final String HOTEL_RATING = "Hotel Rating";
        public static final String HOTEL_RATING_VIEWED = "Hotel Rating Viewed";
        public static final String HOTEL_REFERRER = "Hotel Referrer";
        public static final String HOTEL_RENTALS = "Hotel Rentals";
        public static final String HOTEL_REVIEWS_VIEWED = "Hotel Reviews Viewed";
        public static final String HOTEL_SHARED = "Hotel Shared";
        public static final String HOTEL_STARS = "Hotel Stars";
        public static final String HOTEL_TYPE = "Hotel Type";
        public static final String ID = "ID";
        public static final String INFO = "info";
        public static final String INSTAGRAM = "instagram";
        public static final String INSTALL_AGENCY = "Install Agency";
        public static final String INSTALL_COMPAIGN = "Install Campaign";
        public static final String INSTALL_MARKER = "Install Marker";
        public static final String INSTALL_MEDIA_SOURCE = "Install Media Source";
        public static final String INSTALL_TIME = "Install Time";
        public static final String INSTALL_TYPE = "Install Type";
        public static final String LANDSCAPE = "landscape";
        public static final String LAUNCH = "launch";
        public static final String LAUNCH_DATE = "Launch Date";
        public static final String LAUNCH_REFERRER = "Launch Referrer";
        public static final String LIST = "list";
        public static final String LOCALE = "Locale";
        public static final String LOCATION = "location";
        public static final String LOCATION_REQUESTED = "Location Requested";
        public static final String LOCATION_SERVICES = "Location Services";
        public static final String MANUAL = "manual";
        public static final String MAP = "map";
        public static final String MARKER = "Marker";
        public static final String MARKS = "marks";
        public static final String ME = "me";
        public static final String MEDIA_SOURCE = "media_source";
        public static final String MENU = "menu";
        public static final String MESSAGE = "Message";
        public static final String MINE = "mine";
        public static final String MIXED = "mixed";
        public static final String NEARBY_CITIES = "results";
        public static final String NONE = "none";
        public static final String NO_CITIES_OR_HOTELS = "no-cities-or-hotels";
        public static final String NO_RESULTS = "no-results";
        public static final String ORIENTATION = "Orientation";
        public static final String PAID_COUNT = "Paid Count";
        public static final String PAID_HOTEL_I_D = "Paid Hotel ID";
        public static final String PAID_OFFER_I_D = "Paid Offer ID";
        public static final String PHONE = "phone";
        public static final String POI = "POI";
        public static final String POPULARITY = "popularity";
        public static final String PORTRAIT = "portrait";
        public static final String PREDICTED_COUNT = "Predicted Count";
        public static final String PREDICTED_DAY_PRICE = "Predicted Day Price";
        public static final String PREDICTED_DURATION = "Predicted Duration";
        public static final String PREDICTED_GATE_ID = "Predicted Gate ID";
        public static final String PREDICTED_HOTEL_ID = "Predicted Hotel ID";
        public static final String PREDICTED_LENGTH = "Predicted Length";
        public static final String PREDICTED_OFFER_PRICE = "Predicted Offer Price";
        public static final String PREDICTED_ROOM_ID = "Predicted Room ID";
        public static final String PRICE = "price";
        public static final String QUICK = "quick";
        public static final String RATED_STARS = "Rated Stars";
        public static final String RATES = "rates";
        public static final String RATE_US_REFERRER = "Rate Us Referrer";
        public static final String RATING = "rating";
        public static final String RENTALS = "rentals";
        public static final String RESULTS = "results";
        public static final String RESULTS_DISCOUNT = "Results Discount";
        public static final String RESULTS_DURATION = "Results Duration";
        public static final String RESULTS_LENGTH = "Results Count";
        public static final String RESULTS_LOWEST_PRICE = "Results Lowest Price";
        public static final String RESULTS_OUT_OF_DATE = "out-of-date-results";
        public static final String RESULTS_TAB = "Results Tab";
        public static final String REVIEWS = "reviews";
        public static final String SEARCH_ADULTS = "Search Adults";
        public static final String SEARCH_CANCEL_DURATION = "Search Cancel Duration";
        public static final String SEARCH_CANCEL_REFERRER = "Search Can\u0441el Referrer";
        public static final String SEARCH_CITY = "Search City";
        public static final String SEARCH_CITY_NAME = "Search City Name";
        public static final String SEARCH_COUNT = "Search Count";
        public static final String SEARCH_DEPTH = "Search Depth";
        public static final String SEARCH_HOTEL = "Search Hotel";
        public static final String SEARCH_KIDS = "Search Kids";
        public static final String SEARCH_LENGTH = "Search Length";
        public static final String SEARCH_PHOTO = "Search Photo";
        public static final String SEARCH_REFERRER = "Search Referrer";
        public static final String SEARCH_REPEAT = "Search Repeat";
        public static final String SEARCH_TYPE = "Search Type";
        public static final String SEARCH_TYPE_LOCATION = "location";
        public static final String SEARCH_TYPE_NEARBY_CITIES = "nearbycity";
        public static final String f724SEARCHURRENCY = "Search \u0421urrency";
        public static final String SERVER_HEADERS = "Server Headers";
        public static final String SHARES = "Shares";
        public static final String SKI = "ski";
        public static final String SOCIAL_NETWORK_NAME = "Social Network Name";
        public static final String SORT_HOTELS_BEFORE = "Sort Hotels Before";
        public static final String SORT_TYPE = "Sort Type";
        public static final String SUPER_FAVORITES = "Favorites";
        public static final String SYSTEM_COUNTRY = "System Country";
        public static final String SYSTEM_LANGUAGE = "System Language";
        public static final String TABLET = "tablet";
        public static final String TOKEN = "Token";
        public static final String TONIGHT = "tonight";
        public static final String TOUGH_FILTERS = "tough-filters";
        public static final String TUTORIAL_DURATION = "Tutorial Duration";
        public static final String TUTORIAL_SCREENS = "Tutorial Screens";
        public static final String TWITTER = "twitter";
        public static final String URL = "url";
        public static final String UTM = "UTM";
        public static final String VKONTAKTE = "vkontakte";
        public static final String f725URRENCY = "\u0421urrency";
    }

    public enum Source {
        SEARCH_FORM(MixPanelParams.FORM),
        MY_HOTELS(MixPanelParams.SUPER_FAVORITES),
        URL_SCHEME(LaunchSource.DEEPLINK),
        NEARBY_CITIES(MixPanelParams.RESULTS);
        
        public final String mixpanelLiteral;

        private Source(String mixpanelLiteral) {
            this.mixpanelLiteral = mixpanelLiteral;
        }
    }

    public enum Tonight {
        YES_AUTO,
        YES_MANUAL,
        NO
    }
}
