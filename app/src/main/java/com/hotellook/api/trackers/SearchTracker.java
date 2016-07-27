package com.hotellook.api.trackers;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hotellook.api.data.SearchTrackData;
import com.hotellook.events.PurchaseEvent;
import com.hotellook.events.SearchCanceledEvent;
import com.hotellook.events.SearchFailEvent;
import com.hotellook.events.SearchFinishEvent;
import com.hotellook.events.SearchStartEvent;
import com.hotellook.search.SearchParams;
import com.hotellook.utils.ExtrusiveFixedList;
import com.hotellook.utils.threads.TaskExecutor;
import com.squareup.otto.Subscribe;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import me.zhanghai.android.materialprogressbar.BuildConfig;

public class SearchTracker {
    private static final int MAX_TRACKED_SEARCHES = 5;
    private static final String PREF_FILE = "PREF_FILE_SEARCHES";
    private static final String PREF_SEARCHES_JSON = "PREF_SEARCHES_JSON";
    private SharedPreferences prefs;
    private final TaskExecutor searchTrackerExecutor;
    private ExtrusiveFixedList<SearchTrackData> searches;
    private Map<SearchParams, SearchTrackData> searchesMap;
    private Type searchesType;

    /* renamed from: com.hotellook.api.trackers.SearchTracker.1 */
    class C11831 extends TypeToken<ExtrusiveFixedList<SearchTrackData>> {
        C11831() {
        }
    }

    /* renamed from: com.hotellook.api.trackers.SearchTracker.2 */
    class C11842 implements Runnable {
        final /* synthetic */ Context val$context;

        C11842(Context context) {
            this.val$context = context;
        }

        public void run() {
            SearchTracker.this.initPrefs(this.val$context);
            SearchTracker.this.readSavedSearchesFromPrefs();
        }
    }

    /* renamed from: com.hotellook.api.trackers.SearchTracker.3 */
    class C11853 implements Runnable {
        final /* synthetic */ SearchStartEvent val$event;

        C11853(SearchStartEvent searchStartEvent) {
            this.val$event = searchStartEvent;
        }

        public void run() {
            SearchTracker.this.addNewSearch(this.val$event.searchParams);
            SearchTracker.this.saveData();
        }
    }

    /* renamed from: com.hotellook.api.trackers.SearchTracker.4 */
    class C11864 implements Runnable {
        final /* synthetic */ SearchFinishEvent val$event;

        C11864(SearchFinishEvent searchFinishEvent) {
            this.val$event = searchFinishEvent;
        }

        public void run() {
            if (SearchTracker.this.searchesMap.containsKey(this.val$event.searchParams())) {
                ((SearchTrackData) SearchTracker.this.searchesMap.get(this.val$event.searchParams())).onSearchSuccess();
                SearchTracker.this.saveData();
            }
        }
    }

    /* renamed from: com.hotellook.api.trackers.SearchTracker.5 */
    class C11875 implements Runnable {
        final /* synthetic */ SearchCanceledEvent val$event;

        C11875(SearchCanceledEvent searchCanceledEvent) {
            this.val$event = searchCanceledEvent;
        }

        public void run() {
            if (SearchTracker.this.searchesMap.containsKey(this.val$event.searchParams)) {
                ((SearchTrackData) SearchTracker.this.searchesMap.get(this.val$event.searchParams)).onSearchCanceled();
                SearchTracker.this.saveData();
            }
        }
    }

    /* renamed from: com.hotellook.api.trackers.SearchTracker.6 */
    class C11886 implements Runnable {
        final /* synthetic */ SearchFailEvent val$event;

        C11886(SearchFailEvent searchFailEvent) {
            this.val$event = searchFailEvent;
        }

        public void run() {
            if (SearchTracker.this.searchesMap.containsKey(this.val$event.searchParams)) {
                ((SearchTrackData) SearchTracker.this.searchesMap.get(this.val$event.searchParams)).onSearchFailed();
                SearchTracker.this.saveData();
            }
        }
    }

    /* renamed from: com.hotellook.api.trackers.SearchTracker.7 */
    class C11897 implements Runnable {
        final /* synthetic */ PurchaseEvent val$event;

        C11897(PurchaseEvent purchaseEvent) {
            this.val$event = purchaseEvent;
        }

        public void run() {
            if (SearchTracker.this.searchesMap.containsKey(this.val$event.searchParams)) {
                ((SearchTrackData) SearchTracker.this.searchesMap.get(this.val$event.searchParams)).addBookClick(this.val$event.roomData.getPrice(), this.val$event.searchParams.currency());
                SearchTracker.this.saveData();
            }
        }
    }

    public SearchTracker(Context context) {
        this.searchTrackerExecutor = TaskExecutor.getTaskExecutor("SEARCH_TRACKER_THREAD");
        this.searchesMap = new HashMap();
        this.searchesType = new C11831().getType();
        this.searchTrackerExecutor.post(new C11842(context));
    }

    private void readSavedSearchesFromPrefs() {
        String json = this.prefs.getString(PREF_SEARCHES_JSON, null);
        if (json != null) {
            try {
                this.searches = (ExtrusiveFixedList) new Gson().fromJson(json, this.searchesType);
            } catch (Exception e) {
                this.searches = new ExtrusiveFixedList(MAX_TRACKED_SEARCHES);
                this.prefs.edit().remove(PREF_SEARCHES_JSON).apply();
            }
        } else {
            this.searches = new ExtrusiveFixedList(MAX_TRACKED_SEARCHES);
        }
        Iterator it = this.searches.iterator();
        while (it.hasNext()) {
            SearchTrackData searchTrackData = (SearchTrackData) it.next();
            this.searchesMap.put(searchTrackData.getSearchParams(), searchTrackData);
        }
    }

    private void initPrefs(Context context) {
        this.prefs = context.getSharedPreferences(PREF_FILE, 0);
    }

    @Subscribe
    public void onSearchStarted(SearchStartEvent event) {
        this.searchTrackerExecutor.post(new C11853(event));
    }

    @Subscribe
    public void onSearchSucceed(SearchFinishEvent event) {
        this.searchTrackerExecutor.post(new C11864(event));
    }

    @Subscribe
    public void onSearchCanceled(SearchCanceledEvent event) {
        this.searchTrackerExecutor.post(new C11875(event));
    }

    @Subscribe
    public void onSearchFailed(SearchFailEvent event) {
        this.searchTrackerExecutor.post(new C11886(event));
    }

    @Subscribe
    public void onBook(PurchaseEvent event) {
        this.searchTrackerExecutor.post(new C11897(event));
    }

    private void addNewSearch(SearchParams searchParams) {
        SearchTrackData trackData = new SearchTrackData(searchParams);
        this.searches.put(trackData);
        this.searchesMap.put(searchParams, trackData);
        saveData();
    }

    private void saveData() {
        this.prefs.edit().putString(PREF_SEARCHES_JSON, new Gson().toJson(this.searches, this.searchesType)).apply();
    }

    public String getSearchesJson() {
        if (this.prefs != null) {
            return this.prefs.getString(PREF_SEARCHES_JSON, BuildConfig.FLAVOR);
        }
        return BuildConfig.FLAVOR;
    }
}
