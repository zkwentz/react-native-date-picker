package com.henninghall.date_picker;

import android.os.Build;

import com.facebook.react.bridge.Dynamic;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.props.DateProp;
import com.henninghall.date_picker.props.FadeToColorProp;
import com.henninghall.date_picker.props.LocaleProp;
import com.henninghall.date_picker.props.MaximumDateProp;
import com.henninghall.date_picker.props.MinimumDateProp;
import com.henninghall.date_picker.props.MinuteIntervalProp;
import com.henninghall.date_picker.props.ModeProp;
import com.henninghall.date_picker.props.Prop;
import com.henninghall.date_picker.props.TextColorProp;
import com.henninghall.date_picker.props.UtcProp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class State {

    private final Prop dateProp = new DateProp();
    private final Prop modeProp = new ModeProp();
    private final Prop localeProp = new LocaleProp();
    private final Prop fadeToColorProp = new FadeToColorProp();
    private final Prop textColorProp = new TextColorProp();
    private final Prop minuteIntervalProp = new MinuteIntervalProp();
    private final Prop minimumDateProp = new MinimumDateProp();
    private final Prop maximumDateProp = new MaximumDateProp();
    private final Prop utcProp = new UtcProp();

    private final HashMap props = new HashMap<String, Prop>() {{
        put(DateProp.name, dateProp);
        put(ModeProp.name, modeProp);
        put(LocaleProp.name, localeProp);
        put(FadeToColorProp.name, fadeToColorProp);
        put(TextColorProp.name, textColorProp);
        put(MinuteIntervalProp.name, minuteIntervalProp);
        put(MinimumDateProp.name, minimumDateProp);
        put(MaximumDateProp.name, maximumDateProp);
        put(UtcProp.name, utcProp);
    }};

    private Integer height;

    private Prop getProp(String name){
        return (Prop) props.get(name);
    }

    void setProp(String propName, Dynamic value){
        getProp(propName).setValue(value);
    }

    // TODO: height
    public void setHeight(Integer height) {
        this.height = height;
    }

    public Mode getMode() {
        return (Mode) modeProp.getValue();
    }

    public String getFadeToColor() {
        return (String) fadeToColorProp.getValue();
    }

    public String getTextColor() {
        return (String) textColorProp.getValue();
    }

    public int getMinuteInterval() {
        return (int) minuteIntervalProp.getValue();
    }

    public Locale getLocale() {
        return (Locale) localeProp.getValue();
    }

    public Calendar getMinimumDate(){
        DateBoundary db = new DateBoundary(getTimeZone(), (String) minimumDateProp.getValue());
        return db.get();
    }

    public Calendar getMaximumDate(){
        DateBoundary db = new DateBoundary(getTimeZone(), (String) maximumDateProp.getValue());
        return db.get();
    }

    public TimeZone getTimeZone(){
        boolean utc = (boolean) utcProp.getValue();
        return utc ? TimeZone.getTimeZone("UTC") : TimeZone.getDefault();
    }

    public Calendar getDate() {
        String date = (String) dateProp.getValue();
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
