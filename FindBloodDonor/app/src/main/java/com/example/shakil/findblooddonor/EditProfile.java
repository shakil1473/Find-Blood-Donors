package com.example.shakil.findblooddonor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btnSave;
    private Button btnEditLogOut;
    private Button btnEditBack;
    private Button btnDeleteAccount;

   ;
    private EditText editTextContactNo;
    private EditText editTextName;

    private Spinner editSpinnertBloodGroup;

    static String name;
    static String contactNo;
    static String bloodGroup;
    static String userID;

    static FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btnSave = findViewById(R.id.btn_edit_save);
        btnEditLogOut =  findViewById(R.id.btn_edit_logout);
        btnEditBack = findViewById(R.id.btn_edit_back);
        btnDeleteAccount = findViewById(R.id.btn_delete_account);

        editTextName = findViewById(R.id.editText_edit_name);
        editTextContactNo = findViewById(R.id.editText_edit_contact);

        editSpinnertBloodGroup = findViewById(R.id.spinner_edit_blood_group);

        Profile profile = new Profile();

        userID = profile.mUserID;
        firebaseUser = profile.firebaseUser;


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.blood_group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        editSpinnertBloodGroup.setAdapter(adapter);
        editSpinnertBloodGroup.setOnItemSelectedListener(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateInfo(userID);
            }
        });

        btnEditLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(EditProfile.this,MainActivity.class);
                startActivity(mainActivity);
            }
        });

        btnEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnProfile = new Intent(EditProfile.this,Profile.class);
                startActivity(returnProfile);
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount(userID);
            }
        });
    }

    private void deleteAccount(String userID) {
        DatabaseReference donorReference = FirebaseDatabase.getInstance().getReference("Donors").child(userID);

        donorReference.removeValue();

        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EditProfile.this,"Best of Luck",Toast.LENGTH_LONG).show();

                    Intent mainActivity = new Intent(EditProfile.this,MainActivity.class);
                    startActivity(mainActivity);
                }
            }
        });
    }


    private void UpdateInfo(String userID) {


        name = editTextName.getText().toString().trim();
        contactNo = editTextContactNo.getText().toString().trim();



        DatabaseReference currentUserIdDB = FirebaseDatabase.getInstance().getReference().child("Donors").child(userID);

        Map newPost = new HashMap();

        if(!contactNo.isEmpty()){
            newPost.put("contactNo",contactNo);
        }

        if(!bloodGroup.isEmpty()){
            newPost.put("bloodGroup",bloodGroup);
        }


        if (!name.isEmpty()){
            newPost.put("donorName",name);
        }

        currentUserIdDB.updateChildren(newPost);


        Intent savedReturnProfile = new Intent(EditProfile.this,Profile.class);
        startActivity(savedReturnProfile);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        bloodGroup = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
