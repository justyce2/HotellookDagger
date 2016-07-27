package com.hotellook.ui.screen.hotel.prices;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hotellook.C1178R;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.search.SearchParams;
import java.util.List;
import java.util.Map;

public class RoomOffersView extends FrameLayout {
    private static final int DEFAULT_ROWS_WHEN_COLLAPSED = 3;
    private Offer bestOffer;
    @Nullable
    private DiscountsByRooms discounts;
    @BindView
    TextView expandBtn;
    @BindView
    FrameLayout expandBtnContainer;
    private RoomItemExpandCollapseListener expandCollapseListener;
    @Nullable
    private HighlightsByRooms highlights;
    @BindView
    LinearLayout offersContainer;
    private int offersDividerHeight;
    @BindView
    TextView roomName;
    private List<Offer> roomResults;
    private Map<Integer, String> roomTypes;
    private int rowsWhenCollapsed;
    private SearchParams searchParams;
    private boolean showExpanded;

    /* renamed from: com.hotellook.ui.screen.hotel.prices.RoomOffersView.1 */
    class C13321 extends MonkeySafeClickListener {
        C13321() {
        }

        public void onSafeClick(View v) {
            if (RoomOffersView.this.showExpanded) {
                RoomOffersView.this.expandCollapseListener.onCollapse();
            } else {
                RoomOffersView.this.expandCollapseListener.onExpand();
            }
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.prices.RoomOffersView.2 */
    class C13332 implements OnTouchListener {
        C13332() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    }

    interface RoomItemExpandCollapseListener {
        void onCollapse();

        void onExpand();
    }

    public RoomOffersView(Context context) {
        this(context, null);
    }

    public RoomOffersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            this.offersDividerHeight = getResources().getDimensionPixelOffset(C1178R.dimen.offers_divider_height);
        }
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            ButterKnife.bind(this);
            setUpExpandBtnListener();
        }
    }

    public void setData(SearchParams searchParams, List<Offer> roomResults, Map<Integer, String> roomTypes, Offer best, boolean showExpanded, DiscountsByRooms discounts, HighlightsByRooms highlights) {
        this.searchParams = searchParams;
        this.roomResults = roomResults;
        this.roomTypes = roomTypes;
        this.showExpanded = showExpanded;
        this.bestOffer = best;
        this.discounts = discounts;
        this.highlights = highlights;
        computeRowsWhenCollapsed(roomResults);
        fillRoomNameTv();
        fillOffers();
        fillExpandBtn();
    }

    private void computeRowsWhenCollapsed(List<Offer> roomResults) {
        if (roomResults.size() == 4) {
            this.rowsWhenCollapsed = 4;
        } else {
            this.rowsWhenCollapsed = DEFAULT_ROWS_WHEN_COLLAPSED;
        }
    }

    private void fillOffers() {
        cleanOffers();
        addOffers();
    }

    private void addOffers() {
        if (this.showExpanded) {
            inflateAndFillOffers(this.roomResults.size());
        } else {
            inflateAndFillOffers(this.rowsWhenCollapsed);
        }
    }

    private void fillExpandBtn() {
        adjustExpandBtnVisibility();
        if (isExpandBtnVisible()) {
            fillExpandBtnText();
        }
    }

    private void fillExpandBtnText() {
        if (this.showExpanded) {
            this.expandBtn.setText(getResources().getString(C1178R.string.room_collapse_txt));
            return;
        }
        this.expandBtn.setText(String.format(getContext().getString(C1178R.string.show_more_offers), new Object[]{Integer.valueOf(calculateHiddenItems())}));
    }

    private boolean isExpandBtnVisible() {
        return this.expandBtn.getVisibility() == 0;
    }

    private void adjustExpandBtnVisibility() {
        if (this.roomResults.size() > this.rowsWhenCollapsed) {
            this.expandBtnContainer.setVisibility(0);
        } else {
            this.expandBtnContainer.setVisibility(8);
        }
    }

    private void setUpExpandBtnListener() {
        this.expandBtn.setOnClickListener(new C13321());
        this.expandBtn.setOnTouchListener(new C13332());
    }

    private int calculateHiddenItems() {
        return this.roomResults.size() - this.rowsWhenCollapsed;
    }

    private void cleanOffers() {
        this.offersContainer.removeAllViews();
    }

    private void inflateAndFillOffers(int maxShowCount) {
        int i = 0;
        while (i < this.roomResults.size() && i < maxShowCount) {
            boolean z;
            OfferView offerView = inflateOfferView();
            Offer room = (Offer) this.roomResults.get(i);
            SearchParams searchParams = this.searchParams;
            String name = room.getName();
            if (room.getRoomId() == this.bestOffer.getRoomId()) {
                z = true;
            } else {
                z = false;
            }
            offerView.setData(searchParams, room, name, z, false, this.discounts, this.highlights);
            this.offersContainer.addView(offerView);
            if (i > 0) {
                ((MarginLayoutParams) offerView.getLayoutParams()).topMargin = this.offersDividerHeight;
            }
            i++;
        }
    }

    private OfferView inflateOfferView() {
        return (OfferView) LayoutInflater.from(getContext()).inflate(C1178R.layout.offer_view, this.offersContainer, false);
    }

    private void fillRoomNameTv() {
        this.roomName.setText(getRoomNameTxt());
    }

    private String getRoomNameTxt() {
        Integer internalTypeId = getMinResult().getInternalTypeId();
        String roomName = (String) this.roomTypes.get(internalTypeId);
        if (roomName == null || internalTypeId.intValue() == -1) {
            return getContext().getString(C1178R.string.rooms_group_not_defined);
        }
        return roomName;
    }

    private Offer getMinResult() {
        return (Offer) this.roomResults.get(0);
    }

    public void setExpandCollapseListener(RoomItemExpandCollapseListener expandCollapseListener) {
        this.expandCollapseListener = expandCollapseListener;
    }
}
