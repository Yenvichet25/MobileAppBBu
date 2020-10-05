package com.example.mobileapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobileapp.R;
import com.example.mobileapp.models.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ArrayAdapter {
    ArrayList<ProductItem> lstProduct;
    public ProductAdapter( Context context, int resource,ArrayList<ProductItem> lst) {
        super(context, resource, lst);
        this.lstProduct = lst;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView.inflate(getContext(), R.layout.product_item,null);

        ProductItem product = lstProduct.get(position);
        ImageView img = (ImageView) v.findViewById(R.id.improduct);
        img.setImageResource(product.getImage());
        img.setTag(""+product.getImage());

        TextView pname = (TextView) v.findViewById(R.id.tvProductName);
        pname.setText(product.getProductName());
        return  v;
    }
}
