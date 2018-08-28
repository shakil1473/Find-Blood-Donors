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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth mAuth;
    private Spinner spinnerBloodGroup;
    private Button btnSignUp;
    private Button btnSignIn;
    private EditText editTextName;
    private EditText editTextContact;
    private EditText editTextEmail;
    private EditText editTextPassword;

    String userEmail;
    String userPassword;
    String userID;
    String name;
    String contactNo;
    String bloodGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        spinnerBloodGroup = findViewById(R.id.spinner_blood_group);
        btnSignUp = findViewById(R.id.btn_signup_done);
        btnSignIn = findViewById(R.id.btn_signIn);
        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_password);
        editTextName = findViewById(R.id.editText_name);
        editTextContact = findViewById(R.id.editText_mobile);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.blood_group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerBloodGroup.setAdapter(adapter);
        spinnerBloodGroup.setOnItemSelectedListener(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail = editTextEmail.getText().toString();
                userPassword = editTextPassword.getText().toString();
                name = editTextName.getText().toString();
                contactNo = editTextContact.getText().toString();

                if(userEmail.length()==0||userPassword.length()==0||name.length()==0||contactNo.length()==0){
                    Toast.makeText(SignUp.this,"Enter all the information correctly",Toast.LENGTH_LONG).show();
                }
                else {

                    authenticateSignUp(userEmail,userPassword);
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn = new Intent(SignUp.this,MainActivity.class);
                startActivity(signIn);
            }
        });
    }

    private void authenticateSignUp(String email,String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            userID = mAuth.getCurrentUser().getUid();
                            userInformations(userID);
                        } else {
                            Log.w("error", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void userInformations(String userID) {

       name = editTextName.getText().toString();
       contactNo = editTextContact.getText().toString();

        DatabaseReference currentUserIdDB = FirebaseDatabase.getInstance().getReference().child("Donors").child(userID);

        Map newPost = new HashMap();
        newPost.put("donorName",name);
        newPost.put("contactNo",contactNo);
        newPost.put("bloodGroup",bloodGroup);

        currentUserIdDB.setValue(newPost);

        Intent signIn = new Intent(this,MainActivity.class);
        startActivity(signIn);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        bloodGroup = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
