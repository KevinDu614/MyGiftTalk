package com.l000phone.mygifttalk.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.l000phone.mygifttalk.CategoryEntity.ChannelsItem;
import com.l000phone.mygifttalk.R;
import com.l000phone.mygifttalk.utils.BitmapHelper;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import java.util.List;

/**
 * 分类界面的gridView的item点击跳转的Activity中ListView的适配器
 */
public class CategoryDetailAdapter extends BaseAdapter {

    private Activity mActivity;
    private List<ChannelsItem.DataBean.ItemsBean> mItemsEntityList;

    public CategoryDetailAdapter(Activity activity) {
        this.mActivity = activity;
    }

    public void setData(List<ChannelsItem.DataBean.ItemsBean> itemsEntityList) {
        this.mItemsEntityList = itemsEntityList;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return mItemsEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemsEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Log.d("TAG", "=====================================================");
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mActivity, R.layout.grid_list_item, null);

            holder.type = (TextView) convertView.findViewById(R.id.iv_list_item_type);
            holder.shortTitle = (TextView) convertView.findViewById(R.id.tv_list_item_short_title);
            holder.nickPic = (ImageView) convertView.findViewById(R.id.iv_list_item_nickpic);
            holder.nickName = (TextView) convertView.findViewById(R.id.iv_list_item_nickname);
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_list_item_img);
            holder.title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tvLikes = (TextView) convertView.findViewById(R.id.tv_favourite);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        ChannelsItem.DataBean.ItemsBean itemsBean = mItemsEntityList.get(position);

        Log.d("======", itemsBean.toString());
        String coverImageUrl = itemsBean.getCover_image_url();
        String nickPicUrl = itemsBean.getAuthor().getAvatar_url();

        holder.type.setText(itemsBean.getColumn().getCategory());
        holder.shortTitle.setText(itemsBean.getColumn().getTitle());
        holder.nickName.setText(itemsBean.getAuthor().getNickname());
        holder.title.setText(itemsBean.getTitle());
        holder.tvLikes.setText(itemsBean.getLikes_count());
        loadImage(holder.imageView, coverImageUrl);
        loadImage(holder.nickPic, nickPicUrl);

        return convertView;
    }

    private void loadImage(ImageView imageView, String icon_url) {

        BitmapUtils utils = new BitmapUtils(mActivity);
        utils.display(imageView, icon_url, new BitmapLoadCallBack<ImageView>() {
            @Override
            public void onLoadCompleted(ImageView imageView, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                Bitmap roundCornerBitmap = BitmapHelper.getRoundCornerImage(bitmap, 15);

                imageView.setImageBitmap(roundCornerBitmap);
            }

            @Override
            public void onLoadFailed(ImageView imageView, String s, Drawable drawable) {

            }
        });
    }

    class ViewHolder {
        TextView type;
        TextView nickName;
        ImageView nickPic;
        TextView title;
        TextView shortTitle;
        ImageView imageView;
        TextView tvLikes;

    }

}
