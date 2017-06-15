package com.example.yangsong.piaoai.myview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.MyBaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yangsong on 2017/3/15.
 */

public class AddressPopuoWindow extends PopupWindow {
    private ListView listView;
    private View mMenuView;
    private AddressAdapter adapter;

    Context context;
    public AddressPopuoWindow(Context context, AdapterView.OnItemClickListener listener) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.address_pop, null);
        this.context=context;
        listView = (ListView) mMenuView.findViewById(R.id.address_lv);

        listView.setOnItemClickListener(listener);

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        // this.setAnimationStyle(R.style.take_photo_anim );
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public void setList(List<PoiItem> list) {
        adapter = new AddressAdapter(list, context);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    class AddressAdapter extends MyBaseAdapter<PoiItem> {
        Context mContext;
        List<PoiItem> mList = new ArrayList<>();

        public AddressAdapter(List<PoiItem> list, Context context) {
            super(list);
            mContext = context;
            mList = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mContext,
                        R.layout.address_item, null);
                new AddressAdapter.ViewHolder(convertView);
            }
            AddressAdapter.ViewHolder holder = (AddressAdapter.ViewHolder) convertView.getTag();
            holder.address_name.setText(mList.get(position).getTitle());
            holder.address_tv.setText(mList.get(position).getSnippet());
           // Log.e("getView", mList.get(position).getTitle() + " " + mList.get(position).getSnippet());
            return convertView;
        }

        class ViewHolder {
            TextView address_name, address_tv;

            ViewHolder(View view) {
                address_name = (TextView) view.findViewById(R.id.address_name);
                address_tv = (TextView) view.findViewById(R.id.address_tv);
                view.setTag(this);
            }
        }
    }
}
