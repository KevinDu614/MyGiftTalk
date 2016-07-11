package com.l000phone.mygifttalk.base.impl;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.gson.Gson;
import com.l000phone.mygifttalk.categoryentity.ChannelGroupsData;
import com.l000phone.mygifttalk.constants.Constants;
import com.l000phone.mygifttalk.R;
import com.l000phone.mygifttalk.base.BaseContentPager;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类界面内容pager的初始化,初始化完后返回view,给frameLayout添加view
 */
public class CategoryContentPager extends BaseContentPager {

    //private CategoryListAdapter adapter;
    private ViewPager categoryViewPager;
    public List<BaseContentPager> viewPagerLists;
    // private List<ListGridBean.DataEntity.ChannelGroupsEntity> channel_groups;

    private List<ChannelGroupsData.DataBean.ChannelGroupsBean> channelGroups;

    public CategoryContentPager(Activity activity) {
        super(activity);
        //System.out.println("====到ListView中来了,数据已经传递过来了.======");
    }

    @Override
    public View initViews() {
        //System.out.println("======初始化ListView了=============");
        View view = View.inflate(mActivity, R.layout.viewpager_category, null);
        categoryViewPager = (ViewPager) view.findViewById(R.id.ct_view_pager);
//        adapter = new CategoryListAdapter();
//        categoryViewPager.setAdapter(adapter);
        //	System.out.println("==========给list设置了适配器了=======");
//		ViewGroup parent = (ViewGroup) listView.getParent();
//		if(parent!=null){
//			parent.removeView(listView);
//		}
        return view;
    }

    @Override
    public void initData() {
        viewPagerLists = new ArrayList<BaseContentPager>();
        StrategyPager strategyPager = new StrategyPager(mActivity);
        GiftPager giftPager = new GiftPager(mActivity);
        viewPagerLists.add(strategyPager);
        viewPagerLists.add(giftPager);
    }

    private void initViewData() {
        String url = Constants.CATEGORY_URL;
        //System.out.println("==到加载数据了======");
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;

                        Gson gson = new Gson();
                        ChannelGroupsData channelGroupsData = gson.fromJson(result, ChannelGroupsData.class);
                        channelGroups = channelGroupsData.getData().getChannel_groups();

                        // System.out.println("数据加载成功了" + result);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        // System.out.println("=========加载数据失败了==========");
                    }
                });
    }

}
