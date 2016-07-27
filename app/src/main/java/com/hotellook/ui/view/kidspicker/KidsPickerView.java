package com.hotellook.ui.view.kidspicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.LayoutTransition.TransitionListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.BottomSheetOpenedEvent;
import com.hotellook.events.CloseKidsPickerEvent;
import com.hotellook.events.KidsUpdatedEvent;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class KidsPickerView extends ScrollView {
    public static final int MAX_KIDS = 4;
    private View addChildBtn;
    private AgePicker agePicker;
    public View applyBtn;
    private View cancelBtn;
    private LinearLayout content;
    private KidsPickerItemView emptyItem;
    private boolean ignoreClicks;
    private List<KidsPickerItemView> kidViews;
    private OnAgeSelectedListener onAgeSelectedListener;
    private View openAgePickerBtn;

    /* renamed from: com.hotellook.ui.view.kidspicker.KidsPickerView.10 */
    class AnonymousClass10 extends AnimatorListenerAdapter {
        final /* synthetic */ View val$animatedView;

        AnonymousClass10(View view) {
            this.val$animatedView = view;
        }

        public void onAnimationEnd(Animator animation) {
            this.val$animatedView.setVisibility(KidsPickerView.MAX_KIDS);
        }
    }

    /* renamed from: com.hotellook.ui.view.kidspicker.KidsPickerView.1 */
    class C14321 implements OnAgeSelectedListener {
        C14321() {
        }

        public void onAgeSelected(int age) {
            transformEmptyToKid(age);
            agePicker.setVisibility(8);
            showNewKidControls();
            sendUpdateEvent();
        }
    }

    /* renamed from: com.hotellook.ui.view.kidspicker.KidsPickerView.2 */
    class C14342 extends MonkeySafeClickListener {

        /* renamed from: com.hotellook.ui.view.kidspicker.KidsPickerView.2.1 */
        class C14331 implements TransitionListener {
            C14331() {
            }

            public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
            }

            public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                content.getLayoutTransition().removeTransitionListener(this);
                turnOnClicks();
            }
        }

        C14342() {
        }

        public void onSafeClick(View v) {
            turnOffClicks();
            planToRemoveClickIgnoreAfterLayoutAnimation();
            removeFromList((KidsPickerItemView) v);
            content.removeView(v);
            sendUpdateEvent();
            adjustOpenAgePickerBtn();
            if (noKids()) {
                close();
            }
        }

        private void planToRemoveClickIgnoreAfterLayoutAnimation() {
            content.getLayoutTransition().addTransitionListener(new C14331());
        }
    }

    /* renamed from: com.hotellook.ui.view.kidspicker.KidsPickerView.3 */
    class C14353 extends MonkeySafeClickListener {
        C14353() {
        }

        public void onSafeClick(View v) {
        }
    }

    /* renamed from: com.hotellook.ui.view.kidspicker.KidsPickerView.4 */
    class C14364 extends MonkeySafeClickListener {
        C14364() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new CloseKidsPickerEvent());
        }
    }

    /* renamed from: com.hotellook.ui.view.kidspicker.KidsPickerView.5 */
    class C14375 extends MonkeySafeClickListener {
        C14375() {
        }

        public void onSafeClick(View v) {
            turnOffClicks();
            onAgeSelectedListener.onAgeSelected(agePicker.getSelectedAge().intValue());
            adjustOpenAgePickerBtn();
        }
    }

    /* renamed from: com.hotellook.ui.view.kidspicker.KidsPickerView.6 */
    class C14386 extends MonkeySafeClickListener {
        C14386() {
        }

        public void onSafeClick(View v) {
            turnOffClicks();
            if (noKids()) {
                close();
                return;
            }
            agePicker.setVisibility(8);
            content.removeView(emptyItem);
            showNewKidControls();
        }
    }

    /* renamed from: com.hotellook.ui.view.kidspicker.KidsPickerView.7 */
    class C14407 extends MonkeySafeClickListener {

        /* renamed from: com.hotellook.ui.view.kidspicker.KidsPickerView.7.1 */
        class C14391 implements TransitionListener {
            C14391() {
            }

            public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
            }

            public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                content.getLayoutTransition().removeTransitionListener(this);
                agePicker.scrollToDefaultPosition();
                smoothScrollTo(0, getBottom());
            }
        }

        C14407() {
        }

        public void onSafeClick(View v) {
            turnOffClicks();
            if (kidViews.size() == KidsPickerView.MAX_KIDS) {
                turnOnClicks();
                return;
            }
            emptyItem = createKidItem(null, true);
            content.addView(emptyItem, kidViews.size());
            planToScrollToBottomAfterLayoutTransition();
            agePicker.setVisibility(0);
            showAgePickerControls();
        }

        private void planToScrollToBottomAfterLayoutTransition() {
            content.getLayoutTransition().addTransitionListener(new C14391());
        }
    }

    /* renamed from: com.hotellook.ui.view.kidspicker.KidsPickerView.8 */
    class C14418 extends AnimatorListenerAdapter {
        C14418() {
        }

        public void onAnimationEnd(Animator animation) {
            turnOnClicks();
        }
    }

    /* renamed from: com.hotellook.ui.view.kidspicker.KidsPickerView.9 */
    class C14429 extends AnimatorListenerAdapter {
        final /* synthetic */ View val$view;

        C14429(View view) {
            this.val$view = view;
        }

        public void onAnimationStart(Animator animation) {
            this.val$view.setVisibility(0);
        }
    }

    public KidsPickerView(Context context) {
        this(context, null);
    }

    public KidsPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.kidViews = new LinkedList();
        this.ignoreClicks = false;
        this.onAgeSelectedListener = new C14321();
    }

    static String getKidAgeText(Integer age, Context context) {
        if (age == 0) {
            return context.getString(C1178R.string.kp_zero_age);
        }
        return age + " " + context.getResources().getQuantityString(C1178R.plurals.kp_age, age);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.content = (LinearLayout) findViewById(C1178R.id.content);
    }

    public void init(List<Integer> kids) {
        for (int i = 0; i < kids.size(); i++) {
            KidsPickerItemView kidView = createKidItem(kids.get(i), false);
            this.kidViews.add(kidView);
            this.content.addView(kidView, i);
        }
        this.emptyItem = createKidItem(null, true);
        this.content.addView(this.emptyItem);
        addAgePickerView();
        addControls();
        adjustOpenAgePickerBtn();
        if (this.kidViews.size() == 0) {
            this.agePicker.setVisibility(0);
            this.emptyItem.setVisibility(0);
            return;
        }
        this.agePicker.setVisibility(8);
        this.emptyItem.setVisibility(8);
    }

    private KidsPickerItemView createKidItem(Integer age, boolean isEmpty) {
        KidsPickerItemView kidView = (KidsPickerItemView) LayoutInflater.from(getContext()).inflate(C1178R.layout.kids_picker_item, this, false);
        if (isEmpty) {
            kidView.setEmpty(true);
        } else {
            kidView.setAge(age);
        }
        kidView.setOnRemoveListener(new C14342());
        kidView.setOnClickListener(new C14353());
        return kidView;
    }

    private void close() {
        HotellookApplication.eventBus().post(new CloseKidsPickerEvent());
    }

    private void removeFromList(KidsPickerItemView item) {
        for (int i = 0; i < this.kidViews.size(); i++) {
            if (item == this.kidViews.get(i)) {
                this.kidViews.remove(i);
                return;
            }
        }
    }

    private void addControls() {
        View controls = LayoutInflater.from(getContext()).inflate(C1178R.layout.kids_picker_controls, this, false);
        this.addChildBtn = controls.findViewById(C1178R.id.add_child);
        this.cancelBtn = controls.findViewById(C1178R.id.cancel);
        this.openAgePickerBtn = controls.findViewById(C1178R.id.open_age_picker);
        this.applyBtn = controls.findViewById(C1178R.id.apply);
        this.content.addView(controls);
        this.applyBtn.setOnClickListener(new C14364());
        this.addChildBtn.setOnClickListener(new C14375());
        this.cancelBtn.setOnClickListener(new C14386());
        this.openAgePickerBtn.setOnClickListener(new C14407());
        if (this.kidViews.size() == 0) {
            this.cancelBtn.setVisibility(0);
            this.addChildBtn.setVisibility(0);
            this.openAgePickerBtn.setVisibility(MAX_KIDS);
            this.applyBtn.setVisibility(MAX_KIDS);
            return;
        }
        this.cancelBtn.setVisibility(MAX_KIDS);
        this.addChildBtn.setVisibility(MAX_KIDS);
        this.openAgePickerBtn.setVisibility(0);
        this.applyBtn.setVisibility(0);
    }

    private void turnOnClicks() {
    }

    private void turnOffClicks() {
    }

    private boolean noKids() {
        return this.kidViews.size() == 0;
    }

    private void sendUpdateEvent() {
        HotellookApplication.eventBus().post(new KidsUpdatedEvent(getKids()));
    }

    private List<Integer> getKids() {
        List<Integer> kids = new ArrayList();
        for (int i = 0; i < this.kidViews.size(); i++) {
            kids.add(this.kidViews.get(i).getAge());
        }
        return kids;
    }

    private void showNewKidControls() {
        ObjectAnimator addChildAnimator = createFadeOutAnimator(addChildBtn);
        ObjectAnimator cancelBtnAnimator = createFadeOutAnimator(cancelBtn);
        ObjectAnimator openPickerBtnAnimator = createFadeInAnimator(openAgePickerBtn);
        ObjectAnimator applyBtnAnimator = createFadeInAnimator(applyBtn);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(addChildAnimator).with(cancelBtnAnimator);
        animatorSet.play(openPickerBtnAnimator).with(applyBtnAnimator).after(addChildAnimator);
        animatorSet.addListener(new C14418());
        animatorSet.start();
    }

    @NonNull
    private ObjectAnimator createFadeInAnimator(View view) {
        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f);
        fadeInAnimator.addListener(new C14429(view));
        return fadeInAnimator;
    }

    private void adjustOpenAgePickerBtn() {
        if (this.kidViews.size() == MAX_KIDS) {
            this.openAgePickerBtn.setEnabled(false);
        } else {
            this.openAgePickerBtn.setEnabled(true);
        }
    }

    @NonNull
    private ObjectAnimator createFadeOutAnimator(View animatedView) {
        ObjectAnimator fadeOutAnimator = ObjectAnimator.ofFloat(animatedView, View.ALPHA, 1.0f, 0.0f);
        fadeOutAnimator.addListener(new AnonymousClass10(animatedView));
        return fadeOutAnimator;
    }

    private void showAgePickerControls() {
        ObjectAnimator openAgePickerBtnAnimator = createFadeOutAnimator(this.openAgePickerBtn);
        ObjectAnimator applyBtnAnimator = createFadeOutAnimator(this.applyBtn);
        ObjectAnimator addChildBtnAnimator = createFadeInAnimator(this.addChildBtn);
        ObjectAnimator cancelBtnAnimator = createFadeInAnimator(this.cancelBtn);
        AnimatorSet showAgePickerControlsAnimation = new AnimatorSet();
        showAgePickerControlsAnimation.play(openAgePickerBtnAnimator).with(applyBtnAnimator);
        showAgePickerControlsAnimation.play(addChildBtnAnimator).with(cancelBtnAnimator).after(openAgePickerBtnAnimator);
        showAgePickerControlsAnimation.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                turnOnClicks();
            }
        });
        showAgePickerControlsAnimation.start();
    }

    private void addAgePickerView() {
        this.agePicker = (AgePicker) LayoutInflater.from(getContext()).inflate(C1178R.layout.kids_picker_age_picker, this, false);
        this.content.addView(this.agePicker);
        this.agePicker.setOnAgeSelectedListener(this.onAgeSelectedListener);
        this.agePicker.setOnAgeItemTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void transformEmptyToKid(int age) {
        this.emptyItem.setEmpty(false);
        this.emptyItem.setAge(age);
        this.kidViews.add(this.emptyItem);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        HotellookApplication.eventBus().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        HotellookApplication.eventBus().unregister(this);
        super.onDetachedFromWindow();
    }

    @Subscribe
    public void onBottomSheetOpened(BottomSheetOpenedEvent event) {
        this.content.setLayoutTransition(new LayoutTransition());
    }
}
