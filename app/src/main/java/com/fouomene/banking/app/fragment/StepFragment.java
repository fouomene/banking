
package com.fouomene.banking.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fouomene.banking.app.R;
import com.fouomene.banking.app.StepDetailActivity;
import com.fouomene.banking.app.adapter.StepAdapter;
import com.fouomene.banking.app.model.Recipe;
import com.fouomene.banking.app.model.Step;
import com.fouomene.banking.app.utils.Utility;

import java.util.Collections;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class StepFragment extends Fragment implements StepAdapter.ItemClickListener  {

    private final String LOG_TAG = StepFragment.class.getSimpleName();


    private Recipe mRecipe;
    private List<Step> mStepsList = Collections.emptyList();

    private StepAdapter mStepAdapter;
    private TextView mIngredientsView;
    private RecyclerView mRecyclerView;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * StepDetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Step step);
    }

    public StepFragment() {
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //The Step Activity called via intent. Inspect the intent for Steps data.
        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        if ( intent != null && intent.getParcelableExtra(Utility.EXTRA_RECIPE) != null){
            mRecipe = intent.getParcelableExtra(Utility.EXTRA_RECIPE);
            mStepsList = mRecipe.getSteps();
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(Utility.EXTRA_RECIPE)) {
            mRecipe = savedInstanceState.getParcelable(Utility.EXTRA_RECIPE);
        }

        mIngredientsView = (TextView) rootView.findViewById(R.id.ingrediens);
        mIngredientsView.setText(Utility.getStringFromIngredientList(mRecipe.getIngredients()));

        // Set the RecyclerView to its corresponding view
        mRecyclerView = rootView.findViewById(R.id.recyclerViewSteps);

        // Set the layout for the RecyclerView to be a Linear Layout, which measures and
        // positions items within a RecyclerView into a Linear list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        // Initialize the adapter and attach it to the RecyclerView
        mStepAdapter = new StepAdapter(getActivity().getApplicationContext(), this);
        mRecyclerView.setAdapter(mStepAdapter);
        mStepAdapter.setSteps(mStepsList);
       // ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPosition(Integer.parseInt(Utility.getPreferredCurrentVisiblePositionStep(getActivity().getApplicationContext())));

        return rootView;
    }


    @Override
    public void onItemClickListener(Step itemStep) {

        Utility.setPreferredCurrentVisiblePositionStep(getActivity().getApplicationContext(),((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition()+"");

        Utility.setPreferredStep(getActivity().getApplicationContext(),itemStep.getShortDescription());
        // When the user clicks on the step

        ((Callback) getActivity()).onItemSelected(itemStep);
        /*Intent intent = new Intent(getActivity().getApplicationContext(), StepDetailActivity.class);
        intent.putExtra(Utility.EXTRA_STEP, itemStep);
        startActivity(intent);*/
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        outState.putParcelable(Utility.EXTRA_RECIPE, mRecipe);
        super.onSaveInstanceState(outState);
    }

}