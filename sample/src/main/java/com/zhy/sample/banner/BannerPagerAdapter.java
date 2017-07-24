package com.zhy.sample.banner;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * @author 邱永恒
 * @time 2017/7/24  16:03
 * @desc ${TODD}
 */

public class BannerPagerAdapter extends PagerAdapter{
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
