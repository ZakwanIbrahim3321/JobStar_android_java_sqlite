package com.example.jobstar.ui.signUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.jobstar.SqlLite.DBHelper;
import com.example.jobstar.databinding.ActivityLoginBinding;
import com.example.jobstar.databinding.ActivitySignUpBinding;
import com.example.jobstar.ui.login.LoginActivity;
import com.example.jobstar.ui.main.MainActivity;
import com.example.jobstar.ui.splash.splashActivity;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        DB = new DBHelper(this);

        binding.btnLoginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = binding.userNameSignUp.getText().toString();
                String email = binding.userEmailSignUp.getText().toString();
                String phone = binding.userPhoneSignUp.getText().toString();
                String password = binding.passwordSignUp.getText().toString();
                String rePassword = binding.confirmPasswordSignUp.getText().toString();

                if (validateSignUpInfo(name,email,phone,password,rePassword)){

                    if (password.equals(rePassword)){
                        Boolean checkUser = DB.checkUserName(name);
                        if(!checkUser){
                            Boolean insert = DB.insertDate(name,password,email,phone);
                            if (insert){
                                Toast.makeText(SignUpActivity.this, "You are registered", Toast.LENGTH_SHORT).show();
                                openLoginActivity();
                            }else {
                                Toast.makeText(SignUpActivity.this, "You are not registered", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(SignUpActivity.this, "User Already exists", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SignUpActivity.this, "Password Do Not Match", Toast.LENGTH_SHORT).show();
                    }


                }else {
                    Toast.makeText(SignUpActivity.this, "Please Fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Boolean validateSignUpInfo(String name,String email,String phone,String pass, String RePass){
        return !name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !pass.isEmpty() && !RePass.isEmpty();
    }



    private void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}