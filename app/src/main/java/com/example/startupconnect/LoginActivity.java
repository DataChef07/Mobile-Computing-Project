package com.example.startupconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button login;
    private boolean loginclickflag = false;
    private TextView forgotPassword;
    private FirebaseAuth auth;

    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    public static final String EXTRA_USERNAME="com.example.startupconnect.example.EXTRA_USERNAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.changePassword);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        forgotPassword = findViewById(R.id.forgotPassword);
        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginclickflag = true;
                final String Email = email.getText().toString();
                final String Password = password.getText().toString();

                if(dataCheck(Email, Password)){
                    auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(auth.getCurrentUser().isEmailVerified()){
                                    String userName = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).toString();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                                    intent.putExtra(EXTRA_USERNAME,userName);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, "Please Verify Your Email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Forgotpassword.class);
                startActivity(intent);
                finish();
            }
        });

        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null && user.isEmailVerified()) {
                    String userName = auth.getCurrentUser().toString();
                    Intent intent = new Intent( LoginActivity.this, MainActivity2.class);//later we create
                    intent.putExtra(EXTRA_USERNAME,userName);
                    startActivity(intent);
                    finish();
                }
            }
        };

    }

    private boolean dataCheck(String email, String password) {
        if (email.equals("") ||password.equals("")) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(firebaseAuthStateListener);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        auth.signOut();
//    }
}