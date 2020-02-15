package com.henninghall.date_picker;


import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.props.DateProp;
import com.henninghall.date_picker.props.FadeToColorProp;
import com.henninghall.date_picker.props.LocaleProp;
import com.henninghall.date_picker.props.MaximumDateProp;
import com.henninghall.date_picker.props.MinimumDateProp;
import com.henninghall.date_picker.props.MinuteIntervalProp;
import com.henninghall.date_picker.props.ModeProp;
import com.henninghall.date_picker.props.TextColorProp;
import com.henninghall.date_picker.props.UtcProp;

import net.time4j.android.ApplicationStarter;

import java.lang.reflect.Method;
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

  @ReactPropGroup(names = { DateProp.name, ModeProp.name, LocaleProp.name, MaximumDateProp.name,
          MinimumDateProp.name, FadeToColorProp.name, TextColorProp.name, UtcProp.name, MinuteIntervalProp.name})
  public void setProps(PickerView view, int index, Dynamic value) {
    String[] propNames = getMethodAnnotation("setProps").names();
    String propName = propNames[index];
    view.getState().setProp(propName, value);
    view.updatedProps.add(propName);
  }

  @ReactPropGroup(names = {"height", "width"}, customType = "Style")
  public void setStyle(PickerView view, int index, Integer style) {
    if(index == 0) {
      view.getState().setHeight(style);
      view.updatedProps.add("height");
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

  private ReactPropGroup getMethodAnnotation(String methodName) {
    Method[] methods = this.getClass().getMethods();
    Method method = null;
    for (Method m : methods) {
      if (m.getName().equals(methodName))
        method = m;
    }
    return method.getAnnotation(ReactPropGroup.class);
  }

}


