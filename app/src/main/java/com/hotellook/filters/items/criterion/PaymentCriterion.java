package com.hotellook.filters.items.criterion;

import android.support.annotation.Nullable;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.core.api.pojo.search.OptionsData;

public class PaymentCriterion implements Criterion<Offer> {
    private final boolean checkCardNotRequired;
    private final boolean checkPayLater;
    private final boolean checkPayNow;

    public PaymentCriterion(boolean payNow, boolean payLater, boolean cardNotRequired) {
        this.checkPayNow = payNow;
        this.checkPayLater = payLater;
        this.checkCardNotRequired = cardNotRequired;
    }

    public static PaymentCriterion payNow() {
        return new PaymentCriterion(true, false, false);
    }

    public static PaymentCriterion payLater() {
        return new PaymentCriterion(false, true, false);
    }

    public static PaymentCriterion cardNotRequired() {
        return new PaymentCriterion(false, false, true);
    }

    public boolean passes(Offer value) {
        boolean z = true;
        OptionsData options = value.getOptions();
        if (this.checkPayNow && this.checkCardNotRequired) {
            return false;
        }
        if (!this.checkPayNow || !this.checkPayLater) {
            if ((this.checkPayNow && !isPayNow(options)) || ((this.checkPayLater && !isPayLater(options)) || (this.checkCardNotRequired && !isCardNotRequired(options)))) {
                z = false;
            }
            return z;
        } else if (isPayNow(options) || isPayLater(options)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isPayNow(@Nullable OptionsData options) {
        return hasPaymentInfo(options) && options.isPaymentNow();
    }

    private boolean isPayLater(@Nullable OptionsData options) {
        return hasPaymentInfo(options) && options.isPaymentInHotel();
    }

    private boolean isCardNotRequired(@Nullable OptionsData options) {
        return options != null && options.isCardNotRequired();
    }

    private boolean hasPaymentInfo(@Nullable OptionsData options) {
        return options != null && options.hasPaymentInfo();
    }
}
