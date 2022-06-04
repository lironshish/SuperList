package com.example.superlist.java.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.superlist.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {

    private FloatingActionButton signup_FAB_profile_pic;
    private CircleImageView signup_IMG_user;
    private TextInputLayout form_EDT_name;
    private MaterialButton panel_BTN_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();
        initButtons();
    }

    private void findViews(){
        signup_FAB_profile_pic = findViewById(R.id.signup_FAB_profile_pic);
        signup_IMG_user = findViewById(R.id.signup_IMG_user);
        form_EDT_name = findViewById(R.id.form_EDT_name);
        panel_BTN_update = findViewById(R.id.panel_BTN_update);

    }


    private void initButtons(){
        signup_FAB_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        panel_BTN_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));

            }
        });
    }



}