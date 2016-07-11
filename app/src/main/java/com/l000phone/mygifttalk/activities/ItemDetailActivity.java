package com.l000phone.mygifttalk.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.l000phone.mygifttalk.CategoryEntity.ChannelsItem;
import com.l000phone.mygifttalk.R;
import com.l000phone.mygifttalk.adapter.CategoryDetailAdapter;
import com.l000phone.mygifttalk.view.RefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

/**
 * 分类界面的gridView的item点击跳转的Activity
 */
public class ItemDetailActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView tvTitle;
    private Button btnBack;
    private RefreshListView mListView;
    private String imgUrl;
    private String name;
    private String nextPageUrl;
    private CategoryDetailAdapter detailAdapter;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_item);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        btnBack = (Button) findViewById(R.id.btn_back);
        mListView = (RefreshListView) findViewById(R.id.lv_list);

        imgUrl = getIntent().getStringExtra("imgUrl");
        Log.i("TAG", imgUrl);

        name = getIntent().getStringExtra("name");
        tvTitle.setText(this.name);

        getDateForServer();

        mListView.setOnItemClickListener(this);
        btnBack.setOnClickListener(this);

        mListView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDateForServer();
            }

            @Override
            public void onLoadMore() {
                if (nextPageUrl != null) {
                    getMoreDateForServer();
                } else {
                    Toast.makeText(ItemDetailActivity.this, "最后一页了", Toast.LENGTH_SHORT)
                            .show();
                    mListView.onRefreshComplete(false);
                }
            }
        });
    }

    private void getDateForServer() {

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, imgUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String result = responseInfo.result;
                Log.i("TAG", result);
                ParseJsonToBean(result, false);
                mListView.onRefreshComplete(true);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(ItemDetailActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
                mListView.onRefreshComplete(false);
            }
        });

    }

    private void getMoreDateForServer() {

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, nextPageUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String result = responseInfo.result;
                Log.e("======", result);
                ParseJsonToBean(result, true);
                mListView.onRefreshComplete(true);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(ItemDetailActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
                mListView.onRefreshComplete(false);
            }
        });

    }

    private List<ChannelsItem.DataBean.ItemsBean> itemsEntityList;

    //解析数据
    private void ParseJsonToBean(String result, boolean isMore) {

        Gson gson = new Gson();

        ChannelsItem itemBean = gson.fromJson(result, ChannelsItem.class);

        String more = itemBean.getData().getPaging().getNext_url();
        if (!TextUtils.isEmpty(more)) {
            nextPageUrl = more;
        } else {
            nextPageUrl = null;
        }

        if (!isMore) {
            itemsEntityList = itemBean.getData().getItems();
            if (itemsEntityList != null) {
                Log.i("TAG", itemsEntityList.toString());
                //条目添加adapter()
                detailAdapter = new CategoryDetailAdapter(ItemDetailActivity.this);
                detailAdapter.setData(itemsEntityList);
                mListView.setAdapter(detailAdapter);
            }
        } else {
            List<ChannelsItem.DataBean.ItemsBean> moreItems = itemBean.getData().getItems();
            itemsEntityList.addAll(moreItems);
            detailAdapter.setData(itemsEntityList);
            detailAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击跳转至WebView界面
        ChannelsItem.DataBean.ItemsBean itemsEntity = itemsEntityList.get(position);
        String webUrl = itemsEntity.getUrl();
        Intent intent = new Intent(ItemDetailActivity.this, StrategyDetail.class);
        intent.putExtra("url", webUrl);
        startActivity(intent);
    }

}
