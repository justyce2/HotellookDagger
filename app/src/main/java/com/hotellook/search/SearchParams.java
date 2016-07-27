package com.hotellook.search;

import android.location.Location;
import android.support.annotation.NonNull;
import com.hotellook.api.data.DestinationType;
import com.hotellook.utils.DateUtils;
import java.util.Calendar;
import java.util.List;
import me.zhanghai.android.materialprogressbar.BuildConfig;

public class SearchParams {
    private final int adultsCount;
    private final boolean allowEnGates;
    @NonNull
    private final Calendar checkIn;
    @NonNull
    private final Calendar checkOut;
    @NonNull
    private final String currency;
    @NonNull
    private final String destinationName;
    private final int destinationType;
    private final long hotelId;
    @NonNull
    private final List<Integer> kids;
    @NonNull
    private final String language;
    @NonNull
    private final String latinCityName;
    @NonNull
    private final Location location;
    private final int locationId;

    public static class Builder {
        private String EMPTY_DESTINATION;
        private int adults;
        private boolean allowEnGates;
        private Calendar checkIn;
        private Calendar checkOut;
        private String currency;
        private String destinationName;
        private int destinationType;
        private long hotelId;
        private List<Integer> kids;
        private String language;
        private String latinCityName;
        private Location location;
        private int locationId;

        public Builder() {
            this.EMPTY_DESTINATION = BuildConfig.FLAVOR;
        }

        public Builder locationId(int locationId) {
            this.locationId = locationId;
            return this;
        }

        public Builder hotelId(long hotelId) {
            this.hotelId = hotelId;
            return this;
        }

        public Builder checkIn(Calendar checkIn) {
            this.checkIn = checkIn;
            return this;
        }

        public Builder checkOut(Calendar checkOut) {
            this.checkOut = checkOut;
            return this;
        }

        public Builder adults(int adults) {
            this.adults = adults;
            return this;
        }

        public Builder kids(List<Integer> kids) {
            this.kids = kids;
            return this;
        }

        public Builder location(Location location) {
            this.location = location;
            return this;
        }

        public Builder destinationName(String destinationName) {
            if (destinationName == null) {
                this.destinationName = this.EMPTY_DESTINATION;
            } else {
                this.destinationName = destinationName;
            }
            return this;
        }

        public Builder latinCityName(String destinationLatinName) {
            if (destinationLatinName == null) {
                this.latinCityName = this.EMPTY_DESTINATION;
            } else {
                this.latinCityName = this.destinationName;
            }
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder allowEnGates(boolean allowEnGates) {
            this.allowEnGates = allowEnGates;
            return this;
        }

        public Builder destinationType(int destinationType) {
            this.destinationType = destinationType;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public SearchParams build() {
            if (this.checkIn != null && this.checkOut != null && this.kids != null && this.destinationName != null && this.location != null && this.currency != null && this.language != null) {
                return new SearchParams();
            }
            throw new NullPointerException("Search params fields cannot be null");
        }
    }

    private SearchParams(Builder builder) {
        this.locationId = builder.locationId;
        this.hotelId = builder.hotelId;
        this.checkIn = builder.checkIn;
        this.checkOut = builder.checkOut;
        this.adultsCount = builder.adults;
        this.kids = builder.kids;
        this.location = builder.location;
        this.destinationName = builder.destinationName;
        this.latinCityName = builder.latinCityName;
        this.currency = builder.currency;
        this.destinationType = builder.destinationType;
        this.language = builder.language;
        this.allowEnGates = builder.allowEnGates;
    }

    @NonNull
    public String currency() {
        return this.currency;
    }

    public int locationId() {
        return this.locationId;
    }

    @NonNull
    public Calendar checkIn() {
        return this.checkIn;
    }

    @NonNull
    public Calendar checkOut() {
        return this.checkOut;
    }

    public int adults() {
        return this.adultsCount;
    }

    public int nightsCount() {
        return DateUtils.daysBetween(this.checkIn, this.checkOut);
    }

    @NonNull
    public Location location() {
        return this.location;
    }

    public boolean isHotelSearch() {
        return this.hotelId != -1;
    }

    public long hotelId() {
        return this.hotelId;
    }

    @NonNull
    public String destinationName() {
        return this.destinationName;
    }

    public int kidsCount() {
        return this.kids.size();
    }

    @NonNull
    public List<Integer> kids() {
        return this.kids;
    }

    public boolean isDestinationTypeUserLocation() {
        return this.destinationType == DestinationType.USER_LOCATION;
    }

    public boolean isDestinationTypeMapPoint() {
        return this.destinationType == DestinationType.MAP_POINT;
    }

    @NonNull
    public String language() {
        return this.language;
    }

    public boolean areEnGatesAllowed() {
        return this.allowEnGates;
    }

    public boolean isDestinationTypeCity() {
        return this.destinationType == DestinationType.CITY;
    }

    public int type() {
        return this.destinationType;
    }

    public boolean hasHotelId() {
        return this.hotelId != -1;
    }

    public Builder toBuilder() {
        return new Builder().locationId(this.locationId).hotelId(this.hotelId).checkIn(this.checkIn).checkOut(this.checkOut).adults(this.adultsCount).kids(this.kids).location(this.location).destinationName(this.destinationName).latinCityName(this.latinCityName).currency(this.currency).destinationType(this.destinationType).language(this.language).allowEnGates(this.allowEnGates);
    }

    public boolean isDestinationTypeNearbyCities() {
        return this.destinationType == DestinationType.NEARBY_CITIES;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SearchParams that = (SearchParams) o;
        if (this.locationId == that.locationId && this.hotelId == that.hotelId && this.adultsCount == that.adultsCount && this.destinationType == that.destinationType && this.allowEnGates == that.allowEnGates && this.checkIn.equals(that.checkIn) && this.checkOut.equals(that.checkOut) && this.kids.equals(that.kids) && Float.compare((float) this.location.getLatitude(), (float) that.location.getLatitude()) == 0 && Float.compare((float) this.location.getLongitude(), (float) that.location.getLongitude()) == 0 && this.currency.equals(that.currency)) {
            return this.language.equals(that.language);
        }
        return false;
    }

    public int hashCode() {
        return (((((((((((((((((((((this.locationId * 31) + ((int) (this.hotelId ^ (this.hotelId >>> 32)))) * 31) + this.adultsCount) * 31) + this.destinationType) * 31) + (this.allowEnGates ? 1 : 0)) * 31) + this.checkIn.hashCode()) * 31) + this.checkOut.hashCode()) * 31) + this.kids.hashCode()) * 31) + Double.valueOf(this.location.getLatitude()).hashCode()) * 31) + Double.valueOf(this.location.getLongitude()).hashCode()) * 31) + this.currency.hashCode()) * 31) + this.language.hashCode();
    }

    public String toString() {
        return "SearchParams{locationId=" + this.locationId + ", hotelId=" + this.hotelId + ", adultsCount=" + this.adultsCount + ", destinationType=" + this.destinationType + ", allowEnGates=" + this.allowEnGates + ", checkIn=" + this.checkIn + ", checkOut=" + this.checkOut + ", kids=" + this.kids + ", location=" + this.location + ", destinationName='" + this.destinationName + '\'' + ", latinCityName='" + this.latinCityName + '\'' + ", currency='" + this.currency + '\'' + ", language='" + this.language + '\'' + '}';
    }

    @NonNull
    public String latinCityName() {
        return this.latinCityName;
    }
}
