package com.example.simpletodo;

import android.support.constraint.solver.widgets.Helper;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;
    Button btnAdd;
    EditText EditItem;
    RecyclerView rvItem;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        EditItem = findViewById(R.id.EditItem);
        rvItem = findViewById(R.id.rvItem);

        loadItems();
        EditItem.setText("");

        ItemsAdapter.OnLongClickListener onLongClickListener =new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClickListener(int position) {
                // delete the item form the model
                //notify the adapter which position we deleted the item
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "item was removed",  Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };


        itemsAdapter  = new ItemsAdapter(items, onLongClickListener);
        rvItem.setAdapter(itemsAdapter);
        rvItem.setLayoutManager(new LinearLayoutManager(this));


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem  = EditItem.getText().toString();
                // add item to the model
                items.add(todoItem);
                // Notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size()-1);

                EditItem.setText("");
                Toast.makeText(getApplicationContext(), "item was added",  Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });

    }

    private File getDataFile() {
        return new File(getFilesDir(), "data2.txt");

    }
    // this function will load item by reading every line of the datafile
    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e ){
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }

    }
    // this function saves items by writing them into the data file
    private void saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e){
            Log.e("MainActivity","Error writing items",e);
        }
    }
}