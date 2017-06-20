package io.github.h911002.firenews.module.me.earning;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.BaseActivity;
import io.github.h911002.firenews.base.mvp.IPresenter;
import io.github.h911002.firenews.support.view.slidingtab.SlidingItem;
import io.github.h911002.firenews.support.view.slidingtab.SlidingTabLayout;

/**
 * Created by liaoheng on 2017/6/6.
 */

public class EarningActivity extends BaseActivity implements SlidingTabLayout.OnTabSwitchListener {
    private SlidingTabLayout tabPageIndicator;
    private ViewPager viewPager;

    @Override
    protected IPresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int onContentViewID() {
        return R.layout.activity_earning;
    }

    @Override
    protected void onContentViewCreated(View contentView, Bundle savedInstanceState) {
        initBlueTitle("我的收入", true);
        tabPageIndicator = (SlidingTabLayout) findViewById(R.id.tab_indicator);
        tabPageIndicator.setOnTabSwitchListener(this);
        List<SlidingItem> slidingItems = Arrays.asList(new SlidingItem("金币明细"), new SlidingItem
                ("余额明细"));
        tabPageIndicator.setSlidingItems(slidingItems);
        viewPager = (ViewPager) findViewById(R.id.vp_content);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new EarningFragment();
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "title" + position;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
                tabPageIndicator.onSliding(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onTabClicked(int position, SlidingItem slidingItem) {
        viewPager.setCurrentItem(position, true);
    }
}
