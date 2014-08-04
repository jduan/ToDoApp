package com.example.todoapp;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {
    private static final int REQUEST_CODE = 20;
    private List<Item> todoItems;
    ArrayAdapter<Item> itemsAdapater;
    ListView lvItems;
    private ItemDAO datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        
        datasource = new ItemDAO(this);
        datasource.open();
        
        todoItems = datasource.getAllItems();

        lvItems = (ListView) findViewById(R.id.lvItems);
        itemsAdapater = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(itemsAdapater);
        setupListViewListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
                i.putExtra("item", todoItems.get(position));
                i.putExtra("itemIdx", position);
                startActivityForResult(i, REQUEST_CODE);
            }
        });

        lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Item itemRemoved = todoItems.remove(position);
                datasource.deleteItem(itemRemoved);
                itemsAdapater.notifyDataSetChanged();
                return true;
            }
        });
    }

    public void addTodoItem(View v) {
        EditText edNewItem = (EditText) findViewById(R.id.etAddItem);
        Item item = datasource.createItem(edNewItem.getText().toString());
        itemsAdapater.add(item);
        edNewItem.setText("");
        System.out.println("added item to view");
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Item updatedItem = (Item) data.getExtras().getSerializable("item");
            int itemIdx = data.getIntExtra("itemIdx", -1);
            todoItems.set(itemIdx, updatedItem);
            itemsAdapater.notifyDataSetChanged();
            datasource.saveItem(updatedItem);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
