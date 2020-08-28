package com.example.s1103338_s1104467_iiatimd_app;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RecipeItem {

    @ColumnInfo
    private String mImageURL;
    @ColumnInfo
    private String mReceptTitel;
    @ColumnInfo
    private String mDescription;
    @ColumnInfo
    private String mRecipe;

    @PrimaryKey
    private int mUuid;

    public RecipeItem( String imageURL, String receptTitel, String description, String recipe, int uuid){
//    public RecipeItem(String recipeTitel, String titel){
        this.mImageURL = imageURL;
        this.mReceptTitel = receptTitel;
        this.mDescription = description;
        this.mRecipe = recipe;
        this.mUuid = uuid;

    }
    public String getImageURL(){

        return mImageURL;
    }

    public String getReceptTitel(){
        return mReceptTitel;
    }
    public String getDescription(){

        return mDescription;
    }

    public String getRecipe() {
        return mRecipe;
    }

    public int getUuid(){
        return mUuid;
    }
}
