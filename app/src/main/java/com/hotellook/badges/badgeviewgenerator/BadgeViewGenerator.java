package com.hotellook.badges.badgeviewgenerator;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.devspark.robototextview.util.RobotoTypefaceManager;
import com.devspark.robototextview.util.RobotoTypefaceUtils;
import com.devspark.robototextview.widget.RobotoTextView;
import com.hotellook.C1178R;
import com.hotellook.badges.badgeviewgenerator.ViewParams.Builder;

public class BadgeViewGenerator {
    private static final int LARGE_BADGE_HEIGHT = 24;
    private static final int LARGE_BOTTOM_PADDING = 3;
    private static final int LARGE_CORNER_RADIUS = 2;
    private static final int LARGE_IMAGE_SIDE_PADDING = 6;
    private static final int LARGE_IMAGE_VERTICAL_PADDING = 7;
    private static final int LARGE_SIDE_PADDING = 8;
    private static final int LARGE_TEXT_SIZE = 14;
    private static final int LARGE_TOP_PADDING = 2;
    private static final int SMALL_BADGE_HEIGHT = 16;
    private static final int SMALL_BOTTOM_PADDING = 4;
    private static final int SMALL_CORNER_RADIUS = 1;
    private static final int SMALL_IMAGE_VERTICAL_PADDING = 4;
    private static final int SMALL_SIDE_PADDING = 4;
    private static final int SMALL_TEXT_SIZE = 10;
    private static final int SMALL_TOP_PADDING = 0;
    private final Context context;
    private final float density;
    private final ViewParams imageViewParams;
    private final float[] rectRadiusArray;
    private final int textColor;
    private final ViewParams textViewParams;

    private BadgeViewGenerator(Context context, ViewParams textViewParams, ViewParams imageViewParams) {
        this.context = context.getApplicationContext();
        this.textViewParams = textViewParams;
        this.imageViewParams = imageViewParams;
        this.textColor = ContextCompat.getColor(context, 17170443);
        this.density = this.context.getResources().getDisplayMetrics().density;
        int radius = (int) (2.0f * this.density);
        float[] fArr = new float[LARGE_SIDE_PADDING];
        fArr[0] = (float) radius;
        fArr[SMALL_CORNER_RADIUS] = (float) radius;
        fArr[LARGE_TOP_PADDING] = (float) radius;
        fArr[LARGE_BOTTOM_PADDING] = (float) radius;
        fArr[SMALL_SIDE_PADDING] = (float) radius;
        fArr[5] = (float) radius;
        fArr[LARGE_IMAGE_SIDE_PADDING] = (float) radius;
        fArr[LARGE_IMAGE_VERTICAL_PADDING] = (float) radius;
        this.rectRadiusArray = fArr;
    }

    public static BadgeViewGenerator withLargeView(Context context) {
        return new BadgeViewGenerator(context, buildLargeTextViewParams(), buildLargeImageViewParams());
    }

    public static BadgeViewGenerator withSmallView(Context context) {
        return new BadgeViewGenerator(context, buildSmallTextViewParams(), buildSmallImageViewParams());
    }

    private static ViewParams buildLargeImageViewParams() {
        Builder builder = new Builder();
        builder.height(LARGE_BADGE_HEIGHT).bottom(LARGE_IMAGE_VERTICAL_PADDING).left(LARGE_IMAGE_SIDE_PADDING).right(LARGE_IMAGE_SIDE_PADDING).top(LARGE_IMAGE_VERTICAL_PADDING).cornerRadius(LARGE_TOP_PADDING);
        return builder.build();
    }

    private static ViewParams buildLargeTextViewParams() {
        Builder builder = new Builder();
        builder.height(LARGE_BADGE_HEIGHT).bottom(LARGE_BOTTOM_PADDING).left(LARGE_SIDE_PADDING).right(LARGE_SIDE_PADDING).top(LARGE_TOP_PADDING).textSize(LARGE_TEXT_SIZE).cornerRadius(LARGE_TOP_PADDING);
        return builder.build();
    }

    private static ViewParams buildSmallImageViewParams() {
        Builder builder = new Builder();
        builder.height(SMALL_BADGE_HEIGHT).bottom(SMALL_SIDE_PADDING).left(SMALL_SIDE_PADDING).right(SMALL_SIDE_PADDING).top(SMALL_SIDE_PADDING).cornerRadius(SMALL_CORNER_RADIUS);
        return builder.build();
    }

    private static ViewParams buildSmallTextViewParams() {
        Builder builder = new Builder();
        builder.height(SMALL_BADGE_HEIGHT).bottom(SMALL_SIDE_PADDING).left(SMALL_SIDE_PADDING).right(SMALL_SIDE_PADDING).top(0).textSize(SMALL_TEXT_SIZE).cornerRadius(SMALL_CORNER_RADIUS);
        return builder.build();
    }

    public View generateView(String text, int color) {
        RobotoTextView textView = new RobotoTextView(this.context);
        textView.setLayoutParams(new LayoutParams(-2, (int) (((float) this.textViewParams.height) * this.density)));
        setPaddings(textView, this.textViewParams);
        textView.setText(text);
        textView.setTextColor(this.textColor);
        textView.setTextSize(SMALL_CORNER_RADIUS, (float) this.textViewParams.textSize);
        setTypeface(textView);
        textView.setBackground(getBadgeBkgDrawable(color));
        return textView;
    }

    private void setTypeface(RobotoTextView textView) {
        RobotoTypefaceUtils.setUp((TextView) textView, RobotoTypefaceManager.obtainTypeface(this.context, SMALL_SIDE_PADDING));
    }

    @NonNull
    private ShapeDrawable getBadgeBkgDrawable(int color) {
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.getPaint().setColor(color);
        shapeDrawable.setShape(new RoundRectShape(this.rectRadiusArray, null, null));
        return shapeDrawable;
    }

    public View generateFavoriteBadge() {
        ImageView imageView = new ImageView(this.context);
        imageView.setLayoutParams(new LayoutParams(-2, (int) (((float) this.textViewParams.height) * this.density)));
        imageView.setImageDrawable(ContextCompat.getDrawable(this.context, C1178R.drawable.ic_favorite_badge));
        imageView.setBackground(getBadgeBkgDrawable(ContextCompat.getColor(this.context, C1178R.color.red_BFD0021B)));
        setPaddings(imageView, this.imageViewParams);
        return imageView;
    }

    private void setPaddings(View view, ViewParams viewParams) {
        view.setPadding((int) (((float) viewParams.left) * this.density), (int) (((float) viewParams.top) * this.density), (int) (((float) viewParams.right) * this.density), (int) (((float) viewParams.bottom) * this.density));
    }
}
