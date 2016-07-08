package com.l000phone.mygifttalk.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.l000phone.mygifttalk.CategoryEntity.SingleItemData;
import com.l000phone.mygifttalk.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import java.util.List;

/**
 * 分类-礼物-单个品类(分类-礼物-智能设备)的GridView的Adapter
 */
public class SingleCategoryAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<SingleItemData.DataBean.ItemsBean> mSingleCategoryItems;

    public SingleCategoryAdapter(Activity activity) {
        mActivity = activity;
    }

    public void setData(List<SingleItemData.DataBean.ItemsBean> singleCategoryItems) {
        mSingleCategoryItems = singleCategoryItems;
    }

    @Override
    public int getCount() {
        return mSingleCategoryItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mSingleCategoryItems.get(position);
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
            convertView = View.inflate(mActivity, R.layout.singlepager_gridview_item, null);
            vh.ivSingleimg = (ImageView) convertView.findViewById(R.id.iv_singleimg);
            vh.tvSingletitle = (TextView) convertView.findViewById(R.id.tv_singletitle);
            vh.tvSingleprice = (TextView) convertView.findViewById(R.id.tv_singleprice);
            vh.tvSinglelike = (TextView) convertView.findViewById(R.id.tv_singlelike);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //各条目图片下载(智能设备界面条目)
        BitmapUtils utils = new BitmapUtils(mActivity);
        utils.display(vh.ivSingleimg, mSingleCategoryItems.get(position).getCover_image_url(), new BitmapLoadCallBack<ImageView>() {
            @Override
            public void onLoadCompleted(ImageView imageView, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                vh.ivSingleimg.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadFailed(ImageView imageView, String s, Drawable drawable) {

            }
        });
        //条目名称(Adam Elements·双接口手机极速U盘)
        vh.tvSingletitle.setText(mSingleCategoryItems.get(position).getName());
        vh.tvSingleprice.setText(mSingleCategoryItems.get(position).getPrice());
        vh.tvSinglelike.setText(mSingleCategoryItems.get(position).getFavorites_count());

        return convertView;
    }

    class ViewHolder {
        ImageView ivSingleimg;
        TextView tvSingletitle;
        TextView tvSingleprice;
        TextView tvSinglelike;
    }
}
