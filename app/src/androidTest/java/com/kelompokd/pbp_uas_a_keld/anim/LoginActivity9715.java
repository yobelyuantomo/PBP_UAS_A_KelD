package com.kelompokd.pbp_uas_a_keld.anim;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.kelompokd.pbp_uas_a_keld.LoginActivity;
import com.kelompokd.pbp_uas_a_keld.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivity9715 {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginActivity9715() {
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.input_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_email),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("yobelyuantomo@gmail.com"), closeSoftKeyboard());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.input_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_password),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("123456"), closeSoftKeyboard());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btn_login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        materialButton.perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.input_email), withText("yobelyuantomo@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_email),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("yobelyuantom"));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btn_login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        materialButton2.perform(click());


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.input_email), withText("yobelyuantom"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_email),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText6.perform(replaceText("yobelyuantomo@gmail.com"));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.input_password), withText("123456"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_password),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText8.perform(replaceText("123"));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btn_login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        materialButton3.perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.input_password), withText("123"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_password),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText10.perform(replaceText("123gjghjfhj"));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btn_login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        materialButton4.perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText12 = onView(
                allOf(withId(R.id.input_email), withText("yobelyuantomo@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_email),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText12.perform(replaceText("yobelymo@gmail.com"));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btn_login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        materialButton5.perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText15 = onView(
                allOf(withId(R.id.input_email), withText("yobelymo@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_email),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText15.perform(replaceText("yobelyuantomo@gmail.com"));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText17 = onView(
                allOf(withId(R.id.input_password), withText("123gjghjfhj"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.layout_password),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText17.perform(replaceText("11111111"));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.btn_login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        materialButton6.perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
