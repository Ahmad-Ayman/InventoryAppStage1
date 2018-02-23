package com.freelance.ahmed.inventoryapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ahmed on 2/23/2018.
 */

public class InventoryAppDbHelper extends SQLiteOpenHelper {
    // The database name
    private static final String DATABASE_NAME = "inventory.db";

    //  The database version
    private static final int DATABASE_VERSION = 1;

    public InventoryAppDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold products data
        final String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE " + InventoryAppContract.InventoryEntry.TABLE_NAME + " (" +
                InventoryAppContract.InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                InventoryAppContract.InventoryEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, " +
                InventoryAppContract.InventoryEntry.COLUMN_PRODUCT_PRICE + " REAL NOT NULL, " +
                InventoryAppContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL, " +
                InventoryAppContract.InventoryEntry.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL, " +
                InventoryAppContract.InventoryEntry.COLUMN_SUPPLIER_PHONE + " TEXT NOT NULL " +
                "); ";
        // execute the query
        sqLiteDatabase.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop the Database
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InventoryAppContract.InventoryEntry.TABLE_NAME);
        // Create new one
        onCreate(sqLiteDatabase);
    }
}
