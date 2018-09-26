package com.datepickerexample;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.KeyEvent;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction reactEditText = onView(
                allOf(withContentDescription("searchInputAcc"), isDisplayed()));
        reactEditText.perform(replaceText(""));

        ViewInteraction reactEditText2 = onView(
                allOf(withContentDescription("searchInputAcc"), isDisplayed()));
        reactEditText2.perform(click());

        ViewInteraction reactEditText3 = onView(
                allOf(withContentDescription("searchInputAcc"), isDisplayed()));
        reactEditText3.perform(replaceText("bold"));
        ViewInteraction re4 = onView(allOf(withContentDescription("searchInputAcc"), isDisplayed()));
        re4.perform(pressKey(KeyEvent.KEYCODE_ENTER));

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}