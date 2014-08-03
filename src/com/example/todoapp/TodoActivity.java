package com.example.todoapp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {
	private ArrayList<String> todoItems;
	ArrayAdapter<String> itemsAdapater;
	ListView lvItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		
		lvItems = (ListView) findViewById(R.id.lvItems);
		todoItems = new ArrayList<String>();
		itemsAdapater = new ArrayAdapter<>(this,  android.R.layout.simple_list_item_1, todoItems);
		lvItems.setAdapter(itemsAdapater);
		todoItems.add("Item 1");
		todoItems.add("Item 2");
	}
	
	public void addTodoItem(View v) {
		EditText edNewItem = (EditText) findViewById(R.id.etAddItem);
		itemsAdapater.add(edNewItem.getText().toString());
		edNewItem.setText("");
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
