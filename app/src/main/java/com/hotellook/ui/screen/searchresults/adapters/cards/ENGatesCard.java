package com.hotellook.ui.screen.searchresults.adapters.cards;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.ENGatesCardClosedEvent;
import com.hotellook.events.RemoveCardEvent;
import com.hotellook.events.ShowEnGatesQuestionDialogEvent;
import com.hotellook.filters.Filters;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.search.SearchData;
import com.hotellook.utils.CommonPreferences;

public class ENGatesCard implements Card {
    private int position;
    private final CommonPreferences preferences;

    /* renamed from: com.hotellook.ui.screen.searchresults.adapters.cards.ENGatesCard.1 */
    class C13751 extends MonkeySafeClickListener {
        C13751() {
        }

        public void onSafeClick(View v) {
            ENGatesCard.this.preferences.setEnGatesQuestionNeverShowInResults();
            HotellookApplication.eventBus().post(new ENGatesCardClosedEvent());
            HotellookApplication.eventBus().post(new RemoveCardEvent(ENGatesCard.this.position));
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.adapters.cards.ENGatesCard.2 */
    class C13762 extends MonkeySafeClickListener {
        C13762() {
        }

        public void onSafeClick(View v) {
            ENGatesCard.this.preferences.setEnGatesAllowed(true);
            HotellookApplication.eventBus().post(new ShowEnGatesQuestionDialogEvent());
        }
    }

    public ENGatesCard() {
        this.preferences = HotellookApplication.getApp().getComponent().getCommonPreferences();
    }

    public int position() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int type() {
        return 4;
    }

    public ViewHolder createViewHolder(ViewGroup parent) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.en_gates_card, parent, false);
        layout.findViewById(C1178R.id.close).setOnClickListener(new C13751());
        layout.findViewById(C1178R.id.apply_btn).setOnClickListener(new C13762());
        return new JustViewHolder(layout);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    public void notifyDataChanged() {
    }

    public void init(SearchData search, Filters filters, PersistentFilters persistentFilters) {
    }
}
