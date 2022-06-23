package com.example.superlist.superlist.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.superlist.superlist.Finals.Keys;
import com.example.superlist.superlist.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class DataManager {

    private final FirebaseAuth firebaseAuth;
    private final FirebaseStorage storage;
    private final FirebaseDatabase realTimeDB;

    private User currentUser;
    private String currentListUid;
    private String currentListCreator;
   // private MyItem currentItem;
    private String currentCategoryUid;
    private String currentListTitle;
    private String token;


    private static DataManager single_instance = null;

    private DataManager(){
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

    public  FirebaseAuth getFirebaseAuth() {
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

//    public MyItem getCurrentItem() {
//        return currentItem;
//    }
//
//    public void setCurrentItem(MyItem currentItem) {
//        this.currentItem = currentItem;
//    }

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





    //MyDataManager Methods

    /**
     * Method will load the connected user's data from database. and update his current device token for Cloud messaging
     */
//    public void loadUserFromDB() {
//        // Successfully signed in
//
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
//            @Override
//            public void onSuccess(String s) {
//                token = s;
//                DatabaseReference myRef = getRealTimeDB().getReference(Keys.KEY_UID_TO_TOKENS).child(user.getUid());
//                myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DataSnapshot> task) {
//                        if(task.isSuccessful()){
//                            myRef.setValue(token);
//                            Log.d("pttt", "token is : " + token);
//                        }
//                    }
//                });
//            }
//        });
//
//    }



//    public  void userListsChange(User user) {
//
//        // Create new post at /user-posts/$userid/$postid and at
//        // /posts/$postid simultaneously
//
//        DatabaseReference mDatabase;
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        String key = mDatabase.child(Keys.KEY_USERS).push().getKey();
//
//        user = new User(user.getUid(),user.getName(),user.getPhoneNumber());
//
//        //String uid, String name, String phoneNumber
//        Map<String, Object> postValues = user.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/users/" + key, postValues); //lists
//        childUpdates.put("/user-lists/" + user.getUid() + "/" + key, postValues); //user lists
//
//        mDatabase.updateChildren(childUpdates);
//
//    }









}
