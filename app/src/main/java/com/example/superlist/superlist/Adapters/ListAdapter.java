package com.example.superlist.superlist.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superlist.superlist.Objects.List;
import com.google.android.material.textview.MaterialTextView;
import com.example.superlist.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface Listlistener {
        void clicked(List List, int position);
    }

    private Activity activity;
    private ArrayList<List> lists = new ArrayList<>();
    private Listlistener listlistener;

    public ListAdapter(Activity activity, ArrayList<List> lists){
        this.activity = activity;
        this.lists = lists;
    }

    public void setLististener(Listlistener listlistener) {
        this.listlistener = listlistener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card, parent, false);
        ListHolder liqueurHolder = new ListHolder(view);
        return liqueurHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final ListHolder holder = (ListHolder) viewHolder;
        List List = getItem(position);

        holder.list_LBL_title.setText(List.getTitle());
        holder.list_LBL_amount.setText("" + List.getItems_Counter());


        int resourceId = activity.getResources().getIdentifier(List.getImage(), "drawable", activity.getPackageName());
        holder.list_IMG_image.setImageResource(resourceId);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public List getItem(int position) {
        return lists.get(position);
    }



    class ListHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView list_IMG_image;
        private MaterialTextView list_LBL_title;
        private MaterialTextView list_LBL_amount;


        public ListHolder(View itemView) {
            super(itemView);
            list_IMG_image = itemView.findViewById(R.id.list_IMG_image);
            list_LBL_title = itemView.findViewById(R.id.list_LBL_title);
            list_LBL_amount = itemView.findViewById(R.id.list_LBL_amount);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listlistener != null) {
                        listlistener.clicked(getItem(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });
        }

    }

}
