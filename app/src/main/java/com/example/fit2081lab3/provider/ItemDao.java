package com.example.fit2081lab3.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("select * from itemTable")
    LiveData<List<Item>> getAllItem();

    @Query("select * from itemTable where itemName=:name")
    List<Item> getSearchItem(String name);

    @Insert
    void addItem(Item item);

    @Query("delete from itemTable where itemName= :name")
    void deleteItem(String name);

    @Query("delete FROM itemTable")
    void deleteAllItems();
}
