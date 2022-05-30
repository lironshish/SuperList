package com.example.superlist.java.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.superlist.R;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {
    private ImageView sign_up_IMG_background;
    private ImageView sign_up_IMG_list;
    private ImageView splash_IMG_logo;
    private TextView sign_up_TXT_title;
    private EditText sign_up_LBL_phone_number;
    private MaterialButton sign_up_BTN_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        initButtons();
    }

    public void findViews(){
        sign_up_IMG_background = findViewById(R.id.sign_up_IMG_background);
        sign_up_IMG_list = findViewById(R.id.sign_up_IMG_list);
        splash_IMG_logo = findViewById(R.id.splash_IMG_logo);
        sign_up_TXT_title = findViewById(R.id.sign_up_TXT_title);
   //     sign_up_LBL_phone_number = findViewById(R.id.sign_up_LBL_phone_number);
        sign_up_BTN_Login = findViewById(R.id.sign_up_BTN_Login);
    }
    public void initButtons() {
        sign_up_BTN_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                replaceActivity("buttons");
            }
        });
    }
}