package com.hotellook.db;

import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.db.data.PoiHistoryItem;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import timber.log.Timber;

public class PoiHistoryDBHelper {
    private final DatabaseHelper databaseHelper;

    /* renamed from: com.hotellook.db.PoiHistoryDBHelper.1 */
    class C11971 implements OnSubscribe<List<PoiHistoryItem>> {
        final /* synthetic */ List val$cityIds;
        final /* synthetic */ long val$count;

        C11971(long j, List list) {
            this.val$count = j;
            this.val$cityIds = list;
        }

        public void call(Subscriber<? super List<PoiHistoryItem>> subscriber) {
            try {
                List<PoiHistoryItem> items = PoiHistoryDBHelper.this.databaseHelper.getPoiHistoryDao().queryBuilder().limit(Long.valueOf(this.val$count)).orderBy(PoiHistoryItem.VERSION, false).where().in(PoiHistoryItem.CITY_ID, this.val$cityIds).query();
                if (items != null) {
                    subscriber.onNext(items);
                }
                subscriber.onCompleted();
            } catch (SQLException e) {
                subscriber.onError(e);
            }
        }
    }

    public PoiHistoryDBHelper(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public Observable<List<PoiHistoryItem>> getItemsForCities(List<CityInfo> cityInfoList, long count) {
        List<Integer> cityIds = new ArrayList(cityInfoList.size());
        for (CityInfo cityInfo : cityInfoList) {
            cityIds.add(Integer.valueOf(cityInfo.getId()));
        }
        return Observable.create(new C11971(count, cityIds));
    }

    public void save(PoiHistoryItem item) {
        try {
            this.databaseHelper.getPoiHistoryDao().createOrUpdate(item);
        } catch (SQLException e) {
            Timber.m757w(e, "Error on save %s", item.toString());
        }
    }

    public void update(PoiHistoryItem item) {
        try {
            this.databaseHelper.getPoiHistoryDao().update((Object) item);
        } catch (SQLException e) {
            Timber.m757w(e, "Error on update %s", item.toString());
        }
    }
}
