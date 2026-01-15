package com.example.listycity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    Button addButton;
    Button removeButton;

    LinearLayout dialogContainer;
    Button confirmButton;
    EditText textInput;

    int selected = -1;
    boolean dialogOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.listView);
        addButton = findViewById(R.id.addButton);
        removeButton = findViewById(R.id.removeButton);

        dialogContainer = findViewById(R.id.dialogContainer);
        confirmButton = findViewById(R.id.confirmButton);
        textInput = findViewById(R.id.editTextInput);

        String[] Cities = {"Edmonton", "Calgary", "Amsterdam"};

        dataList = new ArrayList<String>();
        dataList.addAll(Arrays.asList(Cities));

        cityAdapter = new ArrayAdapter<String>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = position;
                if(!dialogOpen)
                removeButton.setEnabled(true);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButton.setEnabled(false);
                removeButton.setEnabled(false);
                showDialog();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = textInput.getText().toString();
                if(!input.isBlank()) {
                    cityAdapter.add(input);
                    textInput.setText("");
                    hideDialog();
                    addButton.setEnabled(true);
                    if(selected > -1) removeButton.setEnabled(true);
                }
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.remove(selected);
                cityList.setAdapter(cityAdapter);

                selected = -1;
                removeButton.setEnabled(false);
            }
        });
    }

    void showDialog() {
        dialogOpen = true;
        dialogContainer.setVisibility(VISIBLE);
        confirmButton.setEnabled(true);
        textInput.setEnabled(true);
    }

    void hideDialog() {
        dialogOpen = false;
        dialogContainer.setVisibility(GONE);
        confirmButton.setEnabled(true);
        textInput.setEnabled(true);
    }
}