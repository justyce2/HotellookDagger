package com.hotellook.ui.dialog.error;

import android.support.annotation.Nullable;
import android.view.View.OnClickListener;
import com.hotellook.C1178R;
import com.hotellook.ui.dialog.OneButtonDialogContent;
import pl.charmas.android.reactivelocation.C1822R;
import retrofit.RetrofitError;
import retrofit.RetrofitError.Kind;

public class NetworkErrorDialogContentFactory {

    /* renamed from: com.hotellook.ui.dialog.error.NetworkErrorDialogContentFactory.1 */
    static /* synthetic */ class C12131 {
        static final /* synthetic */ int[] $SwitchMap$retrofit$RetrofitError$Kind;

        static {
            $SwitchMap$retrofit$RetrofitError$Kind = new int[Kind.values().length];
            try {
                $SwitchMap$retrofit$RetrofitError$Kind[Kind.NETWORK.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$retrofit$RetrofitError$Kind[Kind.CONVERSION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$retrofit$RetrofitError$Kind[Kind.HTTP.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$retrofit$RetrofitError$Kind[Kind.UNEXPECTED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public OneButtonDialogContent getDialogContent(@Nullable RetrofitError error, OnClickListener clickListener) {
        int title = 0;
        int message = 0;
        int btn = 0;
        if (error != null) {
            switch (C12131.$SwitchMap$retrofit$RetrofitError$Kind[error.getKind().ordinal()]) {
                case C1822R.styleable.SignInButton_colorScheme /*1*/:
                    title = C1178R.string.error_title_network;
                    message = C1178R.string.error_message_network;
                    btn = C1178R.string.error_btn_network;
                    break;
                case C1822R.styleable.SignInButton_scopeUris /*2*/:
                case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                case C1822R.styleable.MapAttrs_cameraTilt /*4*/:
                    title = C1178R.string.error_title_server;
                    message = C1178R.string.error_message_server;
                    btn = C1178R.string.error_btn_server;
                    break;
                default:
                    break;
            }
        }
        title = C1178R.string.error_title_server;
        message = C1178R.string.error_message_server;
        btn = C1178R.string.error_btn_server;
        return new OneButtonDialogContent(title, message, btn, clickListener);
    }
}
