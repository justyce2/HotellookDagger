package com.hotellook.utils.sdk;

import android.widget.ListPopupWindow;
import android.widget.Spinner;
import java.lang.reflect.Field;

public class SpinnerDropDownUtils {
    public static void applyHeight(Spinner spinner, int heightPx) {
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            ((ListPopupWindow) popup.get(spinner)).setHeight(heightPx);
        } catch (NoClassDefFoundError e) {
        } catch (ClassCastException e2) {
        } catch (NoSuchFieldException e3) {
        } catch (IllegalAccessException e4) {
        }
    }
}
