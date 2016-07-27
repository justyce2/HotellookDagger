package com.hotellook.ui.screen.hotel.features;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.hotellook.C1178R;
import com.hotellook.common.locale.Constants.Language;
import com.hotellook.databinding.HotelStaffLanguageViewBinding;
import pl.charmas.android.reactivelocation.C1822R;
import timber.log.Timber;

public class HotelStaffLanguageView extends LinearLayout {
    private HotelStaffLanguageViewBinding binding;

    public HotelStaffLanguageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.binding = (HotelStaffLanguageViewBinding) DataBindingUtil.bind(this);
        }
    }

    public void bindTo(@NonNull String language) {
        int i = -1;
        switch (language.hashCode()) {
            case 3201:
                if (language.equals(Language.GERMAN)) {
                    i = 2;
                    break;
                }
                break;
            case 3246:
                if (language.equals(Language.SPANISH)) {
                    i = 1;
                    break;
                }
                break;
            case 3276:
                if (language.equals(Language.FRENCH)) {
                    i = 3;
                    break;
                }
                break;
            case 3371:
                if (language.equals(Language.ITALIAN)) {
                    i = 4;
                    break;
                }
                break;
            case 3383:
                if (language.equals(Language.JAPANESE)) {
                    i = 9;
                    break;
                }
                break;
            case 3428:
                if (language.equals(Language.KOREAN)) {
                    i = 7;
                    break;
                }
                break;
            case 3588:
                if (language.equals(Language.PORTUGUESE)) {
                    i = 5;
                    break;
                }
                break;
            case 3651:
                if (language.equals(Language.RUSSIAN)) {
                    i = 0;
                    break;
                }
                break;
            case 3700:
                if (language.equals(Language.THAI)) {
                    i = 6;
                    break;
                }
                break;
            case 3886:
                if (language.equals(Language.CHINESE)) {
                    i = 8;
                    break;
                }
                break;
        }
        switch (i) {
            case C1822R.styleable.SignInButton_buttonSize /*0*/:
                this.binding.text.setText(getResources().getString(C1178R.string.ru_staff));
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                this.binding.text.setText(getResources().getString(C1178R.string.es_staff));
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                this.binding.text.setText(getResources().getString(C1178R.string.de_staff));
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                this.binding.text.setText(getResources().getString(C1178R.string.fr_staff));
            case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
                this.binding.text.setText(getResources().getString(C1178R.string.it_staff));
            case C1822R.styleable.MapAttrs_cameraZoom /*5*/:
                this.binding.text.setText(getResources().getString(C1178R.string.pt_staff));
            case C1822R.styleable.MapAttrs_liteMode /*6*/:
                this.binding.text.setText(getResources().getString(C1178R.string.th_staff));
            case C1822R.styleable.MapAttrs_uiCompass /*7*/:
                this.binding.text.setText(getResources().getString(C1178R.string.ko_staff));
            case C1822R.styleable.MapAttrs_uiRotateGestures /*8*/:
                this.binding.text.setText(getResources().getString(C1178R.string.zh_staff));
            case C1822R.styleable.MapAttrs_uiScrollGestures /*9*/:
                this.binding.text.setText(getResources().getString(C1178R.string.ja_staff));
            default:
                Timber.m753e("Not supported for display in language: %s", language);
                setVisibility(8);
        }
    }
}
