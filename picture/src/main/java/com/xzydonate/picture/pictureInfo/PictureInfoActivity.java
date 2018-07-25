package com.xzydonate.picture.pictureInfo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.xzydonate.basesdk.activity.BaseEventActivity;
import com.xzydonate.basesdk.util.UrLRouter;
import com.xzydonate.picture.PictureResp;
import com.xzydonate.picture.R;
import com.xzydonate.picture.R2;

import butterknife.BindView;

@Route(path = UrLRouter.PICTURE_PAGE_INFO_ACT)
public class PictureInfoActivity extends BaseEventActivity {

    @BindView(R2.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R2.id.image)
    ImageView mImage;
    @BindView(R2.id.tool)
    Toolbar mToolbar;
    @BindView(R2.id.fab)
    FloatingActionButton mFab;

    @Override
    public int createView(Bundle savedInstanceState) {
        return R.layout.activity_picture_page_info;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void resumeView() {

    }

    @Override
    public void destroyView() {

    }

    @Override
    public void onReceive(boolean isSticky, String eventTag, Object event) {
        if(isSticky){
            switch (eventTag){
                case "PictureResp":
                    PictureResp pictureResp = (PictureResp) event;
                    Glide.with(this).load(pictureResp.getUrl()).into(mImage);
                    break;
            }
        }
    }
}