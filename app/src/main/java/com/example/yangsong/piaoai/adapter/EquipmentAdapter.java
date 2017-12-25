package com.example.yangsong.piaoai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.MyBaseAdapter;
import com.example.yangsong.piaoai.bean.Facility;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/3/25.
 */
public class EquipmentAdapter extends MyBaseAdapter<Facility.ResBodyBean.ListBean> {
    Context mContext;
    List<Facility.ResBodyBean.ListBean> mList = new ArrayList<>();

    public EquipmentAdapter(List<Facility.ResBodyBean.ListBean> list, Context context) {
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
        if (mList.get(position).getType().equals("3"))
            holder.device_name.setText(mContext.getString(R.string.adapter_msg));
        else
            holder.device_name.setText(mContext.getString(R.string.adapter_msg2));
        holder.device_nickName.setText(mList.get(position).getDeviceName());

        return convertView;
    }


    class ViewHolder {
        TextView device_name, device_nickName;

        public ViewHolder(View view) {
            device_name = (TextView) view.findViewById(R.id.device_name);
            device_nickName = (TextView) view.findViewById(R.id.device_nickName);
            view.setTag(this);
        }
    }
}
