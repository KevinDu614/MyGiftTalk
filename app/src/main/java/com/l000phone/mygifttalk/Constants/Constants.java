package com.l000phone.mygifttalk.Constants;

import android.content.Context;
import android.content.SharedPreferences;

import com.l000phone.mygifttalk.base.BaseApplication;


/**
 * URL实体类
 */
public interface Constants {

    Context context = BaseApplication.getContext();

    SharedPreferences preferences = context.getSharedPreferences("config", 0);


    //服务器地址
    String SERVER_URL = "http://api.liwushuo.com/";
    //首页根据性别分类URL,gender=1为男,=2为女,后面是年龄段数值为:1--5
    String HOME_URL = SERVER_URL + "v2/channels/preset?gender=1&generation=1";


    //攻略之栏目
    String SUBJECT_URL = "http://api.liwushuo.com/v2/columns";
    //攻略之栏目条目跳转网址
    //%d占位,用条目的栏目项的id替换(WebView跳转URL)
    //ColumnsDate.DataBean.ColumnsBean.id
    //攻略->栏目->每日值得Buy条目: http://api.liwushuo.com/v2/columns/46?limit=20&offset=0
    String CATEGORY_ITEM_URL = "http://api.liwushuo.com/v2/columns/%d?limit=20&offset=0";

    //攻略之GridView(品类,风格,对象)
    String CATEGORY_URL = "http://api.liwushuo.com/v2/channel_groups/all";
    //攻略GridView条目
    //%d占位符,用单个产品id替换
    //ChannelGroupsData.DataBean.ChannelGroupsBean.ChannelsBean.id
    //攻略->品类->礼物:http:api.liwushuo.com/v2/channels/111/items_v2?gender=1&generation=2&order_by=now&limit=20&offset=0
    String CATEGORY_URL_ITEM = "http:api.liwushuo.com/v2/channels/%d/items_v2?gender=1&generation=2&order_by=now&limit=20&offset=0";
    //攻略GridView条目的某一项跳转WebView
    //%d占位符
    //ChannelsItem.DataBean.ItemsBean.id
    //攻略->品类->礼物->古风墨色,许你一场美丽的意外:http://www.liwushuo.com/posts/1044044
    String CATEGORY_URL_ITEM_ITEM = "http://www.liwushuo.com/posts/%d";


    //选礼神器条目界面
    String SELECT_GIFT_URL = "http://api.liwushuo.com/v2/search/item_by_type?limit=20&offset=0";
    //选礼神器标题
    String SELECT_GIFT_TITLE_URL = "http://api.liwushuo.com/v2/search/item_filter";
    //选礼神器-单个产品跳转网址
    //%d占位符,用单个产品id替换(WebView跳转URL)
    //ChangeGiftData.DataBean.ItemsBean.id
    //选礼神器->暖手宝:http://api.liwushuo.com/v2/items/1043991
    String SELECT_PRODUCT = "http://www.liwushuo.com/items/%d";


    //分类之单品
    String GIFT_CATEGORY_URL = "http://api.liwushuo.com/v2/item_categories/tree";
    //分类单品项点击某条目
    //%d占位符,用单个产品id替换
    //SingleItemData.DataBean.ItemsBean.id
    //单品->智能设备:http://api.liwushuo.com/v2/item_subcategories/7/items?limit=20&offset=0
    String GIFT_CATEGORY_ITEM_URL = "http://api.liwushuo.com/v2/item_subcategories/%d/items?limit=20&offset=0";
    //分类单品条目中点击某项产品跳转网址
    //%d占位符,用单个产品id替换(WebView跳转URL)
    //单品->智能设备->左上的双接口优盘: http://api.liwushuo.com/v2/items/1061793
    String GIFT_CATEGORY_ITEM_ITEM_URL = "http://api.liwushuo.com/v2/items/%d";


    //热词
    String CATEGORY_HOT_WORDS_URL = "http://api.liwushuo.com/v2/search/hot_words";

}