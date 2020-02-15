package com.henninghall.date_picker;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;


class Style {
    private static int DP_PER_SHOW_SHOW_COUNT = 35;

    private final GradientDrawable gradientBottom;
    private final GradientDrawable gradientTop;
    private final PickerView pickerView;

    public Style(PickerView pickerView) {
        this.pickerView = pickerView;
        ImageView overlayTop = (ImageView) pickerView.findViewById(R.id.overlay_top);
        ImageView overlayBottom = (ImageView) pickerView.findViewById(R.id.overlay_bottom);
        this.gradientTop =  (GradientDrawable) overlayTop.getDrawable();
        this.gradientBottom =  (GradientDrawable) overlayBottom.getDrawable();
    }

    public void updateFadeToColor() {
        String color = pickerView.getState().getFadeToColor();
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

    public int getShownCount() {
        int height = pickerView.getState().getHeight();
        int showCount = height / DP_PER_SHOW_SHOW_COUNT;
        int oddShowCount = showCount % 2 == 0 ? showCount + 1 : showCount;
        return oddShowCount;
    }



    private boolean validColor(String color){
        return color != null && color.length() == 7;
    }

}
