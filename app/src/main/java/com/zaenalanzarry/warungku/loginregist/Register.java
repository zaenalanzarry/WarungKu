package com.zaenalanzarry.warungku.loginregist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.zaenalanzarry.warungku.R;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private TextView btnregist;
    private EditText etnamawarung, etnohp, etemail, etpass;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        back = findViewById(R.id.backtoLogin);
        back.setOnClickListener(this);

        btnregist = findViewById(R.id.btnRegister);
        btnregist.setOnClickListener(this);

        etnamawarung = findViewById(R.id.etNamaWarung);
        etnohp = findViewById(R.id.etHP);
        etemail = findViewById(R.id.etEmail);
        etpass = findViewById(R.id.etPassword);

        progressBar = findViewById(R.id.progressbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backtoLogin:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.btnRegister:
                btnregist();
                break;
        }
    }

    private void btnregist() {
        String namawarung = etnamawarung.getText().toString().trim();
        String nohp = etnohp.getText().toString().trim();
        String email = etemail.getText().toString().trim();
        String pass = etpass.getText().toString().trim();

        if(namawarung.isEmpty()){
            etnamawarung.setError("Nama Warung Harus di Isi!");
            etnamawarung.requestFocus();
            return;
        }

        if(nohp.isEmpty()){
            etnohp.setError("Nomer Handphone Harus di Isi!");
            etnohp.requestFocus();
            return;
        }

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

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(namawarung, nohp, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "User telah Berhasil Registrasi!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else {
                                        Toast.makeText(Register.this, "User Gagal Registrasi!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(Register.this, "User Gagal Registrasi!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}