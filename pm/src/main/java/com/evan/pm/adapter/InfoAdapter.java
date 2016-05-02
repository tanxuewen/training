package com.evan.pm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.evan.pm.R;
import com.evan.pm.entity.Account;

import java.util.List;


/**
 * Created by evan on 2016/4/22.
 */
public class InfoAdapter extends BaseAdapter {

    List<Account> accounts;
    Context context;

    public InfoAdapter(Context context, List<Account> accounts) {
        super();
        this.context = context;
        this.accounts = accounts;
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public Account getItem(int position) {
        return accounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_info,null);
            viewHolder.desc_tv = (TextView) convertView.findViewById(R.id.info_item_desc);
            viewHolder.username_tv = (TextView) convertView.findViewById(R.id.info_item_username);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Account account = getItem(position);
        viewHolder.desc_tv.setText(account.getDescription());
        viewHolder.username_tv.setText(account.getUsername());
        return convertView;
    }

    class ViewHolder{
        TextView desc_tv;
        TextView username_tv;
    }
}
