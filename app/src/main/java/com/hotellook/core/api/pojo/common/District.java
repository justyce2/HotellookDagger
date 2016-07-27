package com.hotellook.core.api.pojo.common;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class District {
    private int id;
    @SerializedName("name_info")
    private NameInfo nameInfo;

    public static class NameInfo {
        private Map<Integer, String> declensions;
        private String name;

        public String getName() {
            return this.name;
        }

        public Map<Integer, String> getDeclensions() {
            return this.declensions;
        }
    }

    public int getId() {
        return this.id;
    }

    public NameInfo getNameInfo() {
        return this.nameInfo;
    }
}
