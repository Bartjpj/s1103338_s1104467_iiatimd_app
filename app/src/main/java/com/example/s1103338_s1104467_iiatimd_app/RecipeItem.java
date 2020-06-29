package com.example.s1103338_s1104467_iiatimd_app;

public class RecipeItem {
    private String mImageURL;
    private String mReceptTitel;

    public RecipeItem( String imageURL, String recipeTitel){
        mImageURL = imageURL;
        mReceptTitel = recipeTitel;

    }
    public String getmImageURL(){
        return mImageURL;
    }

    public String getmReceptTitel(){
        return mReceptTitel;
    }
}
