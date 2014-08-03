package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
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
		loadItemsFromFile();
		itemsAdapater = new ArrayAdapter<>(this,  android.R.layout.simple_list_item_1, todoItems);
		lvItems.setAdapter(itemsAdapater);
		setupListViewListener();
	}
	
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				todoItems.remove(position);
				itemsAdapater.notifyDataSetChanged();
		        saveToFile();
				return true;
			}
		});
	}
	
	private void loadItemsFromFile() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			List<String> lines = FileUtils.readLines(todoFile);
			todoItems = new ArrayList<String>(lines);
		} catch (IOException e) {
			todoItems = new ArrayList<String>();
			e.printStackTrace();
		}
	}
	
	private void saveToFile() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, todoItems);
			System.out.println("Wrote items to file");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addTodoItem(View v) {
		EditText edNewItem = (EditText) findViewById(R.id.etAddItem);
		itemsAdapater.add(edNewItem.getText().toString());
		edNewItem.setText("");
		System.out.println("added item to view");
		saveToFile();
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
