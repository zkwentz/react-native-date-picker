package com.henninghall.date_picker;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.henninghall.date_picker.props.DateProp;
import com.henninghall.date_picker.props.FadeToColorProp;
import com.henninghall.date_picker.props.LocaleProp;
import com.henninghall.date_picker.props.ModeProp;
import com.henninghall.date_picker.props.TextColorProp;
import com.henninghall.date_picker.wheelFunctions.AnimateToDate;
import com.henninghall.date_picker.wheelFunctions.Refresh;
import com.henninghall.date_picker.wheelFunctions.SetDate;
import com.henninghall.date_picker.wheelFunctions.SetShowCount;
import com.henninghall.date_picker.wheelFunctions.TextColor;
import com.henninghall.date_picker.wheelFunctions.UpdateVisibility;
import com.henninghall.date_picker.wheels.Wheel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public class PickerView extends RelativeLayout {

    private final View rootView = inflate(getContext(), R.layout.datepicker_view, this);
    private final Style style;
    private Wheels wheels;
    private LinearLayout wheelsWrapper;
    private State state;
    public ArrayList<String> updatedProps = new ArrayList<>();

    public PickerView() {
        super(DatePickerManager.context);
        state = new State();
        style = new Style(this);
        wheels = new Wheels(this);
        wheelsWrapper = (LinearLayout) this.findViewById(R.id.wheelsWrapper);
        wheelsWrapper.setWillNotDraw(false);
    }


    public void update() {

        if(updatedProps.contains(FadeToColorProp.name)) {
            style.updateFadeToColor();
        }

        if(updatedProps.contains(TextColorProp.name)) {
            updateTextColor();
        }

        if(updatedProps.contains(ModeProp.name)) {
            wheels.applyOnAllWheels(new UpdateVisibility());
        }

        if(updatedProps.contains("height")) {
            updateHeight();
        }

        if(updatedProps.contains(ModeProp.name) || updatedProps.contains(LocaleProp.name)) {
            wheels.updateWheelOrder(state.getLocale());
        }

        ArrayList<String> nonRefreshingProps = new ArrayList<String>(){{
            add(DateProp.name);
            add(FadeToColorProp.name);
            add(TextColorProp.name);
        }};
        updatedProps.removeAll(nonRefreshingProps);

        if(updatedProps.size() != 0) {
            wheels.applyOnAllWheels(new Refresh());
        }

        updateDate();

        updatedProps = new ArrayList<>();
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

    public Collection<Wheel> getVisibleWheels() {
        return wheels.getVisibleWheels();
    }

    protected String getDateString(){
        return wheels.getDateString();
    }

    public View getRootView(){
        return rootView;
    }

    public void updateDate() {
        wheels.applyOnAllWheels(new SetDate(state.getDate()));
    }

    private void updateHeight() {
        int shownCount = style.getShownCount();
        wheels.applyOnAllWheels(new SetShowCount(shownCount));
        setShownCountOnEmptyWheels(shownCount);
    }

    protected SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(wheels.getFormatPattern(), state.getLocale());
    }

    public void updateTextColor() {
        wheels.applyOnAllWheels(new TextColor(state.getTextColor()));
    }

    private void setShownCountOnEmptyWheels(int shownCount) {
        for (int id : Utils.emptyWheelIds) {
            NumberPickerView view = (NumberPickerView) findViewById(id);
            if(view != null) view.setShownCount(shownCount);
        }
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

    public void animateToDate(Calendar date) {
        wheels.applyOnVisibleWheels(new AnimateToDate(date));
    }
}
