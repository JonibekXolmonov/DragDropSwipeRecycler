package com.example.dragdropswiperecycler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dragdropswiperecycler.R;
import com.example.dragdropswiperecycler.activity.MainActivity;
import com.example.dragdropswiperecycler.helper.ItemTouchHelperAdapter;
import com.example.dragdropswiperecycler.model.Contact;

import java.util.ArrayList;
import java.util.Collections;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

    MainActivity mainActivity;
    Context context;
    ArrayList<Contact> contacts = new ArrayList<>();

    public ContactAdapter(MainActivity mainActivity, ArrayList<Contact> contacts) {
        this.mainActivity = mainActivity;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Contact contact = contacts.get(position);

        if (holder instanceof ContactViewHolder) {
            ((ContactViewHolder) holder).name.setText(contact.getName());
            ((ContactViewHolder) holder).number.setText(contact.getNumber());
            ((ContactViewHolder) holder).layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.openDetailPage(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition<toPosition){
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(contacts,i,i+1);
            }
        }else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(contacts,i,i-1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        contacts.remove(position);
        notifyItemRemoved(position);
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView name;
        TextView number;
        LinearLayout layout;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            layout = view.findViewById(R.id.linear_layout);
            name = view.findViewById(R.id.tv_contact_name);
            number = view.findViewById(R.id.tv_contact_number);
        }
    }
}
