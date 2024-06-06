package com.example.zooseeker_jj_zaaz_team_52;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MapTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mapTest() {
        ViewInteraction zoomarker = onView(
                childAtPosition(
                        allOf(withId(R.id.relativeLayout),
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0)),
                        11));
        zoomarker.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(androidx.appcompat.R.id.alertTitle), withText("Lion"),
                        withParent(allOf(withId(androidx.appcompat.R.id.title_template),
                                withParent(withId(androidx.appcompat.R.id.topPanel)))),
                        isDisplayed()));
        textView.check(matches(withText("Lion")));

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("Confirm"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.buttonPanel),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction zoomarker2 = onView(
                childAtPosition(
                        allOf(withId(R.id.relativeLayout),
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0)),
                        27));
        zoomarker2.perform(scrollTo(), click());

        ViewInteraction textView2 = onView(
                allOf(withId(androidx.appcompat.R.id.alertTitle), withText("Polar Bear"),
                        withParent(allOf(withId(androidx.appcompat.R.id.title_template),
                                withParent(withId(androidx.appcompat.R.id.topPanel)))),
                        isDisplayed()));
        textView2.check(matches(withText("Polar Bear")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
