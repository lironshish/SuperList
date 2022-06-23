package com.example.superlist.superlist.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.superlist.R;
import com.example.superlist.superlist.Finals.Keys;
import com.example.superlist.superlist.Firebase.DataManager;
import com.example.superlist.superlist.Objects.List;
import com.example.superlist.superlist.Objects.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;


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
                List tempList = new List();
                tempList.setTitle(form_EDT_name.getEditText().getText().toString());
                DatabaseReference myRef = realtimeDB.getReference(Keys.KEY_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lists");
                myRef.child(tempList.getTitle()).child("image").setValue(tempList.getImage());
                myRef.child(tempList.getTitle()).child("ohad").setValue(4);

                startActivity(new Intent(CreateListActivity.this, MainActivity.class));
                finish();
            }
        });
    }


    /**
     * Function will store the given List to Firestore.
     *
     * @param listToStore is the list you wish to store in the Firebase Firestore
     */
    private void storeListInDB(List listToStore) {

        DatabaseReference myRef = realtimeDB.getReference(Keys.KEY_LISTS).child(listToStore.getUID());
        myRef.child("title").setValue(listToStore.getTitle());
        myRef.child("image_cover").setValue(listToStore.getImage());
        startActivity(new Intent(CreateListActivity.this, MainActivity.class));
        finish();
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