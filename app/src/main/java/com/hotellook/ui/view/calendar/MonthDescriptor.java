package com.hotellook.ui.view.calendar;

import java.util.Date;

class MonthDescriptor {
    private final Date date;
    private String label;
    private final int month;
    private final int year;

    public MonthDescriptor(int month, int year, Date date, String label) {
        this.month = month;
        this.year = year;
        this.date = date;
        this.label = label;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }

    public Date getDate() {
        return this.date;
    }

    public String getLabel() {
        return this.label;
    }

    void setLabel(String label) {
        this.label = label;
    }

    public String toString() {
        return "MonthDescriptor{label='" + this.label + '\'' + ", month=" + this.month + ", year=" + this.year + '}';
    }
}
