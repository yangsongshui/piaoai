package com.example.yangsong.piaoai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.MyBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangsong on 2017/5/16.
 */

public class UserAdapter extends MyBaseAdapter<String> {
    Context mContext;
    List<String> mList = new ArrayList<>();

    public UserAdapter(List<String> list, Context context) {
        super(list);
        mContext = context;
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext,
                    R.layout.user_item, null);
            new UserAdapter.ViewHolder(convertView);
        }
        UserAdapter.ViewHolder holder = (UserAdapter.ViewHolder) convertView.getTag();
        //  holder.link_name_tv.setText(mList.get(position).getName());

        return convertView;
    }

    class ViewHolder {
        TextView link_name_tv;

        public ViewHolder(View view) {
            // link_name_tv = (TextView) view.findViewById(R.id.link_name_tv);
            view.setTag(this);
        }
    }
}
