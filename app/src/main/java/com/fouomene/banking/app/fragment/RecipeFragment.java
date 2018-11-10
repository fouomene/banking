package com.fouomene.banking.app.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.Toast;


import com.fouomene.banking.app.BuildConfig;
import com.fouomene.banking.app.R;
import com.fouomene.banking.app.StepActivity;
import com.fouomene.banking.app.adapter.RecipeAdapter;
import com.fouomene.banking.app.api.BankingService;
import com.fouomene.banking.app.model.Recipe;
import com.fouomene.banking.app.utils.Utility;

import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecipeFragment extends Fragment implements RecipeAdapter.ItemClickListener {

    // Constant for logging
    private static final String TAG = RecipeFragment.class.getSimpleName();

    private List<Recipe> recipesList = Collections.emptyList();

    private final  int numberOfColumnsGrid = 2;
    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;

    private int layout_value;

    public RecipeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        Utility.setIdleResourceTo(false);
        // Set the RecyclerView to its corresponding view
        mRecyclerView = rootView.findViewById(R.id.recyclerViewRecipes);

        // Set the layout for the RecyclerView to be a Grid Layout, which measures and
        // positions items within a RecyclerView into a Grid list
        // smartphone or tablette
        layout_value = getContext().getResources().getInteger(R.integer.layout_value);

        if (layout_value == 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        }else {

            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), numberOfColumns()));
        }
        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new RecipeAdapter(getActivity().getApplicationContext(), this);
        mRecyclerView.setAdapter(mAdapter);


        //for log
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);

        if (isOnline()){


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Utility.API_URL)
                    .client(new OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            BankingService request = retrofit.create(BankingService.class);
            Call<List<Recipe>> call = request.getRecipes();

            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {

                    recipesList = response.body();
                    mAdapter.setRecipes(recipesList);

                   // Log.d(TAG, response.body().toString());
                /*   if (layout_value == 1) {
                        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPosition(Integer.parseInt(Utility.getPreferredCurrentVisiblePosition(getActivity().getApplicationContext())));
                    }else {

                        ((GridLayoutManager) mRecyclerView.getLayoutManager()).scrollToPosition(Integer.parseInt(Utility.getPreferredCurrentVisiblePosition(getActivity().getApplicationContext())));
                    }*/

                    Utility.setIdleResourceTo(true);
                }

                @Override
                public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                    Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.server_unavailable), Toast.LENGTH_SHORT).show();
                    Utility.setIdleResourceTo(true);
                }
            });


        }else {

            Snackbar snackbar = Snackbar
                    .make(rootView, getResources().getString(R.string.check_network), Snackbar.LENGTH_LONG);
            snackbar.show();


        }

        return rootView;
    }

    public boolean isOnline() {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;
    }

    @Override
    public void onItemClickListener(Recipe itemRecipe) {

        if (layout_value == 1) {
            Utility.setPreferredCurrentVisiblePosition(getActivity().getApplicationContext(),((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition()+"");
        }else {

            Utility.setPreferredCurrentVisiblePosition(getActivity().getApplicationContext(),((GridLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition()+"");
        }

        Utility.setPreferredRecipe(getActivity().getApplicationContext(),itemRecipe.getName());
        Utility.setPreferredIngredients(getActivity().getApplicationContext(), Utility.getStringLnFromIngredientList(itemRecipe.getIngredients()));
       // Utility.setPreferredCurrentVisiblePositionStep(getActivity().getApplicationContext(),-1+"");
        // When the user clicks on the Recipe
        Intent intent = new Intent(getActivity().getApplicationContext(), StepActivity.class);
        intent.putExtra(Utility.EXTRA_RECIPE, itemRecipe);
        startActivity(intent);
    }


    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the item
        int widthDivider = 200;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2; //to keep the grid aspect
        return nColumns;
    }



}