package com.henninghall.date_picker;

import android.os.Build;

import org.apache.commons.lang3.LocaleUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class State {

    private Mode mode;
    private String date;
    private Locale locale = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? Locale.forLanguageTag("en") : Locale.getDefault();
    private String minimumDate;
    private String maximumDate;
    private String fadeToColor;
    private String textColor;

    private boolean utc;
    private int minuteInterval = 1;
    private Integer height;

    public void setMode(String mode) {
        try {
            this.mode = Mode.valueOf(mode);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid mode. Valid modes: 'datetime', 'date', 'time'");
        }
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocale(String locale) {
        this.locale = LocaleUtils.toLocale(locale.replace('-','_'));
    }

    public void setMinimumDate(String date) {
        this.minimumDate = date;
    }

    public void setMaximumDate(String date) {
        this.maximumDate = date;
    }

    public void setTextColor(String color) {
        this.textColor = color;
    }

    public void setFadeToColor(String color) {
        this.fadeToColor = color;
    }

    public void setMinuteInterval(int interval) {
        this.minuteInterval = interval;
    }

    public void setUtc(boolean utc) {
        this.utc = utc;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Mode getMode() {
        return mode;
    }

    public String getFadeToColor() {
        return fadeToColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public int getMinuteInterval() {
        return minuteInterval;
    }

    public Locale getLocale() {
        return locale;
    }

    public Calendar getMinimumDate(){
        DateBoundary db = new DateBoundary(getTimeZone(), minimumDate);
        return db.get();
    }

    public Calendar getMaximumDate(){
        DateBoundary db = new DateBoundary(getTimeZone(), maximumDate);
        return db.get();
    }

    public TimeZone getTimeZone(){
        return utc ? TimeZone.getTimeZone("UTC") : TimeZone.getDefault();
    }

    public Calendar getDate() {
        return Utils.isoToCalendar(date, getTimeZone());
    }

    public Integer getHeight() {
        return height;
    }

    // Rounding cal to closest minute interval
    public Calendar getInitialDate() {
        Calendar cal = Calendar.getInstance();
        int minuteInterval = getMinuteInterval();
        if(minuteInterval <= 1) return cal;
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm", getLocale());
        int exactMinute = Integer.valueOf(minuteFormat.format(cal.getTime()));
        int diffSinceLastInterval = exactMinute % minuteInterval;
        int diffAhead = minuteInterval - diffSinceLastInterval;
        int diffBehind= -diffSinceLastInterval;
        boolean closerToPrevious = minuteInterval / 2 > diffSinceLastInterval;
        int diffToExactValue = closerToPrevious ? diffBehind : diffAhead;
        cal.add(Calendar.MINUTE, diffToExactValue);
        return (Calendar) cal.clone();
    }



}
