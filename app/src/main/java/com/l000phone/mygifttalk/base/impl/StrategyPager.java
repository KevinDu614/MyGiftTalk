package com.l000phone.mygifttalk.base.impl;

import com.google.gson.Gson;
import com.l000phone.mygifttalk.CategoryEntity.ChannelGroupsData;
import com.l000phone.mygifttalk.CategoryEntity.ColumnsDate;
import com.l000phone.mygifttalk.Constants.Constants;
import com.l000phone.mygifttalk.R;
import com.l000phone.mygifttalk.activities.ItemDetailActivity;
import com.l000phone.mygifttalk.adapter.CategoryGridAdapter;
import com.l000phone.mygifttalk.base.BaseContentPager;
import com.l000phone.mygifttalk.utils.BitmapHelper;
import com.l000phone.mygifttalk.view.CustomGridView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * 攻略界面
 * Created by Leon on 2016/7/7.
 */

public class StrategyPager extends BaseContentPager implements View.OnClickListener {
    public StrategyPager(Activity activity) {
        super(activity);
    }

    ListView strategyList;
    List<ChannelGroupsData.DataBean.ChannelGroupsBean> channel_groups;
    LinearLayout id_gallery;

    @Override
    public View initViews() {
        //布局文件填充
        View view = View.inflate(mActivity, R.layout.strategy_list, null);
        strategyList = (ListView) view.findViewById(R.id.lv_strategy);
        //给ListView添加栏目布局
        View SubjectView = View.inflate(mActivity, R.layout.subject_layout, null);

        id_gallery = (LinearLayout) SubjectView.findViewById(R.id.id_gallery);
        TextView see_more_subject = (TextView) SubjectView.findViewById(R.id.see_more_subject);
        see_more_subject.setOnClickListener(this);
        //给栏目加载数据
        String subjectUrl = Constants.SUBJECT_URL;
        initSubjectData(subjectUrl);
        //给品类,风格,对象的GridView添加数据
        String cateUrl = Constants.CATEGORY_URL;
        initCateData(cateUrl);


        strategyList.addHeaderView(SubjectView);


        return view;
    }

    //给品类,风格,对象的GridView添加数据
    private void initCateData(String cateUrl) {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, cateUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.i("initCateData", "种类数据加载成功");
                ParseJsonToBean(result, false);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i("initCateData", "种类数据加载失败");
            }
        });
    }

    //给栏目加载数据
    private void initSubjectData(String subjectUrl) {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, subjectUrl, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.i("initSubjectData", "栏目数据加载成功");
                ParseJsonToBean(result, true);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i("initSubjectData", "栏目数据加载失败");
            }
        });
    }

    private void ParseJsonToBean(String result, boolean isHeaderView) {
        Gson gson = new Gson();
        if (isHeaderView) {
            //解析栏目数据
            ColumnsDate columnsDate = gson.fromJson(result, ColumnsDate.class);
            List<ColumnsDate.DataBean.ColumnsBean> columns = columnsDate.getData().getColumns();
            Log.i("ParseJsonToBean", "给栏目项设置数据");
            if (columnsDate != null) {
                for (int i = 0; i < columns.size(); i++) {
                    LinearLayout columnView = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.recycler_view_item, id_gallery, false);
                    TextView tv_columns_type = (TextView) columnView.findViewById(R.id.tv_columns_type);
                    TextView tv_columns_phase = (TextView) columnView.findViewById(R.id.tv_columns_phase);
                    TextView tv_columns_author = (TextView) columnView.findViewById(R.id.tv_columns_author);
                    final ImageView iv_sub_img = (ImageView) columnView.findViewById(R.id.iv_sub_img);
                    String imageUrl = columns.get(i).getCover_image_url();

                    //栏目item设置其他数据
                    tv_columns_type.setText(columns.get(i).getTitle());
                    tv_columns_phase.setText(columns.get(i).getSubtitle());
                    tv_columns_author.setText(columns.get(i).getAuthor());

                    //下载图片
                    BitmapUtils utils = new BitmapUtils(mActivity);
                    utils.display(iv_sub_img, imageUrl, new BitmapLoadCallBack<ImageView>() {
                        @Override
                        public void onLoadCompleted(ImageView imageView, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                            Bitmap roundCornerImage = BitmapHelper.getRoundCornerImage(bitmap, 10);
                            iv_sub_img.setImageBitmap(roundCornerImage);
                        }

                        @Override
                        public void onLoadFailed(ImageView imageView, String s, Drawable drawable) {

                        }
                    });
                    id_gallery.addView(columnView);
                }
            }
        } else {
            //解析种类数据
            ChannelGroupsData channelGroupsData = gson.fromJson(result, ChannelGroupsData.class);
            //品类,风格,对象
            channel_groups = channelGroupsData.getData().getChannel_groups();
            Log.i("ParseJsonToBean", "给GridView种类项设置数据");
            strategyList.setAdapter(new CategoryListAdapter());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.see_more_subject:
                Toast.makeText(mActivity, "点击了栏目的查看全部", Toast.LENGTH_SHORT).show();
        }
    }

    private class CategoryListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return channel_groups.size();
        }

        @Override
        public Object getItem(int position) {
            return channel_groups.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = View.inflate(parent.getContext(), R.layout.list_item, null);
                vh.name = (TextView) convertView.findViewById(R.id.tv_name);
                vh.gridView = (CustomGridView) convertView.findViewById(R.id.gv_grid);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            //获取 品类,风格,对象 中的一项
            final String name = channel_groups.get(position).getName();
            //设置标题
            vh.name.setText(name);
            //GridView数据
            final List<ChannelGroupsData.DataBean.ChannelGroupsBean.ChannelsBean> channels = channel_groups.get(position).getChannels();
            CategoryGridAdapter mGridAdapter = new CategoryGridAdapter(mActivity);
            mGridAdapter.setData(channels);
            //绑定适配器
            vh.gridView.setAdapter(mGridAdapter);
            //绑定监听器
            vh.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = channels.get(position).getName();//获取品类项的名字(品类的礼物)
                    int channelsId = channels.get(position).getId();//对应项的ID(品类的礼物ID)

                    //拼接Url(攻略->品类->礼物):
                    // http:api.liwushuo.com/v2/channels/111/items_v2?gender=1&generation=2&order_by=now&limit=20&offset=0
                    String CATEGORY_URL_ITEM_after = String.format(Constants.CATEGORY_URL_ITEM, channelsId);
                    Intent intent = new Intent(parent.getContext(), ItemDetailActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("imgUrl", CATEGORY_URL_ITEM_after);
                }
            });
            return convertView;
        }

        private class ViewHolder {
            private TextView name;
            private CustomGridView gridView;
        }

    }
}
