package com.example.fit2081lab3.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "itemTable")
public class Item {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "itemID")
    private int itemID;

    @ColumnInfo(name = "itemName")
    private String itemName;

    @ColumnInfo(name = "itemQuantity")
    private String itemQuantity;

    @ColumnInfo(name = "itemCost")
    private String itemCost;

    @ColumnInfo(name = "itemDescription")
    private String itemDescription;

    @ColumnInfo(name = "itemFrozen")
    private String itemFrozen;

    public Item(String itemName, String itemQuantity, String itemCost, String itemDescription, String itemFrozen) {
        //this.itemID = itemID;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemCost = itemCost;
        this.itemDescription = itemDescription;
        this.itemFrozen = itemFrozen;
    }

    public int getItemID(){
        return itemID;
    }

    public void setItemID(@NonNull int itemID) {
        this.itemID = itemID;
    }

    public String getItemName(){
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity(){
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemCost(){
        return itemCost;
    }

    public void setItemCost(String itemCost) {
        this.itemCost = itemCost;
    }

    public String getItemDescription(){
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemFrozen(){
        return itemFrozen;
    }

    public void setItemFrozen(String itemFrozen) {
        this.itemFrozen = itemFrozen;
    }
}
