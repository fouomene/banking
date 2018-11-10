package com.fouomene.banking.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.espresso.IdlingResource;
import android.text.TextUtils;

import com.fouomene.banking.app.R;
import com.fouomene.banking.app.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static final String API_URL = "https://d17h27t6h515a5.cloudfront.net";
    public static final String EXTRA_RECIPE = "com.fouomene.banking.app.model.recipe";
    public static final String EXTRA_STEP = "com.fouomene.banking.app.model.step";

    public static void setPreferredCurrentVisiblePosition(Context context, String position) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(context.getString(R.string.pref_current_position_key), position);
        editor.commit();
    }

    public static String getPreferredCurrentVisiblePosition(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_current_position_key),
                context.getString(R.string.pref_current_position_default));
    }


    public static void setPreferredCurrentVisiblePositionStep(Context context, String positionStep) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(context.getString(R.string.pref_current_positionstep_key), positionStep);
        editor.commit();
    }

    public static String getPreferredCurrentVisiblePositionStep(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_current_positionstep_key),
                context.getString(R.string.pref_current_positionstep_default));
    }

    public static void setPreferredRecipe(Context context, String recipe) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(context.getString(R.string.pref_recipe_name_key), recipe);
        editor.commit();
    }

    public static String getPreferredRecipe(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_recipe_name_key),
                context.getString(R.string.pref_recipe_name_default));
    }

    public static void setPreferredIngredients(Context context, String ingredients) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(context.getString(R.string.pref_ingredients_key), ingredients);
        editor.commit();
    }

    public static String getPreferredIngredients(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_ingredients_key),
                context.getString(R.string.pref_ingredients_default));
    }

    public static void setPreferredStep(Context context, String step) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(context.getString(R.string.pref_step_description_key), step);
        editor.commit();
    }

    public static String getPreferredStep(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_step_description_key),
                context.getString(R.string.pref_step_description_default));
    }


    public static String getStringFromIngredientList(List<Ingredient> ingredients)  {
        List<String> strList = new ArrayList<>();
        for (Ingredient ingredient: ingredients) {
            strList.add(ingredient.getIngredient());
        }
        return TextUtils.join(" , ",strList );
    }

    public static String getStringLnFromIngredientList(List<Ingredient> ingredients)  {
        List<String> strList = new ArrayList<>();
        for (Ingredient ingredient: ingredients) {
            strList.add(ingredient.getIngredient());
        }
        return TextUtils.join("\n",strList );
    }


    private static SimpleIdlingResource sIdlingResource;
    public static IdlingResource getIdlingResource() {
        if (sIdlingResource == null) {
            sIdlingResource = new SimpleIdlingResource();
        }
        return sIdlingResource;
    }

    public static void setIdleResourceTo(boolean isIdleNow){
        if (sIdlingResource == null) {
            sIdlingResource = new SimpleIdlingResource();
        }
        sIdlingResource.setIdleState(isIdleNow);
    }
}
