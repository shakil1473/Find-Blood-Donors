package com.example.shakil.findblooddonor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {


    static DatabaseReference mDatabaseReference;
    static FirebaseAuth mAuth;
    static FirebaseUser firebaseUser;

    private ListView listViewUserInfo;

    private Button btnBack;
    private Button btnLogout;
    private Button btnEditProfile;

    static String mUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        listViewUserInfo = findViewById(R.id.list_item_profile);

        btnBack = findViewById(R.id.btn_back_profile);
        btnLogout = findViewById(R.id.btn_logout);
        btnEditProfile = findViewById(R.id.btn_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot donorSnapshot : dataSnapshot.getChildren()){

                    FirebaseUser user = mAuth.getCurrentUser();
                    mUserID = user.getUid().toString();

                    UserInformation userInformation = donorSnapshot.getValue(UserInformation.class);

                    userInformation.setDonorName(donorSnapshot.child(mUserID).getValue(UserInformation.class).getDonorName());
                    userInformation.setBloodGroup(donorSnapshot.child(mUserID).getValue(UserInformation.class).getBloodGroup());
                    userInformation.setContactNo(donorSnapshot.child(mUserID).getValue(UserInformation.class).getContactNo());


                    ArrayList<String> userInfo = new ArrayList<>();

                    userInfo.add(userInformation.getDonorName());
                    userInfo.add(userInformation.getBloodGroup());
                    userInfo.add(userInformation.getContactNo());

                    ArrayAdapter adapter = new ArrayAdapter(Profile.this,android.R.layout.simple_list_item_1,userInfo);
                    listViewUserInfo.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editProfile = new Intent(Profile.this,EditProfile.class);
                startActivity(editProfile);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnHome = new Intent(Profile.this,Home.class);
                startActivity(returnHome);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(Profile.this,MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }

}
