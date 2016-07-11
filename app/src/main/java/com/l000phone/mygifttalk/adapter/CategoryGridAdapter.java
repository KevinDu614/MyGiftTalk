package com.l000phone.mygifttalk.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.l000phone.mygifttalk.categoryentity.ChannelGroupsData;
import com.l000phone.mygifttalk.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import java.util.List;

/**
 * 分类界面之攻略中GridView的适配器
 */
public class CategoryGridAdapter extends BaseAdapter {

    private List<ChannelGroupsData.DataBean.ChannelGroupsBean.ChannelsBean> mChannels;

    private Activity mActivity;

    //private String gridViewItemUrl;;
    private String name;
    //    private StringBuffer buffer;

    public CategoryGridAdapter(Activity activity) {
        this.mActivity = activity;
    }

    public void setData(List<ChannelGroupsData.DataBean.ChannelGroupsBean.ChannelsBean> channels) {
        this.mChannels = channels;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mChannels == null ? 0 : mChannels.size();
    }

    @Override
    public Object getItem(int i) {
        return mChannels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //http://api.liwushuo.com/v2/channels/125/items?limit=20&offset=0&gender=1&generation=0
    private String hostUrl = " http://api.liwushuo.com/v2/channels/";
    private String lastUrl = "/items?limit=20&offset=0&gender=1&generation=0";

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(viewGroup.getContext(), R.layout.grid_item, null);
            holder.imageView = (ImageView) view.findViewById(R.id.iv_img);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        ChannelGroupsData.DataBean.ChannelGroupsBean.ChannelsBean channelsBean = mChannels.get(i);

        String icon_url = channelsBean.getCover_image_url();
        loadImage(holder.imageView, icon_url);
        return view;
    }

    private void loadImage(ImageView imageView, String icon_url) {

        BitmapUtils utils = new BitmapUtils(mActivity);
        utils.display(imageView, icon_url, new BitmapLoadCallBack<ImageView>() {
            @Override
            public void onLoadCompleted(ImageView imageView, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {

                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadFailed(ImageView imageView, String s, Drawable drawable) {

            }
        });
    }

    class ViewHolder {
        TextView name;
        ImageView imageView;

    }
}
