package com.example.yangsong.piaoai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.bean.MyBaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/3/25.
 */
public class EquipmentAdapter extends MyBaseAdapter<String> {
    Context mContext;
    List<String> mList = new ArrayList<>();

    public EquipmentAdapter(List<String> list, Context context) {
        super(list);
        mContext = context;
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContext,
                    R.layout.equipment_item, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
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
