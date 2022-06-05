package com.example.superlist.java.Firebase;

import com.example.superlist.java.Objects.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class DataManager {
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore dbFireStore;
    private final FirebaseStorage storage;
  //  private final FirebaseDatabase realTimeDB;

    private User currentUser;

    private static DataManager single_instance = null;

    private DataManager(){
        firebaseAuth = FirebaseAuth.getInstance();
        dbFireStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
     // realTimeDB = FirebaseDatabase.getInstance("https://superme-e69d5-default-rtdb.europe-west1.firebasedatabase.app/");
    }

    //Setters and Getters
    public static DataManager getInstance() {
        return single_instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public DataManager setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        return this;
    }
}
