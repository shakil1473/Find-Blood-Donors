package com.example.shakil.findblooddonor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class showBloodDonors extends AppCompatActivity {

    ListView listViewBloodDonors;
    DatabaseReference mDatabaseReference;
    private Button btnBack;
    private TextView textViewNoDonor;

    static String bloodGroupGiven;
    static String bloodGroupFetch;

    boolean donorFound = false;

    List<UserInformation> bloodDonorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_blood_donors);
        listViewBloodDonors = findViewById(R.id.list_donors);
        btnBack = findViewById(R.id.btn_return);
        textViewNoDonor = findViewById(R.id.textView_no_donor);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Donors");

        bloodDonorList = new ArrayList<>();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                bloodDonorList.clear();

                Home home = new Home();

                for (DataSnapshot bloodDonorSnapshot : dataSnapshot.getChildren()){
                    UserInformation userInformation = bloodDonorSnapshot.getValue(UserInformation.class);

                    bloodGroupFetch = userInformation.getBloodGroup().toString().trim();
                    bloodGroupGiven = home.searchBloodGroup;
                    if (bloodGroupGiven.equals(bloodGroupFetch)){
                        bloodDonorList.add(userInformation);
                        donorFound = true;
                    }
                }

                if (!donorFound){
                    textViewNoDonor.setText("Sorry!! No Blood Donor found");
                }

                BloodDonorList adapter = new BloodDonorList(showBloodDonors.this,bloodDonorList);
                listViewBloodDonors.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(showBloodDonors.this,Home.class);
                startActivity(home);
            }
        });
    }
}
