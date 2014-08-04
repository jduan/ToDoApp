package com.example.todoapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ItemDAO {
    private static final String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_ACTION };
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    
    public ItemDAO(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }
    
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    
    public void close() {
        dbHelper.close();
    }
    
    public Item createItem(String text) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_ACTION, text);
        long insertId = database.insert(MySQLiteHelper.TABLE_ITEMS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ITEMS, allColumns,
                MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null,
                null);
        cursor.moveToFirst();
        Item newItem = cursorToItem(cursor);
        cursor.close();
        return newItem;
    }
    
    public void deleteItem(Item item) {
        long id = item.getId();
        database.delete(MySQLiteHelper.TABLE_ITEMS, MySQLiteHelper.COLUMN_ID + " = " + id, null);
        System.out.println("Deleted item with id: " + id);
    }
    
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<Item>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ITEMS, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Item item = cursorToItem(cursor);
            items.add(item);
            cursor.moveToNext();
        }
        
        cursor.close();
        return items;
    }

    private Item cursorToItem(Cursor cursor) {
        Item item = new Item();
        item.setId(cursor.getLong(0));
        item.setAction(cursor.getString(1));
        return item;
    }
}
