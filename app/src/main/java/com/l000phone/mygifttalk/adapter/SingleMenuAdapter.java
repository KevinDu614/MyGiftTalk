package com.l000phone.mygifttalk.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.l000phone.mygifttalk.categoryentity.SingleCategoriesData;
import com.l000phone.mygifttalk.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import java.util.List;

/**
 * 单个产品目录(热门分类,个性配饰,温暖家居... ...)GridView的适配器
 */
public class SingleMenuAdapter extends BaseAdapter {
    public Activity mActicity;
    //菜单子项(热门分类-智能设备,鲜花蛋糕... ...)
    private List<SingleCategoriesData.DataBean.CategoriesBean.SubcategoriesBean> subcategories;

    SingleMenuAdapter(Activity activity) {
        mActicity = activity;
    }

    public void setData(List<SingleCategoriesData.DataBean.CategoriesBean.SubcategoriesBean> subcategories) {
        this.subcategories = subcategories;
        notifyDataSetChanged();
    }

    ViewHolder holder = null;

    @Override
    public int getCount() {
        return subcategories.size();
    }

    @Override
    public Object getItem(int position) {
        return subcategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            //单个项目布局填充(智能设备)
            convertView = View.inflate(mActicity, R.layout.list_category_singlemenu_gvitem, null);
            vh.ivSingleProduct = (ImageView) convertView.findViewById(R.id.iv_singleProduct);
            vh.tvSingleProductName = (TextView) convertView.findViewById(R.id.tv_singleProductName);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //子项目logo下载
        BitmapUtils utils = new BitmapUtils(mActicity);
        utils.display(vh.ivSingleProduct, subcategories.get(position).getIcon_url(), new BitmapLoadCallBack<ImageView>() {
            @Override
            public void onLoadCompleted(ImageView imageView, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                vh.ivSingleProduct.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadFailed(ImageView imageView, String s, Drawable drawable) {

            }
        });
        vh.tvSingleProductName.setText(subcategories.get(position).getName());

        return convertView;
    }

    private class ViewHolder {
        ImageView ivSingleProduct;
        TextView tvSingleProductName;
    }
}
