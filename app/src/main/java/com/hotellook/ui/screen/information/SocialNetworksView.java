package com.hotellook.ui.screen.information;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.OnSocialNetworkClickEvent;

public class SocialNetworksView extends LinearLayout {
    public static final String FACEBOOK = "https://www.facebook.com/HotelLook.ru";
    public static final String GOOGLE_PLUS = "https://plus.google.com/+HotellookRussia/";
    public static final String INSTAGRAM = "https://instagram.com/hotellook_ru/";
    public static final String TWITTER = "https://twitter.com/HotelLook_ru";
    public static final String VKONTAKTE = "https://vk.com/hotellook";

    private static class OnSocialNetworkClick extends MonkeySafeClickListener {
        private final String mLink;

        public OnSocialNetworkClick(String mLink) {
            this.mLink = mLink;
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new OnSocialNetworkClickEvent(this.mLink));
        }
    }

    public SocialNetworksView(Context context) {
        super(context);
    }

    public SocialNetworksView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(C1178R.id.vk).setOnClickListener(new OnSocialNetworkClick(VKONTAKTE));
        findViewById(C1178R.id.fb).setOnClickListener(new OnSocialNetworkClick(FACEBOOK));
        findViewById(C1178R.id.tw).setOnClickListener(new OnSocialNetworkClick(TWITTER));
        findViewById(C1178R.id.in).setOnClickListener(new OnSocialNetworkClick(INSTAGRAM));
        findViewById(C1178R.id.gp).setOnClickListener(new OnSocialNetworkClick(GOOGLE_PLUS));
    }
}
