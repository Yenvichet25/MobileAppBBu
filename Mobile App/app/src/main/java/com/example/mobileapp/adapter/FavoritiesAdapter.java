package com.example.mobileapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.fragments.Contact;
import com.example.mobileapp.fragments.Favorities;
import com.example.mobileapp.models.ContactItems;

public class FavoritiesAdapter extends RecyclerView.Adapter<FavoritiesAdapter.ViewHolder> {
    private ContactItems[] favoritesList;

    public  FavoritiesAdapter(ContactItems[] contactList){
        this.favoritesList = contactList;
    }
    @Override
    public FavoritiesAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.favorities_list_item,parent,false);
        ViewHolder  viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( FavoritiesAdapter.ViewHolder holder, int position) {
        final ContactItems contact = favoritesList[position];
        holder.imgContact.setImageResource(contact.getImageId());
        holder.tvContactName.setText(contact.getContactName());
        holder.tvContactNum.setText(contact.getContactNumber());
        holder.imgContact.setTag(""+contact.getImageId());
        holder.tvContactEmail.setText(contact.getContactEmail());
    }

    @Override
    public int getItemCount() {
        return favoritesList.length;
    }
    public  class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgContact;
        private TextView tvContactName;
        private TextView tvContactNum;
        private TextView tvContactEmail;
        private CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            imgContact = (ImageView) itemView.findViewById(R.id.tvFavoriteImg);
            tvContactName = (TextView) itemView.findViewById(R.id.tvFavoriteName);
            tvContactNum = (TextView) itemView.findViewById(R.id.tvFavoriteNumber);
            tvContactEmail = (TextView) itemView.findViewById(R.id.tvFavoriteEmail);
            cardView = (CardView) itemView.findViewById(R.id.cardViewID);
        }
    }
}
