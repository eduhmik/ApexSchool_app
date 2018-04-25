package com.example.eduh_mik.schoolconnect.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduh_mik.schoolconnect.R;
import com.example.eduh_mik.schoolconnect.adapters.viewholders.ContactViewHolder;
import com.example.eduh_mik.schoolconnect.models.Contact;

import java.util.List;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    private Context context;
    private List<Contact> contactList;


    public ContactAdapter(Context context, List<Contact> contactList) {
        this.contactList = contactList;
        this.context = context;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_contact_item, parent, false);
        return new ContactViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, int position) {
        holder.bind(contactList.get(position));

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
}
