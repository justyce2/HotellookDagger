package com.hotellook.common.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class BaseDialogFragment extends DialogFragment {
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }
}
