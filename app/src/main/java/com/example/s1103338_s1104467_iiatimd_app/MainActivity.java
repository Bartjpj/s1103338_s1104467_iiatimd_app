package com.example.s1103338_s1104467_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
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
// helllooo
//        recyclerViewAdapter = new ; vul hier de recycler db in
//        recyclerView.setAdapter(recyclerViewAdapter);
    }

}
