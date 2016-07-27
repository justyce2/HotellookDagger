package com.hotellook.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;

public final class Clipboard {
    private static final String ADDRESS_LABEL = "Address";
    private final ClipboardManager clipboardManager;

    private Clipboard(@NonNull Context context) {
        this.clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
        Preconditions.checkNotNull(this.clipboardManager, "ClipboardManager not found.");
    }

    @NonNull
    public static Clipboard from(@NonNull Context context) {
        return new Clipboard(context);
    }

    public void copyAddress(@NonNull String address) {
        this.clipboardManager.setPrimaryClip(ClipData.newPlainText(ADDRESS_LABEL, address));
    }
}
