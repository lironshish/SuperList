package com.example.superlist.superlist.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.superlist.R;
import com.example.superlist.superlist.Finals.Keys;
import com.example.superlist.superlist.Firebase.DataManager;
import com.example.superlist.superlist.Objects.List;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateListActivity extends AppCompatActivity {

    private FloatingActionButton createList_FAB_profile_pic;
    private ShapeableImageView createList_IMG_user;
    private TextInputLayout form_EDT_name;
    private MaterialButton panel_BTN_create;

    private final DataManager dataManager = DataManager.getInstance();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        findViews();
        initButtons();
    }

    private void findViews(){
        createList_FAB_profile_pic = findViewById(R.id.createCat_FAB_profile_pic);
        createList_IMG_user = findViewById(R.id.create_list_IMG_user);
        form_EDT_name = findViewById(R.id.form_EDT_name);
        panel_BTN_create = findViewById(R.id.panel_BTN_create);
    }

    private void initButtons() {
        createList_FAB_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseCover();
            }
        });

        createList_IMG_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choseCover();
            }
        });

        panel_BTN_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List tempList = new List(); //List(String title,String creatorUid)
                tempList.setTitle(form_EDT_name.getEditText().getText().toString());
                DatabaseReference myRef = realtimeDB.getReference(Keys.KEY_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lists");
                myRef.child(tempList.getTitle()).child("image").setValue(tempList.getImage());
                myRef.child(tempList.getTitle()).child("title").setValue(tempList.getTitle());
                myRef.child(tempList.getTitle()).child("itemsCounter").setValue(0);
                startActivity(new Intent(CreateListActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    /**
     * Load ImagePicker activity to choose the list cover
     */
    private void choseCover() {
        ImagePicker.with(CreateListActivity.this)
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .crop(1f, 1f)
                .maxResultSize(1080, 1080)
                //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

}