package com.l000phone.mygifttalk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.l000phone.mygifttalk.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class RefreshListView extends ListView implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private RotateAnimation rotateUp;
    private RotateAnimation rotateDown;

    public RefreshListView(Context context) {
        super(context);
        initHeadView();
        initFooterView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeadView();
        initFooterView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeadView();
        initFooterView();
    }

    private int mHeadViewHeight;
    private View mFooterView;
    private int mFooterViewHeight;
    private View headView;
    private TextView tvTitle;
    private TextView tvTime;
    private ImageView ivArrow;
    private ProgressBar pbProgress;

    public void initHeadView() {
        headView = View.inflate(getContext(), R.layout.refresh_head_view, null);

        tvTitle = (TextView) headView.findViewById(R.id.tv_title);
        tvTime = (TextView) headView.findViewById(R.id.tv_time);
        ivArrow = (ImageView) headView.findViewById(R.id.iv_arr);
        pbProgress = (ProgressBar) headView.findViewById(R.id.pb_progress);

        //测量headView的高度,0,0,测量的是默认的布局文件的宽高
        headView.measure(0, 0);
        //得到布局文件的高.
        mHeadViewHeight = headView.getMeasuredHeight();
        //隐藏headView
        headView.setPadding(0, -mHeadViewHeight, 0, 0);

        initAnim();
        this.addHeaderView(headView);
    }

    /*
     * 初始化脚布局
	 */
    private void initFooterView() {
        mFooterView = View.inflate(getContext(),
                R.layout.refresh_footer_view, null);
        this.addFooterView(mFooterView);

        mFooterView.measure(0, 0);
        mFooterViewHeight = mFooterView.getMeasuredHeight();

        mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);// 隐藏

        this.setOnScrollListener(this);
    }

    private int startY = -1;
    private static final int STATE_PULL_REFRESH = 0;//没有满足刷新的状态
    private static final int STATE_RELEASE_REFRESH = 1;// 满足了刷新的条件,松开即可刷新
    private static final int STATE_REFRESHING = 2;// 正在刷新

    private int currentState;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {// 确保startY有效
                    startY = (int) ev.getRawY();
                }

                if (currentState == STATE_REFRESHING) {// 正在刷新时不做处理
                    break;
                }

                int endY = (int) ev.getRawY();
                int dy = endY - startY;// 移动便宜量

                if (dy > 0 && getFirstVisiblePosition() == 0) {// 只有下拉并且当前是第一个item,才允许下拉
                    int padding = dy - mHeadViewHeight;// 计算padding
                    headView.setPadding(0, padding, 0, 0);// 设置当前padding

                    if (padding > 0 && currentState != STATE_RELEASE_REFRESH) {// 状态改为松开刷新
                        currentState = STATE_RELEASE_REFRESH;
                        refreshState();
                    } else if (padding < 0 && currentState != STATE_PULL_REFRESH) {// 改为下拉刷新状态
                        currentState = STATE_PULL_REFRESH;
                        refreshState();
                    }

                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:
                startY = -1;// 重置

                if (currentState == STATE_RELEASE_REFRESH) {
                    currentState = STATE_REFRESHING;// 正在刷新
                    headView.setPadding(0, 0, 0, 0);// 显示
                    refreshState();
                } else if (currentState == STATE_PULL_REFRESH) {
                    headView.setPadding(0, -mHeadViewHeight, 0, 0);// 隐藏
                }

                break;

        }
        return super.onTouchEvent(ev);
    }

    /**
     * 刷新下拉控件的布局
     */
    private void refreshState() {
        switch (currentState) {
            case STATE_PULL_REFRESH:
                tvTitle.setText("下拉刷新");
                ivArrow.setVisibility(View.VISIBLE);
                pbProgress.setVisibility(View.INVISIBLE);
                ivArrow.startAnimation(rotateDown);
                break;
            case STATE_RELEASE_REFRESH:
                tvTitle.setText("松开刷新");
                ivArrow.setVisibility(View.VISIBLE);
                pbProgress.setVisibility(View.INVISIBLE);
                ivArrow.startAnimation(rotateUp);
                break;
            case STATE_REFRESHING:
                tvTitle.setText("正在刷新...");
                ivArrow.clearAnimation();// 必须先清除动画,才能隐藏
                ivArrow.setVisibility(View.INVISIBLE);
                pbProgress.setVisibility(View.VISIBLE);

                if (mListener != null) {
                    mListener.onRefresh();
                }
                break;

            default:
                break;
        }
    }

    private void initAnim() {
        rotateUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateUp.setDuration(200);
        rotateUp.setFillAfter(true);
        rotateDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateDown.setDuration(200);
        rotateDown.setFillAfter(true);

    }

    private OnRefreshListener mListener;

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    // 定义一个接口让listView自己去加载数据.
    public interface OnRefreshListener {
        void onRefresh();

        void onLoadMore();// 加载下一页数据
    }

    /*
     * 当listView加载数据完成后调用的方法,收起下拉刷新的控件
	 */
    public void onRefreshComplete(boolean success) {
        if (isLoadingMore) {// 正在加载更多...
            mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);// 隐藏脚布局
            isLoadingMore = false;
        } else {
            currentState = STATE_PULL_REFRESH;
            tvTitle.setText("下拉刷新");
            ivArrow.setVisibility(View.VISIBLE);
            pbProgress.setVisibility(View.INVISIBLE);

            headView.setPadding(0, -mHeadViewHeight, 0, 0);// 隐藏

            if (success) {
                tvTime.setText("最后刷新时间:" + getCurrentTime());
            }
        }

    }

    /**
     * 获取当前时间
     */
    public String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    private boolean isLoadingMore;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE
                || scrollState == SCROLL_STATE_FLING) {

            if (getLastVisiblePosition() == getCount() - 1 && !isLoadingMore) {// 滑动到最后
                System.out.println("到底了.....");
                mFooterView.setPadding(0, 0, 0, 0);// 显示
                setSelection(getCount() - 1);// 改变listview显示位置

                isLoadingMore = true;

                if (mListener != null) {
                    mListener.onLoadMore();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    OnItemClickListener mItemClickListener;

    //重写ListView的方法
    @Override
    public void setOnItemClickListener(
            OnItemClickListener listener) {
        super.setOnItemClickListener(this);

        mItemClickListener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(parent, view, position
                    - getHeaderViewsCount(), id);
        }
    }

}
