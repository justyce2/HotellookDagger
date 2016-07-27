package com.hotellook.ui.view.bottomnavigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.hotellook.C1178R;
import com.hotellook.statistics.Constants.MixPanelParams;
import java.util.ArrayList;
import java.util.List;
import pl.charmas.android.reactivelocation.C1822R;

class NavigationMenuInflater {
    private Context context;

    @NonNull
    public static NavigationMenuInflater from(@NonNull Context context) {
        return new NavigationMenuInflater(context);
    }

    private NavigationMenuInflater(Context context) {
        this.context = context;
    }

    @Nullable
    public NavigationMenu inflate(@MenuRes int menuRes) {
        List<Item> items = new ArrayList();
        try {
            XmlResourceParser parser = this.context.getResources().getLayout(menuRes);
            AttributeSet attrs = Xml.asAttributeSet(parser);
            int eventType = parser.getEventType();
            boolean lookingForEndOfUnknownTag = false;
            String unknownTagName = null;
            while (eventType != 2) {
                eventType = parser.next();
                if (eventType == 1) {
                    break;
                }
            }
            String tagName = parser.getName();
            if (tagName.equals(MixPanelParams.MENU)) {
                eventType = parser.next();
                boolean reachedEndOfMenu = false;
                Item item = null;
                while (!reachedEndOfMenu) {
                    switch (eventType) {
                        case C1822R.styleable.SignInButton_colorScheme /*1*/:
                            throw new RuntimeException("Unexpected end of document");
                        case C1822R.styleable.SignInButton_scopeUris /*2*/:
                            if (!lookingForEndOfUnknownTag) {
                                tagName = parser.getName();
                                if (!tagName.equals("item")) {
                                    lookingForEndOfUnknownTag = true;
                                    unknownTagName = tagName;
                                    break;
                                }
                                item = inflateItem(attrs);
                                break;
                            }
                            break;
                        case C1822R.styleable.MapAttrs_cameraTargetLng /*3*/:
                            tagName = parser.getName();
                            if (!lookingForEndOfUnknownTag || !tagName.equals(unknownTagName)) {
                                if (!tagName.equals("item")) {
                                    if (!tagName.equals(MixPanelParams.MENU)) {
                                        break;
                                    }
                                    reachedEndOfMenu = true;
                                    break;
                                } else if (item == null) {
                                    break;
                                } else {
                                    items.add(item);
                                    break;
                                }
                            }
                            lookingForEndOfUnknownTag = false;
                            unknownTagName = null;
                            break;
                        default:
                            break;
                    }
                    eventType = parser.next();
                }
                return new NavigationMenu(items);
            }
            throw new RuntimeException("Expecting menu, got " + tagName);
        } catch (Exception e) {
            Log.e("NavigationMenuInflater", "Failed to inflate menu", e);
            return null;
        }
    }

    @Nullable
    private Item inflateItem(@NonNull AttributeSet attrs) {
        Item item = null;
        TypedArray a = this.context.obtainStyledAttributes(attrs, C1178R.styleable.NavigationMenu_Item);
        if (a.hasValue(1) && a.hasValue(0) && a.hasValue(2)) {
            item = new Item(a.getResourceId(1, 0), a.getDrawable(0), a.getText(2));
        }
        a.recycle();
        return item;
    }
}
