package com.henninghall.date_picker;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.henninghall.date_picker.wheelFunctions.Refresh;
import com.henninghall.date_picker.wheelFunctions.SetDate;
import com.henninghall.date_picker.wheelFunctions.UpdateVisibility;
import com.henninghall.date_picker.wheelFunctions.WheelFunction;
import com.henninghall.date_picker.wheels.Wheel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public class PickerView extends RelativeLayout {

    private final View rootView = inflate(getContext(), R.layout.datepicker_view, this);
    private final Style style;
    private WheelChangeListener onWheelChangeListener = new WheelChangeListenerImpl(this);
    private Wheels wheels;
    private LinearLayout wheelsWrapper;

    private State state;

    public ArrayList<String> updateProps = new ArrayList<>();

    public PickerView() {
        super(DatePickerManager.context);
        state = new State();
        style = new Style(this);
        wheels = new Wheels(this);
        wheelsWrapper = (LinearLayout) this.findViewById(R.id.wheelsWrapper);
        wheelsWrapper.setWillNotDraw(false);
    }

    public State getState() {
        return state;
    }

    public void addWheel(View wheel, int index) {
        wheelsWrapper.addView(wheel, index);
    }

    public void addWheel(View wheel) {
        wheelsWrapper.addView(wheel);
    }

    public void removeWheel(View wheel) {
        wheelsWrapper.removeView(wheel);
    }

    public void removeAllWheels() {
        wheelsWrapper.removeAllViews();
    }

    protected String getDateString(){
        return wheels.getDateString();
    }

    public View getRootView(){
        return rootView;
    }

    public void updateDate() {
        applyOnAllWheels(new SetDate(state.getDate()));
    }

    public Collection<Wheel> getVisibleWheels() {
        Collection<Wheel> visibleWheels = new ArrayList<>();
        for (Wheel wheel: wheels.getAll()) if (wheel.visible()) visibleWheels.add(wheel);
        return visibleWheels;
    }

    public void applyOnAllWheels(WheelFunction function) {
        for (Wheel wheel: wheels.getAll()) function.apply(wheel);
    }

    public void applyOnVisibleWheels(WheelFunction function) {
        for (Wheel wheel: getVisibleWheels()) function.apply(wheel);
    }

    public WheelChangeListener getListener() {
        return onWheelChangeListener;
    }

    protected SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(wheels.getFormatPattern(), state.getLocale());
    }

    public void update() {

        if(updateProps.contains("fadeToColor")) {
            style.updateFadeToColor();
        }

        if(updateProps.contains("textColor")) {
            style.updateTextColor();
        }

        if(updateProps.contains("mode")) {
            applyOnAllWheels(new UpdateVisibility());
        }

        if(updateProps.contains("height")) {
            style.updateHeight();
        }

        if(updateProps.contains("mode") || updateProps.contains("locale")) {
            wheels.updateWheelOrder(state.getLocale());
        }

        ArrayList<String> nonRefreshingProps = new ArrayList<String>(){{
            add("date");
            add("fadeToColor");
            add("textColor");
        }};
        updateProps.removeAll(nonRefreshingProps);

        if(updateProps.size() != 0) {
            applyOnAllWheels(new Refresh());
        }

        updateDate();

        updateProps = new ArrayList<>();
    }


    public void scroll(int wheelIndex, int scrollTimes) {
        NumberPickerView picker = wheels.getVisibleWheels(wheelIndex).picker;
        int currentIndex = picker.getValue();
        int maxValue = picker.getMaxValue();
        boolean isWrapping = picker.getWrapSelectorWheel();
        int nextValue = currentIndex + scrollTimes;
        if(nextValue <= maxValue || isWrapping) {
            picker.smoothScrollToValue(nextValue % (maxValue + 1));
        }
    }

    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }
}
