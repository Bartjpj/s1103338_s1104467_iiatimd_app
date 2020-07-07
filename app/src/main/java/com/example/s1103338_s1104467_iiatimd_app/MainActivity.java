package com.example.s1103338_s1104467_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
//    private RecyclerView.Adapter recyclerViewAdapter;
    private RecipeAdapter recipeAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        recyclerView = findViewById(R.id.recyclerView);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.hasFixedSize(); //voor de performance :)

        // button om naar de recyclerview te gaan. GEEN bundle aangezien geen data mee hoeft.
        Button buttonListView = findViewById(R.id.buttonListView);
        buttonListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecyclerRecepies.class);
                startActivity(intent);
            }
        });
        //Button om naar random recept te gaan
//        Button buttonRandom = (Button)findViewById(R.id.buttonRandom);
//        buttonRandom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent random = new Intent(MainActivity.this, RandomRecipies.class);
//                startActivity(random);
//            }
//        });

    }


}
