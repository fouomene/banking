
package com.fouomene.banking.app;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fouomene.banking.app.fragment.StepDetailFragment;
import com.fouomene.banking.app.utils.Utility;


public class StepDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = StepDetailActivity.class.getSimpleName();


    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // to display Icon launcher
        ActionBar actionBar = getSupportActionBar();

        //icon back
        actionBar.setDisplayHomeAsUpEnabled(false);

        setTitle(Utility.getPreferredStep(getApplicationContext()));

        if (savedInstanceState == null) {
            // Create the Step Detail fragment and add it to the activity
            // using a fragment transaction.

            Bundle arguments = new Bundle();
           // mUri= getIntent().getData() ;
            arguments.putParcelable(Utility.EXTRA_STEP,getIntent().getParcelableExtra(Utility.EXTRA_STEP));

            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_step_detail, fragment)
                    .commit();
        }

    }



}
