package com.example.superlist.superlist.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.superlist.R;
import com.example.superlist.superlist.Dialogs.WarningDialog;
import com.example.superlist.superlist.Finals.Keys;
import com.example.superlist.superlist.Firebase.DataManager;
import com.example.superlist.superlist.Objects.List;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CreateListActivity extends AppCompatActivity {

    private FloatingActionButton createList_FAB_profile_pic;
    private ShapeableImageView createList_IMG_user;
    private TextInputLayout form_EDT_name;
    private MaterialButton panel_BTN_create;

    //DB
    private final DataManager dataManager = DataManager.getInstance();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();

    //Default
    private String myDownloadUri = "https://firebasestorage.googleapis.com/v0/b/superlist-ad7f9.appspot.com/o/default_pictures%2Fic_default_list.jpg?alt=media&token=515aea93-d3de-468c-89b6-4d3e418a0a4f";

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
            public void onClick(View view) {
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

                if(form_EDT_name.getEditText().getText().toString().isEmpty()){
                    WarningDialog warningDialog = new WarningDialog();
                    warningDialog.show(CreateListActivity.this,"Enter Name List Please");
                } else {

                    //store list in real time DB
                    List tempList = new List(form_EDT_name.getEditText().getText().toString()); //List(String title,String creatorUid)
                    tempList.setTitle(form_EDT_name.getEditText().getText().toString());

                    DatabaseReference listRef = realtimeDB.getReference(Keys.KEY_LISTS).child(tempList.getSerialNumber());

                    listRef.child(Keys.KEY_LIST_IMAGE).setValue(myDownloadUri);
                    listRef.child(Keys.KEY_LIST_TITLE).setValue(tempList.getTitle());
                    listRef.child(Keys.KEY_LIST_SERIAL).setValue(tempList.getSerialNumber());
                    ArrayList<String> tempSharedUsersUID = new ArrayList<>();///
                    tempSharedUsersUID.add(FirebaseAuth.getInstance().getCurrentUser().getUid());///
                    listRef.child("sharedUsers").setValue(tempSharedUsersUID); ///

                    listRef.child("itemsCounter").setValue(0);

                    //add list serial number to current user

                    DatabaseReference tempRef = realtimeDB.getReference(Keys.KEY_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Keys.KEY_USER_MESSAGE);
                    tempRef.child(tempList.getSerialNumber()).setValue(Keys.KEY_NO_MESSAGE);

                    DatabaseReference userRef = realtimeDB.getReference(Keys.KEY_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Keys.KEY_LISTS);
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<String> myLists = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                myLists.add(dataSnapshot.getValue(String.class)); //save previous list
                            }
                            myLists.add(tempList.getSerialNumber());
                            userRef.setValue(myLists);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    startActivity(new Intent(CreateListActivity.this, MainActivity.class));
                    finish();
                }
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //View Indicates the process of the image uploading by Disabling the button
        panel_BTN_create.setEnabled(false);

        //Reference to the exact path where we want the image to be store in Storage
        StorageReference userRef = dataManager.getStorage()
                .getReference()
                .child(Keys.KEY_LISTS_PICTURES)
                .child(dataManager.getFirebaseAuth().getCurrentUser().getUid());

        //Get URI Data and place it in ImageView
        Uri uri = data.getData();

        createList_IMG_user.setImageURI(uri);

        //Get the data from an ImageView as bytes
        createList_IMG_user.setDrawingCacheEnabled(true);
        createList_IMG_user.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) createList_IMG_user.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();

        //Start The upload task
        UploadTask uploadTask = userRef.putBytes(bytes);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    //If upload was successful, We want to get the image url from the storage
                    userRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //Set the profile URL to the object we created
                            myDownloadUri = uri.toString();
                            //View Indicates the process of the image uploading Done by making the button Enabled
                            panel_BTN_create.setEnabled(true);

                        }
                    });
                }
            }
        });

    }




}