package com.example.simpletodo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.DatagramSocket;
import java.util.List;


// reResponsible for Displaying data from the model Into a row in recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{
    public interface OnLongClickListener {
        void onItemLongClickListener(int position);
    }


    List<String> items;
    OnLongClickListener longClickListener;



    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener){
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // use layout inflator to inflate a view
        //

        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // grab item at the position then bind that item into specified viewHolder

        String item = items.get(position);

        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Container to provide easy access to views that represent each row on the list

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }


        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Notify the listener which position was long pressed
                    longClickListener.onItemLongClickListener(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
