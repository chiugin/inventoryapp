package com.example.fit2081lab3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.fit2081lab3.provider.Item;
import com.example.fit2081lab3.provider.ItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "WAREHOUSE";

    private EditText itemName;
    private EditText quantity;
    private EditText cost;
    private EditText description;
    private ToggleButton frozenButton;

    private String itemNameString;
    private String quantityString;
    private String costString;
    private String descriptionString;
    private Boolean frozen;

    DrawerLayout drawer;

   // ArrayList<Item> dataItems = new ArrayList<>();
   // Gson gson = new Gson();
    ItemViewModel mItemViewModel;
    MyRecyclerViewAdapter adapter;

    View activityConstraintLayout;
    int x_down;
    int y_down;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

        itemName = findViewById(R.id.ItemNameText);
        quantity = findViewById(R.id.QuantityText);
        cost = findViewById(R.id.CostText);
        description = findViewById(R.id.DescriptionText);
        frozenButton = findViewById(R.id.toggleButton);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.dl);
        ActionBarDrawerToggle toggle  = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemName.getText().toString().isEmpty()){
                    showEmptyToast();
                } else{
                    Snackbar.make(v, "Item added!", Snackbar.LENGTH_LONG).show();
                    addItemData();
                }

            }
        });

        // week 10 lab code in section below here
        activityConstraintLayout = findViewById(R.id.activityConstraintLayout);

        activityConstraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getActionMasked();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN) :
                        x_down = (int)event.getX();
                        y_down = (int)event.getY();
                        return true;
                    case (MotionEvent.ACTION_MOVE) :
                        return true;
                    case (MotionEvent.ACTION_UP) :
                        if (Math.abs(y_down-event.getY()) < 40){

                            //left to right - difference of x will be negative
                            if (x_down-event.getX()<0){
                                //add item into database
                                if (itemName.getText().toString().isEmpty()){
                                    showEmptyToast();
                                } else{
                                    addItemData();
                                    showToast();
                                }
                            }
                            //right to left - difference of x will be positive
                            else{
                                //clear the text fields of the app
                                clearText();
                            }
                        }
                        return true;
                    default :
                        return false;
                }
            }
        });
        // week 10 lab code ends here

        // week 6 - recycleradapter
        adapter = new MyRecyclerViewAdapter();
        // week 7 - database
        mItemViewModel=new ViewModelProvider(this).get(ItemViewModel.class);
        mItemViewModel.getAllItems().observe(this, newData -> {
            adapter.setData(newData);
            adapter.notifyDataSetChanged();
        });

        // week 4 code starts here!! for Broadcast Receiver
        /* Request permissions to access SMS */
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);

        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //get the id of the selected item
            int id = item.getItemId();
            if (id == R.id.addItemDrawer){
                if (itemName.getText().toString().isEmpty()){
                    showEmptyToast();
                } else{
                    addItemData();
                    showToast();
                }
            } else if (id == R.id.clearFieldDrawer){
                clearText();
            } else if (id == R.id.listItemDrawer) {
                //passing Arraylist using intent
                /*
                Intent i = new Intent(MainActivity.this, ListItemActivity.class);
                i.putExtra("KEY_LIST",dataItems);
                startActivity(i);
                */

                /*
                //passing ArrayList using Gson and SharedPreferences
                String dbStr = gson.toJson(dataItems);
                SharedPreferences sP = getSharedPreferences("db1",0);
                SharedPreferences.Editor edit = sP.edit();
                edit.putString("KEY_LIST", dbStr);
                edit.apply();
                 */

                Intent i = new Intent(MainActivity.this, ListItemActivity.class);
                startActivity(i);
            }

            //close the drawer
            drawer.closeDrawers();
            //tell the OS
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //get the id of the selected item
        int id = item.getItemId();
        if (id == R.id.addItemOption){
            if (itemName.getText().toString().isEmpty()){
                showEmptyToast();
            } else{
                addItemData();
                showToast();
            }
        } else if (id == R.id.clearFieldsOption){
            clearText();
        }
        return super.onOptionsItemSelected(item);
    }

    class MyBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            StringTokenizer sT = new StringTokenizer(msg, ";");
            itemNameString = sT.nextToken();
            quantityString = sT.nextToken();
            costString = sT.nextToken();
            descriptionString = sT.nextToken();
            frozen = Boolean.parseBoolean(sT.nextToken());

            itemName.setText(itemNameString);
            quantity.setText(quantityString);
            cost.setText(costString);
            description.setText(descriptionString);
            frozenButton.setChecked(frozen);

        }
    }


    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");

        //SharedPreferences myData = getSharedPreferences("file1", 0);
        SharedPreferences myData = getPreferences(0);
        itemNameString = myData.getString("item", "");
        quantityString = myData.getString("quantity", "0");
        costString = myData.getString("cost", "0.0");
        descriptionString = myData.getString("description", "");

        itemName.setText(itemNameString);
        quantity.setText(quantityString);
        cost.setText(costString);
        description.setText(descriptionString);
    }

    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");

    }

    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");

        itemNameString = itemName.getText().toString();
        quantityString = quantity.getText().toString();
        costString = cost.getText().toString();
        descriptionString = description.getText().toString();

        //SharedPreferences myData = getSharedPreferences("file1",0);
        SharedPreferences myData = getPreferences(0);
        SharedPreferences.Editor myEditor = myData.edit();
        myEditor.putString("item",itemNameString);
        myEditor.putString("quantity", quantityString);
        myEditor.putString("cost",costString);
        myEditor.putString("description", descriptionString);
        myEditor.apply();
    }

    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }


    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //purpose is to save non-view state so save the instance variable
        //nonViewState is type String (an instance variable!!)
        //outState.putString("keyNonView", nonViewState);
        Log.i(TAG, "onSaveInstanceState");

    }

    //only gets executed if inState != null so no need to check for null Bundle
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        //nonViewState = inState.getString("keyNonView");
        Log.i(TAG, "onRestoreInstanceState");

    }

    public void showEmptyToast(){
        Toast myMessage = Toast.makeText(this, "Please key in item information before adding", Toast.LENGTH_SHORT);
        myMessage.show();
    }

    public void addItem(View v){
        if (itemName.getText().toString().isEmpty()){
            showEmptyToast();
        } else{
            addItemData();
            showToast();
        }
    }

    public void showToast() {
        String showName = itemName.getText().toString().trim();
        if (!showName.isEmpty()) {
            Toast myMessage = Toast.makeText(this, "New item ( " + showName + " ) has been added", Toast.LENGTH_SHORT);
            myMessage.show();
        }
    }

    public void clearField(View v){
        clearText();
    }

    public void clearText() {
        itemName.setText("");
        quantity.setText("0");
        cost.setText("0.0");
        description.setText("");

    }

    public void addItemData(){
        String nam = itemName.getText().toString();
        String quan = quantity.getText().toString();
        String cos = cost.getText().toString();
        String desc = description.getText().toString();
        boolean froz = frozenButton.isChecked();
        Item item = new Item(nam,quan,cos,desc,Boolean.toString(froz));

        //dataItems.add(item);
        mItemViewModel.insert(item);

    }


}
