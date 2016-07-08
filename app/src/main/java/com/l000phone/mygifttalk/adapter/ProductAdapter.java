package com.l000phone.mygifttalk.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.l000phone.mygifttalk.CategoryEntity.SingleCategoriesData;
import com.l000phone.mygifttalk.Constants.Constants;
import com.l000phone.mygifttalk.R;
import com.l000phone.mygifttalk.activities.SingleCategoryActivity;
import com.l000phone.mygifttalk.view.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类-礼物-产品品类(右侧ListView)的Adapter
 */
public class ProductAdapter extends BaseAdapter {
    private Activity mActicity;

    private ArrayList<SingleCategoriesData.DataBean.CategoriesBean> categories;


    public ProductAdapter(Activity activity) {
        mActicity = activity;
    }

    public void setData(ArrayList<SingleCategoriesData.DataBean.CategoriesBean> categories) {
        this.categories = categories;
        notifyDataSetChanged();
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
            convertView = View.inflate(mActicity, R.layout.list_category_item_product, null);
            vh.llSingleMenuName = (LinearLayout) convertView.findViewById(R.id.ll_singleMenuName);
            vh.tvSingleMenuName = (TextView) convertView.findViewById(R.id.tv_singleMenuName);
            vh.gvSingleMenuProduct = (NoScrollGridView) convertView.findViewById(R.id.gv_category_singleMenuProduct);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //---------------热门分类----------------
        vh.tvSingleMenuName.setText(categories.get(position).getName());
        final List<SingleCategoriesData.DataBean.CategoriesBean.SubcategoriesBean> subcategories = categories.get(position).getSubcategories();

        SingleMenuAdapter mSingleMenuAdapter = new SingleMenuAdapter(mActicity);
        mSingleMenuAdapter.setData(subcategories);
        vh.gvSingleMenuProduct.setAdapter(mSingleMenuAdapter);
        vh.gvSingleMenuProduct.setSelector(new ColorDrawable(Color.TRANSPARENT));
        vh.gvSingleMenuProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击跳转至单品分类的子分类项(单品-热门分类-智能设备)
                Intent intent = new Intent(mActicity, SingleCategoryActivity.class);
                //智能设备,鲜花蛋糕... ...
                int singleCategoryId = subcategories.get(position).getId();

                //分类之单品:String GIFT_CATEGORY_URL = "http://api.liwushuo.com/v2/item_categories/tree";
                //单品->智能设备:http://api.liwushuo.com/v2/item_subcategories/7/items?limit=20&offset=0
                //分类单品条目中点击某项产品跳转网址
                //单品->智能设备->左上的双接口优盘: http://api.liwushuo.com/v2/items/1061793
                String singleCategoryUrl = String.format(Constants.GIFT_CATEGORY_ITEM_URL,singleCategoryId);
                //跳转页面后的顶部菜单栏的标题名
                String singleCategoryname = subcategories.get(position).getName();
                intent.putExtra("singleCategoryname", singleCategoryname);
                intent.putExtra("singleCategoryUrl", singleCategoryUrl);
                mActicity.startActivity(intent);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        LinearLayout llSingleMenuName;
        TextView tvSingleMenuName;
        NoScrollGridView gvSingleMenuProduct;
    }
}
