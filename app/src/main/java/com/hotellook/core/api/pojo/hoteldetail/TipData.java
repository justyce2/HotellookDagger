package com.hotellook.core.api.pojo.hoteldetail;

import com.google.gson.annotations.Expose;

public class TipData {
    @Expose
    private String avatar;
    @Expose
    private String canonicalUrl;
    @Expose
    private long createdAt;
    @Expose
    private String id;
    @Expose
    private String lang;
    @Expose
    private int likes;
    @Expose
    private String text;
    @Expose
    private String type;
    @Expose
    private int userId;
    @Expose
    private String userlastname;
    @Expose
    private String username;
    @Expose
    private String venueId;

    public String getUsername() {
        return this.username;
    }

    public String getUserlastname() {
        return this.userlastname;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getText() {
        return this.text;
    }

    public String getLang() {
        return this.lang;
    }

    public String getId() {
        return this.id;
    }

    public String getCanonicalUrl() {
        return this.canonicalUrl;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getVenueId() {
        return this.venueId;
    }

    public int getLikes() {
        return this.likes;
    }

    public long getCreatedAt() {
        return this.createdAt;
    }
}
