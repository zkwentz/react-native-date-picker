package com.henninghall.date_picker;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;

import net.time4j.android.ApplicationStarter;

import java.util.ArrayList;
import java.util.Map;

public class DatePickerManager extends SimpleViewManager<PickerView>  {

  private static final String REACT_CLASS = "DatePickerManager";
  private static final int SCROLL = 1;
  static ThemedReactContext context;

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public PickerView createViewInstance(ThemedReactContext reactContext) {
    DatePickerManager.context = reactContext;
    ApplicationStarter.initialize(reactContext, false); // false = no need to prefetch on time data background tread
    return new PickerView();
  }

  @ReactProp(name = "mode")
  public void setMode(PickerView view, String mode) {
    view.getState().setMode(mode);
    view.updateProps.add("mode");
  }

  @ReactProp(name = "date")
  public void setDate(PickerView view, String date) {
    view.getState().setDate(date);
    view.updateProps.add("date");
  }

  @ReactProp(name = "locale")
  public void setLocale(PickerView view, String locale) {
    view.getState().setLocale(locale);
    view.updateProps.add("locale");
  }

  @ReactProp(name = "minimumDate")
  public void setMinimumDate(PickerView view, String date) {
    view.getState().setMinimumDate(date);
    view.updateProps.add("minimumDate");
  }

  @ReactProp(name = "maximumDate")
  public void setMaximumDate(PickerView view, String date) {
    view.getState().setMaximumDate(date);
    view.updateProps.add("maximumDate");
  }

  @ReactProp(name = "fadeToColor")
  public void setFadeToColor(PickerView view, String color) {
    view.getState().setFadeToColor(color);
    view.updateProps.add("fadeToColor");
  }

  @ReactProp(name = "textColor")
  public void setTextColor(PickerView view, String color) {
    view.getState().setTextColor(color);
    view.updateProps.add("textColor");
  }

  @ReactProp(name = "minuteInterval")
  public void setMinuteInterval(PickerView view,  int interval) throws Exception {
    if (interval < 0 || interval > 59) throw new Exception("Minute interval out of bounds");
    view.getState().setMinuteInterval(interval);
    view.updateProps.add("minuteInterval");
  }

  @ReactProp(name = "utc")
  public void setUtc(PickerView view,  boolean utc) {
    view.getState().setUtc(utc);
    view.updateProps.add("utc");
  }

  @ReactPropGroup(names = {"height", "width"}, customType = "Style")
  public void setStyle(PickerView view, int index, Integer style) {
    if(index == 0) {
      view.getState().setHeight(style);
      view.updateProps.add("height");
    }
  }

  @Override
  public Map<String, Integer> getCommandsMap() {
    return MapBuilder.of("scroll", SCROLL);
  }


  @Override
  protected void onAfterUpdateTransaction(PickerView view) {
   super.onAfterUpdateTransaction(view);
    view.update();
  }

  public void receiveCommand(final PickerView view, int command, final ReadableArray args) {
    if (command == SCROLL) {
      int wheelIndex = args.getInt(0);
      int scrollTimes = args.getInt(1);
      view.scroll(wheelIndex, scrollTimes);
    }
  }

  public Map getExportedCustomBubblingEventTypeConstants() {
    return MapBuilder.builder()
            .put("dateChange", MapBuilder.of("phasedRegistrationNames",
                    MapBuilder.of("bubbled", "onChange")
                    )
            ).build();
  }

}


