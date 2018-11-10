
package com.fouomene.banking.app;

import android.content.Intent;
import  android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.fouomene.banking.app.fragment.StepDetailFragment;
import com.fouomene.banking.app.model.Recipe;
import com.fouomene.banking.app.model.Step;
import com.fouomene.banking.app.fragment.StepFragment;
import com.fouomene.banking.app.utils.Utility;

import java.util.Collections;
import java.util.List;


public class StepActivity extends AppCompatActivity implements StepFragment.Callback {

    private static final String STEPDETAILFRAGMENT_TAG = "SDFTAG";

    private boolean mTwoPane;

    private Toolbar toolbar;

    private Recipe mRecipe;
    private List<Step> mStepsList = Collections.emptyList();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // to display Icon launcher
        ActionBar actionBar = getSupportActionBar();

        //icon back
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle(Utility.getPreferredRecipe(getApplicationContext()));


        Intent intent = getIntent();
        if ( intent != null && intent.getParcelableExtra(Utility.EXTRA_RECIPE) != null){
           mRecipe = intent.getParcelableExtra(Utility.EXTRA_RECIPE);
           mStepsList = mRecipe.getSteps();
        }

        if (findViewById(R.id.fragment_step_detail) != null) {

            //The Step Activity called via intent. Inspect the intent for Steps data.

            StepDetailFragment fragment = new StepDetailFragment();

            if (mStepsList.size()!=0) {
                Bundle args = new Bundle();
                args.putParcelable(Utility.EXTRA_STEP, mStepsList.get(0));
                fragment.setArguments(args);
            }
            // The step container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the step view in this activity by
            // adding or replacing the step detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_step_detail, fragment, STEPDETAILFRAGMENT_TAG)
                        .commit();
        }


        } else {
            mTwoPane = false;
            //getSupportActionBar().setElevation(0f);
        }


    }

    @Override
    public void onItemSelected(Step step) {

        if (mTwoPane) {
            // In two-pane mode, show the player view in this activity by
            // adding or replacing the step detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putParcelable(Utility.EXTRA_STEP, step);

            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_step_detail, fragment, STEPDETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(Utility.EXTRA_STEP, step);
            startActivity(intent);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
