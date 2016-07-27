package com.hotellook.ui.screen.browser;

import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.common.dialog.BaseDialogFragment;

public class BookingLoadingDialogFragment extends BaseDialogFragment {
    private static final String GATE_NAME = "gate name";
    public static final String TAG;
    private String gateName;
    private OnKeyListener keyListener;

    /* renamed from: com.hotellook.ui.screen.browser.BookingLoadingDialogFragment.1 */
    class C12151 implements Runnable {
        final /* synthetic */ ViewGroup val$layout;

        C12151(ViewGroup viewGroup) {
            this.val$layout = viewGroup;
        }

        public void run() {
            if (BookingLoadingDialogFragment.this.getActivity() != null) {
                this.val$layout.getLayoutParams().width = BookingLoadingDialogFragment.this.getResources().getDimensionPixelSize(C1178R.dimen.browser_dialog_width);
                this.val$layout.requestLayout();
            }
        }
    }

    private class BackHandlerKeyListener implements OnKeyListener {
        private BackHandlerKeyListener() {
        }

        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() != 4 || BookingLoadingDialogFragment.this.getActivity() == null) {
                return false;
            }
            BookingLoadingDialogFragment.this.getActivity().onBackPressed();
            return true;
        }
    }

    public BookingLoadingDialogFragment() {
        this.keyListener = new BackHandlerKeyListener();
    }

    static {
        TAG = BookingLoadingDialogFragment.class.getSimpleName();
    }

    public static BookingLoadingDialogFragment create(String agency) {
        BookingLoadingDialogFragment dialog = new BookingLoadingDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GATE_NAME, agency);
        dialog.setArguments(bundle);
        return dialog;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(1, C1178R.style.GreenDialogFragment);
        setCancelable(false);
        this.gateName = getArguments().getString(GATE_NAME);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(C1178R.layout.browser_loading_dialog, container, false);
        ((TextView) layout.findViewById(C1178R.id.gate_name)).setText(getAgencyText());
        getDialog().setOnKeyListener(this.keyListener);
        layout.post(new C12151(layout));
        return layout;
    }

    private String getAgencyText() {
        return String.format(getString(C1178R.string.browser_loading_agency), new Object[]{this.gateName});
    }

    public void setKeyListener(OnKeyListener keyListener) {
        this.keyListener = keyListener;
    }
}
