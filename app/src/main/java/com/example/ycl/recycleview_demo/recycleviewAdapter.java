package com.example.ycl.recycleview_demo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class recycleviewAdapter extends RecyclerView.Adapter<recycleviewAdapter.ViewHolder> {

    public recycleviewAdapter(List<String> list) {
        this.list = list;
    }

    private List<String> list;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public void addItem(int index,String s){
        list.add(index,s);
        notifyItemInserted(index);
    }
    public void deleteItem(int index){

        list.remove(index);
        notifyItemRemoved(index);
    }


    /**
     * 设置点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置长点击事件
     *
     * @param
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_normal, viewGroup, false);
        recycleviewAdapter.ViewHolder viewHolder = new recycleviewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tv_item.setText(list.get(i));
        int adapterPosition = viewHolder.getAdapterPosition();
        if (onItemClickListener != null) {
            viewHolder.tv_item.setOnClickListener(new MyOnClickListener(i, list.get(adapterPosition)));
        }
        if (onItemLongClickListener != null) {
            viewHolder.tv_item.setOnLongClickListener(new MyOnLongClickListener(i,list.get(adapterPosition)));
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item;

        ViewHolder(View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);
        }
    }

    private class MyOnClickListener implements View.OnClickListener {
        private int position;
        private String data;

        public MyOnClickListener(int position, String data) {
            this.position = position;
            this.data = data;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, position, data);
        }
    }

    private class MyOnLongClickListener implements View.OnLongClickListener {
        private int position;
        private String data;

        public MyOnLongClickListener(int position, String data) {
            this.position = position;
            this.data = data;
        }

        @Override
        public boolean onLongClick(View v) {
            onItemLongClickListener.onItemLongClick(v, position, data);
            return true;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String data);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View v, int position, String data);

    }


}
