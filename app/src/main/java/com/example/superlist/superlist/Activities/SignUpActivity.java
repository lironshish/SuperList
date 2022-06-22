package com.example.superlist.superlist.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.superlist.R;
import com.example.superlist.superlist.Firebase.DataManager;
import com.example.superlist.superlist.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {

    private FloatingActionButton signup_FAB_profile_pic;
    private CircleImageView signup_IMG_user;
    private TextInputLayout form_EDT_name;
    private MaterialButton panel_BTN_update;

    private final DataManager dataManager = DataManager.getInstance();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();
    private User tempMyUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();
        initButtons();
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dataManager.getFirebaseAuth().signOut();
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

                String userName = form_EDT_name.getEditText().getText().toString();
                String userID = dataManager.getFirebaseAuth().getCurrentUser().getUid();
                String userPhone = dataManager.getFirebaseAuth().getCurrentUser().getPhoneNumber();
                tempMyUser = new User(userID, userName, userPhone);
                dataManager.setCurrentUser(tempMyUser);
                storeUserInDB(tempMyUser);
            }

        });
    }

    private void storeUserInDB(User userToStore) {
        //Store the user UID by Phone number
        DatabaseReference myRef = realtimeDB.getReference("users").child(userToStore.getUid());
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    myRef.setValue(userToStore);
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }



}