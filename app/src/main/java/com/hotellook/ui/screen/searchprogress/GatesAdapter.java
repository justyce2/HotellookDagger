package com.hotellook.ui.screen.searchprogress;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hotellook.C1178R;
import com.hotellook.api.GateLogoURIProvider;
import com.hotellook.api.GateLogoURIProvider.Gravity;
import com.hotellook.utils.Animators;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class GatesAdapter extends Adapter<ViewHolder> {
    private static final int GATE_HOTELS_WEBSITES = -1;
    private static final int POSITION_HEADER = 0;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private final List<Integer> gatesToShow;
    private final IndeterminateProgressDrawable indeterminateProgressDrawable;
    private Set<Integer> loadedGates;
    private final int logoHeight;
    private final int logoWidth;
    private List<Integer> originalGates;

    class GateViewHolder extends ViewHolder {
        Animator animation;
        @BindView
        ImageView checkMark;
        int gateId;
        @BindView
        TextView hotelsWebsites;
        boolean loaded;
        @BindView
        SimpleDraweeView logo;
        @BindView
        MaterialProgressBar progressBar;

        public GateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HeaderViewHolder extends ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    public GatesAdapter(Context context) {
        this.gatesToShow = new ArrayList();
        this.originalGates = Collections.emptyList();
        this.loadedGates = Collections.emptySet();
        setHasStableIds(true);
        this.logoWidth = context.getResources().getDimensionPixelSize(C1178R.dimen.gate_logo_width);
        this.logoHeight = context.getResources().getDimensionPixelSize(C1178R.dimen.gate_logo_height);
        this.indeterminateProgressDrawable = new IndeterminateProgressDrawable(context);
        this.indeterminateProgressDrawable.setTint(ContextCompat.getColor(context, C1178R.color.gray_BDBDBD));
        this.indeterminateProgressDrawable.start();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) {
            return new HeaderViewHolder(inflater.inflate(C1178R.layout.gates_header, parent, false));
        }
        if (viewType == TYPE_ITEM) {
            ViewHolder gateViewHolder = new GateViewHolder(inflater.inflate(C1178R.layout.gate_progress_item, parent, false));
            gateViewHolder.progressBar.setBackground(this.indeterminateProgressDrawable);
            return gateViewHolder;
        }
        throw new IllegalArgumentException("viewType[" + viewType + "] is unknown");
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position != 0) {
            int i;
            GateViewHolder gateViewHolder = (GateViewHolder) holder;
            int gateId = ((Integer) this.gatesToShow.get(position + GATE_HOTELS_WEBSITES)).intValue();
            boolean prevStateLoaded = gateViewHolder.loaded;
            boolean curStateLoaded = this.loadedGates.contains(Integer.valueOf(gateId));
            long prevHolderGateId = (long) gateViewHolder.gateId;
            resetRunningAnimation(gateViewHolder);
            if (prevHolderGateId == ((long) gateId) && !prevStateLoaded && curStateLoaded) {
                animateToLoaded(gateViewHolder);
            } else {
                gateViewHolder.checkMark.setVisibility(curStateLoaded ? TYPE_HEADER : 8);
                gateViewHolder.progressBar.setVisibility(curStateLoaded ? 8 : TYPE_HEADER);
            }
            DraweeView logo = gateViewHolder.logo;
            logo.setController(((PipelineDraweeControllerBuilder) Fresco.newDraweeControllerBuilder().setUri(getUri(gateId)).setOldController(logo.getController())).build());
            gateViewHolder.loaded = curStateLoaded;
            gateViewHolder.gateId = gateId;
            boolean isHotelsWebsites = gateId == GATE_HOTELS_WEBSITES;
            gateViewHolder.logo.setVisibility(isHotelsWebsites ? 8 : TYPE_HEADER);
            TextView textView = gateViewHolder.hotelsWebsites;
            if (isHotelsWebsites) {
                i = TYPE_HEADER;
            } else {
                i = 8;
            }
            textView.setVisibility(i);
        }
    }

    @NonNull
    private Uri getUri(int gateId) {
        return GateLogoURIProvider.getURI(gateId, this.logoWidth, this.logoHeight, Gravity.WEST);
    }

    private void resetRunningAnimation(GateViewHolder gateViewHolder) {
        Animator animation = gateViewHolder.animation;
        if (animation != null && animation.isRunning()) {
            animation.end();
        }
    }

    private void animateToLoaded(GateViewHolder gateViewHolder) {
        Animators.createCrossFadeAnimator(gateViewHolder.checkMark, gateViewHolder.progressBar, 8).setDuration(100);
        ValueAnimator scaleAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        scaleAnimator.setInterpolator(new OvershootInterpolator());
        scaleAnimator.addUpdateListener(GatesAdapter$$Lambda$1.lambdaFactory$(gateViewHolder));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{crossFadeAnimator, scaleAnimator});
        animatorSet.start();
        gateViewHolder.animation = animatorSet;
    }

    static /* synthetic */ void lambda$animateToLoaded$0(GateViewHolder gateViewHolder, ValueAnimator animation) {
        float animatedFraction = animation.getAnimatedFraction();
        gateViewHolder.checkMark.setScaleX(animatedFraction);
        gateViewHolder.checkMark.setScaleY(animatedFraction);
    }

    public int getItemCount() {
        return this.gatesToShow.size() + TYPE_ITEM;
    }

    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public void updateLoadedGates(@NonNull Set<Integer> loadedGates) {
        if (loadedGates.containsAll(this.originalGates)) {
            loadedGates.add(Integer.valueOf(GATE_HOTELS_WEBSITES));
        }
        this.loadedGates = loadedGates;
        notifyDataSetChanged();
    }

    public void updateGates(@NonNull List<Integer> gates) {
        this.originalGates = gates;
        this.gatesToShow.clear();
        this.gatesToShow.addAll(this.originalGates);
        this.gatesToShow.add(Integer.valueOf(GATE_HOTELS_WEBSITES));
        notifyDataSetChanged();
    }
}
