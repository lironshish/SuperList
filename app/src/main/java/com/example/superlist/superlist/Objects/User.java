package com.example.superlist.superlist.Objects;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    String uid;
    String name;
    String phoneNumber;
    private String profileImgUrl = "https://firebasestorage.googleapis.com/v0/b/superlist-ad7f9.appspot.com/o/default_pictures%2Fic_default_profile.jpg?alt=media&token=be90a5fd-9d60-4385-8760-3246adfd7cd7"; //default pic
    private ArrayList<String> myListsUids;




    public User(){ }

    public User(String uid, String name, String phoneNumber) {
        this.uid = uid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profileImgUrl = "https://firebasestorage.googleapis.com/v0/b/superlist-ad7f9.appspot.com/o/default_pictures%2Fic_default_profile.jpg?alt=media&token=be90a5fd-9d60-4385-8760-3246adfd7cd7";
        this.myListsUids = new ArrayList<>();

    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public User setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
        return this;
    }

    public ArrayList<String> getMyListsUids() {
        return myListsUids;
    }

    public User setMyListsUids(ArrayList<String> myListsUids) {
        this.myListsUids = myListsUids;
        return this;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profileImgUrl='" + profileImgUrl + '\'' +
                '}';
    }

    public boolean addToListUid(String uidToAdd){
        return this.myListsUids.add(uidToAdd);
    }

    public boolean removeFromListsUids(String listUid) {
        return this.myListsUids.remove(listUid);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("phoneNumber", phoneNumber);
        result.put("profileImgUrl", profileImgUrl);
        result.put("myListsUids", myListsUids);
        return result;
    }
}
