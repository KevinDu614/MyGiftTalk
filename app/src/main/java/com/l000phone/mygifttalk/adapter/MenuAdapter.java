package com.l000phone.mygifttalk.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.l000phone.mygifttalk.categoryentity.SingleCategoriesData;
import com.l000phone.mygifttalk.R;

import java.util.ArrayList;

/**
 * 单品菜单项适配器
 */
public class MenuAdapter extends BaseAdapter {
    public Activity mActivity;

    public ArrayList<SingleCategoriesData.DataBean.CategoriesBean> categories;

    public MenuAdapter(Activity activity) {
        mActivity = activity;
    }

    public void setData(ArrayList<SingleCategoriesData.DataBean.CategoriesBean> categories) {
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = View.inflate(mActivity, R.layout.list_category_menu, null);
            vh.tvMenuName = (TextView) convertView.findViewById(R.id.tv_menuName);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //单品菜单项(热门分类... ...)
        vh.tvMenuName.setText(categories.get(position).getName());
        return convertView;
    }

    private class ViewHolder {
        TextView tvMenuName;
    }
}
