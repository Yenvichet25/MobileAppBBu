package com.example.mobileapp;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapp.adapter.ContactAdapter;
import com.example.mobileapp.adapter.ProductAdapter;
import com.example.mobileapp.models.ContactItems;
import com.example.mobileapp.models.ProductItem;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    GridView gridView;

    ArrayList<ProductItem> lstProduct = new ArrayList<ProductItem>(){};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //show layout
        setContentView(R.layout.product_layout);
        gridView = (GridView) findViewById(R.id.gvProduct);

        lstProduct.add(new ProductItem(R.drawable.product,"ABC Beer"));
        lstProduct.add(new ProductItem(R.drawable.product,"Angkor Beer"));
        lstProduct.add(new ProductItem(R.drawable.product,"Leo Beer"));
        lstProduct.add(new ProductItem(R.drawable.product,"U Beer"));

        ProductAdapter adapter = new ProductAdapter(this,R.layout.product_item,lstProduct);
        gridView.setAdapter(adapter);


    }
}
