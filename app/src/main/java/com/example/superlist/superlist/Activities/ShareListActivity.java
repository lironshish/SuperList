package com.example.superlist.superlist.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.superlist.R;
import com.example.superlist.superlist.Dialogs.SharingFailedDialog;
import com.example.superlist.superlist.Dialogs.SharingSuccessfullyDialog;
import com.example.superlist.superlist.Dialogs.WarningDialog;
import com.example.superlist.superlist.Finals.Keys;
import com.example.superlist.superlist.Firebase.DataManager;
import com.example.superlist.superlist.Objects.List;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class ShareListActivity extends AppCompatActivity {


    private TextView share_TXT_title;
    private TextView share_TXT_code;
    private TextInputLayout share_EDT_phone;
    private MaterialButton share_BTN_share;
    private LottieAnimationView share_LOT_animation;
    private ImageView share_IMG_israel_flag;


    //Firebase
    private final DataManager dataManager = DataManager.getInstance();
    private final FirebaseDatabase realtimeDB = dataManager.getRealTimeDB();

    private Bundle bundle;
    private String currentListName;
    private String currentListSerialNumber;
    private String wantedUserPhoneNumber;
    private String wantedUserUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_list);

        if (getIntent().getBundleExtra("Bundle") != null) {
            this.bundle = getIntent().getBundleExtra("Bundle");
            currentListName = bundle.getString("currentListName");
            currentListSerialNumber = bundle.getString("currentListSerialNumber");
        } else {
            this.bundle = new Bundle();
        }

        findViews();
        initButtons();
    }

    private void initButtons() {
        List sharedList = new List();

        share_BTN_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneTemp = share_EDT_phone.getEditText().getText().toString();
                String newPhone = phoneTemp.replace("-", "");
                wantedUserPhoneNumber = share_TXT_code.getText().toString() + newPhone;

                DatabaseReference usersRef = realtimeDB.getReference(Keys.KEY_USERS);
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean exist = false;
                        for (DataSnapshot userChild : snapshot.getChildren()) {

                            if (userChild.child(Keys.KEY_USER_PHONE_NUMBER).getValue().toString().equals(wantedUserPhoneNumber)) {
                                exist = true;
                                wantedUserUID = userChild.child(Keys.KEY_USER_UID).getValue(String.class);
                                boolean shared = false;
                                for (DataSnapshot newChild : userChild.child(Keys.KEY_LISTS).getChildren()) {

                                    if (newChild.getValue(String.class).equals(currentListSerialNumber)) {
                                        shared = true;

                                        WarningDialog warningDialog = new WarningDialog();
                                        warningDialog.show(ShareListActivity.this, "This list is already shared with this user");
                                    }
                                }
                                if (!shared) {
                                    sharedList.setSerialNumber(currentListSerialNumber);
                                    sharedList.setTitle(currentListName);
                                    shareList(wantedUserUID, sharedList);

                                    // update db with the shared users uid
                                    DatabaseReference listsRef = realtimeDB.getReference(Keys.KEY_LISTS).child(currentListSerialNumber).child("sharedUsers");
                                    listsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            ArrayList<String> sharedUsers = new ArrayList<>();
                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                sharedUsers.add(dataSnapshot.getValue(String.class)); //save previous list
                                            }
                                            sharedUsers.add(wantedUserUID);
                                            listsRef.setValue(sharedUsers);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    SharingSuccessfullyDialog shareDialog = new SharingSuccessfullyDialog();
                                    shareDialog.show(ShareListActivity.this);
                                }

                            }
                        }
                        if (!exist) {
                            SharingFailedDialog shareFailDialog = new SharingFailedDialog();
                            shareFailDialog.show(ShareListActivity.this, "This phone does not exist in the system");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }

    private void findViews() {

        share_TXT_title = findViewById(R.id.share_TXT_title);
        share_EDT_phone = findViewById(R.id.share_EDT_phone);
        share_BTN_share = findViewById(R.id.share_BTN_share);
        share_LOT_animation = findViewById(R.id.share_LOT_animation);
        share_IMG_israel_flag = findViewById(R.id.share_IMG_israel_flag);
        share_TXT_code = findViewById(R.id.share_TXT_code);
    }


    private void shareList(String userSharedUID, List list) {
        DatabaseReference tempRef = realtimeDB.getReference(Keys.KEY_USERS).child(userSharedUID).child(Keys.KEY_USER_MESSAGE);
        tempRef.child(currentListSerialNumber).setValue(Keys.KEY_NO_MESSAGE);


        DatabaseReference listRef = realtimeDB.getReference(Keys.KEY_USERS).child(userSharedUID).child(Keys.KEY_LISTS);
        listRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> myLists = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    myLists.add(dataSnapshot.getValue(String.class)); //save previous list
                }
                myLists.add(list.getSerialNumber());
                listRef.setValue(myLists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}