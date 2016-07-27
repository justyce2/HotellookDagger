package com.hotellook.ui.screen.hotel.visitors;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.hotellook.C1178R;
import com.hotellook.common.locale.Constants.Language;
import com.hotellook.core.api.Constants.TripTypeSplit;
import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.core.api.pojo.hoteldetail.TripType;
import com.hotellook.databinding.HotelVisitorItemViewBinding;
import pl.charmas.android.reactivelocation.C1822R;
import timber.log.Timber;

public class HotelVisitorItemView extends LinearLayout {
    private HotelVisitorItemViewBinding binding;

    public HotelVisitorItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @NonNull
    public static HotelVisitorItemView create(@NonNull ViewGroup parent) {
        return (HotelVisitorItemView) LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.hotel_visitor_item_view, parent, false);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.binding = (HotelVisitorItemViewBinding) DataBindingUtil.bind(this);
        }
    }

    public void bindTo(@NonNull TripType tripType) {
        String type = tripType.getType();
        int percent = tripType.getPercentage();
        int i = -1;
        switch (type.hashCode()) {
            case -1354573888:
                if (type.equals(TripTypeSplit.COUPLE)) {
                    i = 1;
                    break;
                }
                break;
            case -1281860764:
                if (type.equals(TripTypeSplit.FAMILY)) {
                    i = 2;
                    break;
                }
                break;
            case -1146830912:
                if (type.equals(Poi.CATEGORY_BUSINESS)) {
                    i = 3;
                    break;
                }
                break;
            case 3536095:
                if (type.equals(TripTypeSplit.SOLO)) {
                    i = 0;
                    break;
                }
                break;
        }
        switch (i) {
            case C1822R.styleable.SignInButton_buttonSize /*0*/:
                this.binding.icon.setImageResource(C1178R.drawable.ic_visitors_solo);
                this.binding.text.setText(getResources().getString(C1178R.string.percent_solo, new Object[]{Integer.valueOf(percent)}));
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                this.binding.icon.setImageResource(C1178R.drawable.ic_visitors_couple);
                this.binding.text.setText(getResources().getString(C1178R.string.percent_pairs, new Object[]{Integer.valueOf(percent)}));
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                this.binding.icon.setImageResource(C1178R.drawable.ic_visitors_family);
                this.binding.text.setText(getResources().getString(C1178R.string.percent_families, new Object[]{Integer.valueOf(percent)}));
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                this.binding.icon.setImageResource(C1178R.drawable.ic_visitors_business);
                this.binding.text.setText(getResources().getString(C1178R.string.percent_business, new Object[]{Integer.valueOf(percent)}));
            default:
        }
    }

    public void bindTo(int percent, @NonNull String language) {
        this.binding.icon.setImageResource(C1178R.drawable.ic_visitors_country);
        String textFormat = "%d%% \u2014 %s";
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
                this.binding.text.setText(String.format(textFormat, new Object[]{Integer.valueOf(percent), getResources().getString(C1178R.string.ru_guests)}));
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                this.binding.text.setText(String.format(textFormat, new Object[]{Integer.valueOf(percent), getResources().getString(C1178R.string.es_guests)}));
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                this.binding.text.setText(String.format(textFormat, new Object[]{Integer.valueOf(percent), getResources().getString(C1178R.string.de_guests)}));
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                this.binding.text.setText(String.format(textFormat, new Object[]{Integer.valueOf(percent), getResources().getString(C1178R.string.fr_guests)}));
            case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
                this.binding.text.setText(String.format(textFormat, new Object[]{Integer.valueOf(percent), getResources().getString(C1178R.string.it_guests)}));
            case C1822R.styleable.MapAttrs_cameraZoom /*5*/:
                this.binding.text.setText(String.format(textFormat, new Object[]{Integer.valueOf(percent), getResources().getString(C1178R.string.pt_guests)}));
            case C1822R.styleable.MapAttrs_liteMode /*6*/:
                this.binding.text.setText(String.format(textFormat, new Object[]{Integer.valueOf(percent), getResources().getString(C1178R.string.th_guests)}));
            case C1822R.styleable.MapAttrs_uiCompass /*7*/:
                this.binding.text.setText(String.format(textFormat, new Object[]{Integer.valueOf(percent), getResources().getString(C1178R.string.ko_guests)}));
            case C1822R.styleable.MapAttrs_uiRotateGestures /*8*/:
                this.binding.text.setText(String.format(textFormat, new Object[]{Integer.valueOf(percent), getResources().getString(C1178R.string.zh_guests)}));
            case C1822R.styleable.MapAttrs_uiScrollGestures /*9*/:
                this.binding.text.setText(String.format(textFormat, new Object[]{Integer.valueOf(percent), getResources().getString(C1178R.string.ja_guests)}));
            default:
                Timber.m753e("Not supported for display in language: %s", language);
                setVisibility(8);
        }
    }
}
