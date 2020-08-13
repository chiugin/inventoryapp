package com.example.fit2081lab3;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fit2081lab3.provider.Item;
import com.example.fit2081lab3.provider.ItemViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListItemActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyRecyclerViewAdapter adapter;

    //ArrayList<Item> data;
    ItemViewModel mItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        recyclerView =  findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager

        //Getting arraylist using intent
        //data = (ArrayList<Item>) getIntent().getSerializableExtra("KEY_LIST");

        /*
        //Getting arraylist using Gson and SharedPreferences
        SharedPreferences sP = getSharedPreferences("db1",0);
        String dbStr = sP.getString("KEY_LIST","");
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        Gson gson = new Gson();
        data = gson.fromJson(dbStr,type);
         */

        adapter = new MyRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        mItemViewModel=new ViewModelProvider(this).get(ItemViewModel.class);
        mItemViewModel.getAllItems().observe(this, newData -> {
            adapter.setData(newData);
            adapter.notifyDataSetChanged();
        });
    }


}
