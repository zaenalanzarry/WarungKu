package com.zaenalanzarry.warungku.loginregist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.zaenalanzarry.warungku.R;

import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {

    private EditText etemail;
    private Button btnresetPass;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etemail = findViewById(R.id.etEmail);
        btnresetPass = findViewById(R.id.btnresetpass);
        progressBar = findViewById(R.id.progressbar);

        auth = FirebaseAuth.getInstance();

        btnresetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpass();
            }
        });
    }

    private void resetpass() {
        String email = etemail.getText().toString().trim();

        if(email.isEmpty()){
            etemail.setError("Email Harus di Isi!");
            etemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etemail.setError("Dimohon Gunakan Email yang Valid!");
            etemail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Cek Email untuk Reset Password!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ForgotPassword.this, "Coba Lagi! Ada Suatu Masalah!", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}