package com.xzydonate.picture.page1;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.xzydonate.basesdk.activity.BaseFragment;
import com.xzydonate.basesdk.widget.recyclerview.BaseQuickAdapter;
import com.xzydonate.basesdk.widget.recyclerview.BaseViewHolder;
import com.xzydonate.basesdk.util.DensityUtil;
import com.xzydonate.basesdk.util.UrlRouter;
import com.xzydonate.picture.IPictureView;
import com.xzydonate.picture.PictureResp;
import com.xzydonate.picture.R;
import com.xzydonate.picture.R2;
import com.xzydonate.picture.pictureInfo.PictureInfoActivity;

import java.util.List;

import butterknife.BindView;

@Route(path = UrlRouter.PICTURE_PAGE1_FRAG)
public class PicturePage1Fragment extends BaseFragment implements IPictureView {
    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R2.id.swipeRFLayout)
    SwipeRefreshLayout mSwipeRFLayout;

    private PicturePage1Presenter presenter = null;
    private BaseQuickAdapter adapter = null;
    private int page = 2;

    @Override
    public int createView() {
        return R.layout.fragment_picture_page1;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        presenter = new PicturePage1Presenter(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mSwipeRFLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRFLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRFLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRFLayout.setRefreshing(true);
                presenter.loadPictures();
            }
        });

        mSwipeRFLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRFLayout.setRefreshing(true);
                presenter.loadPictures();
            }
        });

    }

    @Override
    public void resumeView() {
        mSwipeRFLayout.setRefreshing(true);
        presenter.loadPictures();
    }

    @Override
    public void destroyView() {

    }

    @Override
    public void onReceive(boolean isSticky, String eventTag, Object event) {

    }

    @Override
    public void loadSuccess(List<PictureResp> data) {
        Log.d("xzy", "loadSuccess ");
        page = 2;
        mSwipeRFLayout.setRefreshing(false);
        if (data.size() > 0) {
            if(adapter == null) {
                adapter = new BaseQuickAdapter<PictureResp, BaseViewHolder>(R.layout.page1_recycler_item, data) {

                    @Override
                    protected void convert(BaseViewHolder helper, final PictureResp item) {
                        helper.setText(R.id.tv, item.getDesc());
                        ImageView imageView = (ImageView) helper.getView(R.id.iv);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                DensityUtil.dip2px(getContext(),235));
                        imageView.setLayoutParams(lp);
                        Glide.with(getContext()).load(item.getUrl()).into(imageView);
                        helper.setOnClickListener(R.id.iv, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                gotoActivity(PictureInfoActivity.class,item);
                            }
                        });
                    }
                };
                adapter.openLoadAnimation();
                adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        presenter.loadMorePictures(8, page);
                    }
                });
                mRecyclerView.setAdapter(adapter);
            }else {
                adapter.setNewData(data);
            }
        }else {

        }
    }

    @Override
    public void loadFail(String errCode, String errMsg) {
        mSwipeRFLayout.setRefreshing(false);
        Log.d("xzy", "loadFail ");
    }

    @Override
    public void loadMoreSuccess(List<PictureResp> data) {
        if(data.size()>0){
            page++;
            adapter.addData(data);
            adapter.loadMoreComplete();
        }else {
            adapter.loadMoreEnd();
        }

    }

    @Override
    public void loadMoreFail(String errCode, String errMsg) {
          adapter.loadMoreFail();
    }
}
