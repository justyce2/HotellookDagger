package com.hotellook.ui.view.tabview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.ui.screen.hotel.general.HotelGeneralFragment;

public class SlidingTabLayout extends HorizontalScrollView {
    private static final int DEFAULT_TAB_VIEW_TEXT_SIZE_DP = 12;
    private static final int TAB_VIEW_PADDING_DIPS = 16;
    private static final int TITLE_OFFSET_DIPS = 24;
    private SparseArray<String> mContentDescriptions;
    private boolean mDistributeEvenly;
    private final SlidingTabStrip mTabStrip;
    private int mTabViewLayoutId;
    private int mTabViewTextViewId;
    private int mTitleOffset;
    private ViewPager mViewPager;
    private OnPageChangeListener mViewPagerPageChangeListener;

    public interface TabColorizer {
        int getIndicatorColor(int i);
    }

    /* renamed from: com.hotellook.ui.view.tabview.SlidingTabLayout.1 */
    class C14471 implements TabColorizer {
        final /* synthetic */ int val$selectorColor;

        C14471(int i) {
            this.val$selectorColor = i;
        }

        public int getIndicatorColor(int position) {
            return this.val$selectorColor;
        }
    }

    private class InternalViewPagerListener implements OnPageChangeListener {
        private int mScrollState;

        private InternalViewPagerListener() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int tabStripChildCount = SlidingTabLayout.this.mTabStrip.getChildCount();
            if (tabStripChildCount != 0 && position >= 0 && position < tabStripChildCount) {
                SlidingTabLayout.this.mTabStrip.onViewPagerPageChanged(position, positionOffset);
                View selectedTitle = SlidingTabLayout.this.mTabStrip.getChildAt(position);
                SlidingTabLayout.this.scrollToTab(position, selectedTitle != null ? (int) (((float) selectedTitle.getWidth()) * positionOffset) : 0);
                if (SlidingTabLayout.this.mViewPagerPageChangeListener != null) {
                    SlidingTabLayout.this.mViewPagerPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }
        }

        public void onPageScrollStateChanged(int state) {
            this.mScrollState = state;
            if (SlidingTabLayout.this.mViewPagerPageChangeListener != null) {
                SlidingTabLayout.this.mViewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        public void onPageSelected(int position) {
            if (this.mScrollState == 0) {
                SlidingTabLayout.this.mTabStrip.onViewPagerPageChanged(position, 0.0f);
                SlidingTabLayout.this.scrollToTab(position, 0);
            }
            for (int i = 0; i < SlidingTabLayout.this.mTabStrip.getChildCount(); i++) {
                boolean z;
                View childAt = SlidingTabLayout.this.mTabStrip.getChildAt(i);
                if (position == i) {
                    z = true;
                } else {
                    z = false;
                }
                childAt.setSelected(z);
            }
            if (SlidingTabLayout.this.mViewPagerPageChangeListener != null) {
                SlidingTabLayout.this.mViewPagerPageChangeListener.onPageSelected(position);
            }
        }
    }

    private class TabClickListener extends MonkeySafeClickListener {
        private TabClickListener() {
        }

        public void onSafeClick(View v) {
            for (int i = 0; i < SlidingTabLayout.this.mTabStrip.getChildCount(); i++) {
                if (v == SlidingTabLayout.this.mTabStrip.getChildAt(i)) {
                    SlidingTabLayout.this.mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    }

    public SlidingTabLayout(Context context) {
        this(context, null);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContentDescriptions = new SparseArray();
        setHorizontalScrollBarEnabled(false);
        setFillViewport(true);
        TypedArray attrsArray = context.getTheme().obtainStyledAttributes(attrs, C1178R.styleable.TabBar, 0, 0);
        this.mTitleOffset = (int) (24.0f * getResources().getDisplayMetrics().density);
        this.mTabStrip = new SlidingTabStrip(context);
        if (attrsArray.hasValue(0)) {
            this.mTabStrip.setCustomTabColorizer(new C14471(attrsArray.getColor(0, -13388315)));
        }
        if (attrsArray.hasValue(1)) {
            float density = getResources().getDisplayMetrics().density;
            this.mTabStrip.setSelectedIndicatorThickness(attrsArray.getDimensionPixelSize(1, 3));
        }
        attrsArray.recycle();
        LinearLayout dummyLayout = new LinearLayout(context);
        addView(dummyLayout, -1, -2);
        dummyLayout.addView(this.mTabStrip, -1, -2);
    }

    public void setCustomTabColorizer(TabColorizer tabColorizer) {
        this.mTabStrip.setCustomTabColorizer(tabColorizer);
    }

    public void setDistributeEvenly(boolean distributeEvenly) {
        this.mDistributeEvenly = distributeEvenly;
    }

    public void setSelectedIndicatorColors(int... colors) {
        this.mTabStrip.setSelectedIndicatorColors(colors);
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mViewPagerPageChangeListener = listener;
    }

    public void setCustomTabView(int layoutResId, int textViewId) {
        this.mTabViewLayoutId = layoutResId;
        this.mTabViewTextViewId = textViewId;
    }

    public void setViewPager(ViewPager viewPager) {
        this.mTabStrip.removeAllViews();
        this.mViewPager = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateTabStrip();
        }
    }

    protected TextView createDefaultTabView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(17);
        textView.setTextSize(2, 12.0f);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new LayoutParams(-2, -2));
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(16843534, outValue, true);
        textView.setBackgroundResource(outValue.resourceId);
        textView.setAllCaps(true);
        int padding = (int) (HotelGeneralFragment.GOOD_LOOKING_ZOOM_LEVEL * getResources().getDisplayMetrics().density);
        textView.setPadding(padding, padding, padding, padding);
        return textView;
    }

    private void populateTabStrip() {
        PagerAdapter adapter = this.mViewPager.getAdapter();
        OnClickListener tabClickListener = new TabClickListener();
        for (int i = 0; i < adapter.getCount(); i++) {
            View tabView = null;
            TextView tabTitleView = null;
            if (this.mTabViewLayoutId != 0) {
                tabView = LayoutInflater.from(getContext()).inflate(this.mTabViewLayoutId, this.mTabStrip, false);
                tabTitleView = (TextView) tabView.findViewById(this.mTabViewTextViewId);
            }
            if (tabView == null) {
                tabView = createDefaultTabView(getContext());
            }
            if (tabTitleView == null && TextView.class.isInstance(tabView)) {
                tabTitleView = (TextView) tabView;
            }
            if (this.mDistributeEvenly) {
                LayoutParams lp = (LayoutParams) tabView.getLayoutParams();
                lp.width = 0;
                lp.weight = 1.0f;
            }
            tabTitleView.setText(adapter.getPageTitle(i));
            tabView.setOnClickListener(tabClickListener);
            String desc = (String) this.mContentDescriptions.get(i, null);
            if (desc != null) {
                tabView.setContentDescription(desc);
            }
            this.mTabStrip.addView(tabView);
            if (i == this.mViewPager.getCurrentItem()) {
                tabView.setSelected(true);
            }
        }
    }

    public void setContentDescription(int i, String desc) {
        this.mContentDescriptions.put(i, desc);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mViewPager != null) {
            scrollToTab(this.mViewPager.getCurrentItem(), 0);
        }
    }

    private void scrollToTab(int tabIndex, int positionOffset) {
        int tabStripChildCount = this.mTabStrip.getChildCount();
        if (tabStripChildCount != 0 && tabIndex >= 0 && tabIndex < tabStripChildCount) {
            View selectedChild = this.mTabStrip.getChildAt(tabIndex);
            if (selectedChild != null) {
                int targetScrollX = selectedChild.getLeft() + positionOffset;
                if (tabIndex > 0 || positionOffset > 0) {
                    targetScrollX -= this.mTitleOffset;
                }
                scrollTo(targetScrollX, 0);
            }
        }
    }

    public void setTabsStripMargins(int left, int top, int right, int bottom) {
        ((MarginLayoutParams) this.mTabStrip.getLayoutParams()).setMargins(left, top, right, bottom);
    }
}
