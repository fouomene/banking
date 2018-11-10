package com.fouomene.banking.app;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.fouomene.banking.app.utils.Utility;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class TestMainActivity {

    private MainActivity mActivity;
    private int resId = R.id.recyclerViewRecipes;
    private RecyclerView mRecyclerView;
    private int itemCount = 0;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {

        mActivityRule.getActivity()
                .getFragmentManager().beginTransaction();

        //we have idling resources since we have data that will be downloaded to populate our recipes recycler view
        mIdlingResource = Utility.getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);

        this.mActivity = this.mActivityRule.getActivity();
        this.mRecyclerView = this.mActivity.findViewById(this.resId);
        this.itemCount = this.mRecyclerView.getAdapter().getItemCount();
    }


    @Test
    public void RecyclerViewTestClicking() {

       onView(withId(this.resId))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

    @Test
    public void RecyclerViewTestDisplayed() {
        onView(new RecyclerViewMatcher(this.resId)
                .atPositionOnView(0, R.id.cardviewrecipe))
                .check(matches(isDisplayed()));

            onView(new RecyclerViewMatcher(this.resId)
                    .atPositionOnView(0, R.id.name))
                    .check(matches(withText("Nutella Pie")));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }


}
