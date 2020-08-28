package com.example.s1103338_s1104467_iiatimd_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    public AppDatabase db;
    public ArrayList<RecipeItem> recipes;

    private FloatingActionButton fabRandomRecipes;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_recepies);
        setTitle("Lijst van alle recepten");
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mRecipeList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);


        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

        parseJSON();

        fabRandomRecipes = findViewById(R.id.fabRandomRecipe);

        fabRandomRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent naarRandomRecipes = new Intent(RecyclerRecepies.this, RandomRecipies.class);

                Random rand = new Random();
                int position = rand.nextInt(mRecipeList.size());
                RecipeItem clickedItem = mRecipeList.get(position);

                naarRandomRecipes.putExtra(EXTRA_URL, clickedItem.getImageURL());
                naarRandomRecipes.putExtra(EXTRA_RECIPETITLE, clickedItem.getReceptTitel());
                naarRandomRecipes.putExtra(EXTRA_RECIPE, clickedItem.getRecipe());

                startActivity(naarRandomRecipes);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//
            }
        });

        recyclerView.scheduleLayoutAnimation();


    }

    private void parseJSON(){
        //Token in een andere string omdat deze vervalt om de dag. Zo is deze makkelijker te vervangen
        String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xMjcuMC4wLjE6ODAwMFwvYXBpXC9sb2dpbiIsImlhdCI6MTU5ODYxMTYwMywiZXhwIjoxNTk4NjE1MjAzLCJuYmYiOjE1OTg2MTE2MDMsImp0aSI6IjRZNDdqZzRwMW0ydVI0cm0iLCJzdWIiOjIsInBydiI6Ijg3ZTBhZjFlZjlmZDE1ODEyZmRlYzk3MTUzYTE0ZTBiMDQ3NTQ2YWEifQ.CxYqqSDFa38SkBIuSVR8hPHK7yqMpTWPEgTr4dzlq9Y";
        String url = "http://192.168.2.11:8000/api/recipes?token="+token;

//        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("stap", "in de request");
                try {
                    JSONArray jsonArray = response.getJSONArray("recipes"); //hits is de naam van array in de url
                    Log.d("stap", "onResponse: is iets binnen gekomen!");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject data = jsonArray.getJSONObject(i);

                        //waardes uit array halen( de namen van database)
                        String receptTitel = data.getString("name");
                        String imageURL = data.getString("image");
                        String description = data.getString("description");
                        String recipe = data.getString("step");
                        int uuid = data.getInt("id");

                        mRecipeList.add(new RecipeItem( imageURL, receptTitel, description, recipe, uuid));

                        // vul hier de DAO database.
//                        new Thread(new InsertRecipeTask(db, mRecipeList.get(i))).start();

                    }
                    mRecipeAdapter = new RecipeAdapter(RecyclerRecepies.this, mRecipeList);
                    recyclerView.setAdapter(mRecipeAdapter);
                    mRecipeAdapter.setOnRecipeClickListener(RecyclerRecepies.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("stap", "GEFAALD");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("stap", "error listner");
            }
        });
//        VolleySingleton.getInstance(this).addToRequestQueue(request);
        mRequestQueue.add(request);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onRecipeClick(int position) {
        // navigate naar nieuwe activity

        Intent detailRecipe = new Intent(this, RandomRecipies.class);

        RecipeItem clickedItem = mRecipeList.get(position);

        detailRecipe.putExtra(EXTRA_URL, clickedItem.getImageURL());
        detailRecipe.putExtra(EXTRA_RECIPETITLE, clickedItem.getReceptTitel());
        detailRecipe.putExtra(EXTRA_RECIPE, clickedItem.getRecipe());
        // voeg hier het recept veld toe, net als in recipe item

        startActivity(detailRecipe);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}