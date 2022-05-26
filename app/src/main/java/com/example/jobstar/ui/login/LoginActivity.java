package com.example.jobstar.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.jobstar.SqlLite.DBHelper;
import com.example.jobstar.databinding.ActivityLoginBinding;
import com.example.jobstar.ui.main.MainActivity;
import com.example.jobstar.ui.signUp.SignUpActivity;
import com.example.jobstar.ui.splash.splashActivity;
import com.example.jobstar.ui.userHomeActivity.UserHomeScreen;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        DB = new DBHelper(this);

        binding.btnSignUpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpActivity();
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.userNameLogin.getText().toString();
                String password = binding.passwordLogin.getText().toString();

                if (validateLoginEntry(name,password)){
                    Boolean checkCredentials = DB.checkCredentials(name,password);
                    if (checkCredentials){
                        Toast.makeText(LoginActivity.this, "You are login", Toast.LENGTH_SHORT).show();
                        splashActivity.userName = name;
                        if (name.equals("admin")){
                            splashActivity.role = "admin";
                        }else {
                            splashActivity.role = "user";
                        }
                        splashActivity.userName = name;
                        openMainActivity();
                    }else {
                        Toast.makeText(LoginActivity.this, "Incorrect Name or Password", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void openSignUpActivity(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public Boolean validateLoginEntry(String name, String pass){
        if (name.isEmpty() && pass.isEmpty()){
            Toast.makeText(this, "Please fill the fields", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

}