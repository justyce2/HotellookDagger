package com.hotellook.ui.screen.hotel.distance;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.hotellook.C1178R;
import com.hotellook.databinding.DistanceItemViewBinding;
import com.hotellook.utils.StringUtils;
import com.hotellook.utils.ValueFormat;
import pl.charmas.android.reactivelocation.C1822R;

public class DistanceItemView extends LinearLayout {
    private DistanceItemViewBinding binding;

    public DistanceItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static DistanceItemView create(@NonNull ViewGroup parent) {
        return (DistanceItemView) LayoutInflater.from(parent.getContext()).inflate(C1178R.layout.distance_item_view, parent, false);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.binding = (DistanceItemViewBinding) DataBindingUtil.bind(this);
        }
    }

    public void bindTo(@NonNull DistanceItem item) {
        setIcon(item.poiType);
        this.binding.text.setText(StringUtils.capitalize(item.poiName));
        this.binding.distance.setText(ValueFormat.distanceToString(getContext(), item.distance));
    }

    private void setIcon(int poiType) {
        switch (poiType) {
            case C1822R.styleable.SignInButton_buttonSize /*0*/:
                this.binding.icon.setImageResource(C1178R.drawable.ic_distances_airport);
            case C1822R.styleable.SignInButton_colorScheme /*1*/:
                this.binding.icon.setImageResource(C1178R.drawable.ic_distances_beach);
            case C1822R.styleable.SignInButton_scopeUris /*2*/:
                this.binding.icon.setImageResource(C1178R.drawable.ic_distances_metro_station);
            case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                this.binding.icon.setImageResource(C1178R.drawable.ic_distances_ski_lift);
            case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
                this.binding.icon.setImageResource(C1178R.drawable.ic_distances_train_station);
            case C1822R.styleable.MapAttrs_cameraZoom /*5*/:
                this.binding.icon.setImageResource(C1178R.drawable.ic_distances_city_center);
            case C1822R.styleable.MapAttrs_liteMode /*6*/:
                this.binding.icon.setImageResource(C1178R.drawable.ic_distances_my_location);
            case C1822R.styleable.MapAttrs_uiCompass /*7*/:
                this.binding.icon.setImageResource(C1178R.drawable.ic_distances_pin);
            default:
        }
    }
}
