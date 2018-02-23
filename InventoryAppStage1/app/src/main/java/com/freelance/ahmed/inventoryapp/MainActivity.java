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
        String pname, pprice, pquantity, suppliername, supplierphone;
        pname = productName.getText().toString();
        pprice = productPrice.getText().toString();
        pquantity = productQuantity.getText().toString();
        suppliername = supplierName.getText().toString();
        supplierphone = supplierPhone.getText().toString();
        if (pname.trim().equals("")) {
            productName.setError(getResources().getString(R.string.error));
        } else {
            products.put(InventoryAppContract.InventoryEntry.COLUMN_PRODUCT_NAME, pname);
        }
        if (pprice.trim().equals("")) {
            productPrice.setError(getResources().getString(R.string.error));
        } else {
            products.put(InventoryAppContract.InventoryEntry.COLUMN_PRODUCT_PRICE, pprice);
        }
        if (pquantity.trim().equals("")) {
            productQuantity.setError(getResources().getString(R.string.error));
        } else {
            products.put(InventoryAppContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY, pquantity);
        }
        if (suppliername.trim().equals("")) {
            supplierName.setError(getResources().getString(R.string.error));
        } else {
            products.put(InventoryAppContract.InventoryEntry.COLUMN_SUPPLIER_NAME, suppliername);
        }
        if (supplierphone.trim().equals("")) {
            supplierPhone.setError(getResources().getString(R.string.error));
        } else {
            products.put(InventoryAppContract.InventoryEntry.COLUMN_SUPPLIER_PHONE, supplierphone);
        }
        long newRowInsertedID = sql.insert(InventoryAppContract.InventoryEntry.TABLE_NAME, null, products);
        if (newRowInsertedID != -1) {
            Toast.makeText(this, getResources().getString(R.string.successmsg), Toast.LENGTH_SHORT).show();
            productName.setText("");
            productPrice.setText("");
            productQuantity.setText("");
            supplierName.setText("");
            supplierPhone.setText("");
        } else {
            Toast.makeText(this, getResources().getString(R.string.failedmsg), Toast.LENGTH_SHORT).show();
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
        TextView data = findViewById(R.id.showdatatv);
        try {

            int productNameColumnIndex = cursor.getColumnIndex(InventoryAppContract.InventoryEntry.COLUMN_PRODUCT_NAME);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryAppContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY);
            while (cursor.moveToNext()) {
                String productnameString = cursor.getString(productNameColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);
                data.append("\n" + "Product: " + productnameString + " | Quantity: " + quantity);
                data.setVisibility(View.VISIBLE);
            }
        } finally {
            cursor.close();
        }

    }

}
