package com.example.s1103338_s1104467_iiatimd_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import static com.example.s1103338_s1104467_iiatimd_app.RecyclerRecepies.EXTRA_RECIPETITLE;
import static com.example.s1103338_s1104467_iiatimd_app.RecyclerRecepies.EXTRA_URL;

public class RecipeDetail extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recipe_detail);

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
}
