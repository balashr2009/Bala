package com.bala.pranks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private TextView alreadyhaveAccount;
    private ImageView imglogo;
    private FirebaseAuth mAuth;
    private EditText email, password, confirmpassword, fullname;
    private Button signup;
    private FirebaseFirestore firebaseFireStore;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        alreadyhaveAccount = findViewById(R.id.txtAlreadyReg);
        imglogo = findViewById(R.id.imgregpagelogo);
        email = findViewById(R.id.etregEmail);
        fullname = findViewById(R.id.etregName);
        password = findViewById(R.id.etregPass);
        confirmpassword = findViewById(R.id.etRegConfirmPass);
        signup = findViewById(R.id.btnReg);
        mAuth = FirebaseAuth.getInstance();
        firebaseFireStore = FirebaseFirestore.getInstance();

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkemailpassword();

            }
        });
        alreadyhaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });


    }

    private void Checkemailpassword() {
        final String Email = email.getText().toString();
        String UserPassword = password.getText().toString();
        final String Name = fullname.getText().toString();
        if (Email.matches(emailPattern)) {
            if (UserPassword.equals(confirmpassword.getText().toString())) {
                mAuth.createUserWithEmailAndPassword(Email, UserPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("name", Name);
                                    map.put("email", Email);
                                    FirebaseDatabase.getInstance().getReference().child("USERS").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(map)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegisterActivity.this, Login.class));
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                                } else {
                                    String Error = task.getException().getMessage();
                                    Toast.makeText(getApplicationContext(), Error, Toast.LENGTH_LONG).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                confirmpassword.setError("confirm password not matched");
            }
        } else {
            email.setError("Email Not Valid.");
        }


    }

    private void checkinputs() {
        if (!TextUtils.isEmpty(email.getText())) {
            if (!TextUtils.isEmpty(fullname.getText())) {
                if (!TextUtils.isEmpty(password.getText()) && password.length() >= 8) {
                    if (!TextUtils.isEmpty(confirmpassword.getText())) {
                        signup.setEnabled(true);
                        signup.setTextColor(Color.rgb(255, 255, 255));
                    } else {
                        signup.setEnabled(false);
                        signup.setTextColor(Color.argb(50, 255, 255, 255));
                    }
                } else {
                    signup.setEnabled(false);
                    signup.setTextColor(Color.argb(50, 255, 255, 255));
                }
            } else {
                signup.setEnabled(false);
                signup.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            signup.setEnabled(false);
            signup.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }
}

