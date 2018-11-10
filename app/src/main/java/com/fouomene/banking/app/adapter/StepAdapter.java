package com.fouomene.banking.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fouomene.banking.app.R;
import com.fouomene.banking.app.model.Step;

import java.util.List;


/**
 * This StepAdapter creates and binds ViewHolders, that hold step,
 * to a RecyclerView to efficiently display data.
 */
public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {


    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds step data and the Context
    private List<Step> mStepEntries;
    private Context mContext;

    /**
     * Constructor for the stepAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public StepAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new StepViewHolder that holds the view for each task
     */
    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the step_item to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.step_item, parent, false);

        return new StepViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        // Determine the values of the wanted data
        Step stepEntry = mStepEntries.get(position);

        //Set values
        holder.shortDescriptionView.setText(stepEntry.getShortDescription());

    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mStepEntries == null) {
            return 0;
        }
        return mStepEntries.size();
    }

    /**
     * When data changes, this method updates the list of stepEntries
     * and notifies the adapter to use the new values on it
     */
    public void setSteps(List<Step> stepEntries) {
        mStepEntries = stepEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(Step itemStep);
    }

    // Inner class for creating ViewHolders
    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView shortDescriptionView;

        /**
         * Constructor for the StepViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public StepViewHolder(View itemView) {
            super(itemView);

            shortDescriptionView = itemView.findViewById(R.id.textViewShortDescription);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Step elementStep = mStepEntries.get(getAdapterPosition());
            mItemClickListener.onItemClickListener(elementStep);
        }
    }
}