package com.example.s1103338_s1104467_iiatimd_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;


public class RecyclerRecepies extends AppCompatActivity implements RecipeAdapter.OnClickListener{

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_RECIPETITLE = "recipeName";
    public static final String EXTRA_RECIPE = "recipe";
//    public static final String EXTRA_RECIPE = "recipe";

    private RecyclerView recyclerView;
    private RecipeAdapter mRecipeAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<RecipeItem> mRecipeList;
    private RequestQueue mRequestQueue;

    private FloatingActionButton fabRandomRecipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_recepies);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mRecipeList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

        fabRandomRecipes = findViewById(R.id.fabRandomRecipe);

        fabRandomRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent naarRandomRecipes = new Intent(RecyclerRecepies.this, RandomRecipies.class);

                Random rand = new Random();
                int position = rand.nextInt(mRecipeList.size());
                RecipeItem clickedItem = mRecipeList.get(position);

                naarRandomRecipes.putExtra(EXTRA_URL, clickedItem.getmImageURL());
                naarRandomRecipes.putExtra(EXTRA_RECIPETITLE, clickedItem.getmReceptTitel());
                naarRandomRecipes.putExtra(EXTRA_RECIPE, clickedItem.getmRecipe());

                startActivity(naarRandomRecipes);
//
            }
        });

        recyclerView.scheduleLayoutAnimation();


    }
    private void parseJSON(){
//        String url = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true";
        String url = "https://veiligzonnen.bartj.nl/recipe.json";
//        String url = "http://iiatimd.test/api/recipes";
//        String url = "http://10.0.2.2:8000/api/recipes";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("recipe"); //hits is de naam van array in de url

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject data = jsonArray.getJSONObject(i);

                        //waardes uit array halen( de namen van database)
                        String receptTitel = data.getString("name");
                        String imageURL = data.getString("image");
                        String description = data.getString("description");
                        String recipe = data.getString("step");

                        mRecipeList.add(new RecipeItem( imageURL, receptTitel, description, recipe));
//                        mRecipeList.add(new RecipeItem(receptTitel, titel));
                    }
                    mRecipeAdapter = new RecipeAdapter(RecyclerRecepies.this, mRecipeList);
                    recyclerView.setAdapter(mRecipeAdapter);
                    mRecipeAdapter.setOnRecipeClickListener(RecyclerRecepies.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onRecipeClick(int position) {
        // navigate naar nieuwe activity

        Intent detailRecipe = new Intent(this, RandomRecipies.class);

        RecipeItem clickedItem = mRecipeList.get(position);

        detailRecipe.putExtra(EXTRA_URL, clickedItem.getmImageURL());
        detailRecipe.putExtra(EXTRA_RECIPETITLE, clickedItem.getmReceptTitel());
        detailRecipe.putExtra(EXTRA_RECIPE, clickedItem.getmRecipe());
        // voeg hier het recept veld toe, net als in recipe item

        startActivity(detailRecipe);

        Log.d("clickview", "onRecipeClick: clicked");
        Toast.makeText(this, "KLIK", Toast.LENGTH_SHORT).show();
    }


}