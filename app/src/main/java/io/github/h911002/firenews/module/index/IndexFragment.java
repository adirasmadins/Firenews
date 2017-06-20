package io.github.h911002.firenews.module.index;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.BaseFragment;
import io.github.h911002.firenews.base.mvp.BasePresenter;
import io.github.h911002.firenews.support.view.IndicatedViewPager;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends BaseFragment {

    private IndexPagerAdapter mIndexPagerAdapter;
    private IndicatedViewPager mIndicatedViewPager;

    public IndexFragment() {
        // Required empty public constructor
    }

    @Override
    protected int onContentViewID() {
        return R.layout.fragment_index;
    }

    @Override
    protected void onContentViewCreated(View contentView, Bundle savedInstanceState) {
        initBlueTitle(getString(R.string.home_tag), false);
        mIndicatedViewPager = (IndicatedViewPager) contentView.findViewById(R.id.indicator_pager);
        mIndexPagerAdapter = new IndexPagerAdapter(getFragmentManager());
        mIndexPagerAdapter.setData(getTest());
        mIndicatedViewPager.setAdapter(mIndexPagerAdapter);
        mIndicatedViewPager.setTitleItems(getTitles());
        mIndicatedViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }


    private class IndexPagerAdapter extends FragmentPagerAdapter {

        private List<Indicator> mIndicators = new ArrayList<Indicator>();

        public IndexPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private void setData(List<Indicator> data) {
            mIndicators.clear();
            mIndicators.addAll(data);
        }

        @Override
        public Fragment getItem(int i) {
            if (i < 0 || i > mIndicators.size()) {
                throw new IndexOutOfBoundsException("index " + i + " is out of range " + "0 and " + mIndicators.size());
            }
            return getContentFragment(mIndicators.get(i));
        }

        @Override
        public long getItemId(int position) {
            if (position <= 0 || position > mIndicators.size()) {
                return -1;
            }
            return mIndicators.get(position).getId();
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mIndicators.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            return fragment;
        }

        private Fragment getContentFragment(Indicator indicator) {
            switch (indicator.getId()) {
                case 0:
                    break;
            }
            ContentFragment fragment = new ContentFragment();
            fragment.setKey(indicator.getKey());
            return fragment;
        }
    }

    private class Indicator {
        private int mId;
        private String mKey;
        private String Text;

        public int getId() {
            return mId;
        }

        public void setId(int id) {
            this.mId = id;
        }

        public String getKey() {
            return mKey;
        }

        public void setKey(String key) {
            mKey = key;
        }

        public String getText() {
            return Text;
        }

        public void setText(String text) {
            Text = text;
        }
    }

    private List<Indicator> getTest() {
        List<Indicator> list = new ArrayList<Indicator>();
        for (int i = 0; i < 10; i++) {
            Indicator indicator = new Indicator();
            indicator.setId(i);
            indicator.setKey("key" + i);
            indicator.setText("title" + i);
            list.add(indicator);
        }
        return list;
    }

    private String[] getTitles() {
        String[] titles = new String[10];
        for (int i = 0; i < 10; i++) {
            titles[i] = "title" + i;
        }
        return titles;
    }
}
