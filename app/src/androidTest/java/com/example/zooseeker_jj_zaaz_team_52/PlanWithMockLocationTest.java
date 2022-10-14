package com.example.zooseeker_jj_zaaz_team_52;


import static android.content.Context.MODE_PRIVATE;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.zooseeker_jj_zaaz_team_52.SearchActivity.KEY;
import static com.example.zooseeker_jj_zaaz_team_52.SearchActivity.SHARED_PREFS;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests that can use mock location and will either trigger replan or update directions
 * accordingly based on given mocked location
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class PlanWithMockLocationTest {
    @Before
    public void setup() {
        PlanDatabase.getSingleton(ApplicationProvider.getApplicationContext()).planListItemDao().deleteAll();
        SharedPreferences sharedPreferences = ApplicationProvider.getApplicationContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY);
        editor.apply();
    }

    @After
    public void cleanup(){
        SharedPreferences sharedPreferences = ApplicationProvider.getApplicationContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY);
        editor.apply();

        PlanDatabase.getSingleton(ApplicationProvider.getApplicationContext()).planListItemDao().deleteAll();
    }

    @Rule
    public ActivityScenarioRule<SearchActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(SearchActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");

    @Rule
    public InstantTaskExecutorRule execRule = new InstantTaskExecutorRule();

    @Test
    public void planWithMockLocationTest() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.actionSearch), withContentDescription("exhibit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.animal_name), withText("Crocodiles"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.animal_name), withText("Koi Fish"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Collapse"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.action_bar),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.create_plan), withText("Create Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.direction_btn), withText("DIRECTIONS"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("Set Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView3.perform(click());

        ViewInteraction editText = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        editText.perform(click());

        ViewInteraction editText2 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        editText2.perform(replaceText("32.746302"), closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        editText3.perform(replaceText("-117.166595"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(android.R.id.button1), withText("Yes"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton4.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.exhibit_name), withText("Crocodiles"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("Crocodiles")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.exhibit_name), withText("Crocodiles"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView2.check(matches(withText("Crocodiles")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.directions_view), withText("Find Crocodiles nearby"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView3.check(matches(withText("Find Crocodiles nearby")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.next_text), withText("Koi Fish * 250"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView4.check(matches(withText("Koi Fish * 250")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.previous_text), withText("Entrance and Exit Gate * 210"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView5.check(matches(withText("Entrance and Exit Gate * 210")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.previous_text), withText("Entrance and Exit Gate * 210"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView6.check(matches(withText("Entrance and Exit Gate * 210")));

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.next_btn), withText("next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.exhibit_name), withText("Koi Fish"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView7.check(matches(withText("Koi Fish")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.previous_text), withText("Crocodiles * 0"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView8.check(matches(withText("Crocodiles * 0")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.next_text), withText("Entrance and Exit Gate * 60"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView9.check(matches(withText("Entrance and Exit Gate * 60")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.next_text), withText("Entrance and Exit Gate * 60"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView10.check(matches(withText("Entrance and Exit Gate * 60")));

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.next_btn), withText("next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.exhibit_name), withText("Entrance and Exit Gate"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView11.check(matches(withText("Entrance and Exit Gate")));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.exhibit_name), withText("Entrance and Exit Gate"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView12.check(matches(withText("Entrance and Exit Gate")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<>() {
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
