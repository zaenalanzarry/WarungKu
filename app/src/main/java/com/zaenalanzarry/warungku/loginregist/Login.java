package com.zaenalanzarry.warungku.loginregist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zaenalanzarry.warungku.R;
import com.zaenalanzarry.warungku.menu.MenuNavigation;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView register, forgotpass;
    private EditText etemail, etpass;
    private Button btnlogin;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.toregister);
        register.setOnClickListener(this);

        btnlogin = findViewById(R.id.btnLogin);
        btnlogin.setOnClickListener(this);

        etemail = findViewById(R.id.etEmail);
        etpass = findViewById(R.id.etPassword);

        progressBar = findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        forgotpass = findViewById(R.id.toforgotpassword);
        forgotpass.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toregister:
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.btnLogin:
                userLogin();
                break;

            case R.id.toforgotpassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;

        }
    }

    private void userLogin() {
        String email = etemail.getText().toString().trim();
        String pass = etpass.getText().toString().trim();

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

        if(pass.isEmpty()){
            etpass.setError("Password Harus di Isi!");
            etpass.requestFocus();
            return;
        }

        if(pass.length() < 6){
            etpass.setError("Min Password Lebih dari 6 Karakter!");
            etpass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            if(user.isEmailVerified()){
                                startActivity(new Intent(Login.this, MenuNavigation.class));
                            } else {
                                user.sendEmailVerification();
                                Toast.makeText(Login.this, "Cek Email untuk Verifikasi!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(Login.this, "Gagal Login! Mohon Cek Kembali!", Toast.LENGTH_LONG).show();
                        }

                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

    }
}