package com.evan.pm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evan.pm.R;
import com.evan.pm.entity.Account;

import java.util.List;


/**
 * Created by evan on 2016/4/22.
 */
public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.MyViewHolder> {

    List<Account> accounts;
    Context context;

    private OnRecyclerViewItemClickListener onItemClickListener;

    public InfoAdapter(Context context, List<Account> accounts) {
        super();
        this.context = context;
        this.accounts = accounts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_info, null);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Account account = accounts.get(position);
        holder.desc_tv.setText(account.getDescription());
        holder.username_tv.setText(account.getUsername());
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
        return accounts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView desc_tv;
        TextView username_tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            desc_tv = ((TextView) itemView.findViewById(R.id.info_item_desc));
            username_tv = (TextView) itemView.findViewById(R.id.info_item_username);
        }
    }
}
