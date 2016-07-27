package com.hotellook.search;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KidsSerializer {
    public static String toString(List<Integer> kids) {
        StringBuilder sb = new StringBuilder();
        for (Integer kidAge : kids) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(kidAge);
        }
        return sb.toString();
    }

    public static List<Integer> parse(String kidsStr) {
        List<Integer> kids = new ArrayList();
        if (!TextUtils.isEmpty(kidsStr)) {
            List<String> kidsStrings = new ArrayList(Arrays.asList(kidsStr.split(",")));
            int i = 0;
            while (i < kidsStrings.size()) {
                try {
                    Integer kidAge = Integer.valueOf((String) kidsStrings.get(i));
                    if (kidAge.intValue() > 17) {
                        kids.clear();
                        break;
                    }
                    kids.add(kidAge);
                    i++;
                } catch (NumberFormatException e) {
                    kids.clear();
                }
            }
        }
        return kids;
    }
}
