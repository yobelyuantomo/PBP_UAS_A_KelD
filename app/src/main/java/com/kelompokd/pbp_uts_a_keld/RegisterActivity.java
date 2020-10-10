package com.kelompokd.pbp_uts_a_keld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    Button btn_register;
    TextInputEditText email_input, pass_input, pass_input2, fullname_input;
    FirebaseAuth firebaseAuth;
    MaterialTextView already_member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Find The Layout ID in XML
        email_input = findViewById(R.id.input_email);
        fullname_input = findViewById(R.id.input_fullname);
        pass_input = findViewById(R.id.input_password);
        pass_input2 = findViewById(R.id.input_retype);
        btn_register = findViewById(R.id.btn_register);
        already_member = findViewById(R.id.already_member);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }

        //When SignUp Button Clicked
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = fullname_input.getText().toString();
                String email = email_input.getText().toString().trim();
                String password = pass_input.getText().toString().trim();
                String password2 = pass_input2.getText().toString().trim();

                if(TextUtils.isEmpty(fullname)){
                    Toast.makeText(getApplicationContext(), "Please Enter Full Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(email) || !email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    Toast.makeText(getApplicationContext(), "Email Invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length() < 6){
                    Toast.makeText(getApplicationContext(), "Password too Short", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(password2)){
                    Toast.makeText(getApplicationContext(), "Password Confirm Doesn't Match", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Register the user into Firebase
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                            homeIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(homeIntent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // When Already Member Clicked
        already_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }
}