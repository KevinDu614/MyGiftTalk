package com.l000phone.mygifttalk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.l000phone.mygifttalk.categoryentity.SingleItemData;
import com.l000phone.mygifttalk.constants.Constants;
import com.l000phone.mygifttalk.R;
import com.l000phone.mygifttalk.adapter.SingleCategoryAdapter;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

/**
 * 展示单个品类产品的Activity
 */
public class SingleCategoryActivity extends AppCompatActivity {

    private LinearLayout llSelctions;
    private TextView tvSingleCategoryName;
    private GridView gvSinglecategory_products;
    private String singleCategoryUrl;
    List<SingleItemData.DataBean.ItemsBean> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_gift_singlegv);

        initViews();
    }

    //初始化Views
    private void initViews() {
        llSelctions = (LinearLayout) findViewById(R.id.ll_selections);
        llSelctions.setVisibility(View.GONE);

        //ActionBar位置的名称
        tvSingleCategoryName = (TextView) findViewById(R.id.tv_singleCategoryName);
        //展示各产品信息的GridView
        gvSinglecategory_products = (GridView) findViewById(R.id.gv_singlecategory_products);

        tvSingleCategoryName.setText(getIntent().getStringExtra("singleCategoryname"));
        singleCategoryUrl = getIntent().getStringExtra("singleCategoryUrl");


        //单品->智能设备:http://api.liwushuo.com/v2/item_subcategories/7/items?limit=20&offset=0


        getDataFromServer(singleCategoryUrl);
        //点击监听效果
        gvSinglecategory_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //单品->智能设备->左上的双接口优盘: http://api.liwushuo.com/v2/items/1061793
                //String GIFT_CATEGORY_ITEM_ITEM_URL = "http://api.liwushuo.com/v2/items/%d";
                int productId = items.get(position).getId();
                //拼接跳转网址
                String web_url = String.format(Constants.GIFT_CATEGORY_ITEM_ITEM_URL,productId);
                Intent intent = new Intent(SingleCategoryActivity.this, StrategyDetail.class);
                intent.putExtra("url", web_url);

                startActivity(intent);
            }
        });
    }

    //从服务器获取数据
    private void getDataFromServer(String singleCategoryUrl) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, singleCategoryUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;

                //解析数据(单品->智能设备界面)
                Gson gson = new Gson();
                SingleItemData singleCategoryData = gson.fromJson(result, SingleItemData.class);
                final List<SingleItemData.DataBean.ItemsBean> items = singleCategoryData.getData().getItems();
                //设置,绑定适配器
                SingleCategoryAdapter singleCategoryAdapter = new SingleCategoryAdapter(SingleCategoryActivity.this);
                singleCategoryAdapter.setData(items);
                gvSinglecategory_products.setAdapter(singleCategoryAdapter);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }


}
