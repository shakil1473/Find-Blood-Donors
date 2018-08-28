package com.example.shakil.findblooddonor;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerBloodGroup;
    private Button btnFindBloodDonor;
    private Button btnProfile;
    private Button btnLogOut;

    static String searchBloodGroup;
    ArrayList bloodDonors;
    ArrayList profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        spinnerBloodGroup = findViewById(R.id.spinner_blood_group_home);
        btnFindBloodDonor = findViewById(R.id.btn_find_blood_donor);
        btnProfile = findViewById(R.id.btn_profile);
        btnLogOut = findViewById(R.id.btn_logout);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.blood_group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinnerBloodGroup.setAdapter(adapter);
        spinnerBloodGroup.setOnItemSelectedListener(this);

        btnFindBloodDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showBloodDonors = new Intent(Home.this,showBloodDonors.class);
                startActivity(showBloodDonors);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent profile = new Intent(Home.this,Profile.class);
                startActivity(profile);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(Home.this,MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        searchBloodGroup = adapterView.getItemAtPosition(position).toString().trim();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public ArrayList getBloodDonors(){
        return bloodDonors;
    }
    public ArrayList Profile(){
        return profile;
    }
}
