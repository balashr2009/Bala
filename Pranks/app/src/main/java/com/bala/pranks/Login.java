package com.bala.pranks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    private EditText mEmail, mPassword;
    private Button mLogin;
    private FirebaseAuth firebaseAuth;
    private TextView mRegisteruser;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private DatabaseReference databaseReference;
    private String Name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = findViewById(R.id.etEmail);
        mPassword = findViewById(R.id.etPassword);
        mLogin = findViewById(R.id.btn_login);
        firebaseAuth = FirebaseAuth.getInstance();
        mRegisteruser = findViewById(R.id.tvNewuserRegister);
        databaseReference= FirebaseDatabase.getInstance().getReference("USERS").child(firebaseAuth.getCurrentUser().getUid());



        mRegisteruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, RegisterActivity.class));
                finish();
            }
        });
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkemailpassword();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Name=snapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Checkemailpassword() {
        if (mEmail.getText().toString().matches(emailPattern)) {
            if (mPassword.length() >= 8) {
                firebaseAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(getApplication(), "Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Login.this, NavDrawerActivity.class);
                            i.putExtra("username",Name);
                            startActivity(i);

                        } else {
                            String error = task.getException().getMessage().toString();
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                mPassword.setError("password must be at least 8 char");
            }
        } else {
            mEmail.setError("enter valid email");
        }
    }

    private void checkinput() {
        if (!TextUtils.isEmpty(mEmail.getText().toString())) {
            if (!TextUtils.isEmpty(mPassword.getText().toString())) {
                mLogin.setEnabled(true);

            } else {
                mLogin.setEnabled(false);
            }
        } else {
            mLogin.setEnabled(false);
        }
    }

}