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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RecyclerRecepies extends AppCompatActivity implements RecipeAdapter.OnClickListener{

    private RecyclerView recyclerView;
    private RecipeAdapter mRecipeAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<RecipeItem> mRecipeList;
    private RequestQueue mRequestQueue;


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

//        recyclerViewAdapter = new ; vul hier de recycler db in
//        recyclerView.setAdapter(recyclerViewAdapter);
    }


    private void parseJSON() {
        String url = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true";
//        String url = "http://iiatimd.test/api/recipes";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits"); //hits is de naam van array in de url

                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject hit = jsonArray.getJSONObject(i);

                        //waardes uit array halen( de namen van database)
                        String receptTitel = hit.getString("user");
                        String imageURL = hit.getString("previewURL");

                        mRecipeList.add(new RecipeItem( imageURL, receptTitel));
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
//        mRecipeList.get(position);
//        Intent detailRecipe = new Intent(this, list.java);
        RecipeItem clickedItem = mRecipeList.get(position);
        Log.d("clickview", "onRecipeClick: clicked");
        Toast.makeText(this, "KLIK", Toast.LENGTH_SHORT).show();
    }

}