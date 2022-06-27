package com.example.superlist.superlist.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.superlist.R;
import com.example.superlist.superlist.Objects.Item;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface itemListener {
        void clicked(Item item, int position);

        void longClick(Item item, int adapterPosition);
    }

    private Activity activity;
    private ArrayList<Item> items = new ArrayList<>();
    private itemListener itemListener;

    public ItemAdapter(Activity activity, ArrayList<Item> items, itemListener itemListener) {
        this.activity = activity;
        this.items = items;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final ItemHolder holder = (ItemHolder) viewHolder;
        Item item = getItem(position);

        holder.list_LBL_title.setText(item.getName());
        holder.list_LBL_amount.setText(item.getAmount() + item.getSuffix());

        Glide
                .with(activity)
                .load(R.drawable.ic_shopping_bag)
                .into(holder.list_IMG_image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Item getItem(int position) {
        return items.get(position);
    }


    class ItemHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView list_IMG_image;
        private MaterialTextView list_LBL_title;
        private MaterialTextView list_LBL_amount;


        public ItemHolder(View itemView) {
            super(itemView);
            list_IMG_image = itemView.findViewById(R.id.list_IMG_image);
            list_LBL_title = itemView.findViewById(R.id.list_LBL_title);
            list_LBL_amount = itemView.findViewById(R.id.list_LBL_amount);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemListener != null) {
                        itemListener.clicked(getItem(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemListener.longClick(getItem(getAdapterPosition()), getAdapterPosition());
                    return true;
                }
            });
        }

    }
}
