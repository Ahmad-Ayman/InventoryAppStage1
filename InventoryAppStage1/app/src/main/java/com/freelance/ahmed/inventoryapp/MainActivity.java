package com.freelance.ahmed.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.freelance.ahmed.inventoryapp.Data.InventoryAppContract;
import com.freelance.ahmed.inventoryapp.Data.InventoryAppDbHelper;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText productName;
    private TextInputEditText productPrice;
    private TextInputEditText productQuantity;
    private TextInputEditText supplierName;
    private TextInputEditText supplierPhone;
    private Button insertingBtn;
    private Button queryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productName = findViewById(R.id.produtName);
        productPrice = findViewById(R.id.produtPrice);
        productQuantity = findViewById(R.id.productQuantity);
        supplierName = findViewById(R.id.supplierName);
        supplierPhone = findViewById(R.id.supplierPhone);
        insertingBtn = findViewById(R.id.addtoDB);
        queryBtn = findViewById(R.id.getfromDB);
        insertingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();


            }
        });

        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryData();
            }
        });

    }

    private void insertData() {
        InventoryAppDbHelper dbHelper = new InventoryAppDbHelper(this);
        SQLiteDatabase sql = dbHelper.getWritableDatabase();
        ContentValues products = new ContentValues();
        products.put(InventoryAppContract.InventoryEntry.COLUMN_PRODUCT_NAME, productName.getText().toString());
        products.put(InventoryAppContract.InventoryEntry.COLUMN_PRODUCT_PRICE, productPrice.getText().toString());
        products.put(InventoryAppContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY, productQuantity.getText().toString());
        products.put(InventoryAppContract.InventoryEntry.COLUMN_SUPPLIER_NAME, supplierName.getText().toString());
        products.put(InventoryAppContract.InventoryEntry.COLUMN_SUPPLIER_PHONE, supplierPhone.getText().toString());
        long newRowInsertedID = sql.insert(InventoryAppContract.InventoryEntry.TABLE_NAME, null, products);
        if (newRowInsertedID != -1) {
            Toast.makeText(this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
            productName.setText("");
            productPrice.setText("");
            productQuantity.setText("");
            supplierName.setText("");
            supplierPhone.setText("");
        } else {
            Toast.makeText(this, "Failed to Add the Product", Toast.LENGTH_SHORT).show();
        }

    }

    private void queryData() {
        InventoryAppDbHelper dbHelper = new InventoryAppDbHelper(this);
        SQLiteDatabase sql = dbHelper.getReadableDatabase();
        String[] proj = {
                InventoryAppContract.InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryAppContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY
        };
        Cursor cursor = sql.query(InventoryAppContract.InventoryEntry.TABLE_NAME,
                proj,
                null,
                null,
                null,
                null,
                InventoryAppContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY
        );

        try {

            int productNameColumnIndex = cursor.getColumnIndex(InventoryAppContract.InventoryEntry.COLUMN_PRODUCT_NAME);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryAppContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY);
            while (cursor.moveToNext()) {
                String productnameString = cursor.getString(productNameColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);
                TextView data = findViewById(R.id.showdatatv);
                data.append("\n" + "Product: " + productnameString + " | Quantity: " + quantity);
                data.setVisibility(View.VISIBLE);
            }
        } finally {
            cursor.close();
        }

    }

}
