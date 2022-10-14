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
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Full integration test that follows given success scenario for Milestone 2
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class SuccessScenarioTest {

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
    public InstantTaskExecutorRule execRule = new InstantTaskExecutorRule();

    @Rule
    public ActivityScenarioRule<SearchActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(SearchActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");

    @Test
    public void successScenarioTest() {
        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.actionSearch), withContentDescription("exhibit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction searchAutoComplete = onView(
                allOf(withId(androidx.appcompat.R.id.search_src_text),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.search_plate),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(click());

        ViewInteraction searchAutoComplete2 = onView(
                allOf(withId(androidx.appcompat.R.id.search_src_text),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.search_plate),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete2.perform(replaceText("gor"), closeSoftKeyboard());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.animal_name), withText("Gorillas"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(androidx.appcompat.R.id.search_close_btn), withContentDescription("Clear query"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.search_plate),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.search_edit_frame),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatImageView.perform(click());


        ViewInteraction searchAutoComplete3 = onView(
                allOf(withId(androidx.appcompat.R.id.search_src_text),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.search_plate),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete3.perform(replaceText("hip"), closeSoftKeyboard());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.animal_name), withText("Hippos"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(androidx.appcompat.R.id.search_close_btn), withContentDescription("Clear query"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.search_plate),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.search_edit_frame),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction searchAutoComplete4 = onView(
                allOf(withId(androidx.appcompat.R.id.search_src_text),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.search_plate),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete4.perform(replaceText("orangu"), closeSoftKeyboard());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.animal_name), withText("Orangutans"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView3.perform(click());

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(androidx.appcompat.R.id.search_close_btn), withContentDescription("Clear query"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.search_plate),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.search_edit_frame),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatImageView3.perform(click());

        ViewInteraction searchAutoComplete5 = onView(
                allOf(withId(androidx.appcompat.R.id.search_src_text),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.search_plate),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.search_edit_frame),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete5.perform(replaceText("cap"), closeSoftKeyboard());

        ViewInteraction materialTextView4 = onView(
                allOf(withId(R.id.animal_name), withText("Capuchin Monkeys"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView4.perform(click());

        ViewInteraction appCompatImageView4 = onView(
                allOf(withId(androidx.appcompat.R.id.search_close_btn), withContentDescription("Clear query"),
                        childAtPosition(
                                allOf(withId(androidx.appcompat.R.id.search_plate),
                                        childAtPosition(
                                                withId(androidx.appcompat.R.id.search_edit_frame),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatImageView4.perform(click());

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

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.next_btn), withText("next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.exhibit_name), withText("Hippos"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("Hippos")));

        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction materialTextView5 = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("Set Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView5.perform(click());

        ViewInteraction editText12 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        editText12.perform(replaceText("32.73971798112842"), closeSoftKeyboard());

        ViewInteraction editText13 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        editText13.perform(replaceText("-117.16644660080382"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(android.R.id.button1), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton4.perform(scrollTo(), click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.exhibit_name), withText("Hippos"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView2.check(matches(withText("Hippos")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.directions_view), withText("Proceed on Treetops Way 60 feet towards Treetops Way / Hippo Trail\nProceed on Hippo Trail 30 feet to Hippos"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView3.check(matches(withText("Proceed on Treetops Way 60 feet towards Treetops Way / Hippo Trail\nProceed on Hippo Trail 30 feet to Hippos")));

        ViewInteraction overflowMenuButton2 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton2.perform(click());

        ViewInteraction materialTextView6 = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("Set Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView6.perform(click());

        ViewInteraction editText14 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        editText14.perform(replaceText("32.746302644092815"), closeSoftKeyboard());

        ViewInteraction editText15 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        editText15.perform(replaceText("-117.16659525430192"), closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(android.R.id.button1), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton5.perform(scrollTo(), click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.exhibit_name), withText("Hippos"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView4.check(matches(withText("Hippos")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.directions_view), withText("Proceed on Hippo Trail 10 feet to Hippos"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView5.check(matches(withText("Proceed on Hippo Trail 10 feet to Hippos")));

        ViewInteraction overflowMenuButton3 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton3.perform(click());

        ViewInteraction materialTextView7 = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("Set Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView7.perform(click());

        ViewInteraction editText16 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        editText16.perform(replaceText("32.746320519009025"), closeSoftKeyboard());

        ViewInteraction editText17 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        editText17.perform(replaceText("-117.16364410510093"), closeSoftKeyboard());

        ViewInteraction materialButton6 = onView(
                allOf(withId(android.R.id.button1), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton6.perform(scrollTo(), click());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.exhibit_name), withText("Hippos"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView6.check(matches(withText("Hippos")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.directions_view), withText("Find Hippos nearby"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView7.check(matches(withText("Find Hippos nearby")));

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.next_btn), withText("next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton7.perform(click());

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.exhibit_name), withText("Capuchin Monkeys"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView8.check(matches(withText("Capuchin Monkeys")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.directions_view), withText("Proceed on Hippo Trail 40 feet towards Monkey Trail / Hippo Trail\nProceed on Monkey Trail 50 feet to Capuchin Monkeys"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView9.check(matches(withText("Proceed on Hippo Trail 40 feet towards Monkey Trail / Hippo Trail\nProceed on Monkey Trail 50 feet to Capuchin Monkeys")));

        ViewInteraction overflowMenuButton4 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton4.perform(click());

        ViewInteraction materialTextView8 = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("Set Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView8.perform(click());

        ViewInteraction editText18 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        editText18.perform(replaceText("32.748983757274594"), closeSoftKeyboard());

        ViewInteraction editText19 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        editText19.perform(replaceText("-117.16951754140803"), closeSoftKeyboard());

        ViewInteraction materialButton8 = onView(
                allOf(withId(android.R.id.button1), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton8.perform(scrollTo(), click());

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.exhibit_name), withText("Capuchin Monkeys"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView10.check(matches(withText("Capuchin Monkeys")));

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.directions_view), withText("Proceed on Monkey Trail 50 feet to Capuchin Monkeys"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView11.check(matches(withText("Proceed on Monkey Trail 50 feet to Capuchin Monkeys")));

        ViewInteraction materialButton9 = onView(
                allOf(withId(R.id.previous_btn), withText("Previous"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton9.perform(click());

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.exhibit_name), withText("Hippos"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView12.check(matches(withText("Hippos")));

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.directions_view), withText("Proceed on Hippo Trail 40 feet to Hippos"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView13.check(matches(withText("Proceed on Hippo Trail 40 feet to Hippos")));

        ViewInteraction overflowMenuButton5 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton5.perform(click());

        ViewInteraction materialTextView9 = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("Set Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView9.perform(click());

        ViewInteraction editText20 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        editText20.perform(replaceText("32.748538318135594"), closeSoftKeyboard());

        ViewInteraction editText21 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        editText21.perform(replaceText("-117.17255093386991"), closeSoftKeyboard());

        ViewInteraction materialButton10 = onView(
                allOf(withId(android.R.id.button1), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton10.perform(scrollTo(), click());

        ViewInteraction textView14 = onView(
                allOf(IsInstanceOf.instanceOf(android.widget.TextView.class), withText("Alert!"),
                        withParent(allOf(IsInstanceOf.instanceOf(android.widget.LinearLayout.class),
                                withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView14.check(matches(withText("Alert!")));

        ViewInteraction textView15 = onView(
                allOf(withId(android.R.id.message), withText("Would you like to replan your route?"),
                        withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView15.check(matches(withText("Would you like to replan your route?")));

        ViewInteraction materialButton11 = onView(
                allOf(withId(android.R.id.button1), withText("Yes"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton11.perform(scrollTo(), click());

        ViewInteraction overflowMenuButton6 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton6.perform(click());

        ViewInteraction materialTextView10 = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("Set Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView10.perform(click());

        ViewInteraction editText22 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        editText22.perform(replaceText("32.746320519009025"), closeSoftKeyboard());

        ViewInteraction editText23 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        editText23.perform(replaceText("-117.16364410510093"), closeSoftKeyboard());

        ViewInteraction materialButton12 = onView(
                allOf(withId(android.R.id.button1), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton12.perform(scrollTo(), click());

        ViewInteraction textView16 = onView(
                allOf(withId(R.id.directions_view), withText("Find Hippos nearby"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView16.check(matches(withText("Find Hippos nearby")));

        ViewInteraction materialButton13 = onView(
                allOf(withId(R.id.next_btn), withText("next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton13.perform(click());

        ViewInteraction overflowMenuButton7 = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton7.perform(click());

        ViewInteraction materialTextView11 = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("Set Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView11.perform(click());

        ViewInteraction editText24 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        editText24.perform(replaceText("32.74577064618906"), closeSoftKeyboard());

        ViewInteraction editText25 = onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        editText25.perform(replaceText("-117.17837098901099"), closeSoftKeyboard());

        ViewInteraction materialButton14 = onView(
                allOf(withId(android.R.id.button1), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton14.perform(scrollTo(), click());

        ViewInteraction textView17 = onView(
                allOf(withId(android.R.id.message), withText("Would you like to replan your route?"),
                        withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView17.check(matches(withText("Would you like to replan your route?")));

        ViewInteraction materialButton15 = onView(
                allOf(withId(android.R.id.button1), withText("Yes"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton15.perform(scrollTo(), click());

        ViewInteraction textView18 = onView(
                allOf(withId(R.id.exhibit_name), withText("Gorillas"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView18.check(matches(withText("Gorillas")));

        ViewInteraction textView19 = onView(
                allOf(withId(R.id.directions_view), withText("Proceed on Monkey Trail 50 feet to Gorillas"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView19.check(matches(withText("Proceed on Monkey Trail 50 feet to Gorillas")));

        ViewInteraction materialButton16 = onView(
                allOf(withId(R.id.next_btn), withText("next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton16.perform(click());

        ViewInteraction textView20 = onView(
                allOf(withId(R.id.exhibit_name), withText("Capuchin Monkeys"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView20.check(matches(withText("Capuchin Monkeys")));

        ViewInteraction textView21 = onView(
                allOf(withId(R.id.directions_view), withText("Proceed on Monkey Trail 230 feet to Capuchin Monkeys"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView21.check(matches(withText("Proceed on Monkey Trail 230 feet to Capuchin Monkeys")));

        ViewInteraction materialButton17 = onView(
                allOf(withId(R.id.previous_btn), withText("Previous"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton17.perform(click());

        ViewInteraction materialButton18 = onView(
                allOf(withId(R.id.skip_btn), withText("Skip"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton18.perform(click());

        ViewInteraction textView22 = onView(
                allOf(withId(R.id.previous_text), withText("Hippos * 70"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView22.check(matches(withText("Hippos * 70")));

        ViewInteraction textView23 = onView(
                allOf(withId(R.id.exhibit_name), withText("Capuchin Monkeys"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView23.check(matches(withText("Capuchin Monkeys")));

        ViewInteraction materialButton19 = onView(
                allOf(withId(R.id.next_btn), withText("next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton19.perform(click());

        ViewInteraction textView24 = onView(
                allOf(withId(R.id.exhibit_name), withText("Entrance and Exit Gate"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView24.check(matches(withText("Entrance and Exit Gate")));
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
