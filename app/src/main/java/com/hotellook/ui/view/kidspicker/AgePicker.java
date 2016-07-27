package com.hotellook.ui.view.kidspicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import com.devspark.robototextview.util.RobotoTypefaceManager;
import com.devspark.robototextview.util.RobotoTypefaceUtils;
import com.hotellook.C1178R;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.ui.view.AdvancedScrollView;
import com.hotellook.ui.view.AdvancedScrollView.OnScrollListener;
import com.hotellook.ui.view.AdvancedScrollView.OnScrollStoppedListener;

public class AgePicker extends RelativeLayout {
    private static final int DEFAULT_SELECTED_AGE_ITEM = 7;
    public static final int MAX_AGE = 17;
    public static final int MIN_AGE = 0;
    static final int NOT_SELECTED = -1;
    private int dividerHeight;
    private int itemVertMargin;
    private int mAgeItemHeight;
    private View mBottomDivider;
    private int mCentralAgeItemIdx;
    private LinearLayout mItemsContainer;
    private AdvancedScrollView mScrollView;
    private View mTopDivider;
    private OnClickListener onAgeItemClickListener;
    private OnTouchListener onAgeItemTouchListener;
    private OnAgeSelectedListener onAgeSelectedListener;
    private OnClickListener onSelectedAgeItemClickListener;

    /* renamed from: com.hotellook.ui.view.kidspicker.AgePicker.1 */
    class C14241 extends MonkeySafeClickListener {
        C14241() {
        }

        public void onSafeClick(View v) {
            AgePicker.this.mScrollView.smoothScrollBy(AgePicker.MIN_AGE, ((Integer) v.getTag() - getSelectedAge().intValue()) * AgePicker.this.mAgeItemHeight);
        }
    }

    /* renamed from: com.hotellook.ui.view.kidspicker.AgePicker.2 */
    class C14252 extends MonkeySafeClickListener {
        C14252() {
        }

        public void onSafeClick(View v) {
            if (onAgeSelectedListener != null) {
                onAgeSelectedListener.onAgeSelected(getSelectedAge().intValue());
            }
        }
    }

    /* renamed from: com.hotellook.ui.view.kidspicker.AgePicker.3 */
    class C14263 implements Runnable {
        C14263() {
        }

        public void run() {
            AgePicker.this.scrollToDefaultPosition();
        }
    }

    /* renamed from: com.hotellook.ui.view.kidspicker.AgePicker.4 */
    class C14274 implements OnScrollStoppedListener {
        C14274() {
        }

        public void onScrollStopped() {
            int currentScroll = mScrollView.getScrollY();
            int offsetToCompensate = currentScroll % mAgeItemHeight;
            if (offsetToCompensate < mAgeItemHeight / 2) {
                mScrollView.smoothScrollTo(AgePicker.MIN_AGE, currentScroll - offsetToCompensate);
            } else {
                .mScrollView.smoothScrollTo(AgePicker.MIN_AGE, (mAgeItemHeight + currentScroll) - offsetToCompensate);
            }
        }
    }

    /* renamed from: com.hotellook.ui.view.kidspicker.AgePicker.5 */
    class C14285 implements OnScrollListener {
        C14285() {
        }

        public void onScrollChanged(View view, int x, int y, int oldx, int oldy) {
            if (AgePicker.this.getContext() != null) {
                int scrollYInScrollViewCenter = (int) (((double) AgePicker.this.mScrollView.getScrollY()) + (1.5d * ((double) AgePicker.this.mAgeItemHeight)));
                int selectedIdx = scrollYInScrollViewCenter / AgePicker.this.mAgeItemHeight;
                if (scrollYInScrollViewCenter % AgePicker.this.mAgeItemHeight > AgePicker.this.mAgeItemHeight - AgePicker.this.itemVertMargin || scrollYInScrollViewCenter % AgePicker.this.mAgeItemHeight < AgePicker.this.itemVertMargin) {
                    selectedIdx = AgePicker.NOT_SELECTED;
                }
                if (selectedIdx == AgePicker.NOT_SELECTED) {
                    setAgeItemSelected(AgePicker.this.mCentralAgeItemIdx, false);
                    AgePicker.this.mCentralAgeItemIdx = AgePicker.NOT_SELECTED;
                } else if (AgePicker.this.mCentralAgeItemIdx != selectedIdx) {
                    setAgeItemSelected(selectedIdx, true);
                    setAgeItemSelected(AgePicker.this.mCentralAgeItemIdx, false);
                    AgePicker.this.mCentralAgeItemIdx = selectedIdx;
                }
            }
        }

        private boolean headerOrFooterItem(int idx) {
            return idx == 0 || idx == AgePicker.this.mItemsContainer.getChildCount() + AgePicker.NOT_SELECTED;
        }

        private void setFontToItem(int itemIdx, int typefaceId) {
            RobotoTypefaceUtils.setUp((TextView) AgePicker.this.mItemsContainer.getChildAt(itemIdx), RobotoTypefaceManager.obtainTypeface(AgePicker.this.getContext(), typefaceId));
        }

        private void setAgeItemSelected(int idx, boolean b) {
            if (!headerOrFooterItem(idx) && idx != AgePicker.NOT_SELECTED) {
                if (b) {
                    setFontToItem(idx, 8);
                    AgePicker.this.mItemsContainer.getChildAt(idx).setOnClickListener(AgePicker.this.onSelectedAgeItemClickListener);
                    return;
                }
                AgePicker.this.mItemsContainer.getChildAt(idx).setOnClickListener(AgePicker.this.onAgeItemClickListener);
                setFontToItem(idx, 4);
            }
        }
    }

    /* renamed from: com.hotellook.ui.view.kidspicker.AgePicker.6 */
    class C14296 implements OnTouchListener {
        C14296() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            requestDisallowInterceptTouchEvent(true);
            return false;
        }
    }

    /* renamed from: com.hotellook.ui.view.kidspicker.AgePicker.7 */
    class C14307 implements OnTouchListener {
        C14307() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (AgePicker.this.onAgeItemTouchListener != null) {
                AgePicker.this.onAgeItemTouchListener.onTouch(v, event);
            }
            return false;
        }
    }

    interface OnAgeSelectedListener {
        void onAgeSelected(int i);
    }

    public AgePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.onAgeItemClickListener = new C14241();
        this.onSelectedAgeItemClickListener = new C14252();
        setUp();
    }

    public AgePicker(Context context) {
        this(context, null);
    }

    private void setUp() {
        this.mAgeItemHeight = getResources().getDimensionPixelSize(C1178R.dimen.age_picker_item_height);
        this.itemVertMargin = getResources().getDimensionPixelSize(C1178R.dimen.age_picker_item_vert_margin);
        this.dividerHeight = getResources().getDimensionPixelSize(C1178R.dimen.age_picker_divider_height);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mScrollView = (AdvancedScrollView) findViewById(C1178R.id.scroll_view);
        this.mItemsContainer = (LinearLayout) this.mScrollView.findViewById(C1178R.id.container);
        this.mTopDivider = findViewById(C1178R.id.top_divider);
        this.mBottomDivider = findViewById(C1178R.id.bottom_divider);
        fillContainerWithAgeItems();
        getLayoutParams().height = (this.mAgeItemHeight * 3) + this.dividerHeight;
        this.mScrollView.getLayoutParams().height = this.mAgeItemHeight * 3;
        this.mTopDivider.setTranslationY((float) this.mAgeItemHeight);
        this.mBottomDivider.setTranslationY((float) (this.mAgeItemHeight * 2));
        setUpScrollView();
    }

    private void setUpScrollView() {
        this.mScrollView.post(new C14263());
        this.mScrollView.setOnScrollStoppedListener(new C14274());
        this.mScrollView.setOnScrollLister(new C14285());
        this.mScrollView.setOnTouchListener(new C14296());
    }

    private void fillContainerWithAgeItems() {
        this.mItemsContainer.addView(createDummyAgeItem(this.mAgeItemHeight));
        for (int i = MIN_AGE; i <= MAX_AGE; i++) {
            TextView ageItem = (TextView) LayoutInflater.from(getContext()).inflate(C1178R.layout.kids_picker_age_item, this.mItemsContainer, false);
            ageItem.setTag(Integer.valueOf(i));
            ageItem.setText(KidsPickerView.getKidAgeText(Integer.valueOf(i), getContext()));
            ageItem.setOnClickListener(this.onAgeItemClickListener);
            ageItem.setOnTouchListener(new C14307());
            this.mItemsContainer.addView(ageItem);
        }
        this.mItemsContainer.addView(createDummyAgeItem(this.mAgeItemHeight));
    }

    private View createDummyAgeItem(int itemHeight) {
        Space space = new Space(getContext());
        space.setLayoutParams(new LayoutParams(MIN_AGE, itemHeight));
        return space;
    }

    public void scrollToDefaultPosition() {
        this.mScrollView.scrollTo(MIN_AGE, this.mAgeItemHeight * DEFAULT_SELECTED_AGE_ITEM);
    }

    public Integer getSelectedAge() {
        if (this.mCentralAgeItemIdx == NOT_SELECTED) {
            return Integer.valueOf(this.mScrollView.getScrollY() / this.mAgeItemHeight);
        }
        return Integer.valueOf(this.mCentralAgeItemIdx + NOT_SELECTED);
    }

    public void setOnAgeSelectedListener(OnAgeSelectedListener onAgeSelectedListener) {
        this.onAgeSelectedListener = onAgeSelectedListener;
    }

    public void setOnAgeItemTouchListener(OnTouchListener onAgeItemTouchListener) {
        this.onAgeItemTouchListener = onAgeItemTouchListener;
    }
}
