package com.evan.pm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evan.pm.R;
import com.evan.pm.entity.Category;

import java.util.List;

/**
 * Created by evan on 2016/9/11.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{

    List<Category> categories;
    Context context;
    OnRecyclerViewItemClickListener onItemClickListener;

    public CategoryAdapter(Context context, List<Category> data) {
        super();
        this.context = context;
        this.categories = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.desc_tv.setText(category.getCategory());
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView desc_tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            desc_tv = ((TextView) itemView.findViewById(R.id.category_item_desc));
        }
    }
}
