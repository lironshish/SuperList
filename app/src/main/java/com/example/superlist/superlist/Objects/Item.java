package com.example.superlist.superlist.Objects;

public class Item {

    private String name;
    private float amount = 0;
    private String listUid;
    private String type = " KILO"; //default

    public  Item() {

    }

    public Item(String name, float amount, String listUid, String type) {
        this.name = name;
        this.amount = amount;
        this.listUid = listUid;
        this.type = type;
    }

    public String getSuffix() {
        return type;
    }

    public void setSuffix(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public String getAmountStr() {
        return (amount + "");
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getListUid() {
        return listUid;
    }

    public void setListUid(String listUid) {
        this.listUid = listUid;
    }


}
