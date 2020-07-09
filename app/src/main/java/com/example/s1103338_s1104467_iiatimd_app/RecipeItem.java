package com.example.s1103338_s1104467_iiatimd_app;

public class RecipeItem {
    private String mImageURL;
    private String mReceptTitel;
    private String mDescription, mRecipe;

    public RecipeItem( String imageURL, String recipeTitel, String description, String recipe){
//    public RecipeItem(String recipeTitel, String titel){
        mImageURL = imageURL;
        mReceptTitel = recipeTitel;
        mDescription = description;
        mRecipe = recipe;

    }
    public String getmImageURL(){
        return mImageURL;
    }

    public String getmReceptTitel(){
        return mReceptTitel;
    }
    public String getmDescription(){
        return mDescription;
    }

    public String getmRecipe() {
        return mRecipe;
    }
}
