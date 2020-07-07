package com.example.s1103338_s1104467_iiatimd_app;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.s1103338_s1104467_iiatimd_app.RecyclerRecepies.EXTRA_RECIPETITLE;
import static com.example.s1103338_s1104467_iiatimd_app.RecyclerRecepies.EXTRA_URL;

public class RandomRecipies extends AppCompatActivity implements SensorEventListener {

    private static final float SHAKE_THRESHOLD = 7;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    private boolean activityRunning;

    int last_x;
    int last_y;
    int last_z;
    int lastUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Intent intent = getIntent();
        String imageURL = intent.getStringExtra(EXTRA_URL);
        String recipeTitle = intent.getStringExtra(EXTRA_RECIPETITLE);
        // vang hier het recept zelf op

        ImageView imageView = findViewById(R.id.imageView);
        TextView titleView = findViewById(R.id.recipeTitle);
        //initialiseer hier meer fields

        // vul de velden met de waardes
        Picasso.with(this).load(imageURL).fit().centerInside().into(imageView);
        titleView.setText(recipeTitle);
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

                Intent intent = new Intent(RandomRecipies.this, RandomRecipies.class);
                finish();
                startActivity(intent);

//                Log.d("sensor", "shaked");
//                Toast.makeText(this, "REFRESHED!" , Toast.LENGTH_SHORT).show();
            }
//
//            TextView mTextView = findViewById(R.id.textSensor);
//            mTextView.setText(name);

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
