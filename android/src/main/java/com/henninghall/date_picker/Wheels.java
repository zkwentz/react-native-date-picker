package com.henninghall.date_picker;

import com.henninghall.date_picker.wheels.AmPmWheel;
import com.henninghall.date_picker.wheels.DateWheel;
import com.henninghall.date_picker.wheels.DayWheel;
import com.henninghall.date_picker.wheels.HourWheel;
import com.henninghall.date_picker.wheels.MinutesWheel;
import com.henninghall.date_picker.wheels.MonthWheel;
import com.henninghall.date_picker.wheels.Wheel;
import com.henninghall.date_picker.wheels.YearWheel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

class Wheels {

    private final State state;
    private WheelOrder wheelOrder;
    HourWheel hourWheel;
    DayWheel dayWheel;
    MinutesWheel minutesWheel;
    AmPmWheel ampmWheel;
    DateWheel dateWheel;
    MonthWheel monthWheel;
    YearWheel yearWheel;


    EmptyWheelUpdater emptyWheelUpdater;

    Wheels(PickerView pickerView){
        this.state = pickerView.getState();

        yearWheel = new YearWheel( pickerView, R.id.year);
        monthWheel = new MonthWheel( pickerView, R.id.month);
        dateWheel = new DateWheel( pickerView, R.id.date);
        dayWheel = new DayWheel( pickerView, R.id.day);
        minutesWheel = new MinutesWheel( pickerView, R.id.minutes);
        ampmWheel = new AmPmWheel(pickerView, R.id.ampm);
        hourWheel = new HourWheel(pickerView, R.id.hour);

        wheelOrder = new WheelOrder(this, pickerView);
        emptyWheelUpdater = new EmptyWheelUpdater(pickerView);
        changeAmPmWhenPassingMidnightOrNoon();
    }


    private void changeAmPmWhenPassingMidnightOrNoon(){
        hourWheel.picker.setOnValueChangeListenerInScrolling(new NumberPickerView.OnValueChangeListenerInScrolling() {
            @Override
            public void onValueChangeInScrolling(NumberPickerView picker, int oldVal, int newVal) {
                if(Settings.usesAmPm()){
                    String oldValue = hourWheel.getValueAtIndex(oldVal);
                    String newValue = hourWheel.getValueAtIndex(newVal);
                    boolean passingNoonOrMidnight = (oldValue.equals("12") && newValue.equals("11")) || oldValue.equals("11") && newValue.equals("12");
                    if (passingNoonOrMidnight) ampmWheel.picker.smoothScrollToValue((ampmWheel.picker.getValue() + 1) % 2,false);
                }
            }
        });
    }

    public List<Wheel> getAll(){
        return new ArrayList<>(Arrays.asList(yearWheel, monthWheel, dateWheel, dayWheel, hourWheel, minutesWheel, ampmWheel));
    }

    public void updateWheelOrder(Locale locale){
        wheelOrder.update(locale);
    }

    Wheel getVisibleWheels(int index){
        return wheelOrder.getVisibleWheels().get(index);
    }

    private String getDateFormatPattern(){
        if(state.getMode() == Mode.date){
            return wheelOrder.getVisibleWheel(0).getFormatPattern() + " "
                    + wheelOrder.getVisibleWheel(1).getFormatPattern() + " "
                    + wheelOrder.getVisibleWheel(2).getFormatPattern();
        }
        return dayWheel.getFormatPattern();
    }

    public String getFormatPattern() {
        return this.getDateFormatPattern() + " "
                + hourWheel.getFormatPattern() + " "
                + minutesWheel.getFormatPattern()
                + ampmWheel.getFormatPattern();
    }


    String getDateString() {
        String dateString = (state.getMode() == Mode.date)
                ? wheelOrder.getVisibleWheel(0).getValue() + " "
                + wheelOrder.getVisibleWheel(1).getValue() + " "
                + wheelOrder.getVisibleWheel(2).getValue()
                : dayWheel.getValue();
        return dateString
                + " " + hourWheel.getValue()
                + " " + minutesWheel.getValue()
                + ampmWheel.getValue();
    }

}
