package com.example.superlist.superlist.Objects;

public class List {

    private String UID;
    private String title = "";
    private int items_Counter = 0;
    private String image = "";

    public List() { }

    public List(String UID, String title, int items_Counter, String image) {
        this.UID = UID;
        this.title = title;
        this.items_Counter = items_Counter;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
