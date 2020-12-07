package com.example.mobileapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.adapter.FavoritiesAdapter;
import com.example.mobileapp.models.ContactItems;

public class Favorities extends Fragment {
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        ContactItems[] contact = new ContactItems[]{
                new ContactItems(R.drawable.group,"ABC Beer","09890880","kakaka@gmail.com"),
                new ContactItems(R.drawable.group,"Leo","09865880","dara@gmail.com"),
                new ContactItems(R.drawable.group,"Cambodia Beer","09890540","thida@gmail.com")
        };
        View v = inflater.inflate(R.layout.tab_favarities,container,false);

        RecyclerView RV = (RecyclerView) v.findViewById(R.id.RVFavoriteCard);
        FavoritiesAdapter adapter = new FavoritiesAdapter(contact);
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(getActivity()));
        RV.setAdapter(adapter);
        return v;
    }
}
