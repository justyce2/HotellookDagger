package com.hotellook.ui.screen.hotel.prices;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.devspark.robototextview.widget.RobotoTextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.api.GateLogoURIProvider;
import com.hotellook.api.GateLogoURIProvider.Gravity;
import com.hotellook.common.OfferUtils;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.core.api.pojo.search.OptionsData;
import com.hotellook.currency.CurrencyFormatter;
import com.hotellook.currency.CurrencyRepository;
import com.hotellook.databinding.OfferViewBinding;
import com.hotellook.events.BookRequestEvent;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.search.SearchParams;
import com.hotellook.ui.screen.hotel.FrameOpenSource;
import com.hotellook.utils.StringUtils;

public class OfferView extends RelativeLayout {
    public static final int RESIZE_ANIMATION_DURATION = 200;
    public static final int TIMEOUT_TO_SHOW_TEXT_INSTEAD_LOGO = 4000;
    private OfferViewBinding binding;
    private CurrencyFormatter currencyFormatter;
    private CurrencyRepository currencyRepository;
    @Nullable
    private DiscountsByRooms discounts;
    private boolean extendedMode;
    @Nullable
    private HighlightsByRooms highlights;
    private ControllerListener listener;
    private Offer roomResult;
    private SearchParams searchParams;
    private Runnable timeoutAction;

    /* renamed from: com.hotellook.ui.screen.hotel.prices.OfferView.1 */
    class C13251 extends MonkeySafeClickListener {
        C13251() {
        }

        public void onSafeClick(View v) {
            HotellookApplication.eventBus().post(new BookRequestEvent(OfferView.this.searchParams, OfferView.this.roomResult, OfferView.this.currencyRepository.currencyCode(), FrameOpenSource.RATES));
        }
    }

    /* renamed from: com.hotellook.ui.screen.hotel.prices.OfferView.2 */
    class C13262 extends BaseControllerListener {
        C13262() {
        }

        public void onFailure(String id, Throwable throwable) {
            super.onFailure(id, throwable);
            OfferView.this.binding.gateLogo.removeCallbacks(OfferView.this.timeoutAction);
            OfferView.this.animateLogoToText();
        }

        public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
            super.onFinalImageSet(id, imageInfo, animatable);
            OfferView.this.binding.gateLogo.removeCallbacks(OfferView.this.timeoutAction);
            OfferView.this.animateTextToLogo();
        }
    }

    public OfferView(Context context) {
        this(context, null);
    }

    public OfferView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.timeoutAction = OfferView$$Lambda$1.lambdaFactory$(this);
        this.listener = createImageControllerListener();
        if (!isInEditMode()) {
            init();
        }
    }

    private void init() {
        this.currencyRepository = HotellookApplication.getApp().getComponent().currencyRepository();
        this.currencyFormatter = new CurrencyFormatter(this.currencyRepository);
    }

    public void animateTextToLogo() {
        if (this.binding.gateLogo.getAlpha() == 0.0f && this.binding.gateName.getAlpha() == 1.0f) {
            this.binding.gateName.animate().alpha(0.0f);
            this.binding.gateLogo.animate().alpha(1.0f);
        }
    }

    public void animateLogoToText() {
        if (this.binding.gateLogo.getAlpha() == 1.0f && this.binding.gateName.getAlpha() == 0.0f) {
            this.binding.gateName.animate().alpha(1.0f);
            this.binding.gateLogo.animate().alpha(0.0f);
        }
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            this.binding = (OfferViewBinding) DataBindingUtil.bind(this);
        }
    }

    private int getGateId() {
        if (isInEditMode()) {
            return 0;
        }
        return this.roomResult.getGateId();
    }

    public void setData(SearchParams searchParams, Offer room, String roomName, boolean isBestOffer, boolean extendedMode, DiscountsByRooms discounts, HighlightsByRooms highlights) {
        this.searchParams = searchParams;
        this.roomResult = room;
        this.extendedMode = extendedMode;
        this.discounts = discounts;
        this.highlights = highlights;
        postLoadImage();
        postLogoLoadingTimeoutAction();
        fillRoomName(roomName);
        fillPriceViews(isBestOffer);
        fillOptions();
        fillGateName();
        fillBookingBtn();
        setOnClickListener();
    }

    private void fillBookingBtn() {
        this.binding.bookingBtn.setVisibility(this.extendedMode ? 0 : 8);
        if (this.extendedMode) {
            this.binding.bookingBtn.getBackground().setColorFilter(HotellookApplication.getApp().getComponent().getABTesting().getCTAButtonColor(), Mode.SRC_ATOP);
        }
    }

    private void setOnClickListener() {
        OnClickListener onBookClickListener = createOnBookClickListener();
        if (this.extendedMode) {
            this.binding.bookingBtn.setOnClickListener(onBookClickListener);
        } else {
            this.binding.clickable.setOnClickListener(onBookClickListener);
        }
    }

    @NonNull
    private OnClickListener createOnBookClickListener() {
        return new C13251();
    }

    private void fillRoomName(String roomName) {
        if (roomName != null) {
            this.binding.roomName.setText(roomName);
            this.binding.roomName.setVisibility(0);
            return;
        }
        this.binding.roomName.setVisibility(8);
    }

    private void fillGateName() {
        this.binding.gateName.setText(this.roomResult.getGateName());
        this.binding.gateName.setAlpha(0.0f);
    }

    private void fillOptions() {
        if (getOptions() != null) {
            if (!StringUtils.isBlank(getOptions().getViewSentence())) {
                addOptionView(getOptions().getViewSentence(), false);
            }
            if (getOptions().hasBreakfast()) {
                addOptionView((int) C1178R.string.hotel_room_option_breakfast, false);
            }
            if (getOptions().isFreeWifi()) {
                addOptionView((int) C1178R.string.hotel_room_option_wifi, false);
            }
            if (getOptions().isSmoking()) {
                addOptionView((int) C1178R.string.hotel_room_option_smoking, false);
            }
            if (getOptions().hasHotelWebsite()) {
                addOptionView((int) C1178R.string.option_hotel_online_payment, false);
            }
            if (getOptions().hasPaymentInfo()) {
                if (getOptions().isPaymentInHotel()) {
                    addOptionView((int) C1178R.string.hotel_room_option_pay_in_hotel, false);
                } else if (getOptions().isPaymentNow() && !getOptions().isCardNotRequired()) {
                    addOptionView((int) C1178R.string.hotel_room_option_pay_now, false);
                }
            }
            if (getOptions().isRefundable()) {
                addOptionView((int) C1178R.string.hotel_room_option_refund, false);
            }
            if (getOptions().isCardNotRequired()) {
                addOptionView((int) C1178R.string.hotel_room_option_card_not_required, false);
            }
            if (getOptions().hasPrivatePrice()) {
                addOptionView((int) C1178R.string.hotel_room_option_private_price, true);
            }
            if (getOptions().getAvailableRooms() != 0) {
                addOptionView(getAvailableRoomsTxt(getOptions().getAvailableRooms()), true);
            }
        }
    }

    private String getAvailableRoomsTxt(int availableRooms) {
        return getResources().getQuantityString(C1178R.plurals.hotel_prices_last_rooms, availableRooms, new Object[]{Integer.valueOf(availableRooms)});
    }

    private OptionsData getOptions() {
        return this.roomResult.getOptions();
    }

    private void addOptionView(String optionTxt, boolean highlight) {
        View optionView = inflateOptionView();
        ImageView checkMark = (ImageView) optionView.findViewById(C1178R.id.check_mark);
        TextView optionText = (TextView) optionView.findViewById(C1178R.id.option_name);
        optionText.setText(optionTxt);
        if (highlight) {
            int attentionColor = ContextCompat.getColor(getContext(), C1178R.color.orange_FFB433);
            checkMark.setColorFilter(attentionColor, Mode.SRC_ATOP);
            optionText.setTextColor(attentionColor);
        }
        this.binding.gateOptionsContainer.addView(optionView);
    }

    private void addOptionView(@StringRes int optionStrRes, boolean highlight) {
        addOptionView(getResources().getString(optionStrRes), highlight);
    }

    private View inflateOptionView() {
        return LayoutInflater.from(getContext()).inflate(C1178R.layout.gate_option_layout, this.binding.gateOptionsContainer, false);
    }

    private void postLogoLoadingTimeoutAction() {
        this.binding.gateLogo.removeCallbacks(this.timeoutAction);
        this.binding.gateLogo.postDelayed(this.timeoutAction, 4000);
    }

    private void postLoadImage() {
        post(OfferView$$Lambda$4.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$postLoadImage$0() {
        this.binding.gateLogo.setController(((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) Fresco.newDraweeControllerBuilder().setUri(getUri()).setOldController(this.binding.gateLogo.getController())).setControllerListener(this.listener)).build());
    }

    private void fillPriceViews(boolean isBestOffer) {
        if (OfferUtils.hasValidDiscount(this.roomResult, this.discounts, this.highlights)) {
            adjustPricesVisibility(true);
            fillBeforeDiscountPrice();
            fillDiscountPrice();
        } else if (isBestOffer && OfferUtils.hasPrivatePrice(this.roomResult)) {
            fillPrice();
            highlightOfferPrice();
        } else {
            adjustPricesVisibility(false);
            fillPrice();
        }
    }

    private void highlightOfferPrice() {
        this.binding.price.setTextColor(getResources().getColor(C1178R.color.red_B3D0021A));
        this.binding.price.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(C1178R.drawable.ic_flash), null, null, null);
    }

    private void adjustPricesVisibility(boolean withDiscount) {
        int i;
        int i2 = 8;
        RobotoTextView robotoTextView = this.binding.discountPrice;
        if (withDiscount) {
            i = 0;
        } else {
            i = 8;
        }
        robotoTextView.setVisibility(i);
        robotoTextView = this.binding.beforeDiscountPrice;
        if (withDiscount) {
            i = 0;
        } else {
            i = 8;
        }
        robotoTextView.setVisibility(i);
        RobotoTextView robotoTextView2 = this.binding.price;
        if (!withDiscount) {
            i2 = 0;
        }
        robotoTextView2.setVisibility(i2);
    }

    private void fillPrice() {
        this.binding.price.setText(this.currencyFormatter.formatPrice(this.roomResult.getPrice(), getCurrency()));
    }

    private void fillDiscountPrice() {
        this.binding.discountPrice.setText(this.currencyFormatter.formatPrice(this.roomResult.getPrice(), getCurrency()));
    }

    private void fillBeforeDiscountPrice() {
        RobotoTextView priceTv = this.binding.beforeDiscountPrice;
        priceTv.setText(this.currencyFormatter.formatPrice(this.discounts.get(this.roomResult).getOldPrice(), getCurrency()));
        priceTv.setPaintFlags(priceTv.getPaintFlags() | 16);
    }

    private String getCurrency() {
        return this.searchParams.currency();
    }

    @NonNull
    private Uri getUri() {
        return GateLogoURIProvider.getURI(getGateId(), this.binding.gateLogo.getWidth(), this.binding.gateLogo.getHeight(), Gravity.WEST);
    }

    @NonNull
    private BaseControllerListener createImageControllerListener() {
        return new C13262();
    }
}
