package com.fouomene.banking.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fouomene.banking.app.R;
import com.fouomene.banking.app.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * This RecipeAdapter creates and binds ViewHolders, that hold poster a recipe,
 * to a RecyclerView to efficiently display data.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {


    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds recipe data and the Context
    private List<Recipe> mRecipeEntries;
    private Context mContext;

    /**
     * Constructor for the RecipeAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public RecipeAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new RecipeViewHolder that holds the view for each task
     */
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the recipe_item to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recipe_item, parent, false);

        return new RecipeViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        // Determine the values of the wanted data
        Recipe recipeEntry = mRecipeEntries.get(position);

        //Set values
        holder.nameView.setText(recipeEntry.getName());

        if (!TextUtils.isEmpty(recipeEntry.getImage())){

            Picasso.with(mContext)
                    .load(recipeEntry.getImage())
                    .placeholder(R.drawable.noposter)
                    .error(R.drawable.noposter)
                    .into(holder.imageView);
        }
    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mRecipeEntries == null) {
            return 0;
        }
        return mRecipeEntries.size();
    }

    /**
     * When data changes, this method updates the list of recipeEntries
     * and notifies the adapter to use the new values on it
     */
    public void setRecipes(List<Recipe> recipeEntries) {
        mRecipeEntries = recipeEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(Recipe itemRecipe);
    }

    // Inner class for creating ViewHolders
    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView nameView;

        /**
         * Constructor for the RecipeViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public RecipeViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            nameView = itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Recipe elementRecipe = mRecipeEntries.get(getAdapterPosition());
            mItemClickListener.onItemClickListener(elementRecipe);
        }
    }
}