package com.example.s1103338_s1104467_iiatimd_app;

public class GetRecipeTask implements Runnable{

    AppDatabase db;

    public GetRecipeTask(AppDatabase db){
        this.db = db;
    }
    @Override
    public void run() {
        db.recipeDAO().getAll();
    }
}
