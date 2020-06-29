package com.example.s1103338_s1104467_iiatimd_app;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        setContentView(R.layout.activity_random);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
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
                // HIER KOMT DATA VOOR NA SHAKEN. IF STATEMENT VOOR HET SHAKEN VAN DE TELEFOON
                Log.d("sensor", "shaked");
                Toast.makeText(this, "REFRESHED!" , Toast.LENGTH_SHORT).show();
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
