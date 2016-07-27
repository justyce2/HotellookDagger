package com.hotellook.ui.screen.filters;

import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;

public class SearchViewKeyboardController {
    private final InputMethodManager imm;
    private boolean keyboardPossiblyVisible;
    private final View search;

    /* renamed from: com.hotellook.ui.screen.filters.SearchViewKeyboardController.1 */
    class C12591 implements OnTouchListener {
        C12591() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            SearchViewKeyboardController.this.keyboardPossiblyVisible = true;
            return false;
        }
    }

    /* renamed from: com.hotellook.ui.screen.filters.SearchViewKeyboardController.2 */
    class C12602 implements Runnable {
        final /* synthetic */ Fragment val$fragment;

        C12602(Fragment fragment) {
            this.val$fragment = fragment;
        }

        public void run() {
            if (this.val$fragment.getActivity() != null) {
                SearchViewKeyboardController.this.showKeyboard();
            }
        }
    }

    public SearchViewKeyboardController(View search) {
        this.keyboardPossiblyVisible = false;
        this.search = search;
        this.imm = (InputMethodManager) this.search.getContext().getSystemService("input_method");
        this.search.setOnTouchListener(new C12591());
    }

    public void hideKeyboard() {
        this.imm.hideSoftInputFromWindow(this.search.getWindowToken(), 0);
        this.keyboardPossiblyVisible = false;
    }

    public void showKeyboard() {
        this.imm.showSoftInput(this.search, 1);
        this.keyboardPossiblyVisible = true;
    }

    public void showKeyboardWithDelay(Fragment fragment) {
        this.search.postDelayed(new C12602(fragment), 400);
    }

    public boolean isKeyboardPossiblyVisible() {
        return this.keyboardPossiblyVisible;
    }
}
