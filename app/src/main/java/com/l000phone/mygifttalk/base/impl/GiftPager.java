package com.l000phone.mygifttalk.base.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.google.gson.Gson;
import com.l000phone.mygifttalk.CategoryEntity.SingleCategoriesData;
import com.l000phone.mygifttalk.Constants.Constants;
import com.l000phone.mygifttalk.R;
import com.l000phone.mygifttalk.activities.ChooseGiftToolAct;
import com.l000phone.mygifttalk.adapter.MenuAdapter;
import com.l000phone.mygifttalk.adapter.ProductAdapter;
import com.l000phone.mygifttalk.base.BaseContentPager;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类-单品页面
 */
public class GiftPager extends BaseContentPager {
    private String mUrl = Constants.GIFT_CATEGORY_URL;

    @ViewInject(R.id.rl_choice)
    private RelativeLayout rlChoice;
    @ViewInject(R.id.lv_menu)
    private ListView lvMenu;
    @ViewInject(R.id.lv_product)
    private ListView lvProduct;

    private ArrayList<SingleCategoriesData.DataBean.CategoriesBean> categories;
    private List<SingleCategoriesData.DataBean.CategoriesBean.SubcategoriesBean> subcategories;

    public GiftPager(Activity mActivity) {
        super(mActivity);
        initData();
    }

    @Override
    public View initViews() {

        View view = View.inflate(mActivity, R.layout.category_liwu_pager, null);
        ViewUtils.inject(this, view);
        //选礼神器点击监听
        rlChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("====", "选礼神器被点击了");
                Intent intent = new Intent(mActivity, ChooseGiftToolAct.class);
                intent.putExtra("TitleName", "选礼神器");
                mActivity.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        initDataFromServer();
    }


    //从服务器获取数据(单品界面数据获取)
    private void initDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mUrl, new RequestCallBack<String>() {

            private MenuAdapter mMenuAdapter;
            private ProductAdapter mProductAdaptear;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                //解析数据(单品界面2个ListView数据)
                Gson gson = new Gson();
                SingleCategoriesData categoryData = gson.fromJson(result, SingleCategoriesData.class);
                final List<SingleCategoriesData.DataBean.CategoriesBean> categories = categoryData.getData().getCategories();

                //单品界面左边ListView数据填充
                mMenuAdapter = new MenuAdapter(mActivity);
                mMenuAdapter.setData((ArrayList<SingleCategoriesData.DataBean.CategoriesBean>) categories);
                lvMenu.setAdapter(mMenuAdapter);
                lvMenu.setSelection(0);//默认选择是第一个位置
                //单品左边菜单分类目录设置监听
                lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(mActivity, categories.get(position).getName(), Toast.LENGTH_SHORT).show();
                        /*currentItem = position;
                        mProductAdaptear.setCurrentItem(currentItem);*/
                        lvProduct.setSelection(position);

                    }
                });
                //单品右边ListView
                mProductAdaptear = new ProductAdapter(mActivity);
                mProductAdaptear.setData((ArrayList<SingleCategoriesData.DataBean.CategoriesBean>) categories);
                lvProduct.setAdapter(mProductAdaptear);
                //lvProduct.setSelector(new ColorDrawable(Color.TRANSPARENT));
                lvProduct.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        //lvProduct.setOnTouchListener();
                        int firstItem = view.getFirstVisiblePosition();
                        if (view.getLastVisiblePosition() != categories.size() - 1) {
                            lvMenu.setSelection(firstItem);

                        }
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                    }
                });
            }


            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mActivity, "未连接到网络", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }


}
