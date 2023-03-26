package com.example.startupconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.Objects;

public class Forgotpassword extends AppCompatActivity {

    private Button changePass;
    private int flag;
    private EditText email;
    private final String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        changePass = findViewById(R.id.changePassword);
        email = findViewById(R.id.email);
        flag=0;
        auth = FirebaseAuth.getInstance();

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();

                if (Email.equals("")) {
                    Toast.makeText(Forgotpassword.this, "Please enter registered Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Email.matches(emailPattern)) {
                    Toast.makeText(Forgotpassword.this, "Please Enter valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.fetchSignInMethodsForEmail(Email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        flag = 1;
                        auth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Forgotpassword.this, "Instructions send to registered email", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(Forgotpassword.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                if(flag==0){
                    Toast.makeText(Forgotpassword.this, "Email not registered", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}