package com.zhy.sample.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * @author 邱永恒
 * @time 2017/7/24  16:01
 * @desc ${TODD}
 */

public class BannerViewPager extends ViewPager{
    private Context mContext;
    private PagerAdapter mAdapter;

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        this.mAdapter = adapter;
        super.setAdapter(adapter);
        setAdapter(new BannerPagerAdapter());
    }
}
