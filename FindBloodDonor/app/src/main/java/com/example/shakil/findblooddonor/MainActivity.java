package com.example.shakil.findblooddonor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnClickHere;
    private Button btnSignIn;

    private EditText editTextEmail;
    private EditText editTextPassword;

    static String userID;
    static String mEmail;
    static String mPassword;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        btnClickHere = findViewById(R.id.btn_click_here);
        btnSignIn = findViewById(R.id.btn_login);

        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_password);



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmail = editTextEmail.getText().toString();
                mPassword = editTextPassword.getText().toString();
                if (mEmail.isEmpty()||mPassword.isEmpty()){
                    Toast.makeText(MainActivity.this,"You can't keep email or password field empty.",Toast.LENGTH_LONG).show();

                }
                else {

                    confirmSignIn(mEmail.trim(),mPassword);

                }

            }
        });
        btnClickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    private void confirmSignIn(final String email, final String password) {


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Sign in Success", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            userID = user.getUid().toString();
                            Log.d("userId",userID);
                            home();
                        } else {

                            Toast.makeText(MainActivity.this, "email and password do not match",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void home() {
        Intent home = new Intent(this,Home.class);
        startActivity(home);
    }

    private void signUp() {
        Intent userInformaton = new Intent(this, SignUp.class);
        startActivity(userInformaton);
    }
}
