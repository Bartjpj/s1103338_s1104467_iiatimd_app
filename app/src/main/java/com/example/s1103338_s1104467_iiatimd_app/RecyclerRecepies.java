package com.example.s1103338_s1104467_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
//    public static final String EXTRA_RECIPE = "recipe";

    private RecyclerView recyclerView;
    private RecipeAdapter mRecipeAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<RecipeItem> mRecipeList;
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
                Intent naarRandomRecipes = new Intent(RecyclerRecepies.this, RecipeDetail.class);

                Random rand = new Random();
                int position = rand.nextInt(mRecipeList.size());
                RecipeItem clickedItem = mRecipeList.get(position);

                naarRandomRecipes.putExtra(EXTRA_URL, clickedItem.getmImageURL());
                naarRandomRecipes.putExtra(EXTRA_RECIPETITLE, clickedItem.getmReceptTitel());

                startActivity(naarRandomRecipes);
//
            }
        });

    }
    private void parseJSON(){
        String url = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true";
//        String url = "http://iiatimd.test/api/recipes";
//        String url = "http://10.0.2.2:8000/api/recipes";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits"); //hits is de naam van array in de url

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject data = jsonArray.getJSONObject(i);

                        //waardes uit array halen( de namen van database)
                        String receptTitel = data.getString("user");
                        String imageURL = data.getString("webformatURL");
                        String titel = data.getString("likes");

                        mRecipeList.add(new RecipeItem( imageURL, receptTitel, titel));
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

    @Override
    public void onRecipeClick(int position) {
        // navigate naar nieuwe activity

        Intent detailRecipe = new Intent(this, RecipeDetail.class);
        RecipeItem clickedItem = mRecipeList.get(position);

        detailRecipe.putExtra(EXTRA_URL, clickedItem.getmImageURL());
        detailRecipe.putExtra(EXTRA_RECIPETITLE, clickedItem.getmReceptTitel());
        // voeg hier het recept veld toe, net als in recipe item

        startActivity(detailRecipe);

        Log.d("clickview", "onRecipeClick: clicked");
        Toast.makeText(this, "KLIK", Toast.LENGTH_SHORT).show();
    }


}