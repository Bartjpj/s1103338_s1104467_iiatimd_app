package com.example.s1103338_s1104467_iiatimd_app;

import android.util.Log;

public class InsertRecipeTask implements Runnable {

    AppDatabase db;
    RecipeItem recipeItem;

    public InsertRecipeTask(AppDatabase db, RecipeItem recipeItem){
        this.db = db;
        this.recipeItem = recipeItem;
    }

    @Override
    public void run() {
        db.recipeDAO().InsertRecipe(recipeItem);
    }
}
