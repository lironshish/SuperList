package com.example.superlist.superlist.Objects;

import java.util.ArrayList;
import java.util.UUID;

public class List {

    private String serialNumber;
    private String title = "";
    private int items_Counter = 0;
    private String image_cover = "https://firebasestorage.googleapis.com/v0/b/superlist-ad7f9.appspot.com/o/default_pictures%2Fic_default_list.jpg?alt=media&token=515aea93-d3de-468c-89b6-4d3e418a0a4f";
    private String creatorUid = "";
    private ArrayList<Item> items;
    private ArrayList<String>  UIDsSharedWith;



    public List() {
    }

    public List(String title) {
        this.serialNumber =  UUID.randomUUID().toString();
        this.title = title;
        this.items_Counter = 0;
        this.image_cover = "https://firebasestorage.googleapis.com/v0/b/superlist-ad7f9.appspot.com/o/default_pictures%2Fic_default_list.jpg?alt=media&token=515aea93-d3de-468c-89b6-4d3e418a0a4f";
        this.items = new ArrayList<>();
        this.UIDsSharedWith = new ArrayList<>();
    }

    public List(String title, String serialNumber) {
        this.title = title;
        this.serialNumber = serialNumber;
        this.items_Counter = 0;
        this.image_cover = "https://firebasestorage.googleapis.com/v0/b/superlist-ad7f9.appspot.com/o/default_pictures%2Fic_default_list.jpg?alt=media&token=515aea93-d3de-468c-89b6-4d3e418a0a4f";
        this.items = new ArrayList<>();
        this.UIDsSharedWith = new ArrayList<>();
    }




    public String getImage() {
        return image_cover;
    }

    public void setImage(String image) {
        this.image_cover = image;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public String getCreatorUid() {
        return creatorUid;
    }

    public void setCreatorUid(String creatorUid) {
        this.creatorUid = creatorUid;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "List{" +
                "UID='" + serialNumber + '\'' +
                ", title='" + title + '\'' +
                ", items_Counter=" + items_Counter +
                ", image_cover='" + image_cover + '\'' +
                ", creatorUid='" + creatorUid + '\'' +
                ", items=" + items +
                '}';
    }
}
