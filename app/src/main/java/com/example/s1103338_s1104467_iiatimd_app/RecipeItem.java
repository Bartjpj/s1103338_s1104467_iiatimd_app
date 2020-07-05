package com.example.s1103338_s1104467_iiatimd_app;

public class RecipeItem {
    private String mImageURL;
    private String mReceptTitel;
    private String mTitel;

    public RecipeItem( String imageURL, String recipeTitel, String titel){
//    public RecipeItem(String recipeTitel, String titel){
        mImageURL = imageURL;
        mReceptTitel = recipeTitel;
        mTitel = titel;

    }
    public String getmImageURL(){
        return mImageURL;
    }

    public String getmReceptTitel(){
        return mReceptTitel;
    }
    public String getmTitel(){
        return mTitel;
    }
}
