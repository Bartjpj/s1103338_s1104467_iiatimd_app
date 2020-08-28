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
import static com.example.s1103338_s1104467_iiatimd_app.RecyclerRecepies.EXTRA_RECIPE;

public class RandomRecipies extends AppCompatActivity implements SensorEventListener {

    private static final float SHAKE_THRESHOLD = 7;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    public ArrayList<RecipeItem> mRecipeList;
    private RequestQueue mRequestQueue;

    private boolean activityRunning;

    private FloatingActionButton fabBack, fabRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);
        setTitle("Recept");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Intent intent = getIntent();
        String imageURL = intent.getStringExtra(EXTRA_URL);
        String recipeTitle = intent.getStringExtra(EXTRA_RECIPETITLE);
        String recept = intent.getStringExtra(EXTRA_RECIPE);
        // vang hier het recept zelf op

        ImageView imageView = findViewById(R.id.imageView);
        TextView titleView = findViewById(R.id.recipeTitle);
        TextView receptView = findViewById(R.id.receptView);
        //initialiseer hier meer fields

        // vul de velden met de waardes
        Picasso.get().load(imageURL).fit().centerInside().into(imageView);
        titleView.setText(recipeTitle);
        receptView.setText(recept);

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

            float x = sensorEvent.values[0];
            if ( x > SHAKE_THRESHOLD) {
                refresh();

            }


        }

    }

    private void refresh(){
        Intent naarRandomRecipes = new Intent(this, RandomRecipies.class);

        Random rand = new Random();
        int position = rand.nextInt(mRecipeList.size());
        RecipeItem clickedItem = mRecipeList.get(position);

        naarRandomRecipes.putExtra(EXTRA_URL, clickedItem.getImageURL());
        naarRandomRecipes.putExtra(EXTRA_RECIPETITLE, clickedItem.getReceptTitel());
        naarRandomRecipes.putExtra(EXTRA_RECIPE, clickedItem.getRecipe());

        finish();
        startActivity(naarRandomRecipes);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        Toast.makeText(this, "REFRESHED!" , Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {    }

    private void parseJSON(){
//        String url = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true";
        String url = "https://veiligzonnen.bartj.nl/recipe.json";
//        String url = "http://192.168.2.6:8000/recipes";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("werkt!", "onResponse: is iets binnen gekomen!");
                    JSONArray jsonArray = response.getJSONArray("recipes"); //hits is de naam van array in de url
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject data = jsonArray.getJSONObject(i);

                        String receptTitel = data.getString("name");
                        Log.d("werkt", receptTitel);
                        String imageURL = data.getString("image");
                        String description = data.getString("description");
                        String recipe = data.getString("step");
                        int uuid = data.getInt("id");

                        mRecipeList.add(new RecipeItem( imageURL, receptTitel, description, recipe, uuid));
//                        mRecipeList.add(new RecipeItem(receptTitel, titel));
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
    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
