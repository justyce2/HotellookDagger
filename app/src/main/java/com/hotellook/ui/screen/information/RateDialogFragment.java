package com.hotellook.ui.screen.information;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.OnAppRatedEvent;
import com.hotellook.events.OnDismissRateDialog;
import com.hotellook.events.RateUsEvent;
import com.hotellook.events.RateUsOpenedEvent;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.utils.GooglePlayPage;

public class RateDialogFragment extends DialogFragment {
    public static final String TAG;
    private View mArrow;
    private boolean mBadBoyMode;
    private TextView mBtnNotNow;
    private TextView mBtnSend;
    private View mBtnToGooglePlay;
    private LinearLayout mControls;
    private boolean mDismissByUser;
    private RatingBar mDummyRatingBar;
    private EditText mFeedBack;
    private View mFeedBackTitle;
    private View mFeedBackUnderLine;
    private View mHighFive;
    private int mRating;
    private RatingBar mRatingBar;
    private Source mSource;

    /* renamed from: com.hotellook.ui.screen.information.RateDialogFragment.1 */
    class C13421 implements OnRatingBarChangeListener {
        C13421() {
        }

        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            RateDialogFragment.this.mRatingBar.setRating(rating);
            RateDialogFragment.this.mRating = (int) rating;
            if (rating > 3.0f) {
                RateDialogFragment.this.mDismissByUser = false;
                RateDialogFragment.this.onAppRated();
                RateDialogFragment.this.dismiss();
                RateDialogFragment.this.goToGooglePlay();
                HotellookApplication.eventBus().post(new RateUsEvent(RateDialogFragment.this.mRating, RateDialogFragment.this.mSource));
            } else {
                RateDialogFragment.this.enterMode(true);
            }
            RateDialogFragment.this.mDummyRatingBar.setOnRatingBarChangeListener(null);
            RateDialogFragment.this.mDummyRatingBar.setRating(0.0f);
            RateDialogFragment.this.mDummyRatingBar.setOnRatingBarChangeListener(this);
        }
    }

    /* renamed from: com.hotellook.ui.screen.information.RateDialogFragment.2 */
    class C13432 extends MonkeySafeClickListener {
        C13432() {
        }

        public void onSafeClick(View v) {
            RateDialogFragment.this.mDismissByUser = false;
            RateDialogFragment.this.dismiss();
            RateDialogFragment.this.goToGooglePlay();
            RateDialogFragment.this.onAppRated();
        }
    }

    /* renamed from: com.hotellook.ui.screen.information.RateDialogFragment.3 */
    class C13443 extends MonkeySafeClickListener {
        C13443() {
        }

        public void onSafeClick(View v) {
            RateDialogFragment.this.dismiss();
        }
    }

    /* renamed from: com.hotellook.ui.screen.information.RateDialogFragment.4 */
    class C13454 extends MonkeySafeClickListener {
        C13454() {
        }

        public void onSafeClick(View v) {
            RateDialogFragment.this.mDismissByUser = false;
            HotellookApplication.eventBus().post(new RateUsEvent(RateDialogFragment.this.mRating, RateDialogFragment.this.mSource));
            RateDialogFragment.this.onAppRated();
            RateDialogFragment.this.dismiss();
            new EmailComposer().sendFeedbackEmail(RateDialogFragment.this.getActivity(), (int) RateDialogFragment.this.mRatingBar.getRating(), RateDialogFragment.this.mFeedBack.getText().toString());
        }
    }

    public enum Source {
        MANUAL(MixPanelParams.MANUAL),
        AUTO(MixPanelParams.AUTO);
        
        public String literal;

        private Source(String literal) {
            this.literal = literal;
        }
    }

    public RateDialogFragment() {
        this.mBadBoyMode = false;
        this.mDismissByUser = true;
    }

    static {
        TAG = RateDialogFragment.class.getSimpleName();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(1, C1178R.style.DialogFragment);
        setRetainInstance(true);
        HotellookApplication.eventBus().post(new RateUsOpenedEvent(this.mSource));
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(C1178R.layout.rate_dialog, container, false);
        this.mRatingBar = (RatingBar) layout.findViewById(C1178R.id.stars);
        this.mRatingBar.setRating((float) HotellookApplication.getApp().getComponent().getRateDialogConditionsTracker().getRating());
        this.mDummyRatingBar = (RatingBar) layout.findViewById(C1178R.id.dummy_stars);
        this.mDummyRatingBar.setOnRatingBarChangeListener(new C13421());
        this.mFeedBack = (EditText) layout.findViewById(C1178R.id.feedback);
        this.mFeedBackTitle = layout.findViewById(C1178R.id.feedback_tip);
        this.mFeedBackUnderLine = layout.findViewById(C1178R.id.feedback_underline);
        this.mArrow = layout.findViewById(C1178R.id.arrow);
        this.mHighFive = layout.findViewById(C1178R.id.high_five);
        this.mControls = (LinearLayout) layout.findViewById(C1178R.id.controls);
        this.mBtnToGooglePlay = layout.findViewById(C1178R.id.google_play);
        this.mBtnNotNow = (TextView) layout.findViewById(C1178R.id.btn_not_now);
        this.mBtnSend = (TextView) layout.findViewById(C1178R.id.btn_send);
        this.mBtnToGooglePlay.setOnClickListener(new C13432());
        this.mBtnNotNow.setOnClickListener(new C13443());
        this.mBtnSend.setOnClickListener(new C13454());
        enterMode(this.mBadBoyMode);
        return layout;
    }

    private void onAppRated() {
        HotellookApplication.eventBus().post(new OnAppRatedEvent(this.mRating));
    }

    private void enterMode(boolean badBoyMode) {
        this.mBadBoyMode = badBoyMode;
        if (badBoyMode) {
            this.mFeedBack.setVisibility(0);
            this.mFeedBackUnderLine.setVisibility(0);
            this.mBtnToGooglePlay.setVisibility(0);
            this.mArrow.setVisibility(4);
            this.mHighFive.setVisibility(4);
            this.mFeedBackTitle.setVisibility(0);
            setControlsTopMargin(C1178R.dimen.rate_dialog_controls_margin_in_bad_boy_mode);
            this.mBtnNotNow.setTextColor(getResources().getColor(C1178R.color.green_AADB92));
            this.mBtnSend.setVisibility(0);
            ((InputMethodManager) getActivity().getSystemService("input_method")).showSoftInput(this.mFeedBack, 1);
        } else {
            this.mFeedBack.setVisibility(4);
            this.mFeedBackUnderLine.setVisibility(4);
            this.mBtnToGooglePlay.setVisibility(4);
            this.mArrow.setVisibility(0);
            this.mHighFive.setVisibility(0);
            this.mFeedBackTitle.setVisibility(4);
            setControlsTopMargin(C1178R.dimen.rate_dialog_controls_margin);
            this.mBtnSend.setVisibility(8);
        }
        if (getView() != null) {
            getView().requestLayout();
        }
    }

    private void setControlsTopMargin(int dimen) {
        MarginLayoutParams params = (MarginLayoutParams) this.mControls.getLayoutParams();
        params.setMargins(params.leftMargin, getResources().getDimensionPixelSize(dimen), params.rightMargin, params.bottomMargin);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void goToGooglePlay() {
        new GooglePlayPage(getActivity(), getActivity().getPackageName()).open();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        HotellookApplication.eventBus().post(new OnDismissRateDialog());
        if (this.mDismissByUser) {
            HotellookApplication.eventBus().post(new RateUsEvent(0, this.mSource));
        }
    }

    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    public void setSource(Source source) {
        this.mSource = source;
    }
}
