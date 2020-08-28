package com.example.s1103338_s1104467_iiatimd_app;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDAO {

    @Query("SELECT * FROM RecipeItem")
    List<RecipeItem> getAll();

    @Insert
    void InsertRecipe(RecipeItem recipeItem);

    @Delete
    void delete(RecipeItem recipeItem);
}
