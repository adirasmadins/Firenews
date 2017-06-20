package io.github.h911002.firenews.support.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.github.h911002.firenews.R;


public class IndicatedViewPager extends LinearLayout implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private IndicatorBar mIndicatorBar;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;
    private int mCurrentPosition;
    private HorizontalScrollView mWrapper;
    private int mMarginStart;
    private int mMarginEnd;

    public IndicatedViewPager(Context context) {
        super(context);
        init(context);
    }

    public IndicatedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IndicatedViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setDividerDrawable(getResources().getDrawable(R.drawable.divider_line_1px));
        setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        setOrientation(VERTICAL);
        mMarginStart = getResources().getDimensionPixelOffset(R.dimen.indicated_view_title_margin_left);
        mMarginEnd = getResources().getDimensionPixelOffset(R.dimen.indicated_view_title_margin_Right);

        RelativeLayout indicatorLayout = new RelativeLayout(context);
        LayoutParams indicatorParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                context.getResources().getDimensionPixelSize(R.dimen.indicated_bar_title_height));
        addView(indicatorLayout, indicatorParams);

        mWrapper = new HorizontalScrollView(context);
        mWrapper.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mWrapper.setHorizontalScrollBarEnabled(false);
        mWrapper.setId(R.id.indicator_wrapper);
        RelativeLayout.LayoutParams wrapParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        wrapParams.setMargins(mMarginStart, 0, mMarginEnd, 0);
        indicatorLayout.addView(mWrapper, wrapParams);


        ImageView arrow = new ImageView(context);
        arrow.setId(R.id.arrow_down_id);
        arrow.setImageResource(R.mipmap.ic_arrow_down_grey);
        RelativeLayout.LayoutParams arrowParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        arrowParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        arrowParams.addRule(RelativeLayout.CENTER_VERTICAL);
        arrowParams.setMargins(0, 0, mMarginStart, 0);
        indicatorLayout.addView(arrow, arrowParams);

        mIndicatorBar = new IndicatorBar(context);
        mIndicatorBar.setId(R.id.indicator_bar_id);
        mIndicatorBar.setBackgroundColor(getResources().getColor(R.color.indicator_background));
        mWrapper.addView(mIndicatorBar, new HorizontalScrollView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mViewPager = new ViewPager(context);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setId(R.id.view_pager_id);
        LayoutParams pagerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mViewPager, pagerParams);
    }

    public void setAdapter(PagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    public void setCurrentItem(int position) {
        setCurrentItem(position, false);
    }

    public void setCurrentItem(int position, boolean smoothScroll) {
        mCurrentPosition = position;
        mViewPager.setCurrentItem(position, smoothScroll);
        mIndicatorBar.setSelect(position);
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public void setTitleItems(String[] items) {
        mIndicatorBar.setItems(items);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
        mCurrentPosition = position;
        mIndicatorBar.postPageScrolled(position, positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        if (mListener != null) {
            mListener.onPageSelected(position);
        }
        mCurrentPosition = position;
        mIndicatorBar.setSelect(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
        if (state == 2) {
            mIndicatorBar.scrollToMiddle(mIndicatorBar.getChildAt(mViewPager.getCurrentItem()));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrow_down_id:
                //TODO
                default:
                break;
        }
    }

    private class IndicatorBar extends LinearLayout implements OnClickListener {
        private int selectPosition = 0;
        private int selectTextColor;
        private int unSelectTextColor;
        private int titleWidth;
        private int titlePadding;

        public IndicatorBar(Context context) {
            super(context);
            init(context);
        }

        private void init(Context context) {
            setOrientation(HORIZONTAL);
            setGravity(Gravity.CENTER_VERTICAL);
            Resources res = context.getResources();
            selectTextColor = res.getColor(R.color.default_title_select_color);
            unSelectTextColor = res.getColor(R.color.default_title_color);
            titleWidth = getResources().getDimensionPixelOffset(R.dimen.indicator_title_width);
            titlePadding = res.getDimensionPixelSize(R.dimen.indicated_view_padding);
            setPadding(titlePadding, 0, titlePadding, 0);
        }

        public void setItems(String[] its) {
            removeAllViews();
            if (its == null) {
                return;
            }

            for (int i = 0; i < its.length; i++) {
                TextView textView = new TextView(getContext());
                textView.setText(its[i]);
                textView.setGravity(Gravity.CENTER);
                textView.setTextAppearance(getContext(), R.style.IndicatedBarTextStyle);
                textView.setTag(i);
                textView.setOnClickListener(this);
                textView.setTextColor(i == selectPosition ? selectTextColor : unSelectTextColor);
                addView(textView, new LinearLayout.LayoutParams(titleWidth, ViewGroup.LayoutParams.MATCH_PARENT, 0));
            }
        }

        public void setSelect(int select) {
            selectPosition = select;
            for (int i = 0; i < getChildCount(); i++) {
                TextView child = (TextView) getChildAt(i);
                child.setTextColor(i == select ? selectTextColor : unSelectTextColor);
            }
            postInvalidate();
        }

        public void postPageScrolled(int position, float offset) {
            selectPosition = position;
            postInvalidate();
        }

        @Override
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
        }

        @Override
        public void onClick(View v) {
            if (v instanceof TextView) {
                int tag = (Integer) v.getTag();
                setCurrentItem(tag);
                scrollToMiddle(v);
            }
        }

        public void scrollToMiddle(View view) {
            int[] location = new int[2];
            view.getLocationOnScreen(location);

            WindowManager wm = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
            int screenWidth = wm.getDefaultDisplay().getWidth();
            int scrollDistance = (screenWidth + mMarginStart - mMarginEnd - titleWidth) / 2 - location[0];
            mWrapper.smoothScrollBy(-scrollDistance, 0);
        }
    }
}
