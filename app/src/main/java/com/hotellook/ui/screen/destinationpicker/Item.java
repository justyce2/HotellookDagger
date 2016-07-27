package com.hotellook.ui.screen.destinationpicker;

import android.support.annotation.StringRes;
import com.hotellook.C1178R;
import com.hotellook.api.data.DestinationData;

public class Item {

    public static class DestinationItem extends Item {
        private final DestinationData destinationData;

        public DestinationItem(DestinationData destinationData) {
            this.destinationData = destinationData;
        }

        public DestinationData destinationData() {
            return this.destinationData;
        }
    }

    public static class DividerItem extends Item {
    }

    public static abstract class GroupTitleItem extends Item {
        @StringRes
        public abstract int getTitleResId();
    }

    public static class GroupTitleCitiesItem extends GroupTitleItem {
        public int getTitleResId() {
            return C1178R.string.dp_group_title_cities;
        }
    }

    public static class GroupTitleHistoryItem extends GroupTitleItem {
        public int getTitleResId() {
            return C1178R.string.dp_group_title_history;
        }
    }

    public static class GroupTitleHotelsItem extends GroupTitleItem {
        public int getTitleResId() {
            return C1178R.string.dp_group_title_hotels;
        }
    }

    public static class GroupTitleNearestItem extends GroupTitleItem {
        public int getTitleResId() {
            return C1178R.string.dp_group_title_local;
        }
    }

    public static class MapPointItem extends Item {
    }

    public static class MyLocationItem extends Item {
    }
}
