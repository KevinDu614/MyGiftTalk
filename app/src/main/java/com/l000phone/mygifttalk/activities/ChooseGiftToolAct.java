package com.l000phone.mygifttalk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.l000phone.mygifttalk.CategoryEntity.ChangeGiftData;
import com.l000phone.mygifttalk.Constants.Constants;
import com.l000phone.mygifttalk.R;
import com.l000phone.mygifttalk.adapter.ChooseGiftAdapter;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;


/**
 * 选礼神器的Activity
 */
public class ChooseGiftToolAct extends AppCompatActivity {

    private LinearLayout llSelctions;
    private TextView tvSingleCategoryName;
    private GridView gvSinglecategory_products;
    private String productsUrl;
    private List<ChangeGiftData.DataBean.ItemsBean> items;
    private String web_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("====", "选礼神器界面开启");
        setContentView(R.layout.activity_category_gift_singlegv);
        initViews();
    }

    //初始化Views
    private void initViews() {
        System.out.print("开始初始化界面");
        llSelctions = (LinearLayout) findViewById(R.id.ll_selections);
        tvSingleCategoryName = (TextView) findViewById(R.id.tv_singleCategoryName);
        gvSinglecategory_products = (GridView) findViewById(R.id.gv_singlecategory_products);
        tvSingleCategoryName.setText(getIntent().getStringExtra("TitleName"));
        //初始化界面数据
        getDataFromServer(Constants.SELECT_GIFT_URL);
        //界面GridView添加监听器
        gvSinglecategory_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //选礼神器条目界面
                //String SELECT_GIFT_URL = "http://api.liwushuo.com/v2/search/item_by_type?limit=20&offset=0";
                //选礼神器->暖手宝:http://api.liwushuo.com/v2/items/1043991
                int peoductItemID = items.get(position).getId();
                //获取点击条目的跳转Url
                String web_url = String.format(Constants.SELECT_PRODUCT, peoductItemID);
                Intent intent = new Intent(ChooseGiftToolAct.this, StrategyDetail.class);
                intent.putExtra("url", web_url);
                startActivity(intent);
            }
        });
    }

    //从服务器获取数据
    private void getDataFromServer(String productsUrl) {

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, productsUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                //解析选礼神器界面的数据
                Gson gson = new Gson();
                ChangeGiftData chooseGiftData = gson.fromJson(result, ChangeGiftData.class);
                items = chooseGiftData.getData().getItems();
                Log.i("TAG", items.toString());

                ChooseGiftAdapter chooseGiftAdapter = new ChooseGiftAdapter(ChooseGiftToolAct.this);
                chooseGiftAdapter.setData(items);
                gvSinglecategory_products.setAdapter(chooseGiftAdapter);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

}
