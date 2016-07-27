package com.hotellook.ui.screen.currency;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.currency.CurrencyInfo;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.Dialogs;
import com.hotellook.ui.screen.Toasts;
import com.hotellook.ui.screen.common.BaseMvpFragment;
import com.hotellook.ui.screen.filters.radiogroup.FiltersRadioButton;
import com.hotellook.ui.screen.filters.radiogroup.FiltersRadioGroup;
import com.hotellook.ui.screen.filters.radiogroup.FiltersRadioGroup.OnCheckedChangeListener;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.ViewId;
import com.hotellook.utils.ViewUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyFragment extends BaseMvpFragment<CurrenciesView, CurrenciesPresenter> implements CurrenciesView, OnCheckedChangeListener {
    private AlertDialog alertDialog;
    private CurrenciesComponent component;
    private final Map<Integer, CurrencyInfo> currenciesMap;
    private FiltersRadioGroup filtersRadioGroup;
    public View scrollContent;

    public CurrencyFragment() {
        this.currenciesMap = new HashMap();
    }

    private void createComponent() {
        this.component = DaggerCurrenciesComponent.builder().hotellookComponent(appComponent()).build();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createComponent();
    }

    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(C1178R.layout.fragment_currency, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adjustOffsets(view);
        setUpToolbar();
        this.filtersRadioGroup = (FiltersRadioGroup) view.findViewById(C1178R.id.currency_group);
        this.filtersRadioGroup.setOnCheckedChangeListener(this);
        this.scrollContent = view.findViewById(C1178R.id.scroll_content);
        ViewUtils.addLeftAndRightPaddingsForWideScreen(this.scrollContent);
        ((CurrenciesPresenter) getPresenter()).loadCurrencies();
    }

    public void showCurrencies(String currentCurrency, List<CurrencyInfo> currencyList) {
        ViewId viewId = new ViewId();
        Context context = getActivity();
        for (CurrencyInfo currencyViewModel : currencyList) {
            FiltersRadioButton radioButton = new FiltersRadioButton(context, (int) C1178R.layout.radiobutton_two_lines);
            if (currentCurrency.equals(currencyViewModel.code)) {
                radioButton.setChecked(true);
            }
            radioButton.setTitle(currencyViewModel.code.toUpperCase());
            radioButton.setSubtitle(currencyViewModel.resNameId);
            int id = viewId.getUniqueId();
            radioButton.setId(id);
            this.currenciesMap.put(Integer.valueOf(id), currencyViewModel);
            this.filtersRadioGroup.addRadioButton(radioButton);
        }
    }

    public void onCheckedChanged(FiltersRadioGroup group, int checkedId) {
        ((CurrenciesPresenter) getPresenter()).currencySelected((CurrencyInfo) this.currenciesMap.get(Integer.valueOf(checkedId)));
        mainActivity().onBackPressed();
    }

    public void showToastNotActualResults() {
        Toasts.showCurrencyAlertToast(getContext());
    }

    public void showDialogNotActualResults() {
        this.alertDialog = Dialogs.showCurrencyChangedDialog(mainActivity());
    }

    public CurrenciesPresenter createPresenter() {
        return this.component.presenter();
    }

    public void onDestroyView() {
        if (this.alertDialog != null) {
            this.alertDialog.dismiss();
        }
        super.onDestroyView();
    }

    private void adjustOffsets(@NonNull View layout) {
        AndroidUtils.addMarginToPlaceViewBelowStatusBar(layout);
        AndroidUtils.addMarginToPlaceViewBelowToolbar(layout);
    }

    private void setUpToolbar() {
        TextView title = (TextView) LayoutInflater.from(getActivity()).inflate(C1178R.layout.toolbar_title, mainActivity().getToolbarManager().getToolbar(), false);
        title.setText(getString(C1178R.string.title_currency));
        title.setTextColor(getResources().getColor(17170443));
        MainActivity activity = mainActivity();
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().withShadow().navigationMode(1).bkgColor(getResources().getColor(C1178R.color.toolbar_green_bkg)).statusBarColor(getResources().getColor(C1178R.color.spf_statusbar_bkg)).toggleColor(getResources().getColor(C1178R.color.sr_toggle)).withCustomView(title));
    }
}
