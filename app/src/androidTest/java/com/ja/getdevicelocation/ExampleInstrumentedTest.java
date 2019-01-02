package com.ja.getdevicelocation;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.InputType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 * author: Jiaying Guo
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    private static final String STRING_TO_BE_TYPED = "London";

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    /**
     * Test if the user's input matches the actual weather info display's city name
     */
    @Test
    public void searchCity() {
        onView(withId(R.id.editText)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        onView(withText("Search")).perform(click());
        String expectedText = "London";
        onView(withText(expectedText)).check(matches(isDisplayed())); //line 3
    }

    /**
     * Test the GPS information is in accordance with the current location, android is hardcoded with MountainView as the current location
     */
    @Test
    public void geoMatch() {
        onView(withText("Get Current Location")).perform(click());
        String expectedText = "View";
        onView(withId(R.id.WeatherImage)).check(matches(isDisplayed())); //line 3
    }

//
//    @Test
//    public void geoWeatherPressure() {
//        onView(withText("Get Current Location")).perform(click());
//        String expectedText = "Pressure";
//        onView(withText(expectedText)).check(matches(isDisplayed())); //line 3
//    }
//
//    @Test
//    public void geoWeatherHumidity() {
//        onView(withText("Get Current Location")).perform(click());
//        String expectedText = "Humid";
//        onView(withText(expectedText)).check(matches(isDisplayed())); //line 3
//    }
//
//    @Test
//    public void geoWeatherTempMax() {
//        onView(withText("Get Current Location")).perform(click());
//        String expectedText = "tempMax";
//        onView(withText(expectedText)).check(matches(isDisplayed())); //line 3
////    }

    //test swipe
//    @Test
//    public void geoForecast() {
//        onView(withText("Get Current Location")).perform(click());
//
//        onView(withText("Mountain View")).perform(swipeRight()).check(matches(hasDescendant(withText("Mountain View"))));
//        //onListItem(2).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER, GeneralLocation.CENTER_LEFT, Press.FINGER));
//        //String expectedText = "Day";
//        //onView(withInputType(InputType.TYPE_CLASS_TEXT)).check(matches(isDisplayed())); //line 3
//
//    }





}
