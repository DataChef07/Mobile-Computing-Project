package com.example.startupconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private Button submit;
    private EditText email, password, name, phno, roll;
    private TextView exists, termsAndConditions;
    private RadioGroup confirm;
    private FirebaseAuth auth;

    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private final String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    //references ====> https://mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/#:~:text=Email%20Regex%20%E2%80%93%20Simple%20Validation.&text=%2B)%40(%5CS%2B)%24,then%20a%20non%20whitespace%20character.&text=1.1%20A%20Java%20example%20using%20the%20above%20regex%20for%20email%20validation.&text=1.2%20Below%20is%20a%20JUnit,some%20valid%20and%20invalid%20emails.

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        exists = findViewById(R.id.existingUser);
        termsAndConditions = findViewById(R.id.termsAndConditions);
        submit = findViewById(R.id.changePassword);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        name = findViewById(R.id.name);
        phno = findViewById(R.id.phno);
        roll = findViewById(R.id.roll);

        final CheckBox checkBox = findViewById(R.id.checkbox);
        checkBox.setText("");
        termsAndConditions.setText(Html.fromHtml("I have read and agree to the " + "<a href = 'https://privacypolicysportify.blogspot.com/2023/03/privacy-policy.html'> Terms and Conditions </a>"));
//        termsAndConditions.setText(Html.fromHtml("I have read and agree to the " + "<a href='https://privacypolicysportify.blogspot.com/2023/03/privacy-policy.html'><span style='color:black'>Terms and Conditions</span></a>"));

        termsAndConditions.setClickable(true);
        termsAndConditions.setMovementMethod(LinkMovementMethod.getInstance());
//        termsAndConditions.setTextColor(R.color.black);

        auth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null && user.isEmailVerified()) {
                    Intent intent = new Intent( RegisterActivity.this, MainActivity2.class);//later we create
                    startActivity(intent);
                    finish();
                }
            }
        };

        exists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Email = email.getText().toString();
                final String Password = password.getText().toString();
                final String Name = name.getText().toString();
                final String Phno = phno.getText().toString();
                final String rollNo = roll.getText().toString();
                boolean Checkbox = checkBox.isChecked();
                boolean intrested = false;
                boolean matched = false;
                boolean signedin = false;
                int rating = 0;
                int equipment =0;
                String sport ="";
                String timer ="";


                if(dataCheck(Email, Password, Name, Phno, Checkbox, rollNo)){
                    auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else{
                                auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this, "Registered Successfully. Please Check your Email for Verification", Toast.LENGTH_SHORT).show();
                                            String id = auth.getCurrentUser().getUid();
                                            DatabaseReference curruser = FirebaseDatabase.getInstance().getReference().child("Users").child(id);

                                            Map userDetails = new HashMap<>();
                                            userDetails.put("name", Name);
                                            userDetails.put("Phno", Phno);
                                            userDetails.put("profileImageUrl", "default");
                                            userDetails.put("intrested", intrested);
                                            userDetails.put("matched", matched);
                                            userDetails.put("signedin", signedin);
                                            userDetails.put("rating", rating);
                                            userDetails.put("equipment", equipment);
                                            userDetails.put("sport", sport);
                                            userDetails.put("timer", timer);
                                            userDetails.put("roll", rollNo);

                                            curruser.updateChildren(userDetails);
                                            email.setText("");
                                            name.setText("");
                                            password.setText("");
                                            Intent intent = new Intent( RegisterActivity.this, Loginandreg.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }

            }
        });

    }

    private boolean dataCheck(String email, String password, String name, String phno, boolean checkbox, String roll) {

        if (email.equals("") || name.equals("") || password.equals("") || phno.equals("") || roll.equals("")) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!email.matches (emailPattern)) {
            Toast.makeText(this, "Please Enter valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkbox) {
            Toast.makeText(this, "Accept terms and conditions", Toast.LENGTH_SHORT).show();
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
}