package com.example.s1103338_s1104467_iiatimd_app;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import static com.example.s1103338_s1104467_iiatimd_app.RecyclerRecepies.EXTRA_RECIPETITLE;
import static com.example.s1103338_s1104467_iiatimd_app.RecyclerRecepies.EXTRA_URL;

public class RandomRecipies extends AppCompatActivity implements SensorEventListener {

    private static final float SHAKE_THRESHOLD = 7;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    public ArrayList<RecipeItem> mRecipeList;
    private RequestQueue mRequestQueue;

    private boolean activityRunning;

    int last_x;
    int last_y;
    int last_z;
    int lastUpdate;

    private FloatingActionButton fabBack;
    private FloatingActionButton fabRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);
        setTitle("Recept");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Intent intent = getIntent();
        String imageURL = intent.getStringExtra(EXTRA_URL);
        String recipeTitle = intent.getStringExtra(EXTRA_RECIPETITLE);
        // vang hier het recept zelf op

        ImageView imageView = findViewById(R.id.imageView);
        TextView titleView = findViewById(R.id.recipeTitle);
        //initialiseer hier meer fields

        // vul de velden met de waardes
        Picasso.get().load(imageURL).fit().centerInside().into(imageView);
        titleView.setText(recipeTitle);

        mRecipeList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();


        fabBack = findViewById(R.id.fabBack);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent naarRecycler = new Intent(RandomRecipies.this, RecyclerRecepies.class);
                finish();
                startActivity(naarRecycler);
            }
        });

        fabRefresh = findViewById(R.id.fabRefresh);
        fabRefresh.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                refresh();
            }
        });

    }


    @Override
    protected void onResume(){
        super.onResume();

        activityRunning = true;
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(mSensor != null){
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(this, "Geen beweging :(", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPause(){
        super.onPause();
        activityRunning = false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(activityRunning == true) {

//            String name = String.valueOf(sensorEvent.values[0]);
//            Log.d("waarde", name);

            float x = sensorEvent.values[0];
            if ( x > SHAKE_THRESHOLD) {
//
//                Intent naarRandomRecipes = new Intent(this, RandomRecipies.class);
//
//                Random rand = new Random();
//                int position = rand.nextInt(mRecipeList.size());
//                RecipeItem clickedItem = mRecipeList.get(position);
//
//                naarRandomRecipes.putExtra(EXTRA_URL, clickedItem.getmImageURL());
//                naarRandomRecipes.putExtra(EXTRA_RECIPETITLE, clickedItem.getmReceptTitel());
//                finish();
//                startActivity(naarRandomRecipes);
//
//               Toast.makeText(this, "REFRESHED!" , Toast.LENGTH_SHORT).show();
                refresh();

//                Log.d("sensor", "shaked");
//                Toast.makeText(this, "REFRESHED!" , Toast.LENGTH_SHORT).show();
            }


        }

    }

    private void refresh(){
        Intent naarRandomRecipes = new Intent(this, RandomRecipies.class);

        Random rand = new Random();
        int position = rand.nextInt(mRecipeList.size());
        RecipeItem clickedItem = mRecipeList.get(position);

        naarRandomRecipes.putExtra(EXTRA_URL, clickedItem.getmImageURL());
        naarRandomRecipes.putExtra(EXTRA_RECIPETITLE, clickedItem.getmReceptTitel());
        finish();
        startActivity(naarRandomRecipes);

        Toast.makeText(this, "REFRESHED!" , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {    }

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
}
