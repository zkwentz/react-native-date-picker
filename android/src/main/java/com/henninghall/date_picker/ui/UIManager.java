package com.henninghall.date_picker.ui;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.henninghall.date_picker.R;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.Utils;
import com.henninghall.date_picker.wheelFunctions.AnimateToDate;
import com.henninghall.date_picker.wheelFunctions.Refresh;
import com.henninghall.date_picker.wheelFunctions.SetDate;
import com.henninghall.date_picker.wheelFunctions.SetShowCount;
import com.henninghall.date_picker.wheelFunctions.TextColor;
import com.henninghall.date_picker.wheelFunctions.UpdateVisibility;
import com.henninghall.date_picker.wheels.Wheel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class UIManager {
    private final State state;
    private final LinearLayout wheelsWrapper;
    private final GradientDrawable gradientTop;
    private final GradientDrawable gradientBottom;
    private final View rootView;
    private final EmptyWheels emptyWheelUpdater;

    private Wheels wheels;

    public UIManager(State state, View rootView){
        this.rootView = rootView;
        this.state = state;
        wheels = new Wheels(state, rootView, this);
        wheelsWrapper = (LinearLayout) rootView.findViewById(R.id.wheelsWrapper);
        ImageView overlayTop = (ImageView) rootView.findViewById(R.id.overlay_top);
        ImageView overlayBottom = (ImageView) rootView.findViewById(R.id.overlay_bottom);
        gradientTop =  (GradientDrawable) overlayTop.getDrawable();
        gradientBottom =  (GradientDrawable) overlayBottom.getDrawable();
        wheelsWrapper.setWillNotDraw(false);
        emptyWheelUpdater = new EmptyWheels(rootView,this,state);
    }

    public void updateWheelVisibility(){
        wheels.applyOnAll(new UpdateVisibility());
    }

    public void updateTextColor(){
        wheels.applyOnAll(new TextColor(state.getTextColor()));
    }

    public void updateFadeToColor(){
        String color = state.getFadeToColor();
        int alpha = validColor(color) ? 255 : 0;
        gradientTop.setAlpha(alpha);
        gradientBottom.setAlpha(alpha);
        if(validColor(color)) {
            int startColor = Color.parseColor("#FF"+ color.substring(1));
            int endColor = Color.parseColor("#00" + color.substring(1));
            gradientTop.setColors(new int[] {startColor, endColor});
            gradientBottom.setColors(new int[] {startColor, endColor});
        }
    }

    public void updateHeight(){
        int shownCount = state.getShownCount();
        wheels.applyOnAll(new SetShowCount(shownCount));
        setShownCountOnEmptyWheels(shownCount);
    }

    public void updateWheelOrder() {
        removeAllWheels();
        wheels.addInOrder();
        addEmptyWheels();
    }

    private void addEmptyWheels(){
        emptyWheelUpdater.update();
    }

    public void updateDisplayValues(){
        wheels.applyOnAll(new Refresh());
    }

    public void setWheelsToDate(){
        wheels.applyOnAll(new SetDate(state.getDate()));
    }

    public void scroll(int wheelIndex, int scrollTimes) {
        Wheel wheel = wheels.getWheel(state.getOrderedVisibleWheels().get(wheelIndex));
        NumberPickerView picker = wheel.picker;
        int currentIndex = picker.getValue();
        int maxValue = picker.getMaxValue();
        boolean isWrapping = picker.getWrapSelectorWheel();
        int nextValue = currentIndex + scrollTimes;
        if(nextValue <= maxValue || isWrapping) {
            picker.smoothScrollToValue(nextValue % (maxValue + 1));
        }
    }

    SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(wheels.getFormatPattern(), state.getLocale());
    }

    void addWheel(View wheel, int index) {
        wheelsWrapper.addView(wheel, index);
    }

    void addWheel(View wheel) { wheelsWrapper.addView(wheel); }

    void animateToDate(Calendar date) {
        wheels.applyOnVisible(new AnimateToDate(date));
    }

    private void setShownCountOnEmptyWheels(int shownCount) {
        for (int id : Utils.emptyWheelIds) {
            NumberPickerView view = (NumberPickerView) rootView.findViewById(id);
            if(view != null) view.setShownCount(shownCount);
        }
    }

    private void removeAllWheels() {
        wheelsWrapper.removeAllViews();
    }

    private boolean validColor(String color){
        return color != null && color.length() == 7;
    }

}
