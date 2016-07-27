package com.hotellook.ui.view.mapplaceholder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.hotellook.C1178R;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.utils.GooglePlayPage;

public class MapPlaceHolder extends FrameLayout {
    public static final String GMS_PACKAGE_NAME = "com.google.android.gms";

    /* renamed from: com.hotellook.ui.view.mapplaceholder.MapPlaceHolder.1 */
    class C14431 extends MonkeySafeClickListener {
        final /* synthetic */ Context val$context;

        C14431(Context context) {
            this.val$context = context;
        }

        public void onSafeClick(View v) {
            new GooglePlayPage(this.val$context, MapPlaceHolder.GMS_PACKAGE_NAME).open();
        }
    }

    public MapPlaceHolder(Context context) {
        super(context);
        init(context, null);
    }

    public MapPlaceHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MapPlaceHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {
            LayoutInflater.from(context).inflate(C1178R.layout.map_place_holder, this);
            findViewById(C1178R.id.btn_update_google_services).setOnClickListener(new C14431(context));
        }
    }
}
