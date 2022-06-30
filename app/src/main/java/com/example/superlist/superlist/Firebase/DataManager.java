package com.example.superlist.superlist.Firebase;

import androidx.annotation.NonNull;

import com.example.superlist.superlist.Finals.Keys;
import com.example.superlist.superlist.Objects.User;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class DataManager {

    private final FirebaseAuth firebaseAuth;
    private final FirebaseStorage storage;
    private final FirebaseDatabase realTimeDB;

    private User currentUser;
    private String currentListUid;
    private String currentListCreator;
    private String currentCategoryUid;
    private String currentListTitle;

    private static DataManager single_instance = null;

    private DataManager() {
        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        realTimeDB = FirebaseDatabase.getInstance();
    }

    public static DataManager getInstance() {
        return single_instance;
    }

    public static DataManager initHelper() {
        if (single_instance == null) {
            single_instance = new DataManager();
        }
        return single_instance;
    }

    //Firebase Getters
    public FirebaseDatabase getRealTimeDB() {
        return realTimeDB;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }


    //My Data Base Helpers
    public User getCurrentUser() {
        return currentUser;
    }

    public DataManager setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        return this;
    }

    public String getCurrentListUid() {
        return currentListUid;
    }

    public void setCurrentListUid(String currentListUid) {
        this.currentListUid = currentListUid;
    }

    public String getCurrentListTitle() {
        return currentListTitle;
    }

    public void setCurrentListTitle(String currentListTitle) {
        this.currentListTitle = currentListTitle;
    }

    public String getCurrentCategoryUid() {
        return currentCategoryUid;
    }

    public void setCurrentCategoryUid(String currentCategory) {
        this.currentCategoryUid = currentCategory;
    }

    public void setCurrentListCreator(String creatorUid) {
        this.currentListCreator = creatorUid;
    }

    public String getCurrentListCreator() {
        return currentListCreator;
    }

    public void loadUserFromDB() {
        //Store the user UID by Phone number
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myRef = getRealTimeDB().getReference(Keys.KEY_USERS).child(user.getUid());
        myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    setCurrentUser(user); // OR dataManager.getInstance().setCurrentUser(user);
                } else {
                    //no such document
                }
            }
        });
    }

    public void sendMessage(String message, ArrayList<String> sharedUsers, String listID) {
        DatabaseReference myRef = getRealTimeDB().getReference(Keys.KEY_USERS);
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (int i = 0; i < sharedUsers.size(); i++) {
                        if (dataSnapshot.getKey().equals(sharedUsers.get(i))) {
                            myRef.child(dataSnapshot.getKey()).child(Keys.KEY_USER_MESSAGE).child(listID).setValue(message);
                        }
                    }

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void deleteMessageFromDB(String userUID, String listID) {
        DatabaseReference myRef = getRealTimeDB().getReference(Keys.KEY_USERS).child(userUID).child(Keys.KEY_USER_MESSAGE);
        myRef.child(listID).setValue(Keys.KEY_NO_MESSAGE);
//        myRef.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                snapshot.child("message").
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

}
