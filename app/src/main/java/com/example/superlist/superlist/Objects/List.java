package com.example.superlist.superlist.Objects;

import java.util.ArrayList;
import java.util.UUID;

public class List {

    private String UID;
    private String title = "";
    private int items_Counter = 0;
    private String image_cover = "https://firebasestorage.googleapis.com/v0/b/superlist-ad7f9.appspot.com/o/default_pictures%2Fic_default_list.jpg?alt=media&token=515aea93-d3de-468c-89b6-4d3e418a0a4f";
    private String creatorUid = "";
    private ArrayList<String> itemsUid;



    public List() { }

    public List(String title,String creatorUid) {
        this.UID =  UUID.randomUUID().toString();
        this.title = title;
        this.items_Counter = items_Counter;
        this.image_cover = "https://firebasestorage.googleapis.com/v0/b/superlist-ad7f9.appspot.com/o/default_pictures%2Fic_default_list.jpg?alt=media&token=515aea93-d3de-468c-89b6-4d3e418a0a4f";
        this.creatorUid = creatorUid;
        this.itemsUid = new ArrayList<>();


    }

    public String getImage() {
        return image_cover;
    }

    public void setImage(String image) {
        this.image_cover = image;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getItems_Counter() {
        return items_Counter;
    }

    public void setItems_Counter(int items_Counter) {
        this.items_Counter = items_Counter;
    }
}
