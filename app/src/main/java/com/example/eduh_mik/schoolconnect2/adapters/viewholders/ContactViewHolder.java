package com.example.eduh_mik.schoolconnect2.adapters.viewholders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.models.Contact;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class ContactViewHolder extends RecyclerView.ViewHolder {
    //public TextView tvName,tvDescription, tvStartDate;
    @BindView(R.id.tv_person)
    TextView tvName;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv_Description)
    TextView tvDescription;
    @BindView(R.id.tv_details)
    TextView tvDetails;





    private Context _context;

    public ContactViewHolder(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(final Contact contact) {
        tvName.setText(contact.getFirst_name());
        tvDescription.setText(contact.getEmail());
        tvDetails.setText(contact.getPhone());
        tvPosition.setText(contact.getRole());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(contact.getPhone()));
                Log.e("Phone Number", "tel:"+contact.getPhone());
                _context.startActivity((intent));

            }
        });
        }
        //Calendar calendarNow = Calendar.getInstance();
        //tvStartDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendarNow));
        //date = tvStartDate.toString();

    @OnClick(R.id.card_contact)
    public void onCardClicked() {

    }

}