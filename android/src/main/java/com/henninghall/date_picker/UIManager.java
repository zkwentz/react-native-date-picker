package com.henninghall.date_picker;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class UIManager {
    private Wheels wheels;

    UIManager(){
        wheels = new Wheels(this);
    }

    public void updateWheelVisibility(){ }
    public void updateTextColor(){ }
    public void updateFadeToColor(){ }
    public void updateHeight(){ }
    public void updateWheelOrder(){ }
    public void updateDisplayValues(){ }
    public void setWheelsToDate(){ }

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

}
