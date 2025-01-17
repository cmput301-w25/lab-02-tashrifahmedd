package com.example.listycity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<City> cityDataList;
    private ListView cityList;
    private ArrayAdapter<City> cityAdapter;
    private Button addCityButton;
    private Button deleteCityButton;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Initialize UI components
        cityList = findViewById(R.id.city_list);
        addCityButton = findViewById(R.id.add_city_button);
        deleteCityButton = findViewById(R.id.delete_city_button);

        // Initialize the data list and adapter
        cityDataList = new ArrayList<>();
        cityDataList.add(new City("Edmonton"));
        cityDataList.add(new City("Vancouver"));

        cityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                cityDataList);

        cityList.setAdapter(cityAdapter);

        // Set up click listeners
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            view.setSelected(true);
        });

        addCityButton.setOnClickListener(v -> showAddCityDialog());

        deleteCityButton.setOnClickListener(v -> {
            if (selectedPosition != -1) {
                cityDataList.remove(selectedPosition);
                cityAdapter.notifyDataSetChanged();
                selectedPosition = -1;
            }
        });
    }

    private void showAddCityDialog() {
        final EditText input = new EditText(this);

        new AlertDialog.Builder(this)
                .setTitle("Add City")
                .setView(input)
                .setPositiveButton("CONFIRM", (dialog, which) -> {
                    String cityName = input.getText().toString();
                    if (!cityName.isEmpty()) {
                        cityDataList.add(new City(cityName));
                        cityAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }
}