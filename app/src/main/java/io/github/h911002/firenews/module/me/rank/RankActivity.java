package io.github.h911002.firenews.module.me.rank;

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
import io.github.h911002.firenews.module.me.earning.EarningFragment;
import io.github.h911002.firenews.support.view.slidingtab.SlidingItem;
import io.github.h911002.firenews.support.view.slidingtab.SlidingTabLayout;

public class RankActivity extends BaseActivity implements SlidingTabLayout.OnTabSwitchListener {

    private SlidingTabLayout tabPageIndicator;
    private ViewPager viewPager;

    @Override
    protected IPresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int onContentViewID() {
        return R.layout.activity_rank;
    }

    @Override
    protected void onContentViewCreated(View contentView, Bundle savedInstanceState) {
        initBlueTitle("我的排行榜", true);
        tabPageIndicator = (SlidingTabLayout) findViewById(R.id.tab_indicator);
        tabPageIndicator.setOnTabSwitchListener(this);
        List<SlidingItem> slidingItems = Arrays.asList(new SlidingItem("邀请排行榜"), new SlidingItem
                ("收入排行榜"), new SlidingItem("赠送排行榜"));
        tabPageIndicator.setSlidingItems(slidingItems);
        viewPager = (ViewPager) findViewById(R.id.vp_content);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new EarningFragment();
            }

            @Override
            public int getCount() {
                return 3;
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
