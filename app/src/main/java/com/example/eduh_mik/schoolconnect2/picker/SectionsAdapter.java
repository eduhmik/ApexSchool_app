package com.example.eduh_mik.schoolconnect2.picker;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.activities.ListActivity;
import com.example.eduh_mik.schoolconnect2.models.Section;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SectionsAdapter extends RecyclerView.Adapter<SectionsAdapter.SectionVH> {
    private ArrayList<Section> sections;

    public SectionsAdapter(ArrayList<Section> sections) {
        this.sections = sections;
    }


    @Override
    public SectionVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_class, parent, false);
        return new SectionVH(view);
    }

    @Override
    public void onBindViewHolder(SectionsAdapter.SectionVH holder, int position) {
        holder.nameTextView.setText(sections.get(position).getName());
        holder.codeTextView.setText(sections.get(position).getDesc());
    }


    @Override
    public int getItemCount() {
        return sections == null ? 0 : sections.size();
    }


    public class SectionVH extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view_flag)
        ImageView flagImageView;
        @BindView(R.id.text_view_name)
        TextView nameTextView;
        @BindView(R.id.text_view_code)
        TextView codeTextView;

        public SectionVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), ListActivity.class);
                    //intent.putExtra("section",new Gson().toJson(sections.get(getAdapterPosition())));
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
