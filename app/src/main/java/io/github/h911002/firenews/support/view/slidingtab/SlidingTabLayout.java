package io.github.h911002.firenews.support.view.slidingtab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.support.utils.EmptyUtils;


public class SlidingTabLayout extends LinearLayout {
    private Context mContext;
    /* 默认的页卡颜色 */
    private final int DEFAULT_INDICATOR_COLOR = 0x7f050007;

    /* indicator 的高度 */
    private final int DEFAULT_INDICATOR_HEIGHT = 1;

    /* 默认title字体的大小 */
    private final int DEFAULT_TEXT_SIZE = 14;

    /* 默认title字体的颜色 */
    private final int DEFAULT_TEXT_COLOR = 0x7f050007;

    /* 默认padding */
    private final int DEFAULT_TEXT_PADDING = 12;

    /* 页卡的颜色 */
    private int mIndicatorLineColor = DEFAULT_INDICATOR_COLOR;

    private int mIndicatorContentPaddingLeft = DEFAULT_TEXT_PADDING;
    private int mIndicatorContentPaddingRight = DEFAULT_TEXT_PADDING;
    private int mIndicatorContentPaddingBottom = DEFAULT_TEXT_PADDING;
    private int mIndicatorContentPaddingTop = DEFAULT_TEXT_PADDING;

    /* 滑动指示器的高度 */
    private int mIndicatorHeight = DEFAULT_INDICATOR_HEIGHT;

    /* 页卡画笔 */
    private Paint mIndicatorPaint;

    /* 当前选中的页面位置 */
    private int mSelectedPosition;

    /* 页面的偏移量 */
    private float mSelectionOffset;

    private float INDICATOR_WIDHT = 0.35F;

    public SlidingTabLayout(Context context) {
        this(context, null);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /* 获取TypedArray */
        this.mContext = context;
        initResource(attrs);
        initView();
    }

    private void initResource(AttributeSet attrs) {
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable
                .SlidingTabLayout);
        /* 获取自定义属性的个数 */
        int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attrIndex = typedArray.getIndex(i);
            if (attrIndex == R.styleable.SlidingTabLayout_indicatorLineColor) {
                /* 获取页卡颜色值 */
                mIndicatorLineColor = typedArray.getColor(attrIndex, getResources().getColor(R
                        .color.white));
            } else if (attrIndex == R.styleable.SlidingTabLayout_indicatorContentPaddingBottom) {
                mIndicatorContentPaddingBottom = (int) typedArray.getDimension(attrIndex,
                        DEFAULT_INDICATOR_HEIGHT * getResources().getDisplayMetrics().density);
            } else if (attrIndex == R.styleable.SlidingTabLayout_indicatorContentPaddingTop) {
                mIndicatorContentPaddingTop = (int) typedArray.getDimension(attrIndex,
                        DEFAULT_INDICATOR_HEIGHT * getResources().getDisplayMetrics().density);
            } else if (attrIndex == R.styleable.SlidingTabLayout_indicatorContentPaddingLeft) {
                mIndicatorContentPaddingLeft = (int) typedArray.getDimension(attrIndex,
                        DEFAULT_INDICATOR_HEIGHT * getResources().getDisplayMetrics().density);
            } else if (attrIndex == R.styleable.SlidingTabLayout_indicatorContentPaddingRight) {
                mIndicatorContentPaddingRight = (int) typedArray.getDimension(attrIndex,
                        DEFAULT_INDICATOR_HEIGHT * getResources().getDisplayMetrics().density);
            } else if (attrIndex == R.styleable.SlidingTabLayout_indicatorHeight) {
                /* 获取页卡的高度 */
                mIndicatorHeight = (int) typedArray.getDimension(attrIndex,
                        DEFAULT_INDICATOR_HEIGHT * getResources().getDisplayMetrics().density);
            }
        }
        /* 释放TypedArray */
        typedArray.recycle();
    }

    private void initView() {
        setWillNotDraw(false);
        mIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mIndicatorPaint.setColor(mIndicatorLineColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getChildCount() == 0) {
            return;
        }
        final int height = getHeight();
//        ViewGroup tabView = (ViewGroup) getChildAt(mSelectedPosition);
//        View selectView = tabView.getChildAt(0);
//        int left = tabView.getLeft() + selectView.getLeft();
//        int right = tabView.getLeft() + selectView.getRight();
//        if (mSelectionOffset > 0) {
//            ViewGroup nextTabView = (ViewGroup) getChildAt(mSelectedPosition + 1);
//            View nextView = nextTabView.getChildAt(0);
//            left = left + (int) (mSelectionOffset * (tabView.getMeasuredWidth() - selectView
// .getLeft() + nextView.getLeft()));
//            right = right + (int) (mSelectionOffset * (tabView.getMeasuredWidth() - selectView
// .getRight() + nextView.getLeft() + nextView.getMeasuredWidth()));
//        }

        //固定滑动宽度
        if (getTabCount() > 0) {
            float width = getMeasuredWidth();
            float left = (mSelectedPosition + (1f - INDICATOR_WIDHT) / 2f) * width / getTabCount();
            float right = left + INDICATOR_WIDHT * width / getTabCount();
            if (mSelectionOffset > 0) {
                left = left + (int) (mSelectionOffset * width / getTabCount());
                right = left + INDICATOR_WIDHT * width / getTabCount();
            }

        /* 绘制滑动的页卡 */
            canvas.drawRect(left, height - mIndicatorHeight, right, height, mIndicatorPaint);
        }

    }


    /**
     * 设置数据，初始化SlidingTab， 在这个方法中为SlidingLayout设置 内容
     *
     * @param slidingItems
     */
    public void setSlidingItems(List<SlidingItem> slidingItems) {
        /* 先移除所以已经填充的内容 */
        mSlidingItems = slidingItems;
        removeAllViews();
        populateTabLayout();
    }

    public List<SlidingItem> getmSlidingItems() {
        return mSlidingItems;
    }

    /**
     * 填充layout，设置其内容
     */
    private void populateTabLayout() {
        for (int i = 0; i < mSlidingItems.size(); i++) {
            View tabView = createDefaultTabView(getContext(), mSlidingItems.get(i));
            tabView.setTag(i);
            tabView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnTabSwitchListener.onTabClicked((int) v.getTag(), mSlidingItems.get((int) v
                            .getTag()));
                }
            });
            addView(tabView);
        }
    }


    /**
     * 创建默认的TabItem
     *
     * @param context
     * @return
     */
    private View createDefaultTabView(Context context, SlidingItem item) {
        LinearLayout container = new LinearLayout(context);
        LayoutParams containerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        container.setPadding(mIndicatorContentPaddingLeft, mIndicatorContentPaddingTop,
                mIndicatorContentPaddingRight, mIndicatorContentPaddingBottom);
        container.setLayoutParams(containerParams);
        container.setGravity(Gravity.CENTER);
        View tabView = LayoutInflater.from(context).inflate(R.layout.view_sliding_title, null);


        TextView txtName = (TextView) tabView.findViewById(R.id.tabName);
        if (EmptyUtils.isEmpty(item.name)) {
            txtName.setVisibility(View.GONE);
        } else {
            txtName.setVisibility(View.VISIBLE);
            txtName.setText(item.name);
        }
        LayoutParams childParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        container.addView(tabView, childParam);
        return container;
    }

    public TextView getTitleView(int position) {
        ViewGroup tabView = (ViewGroup) getChildAt(position);
        return (TextView) tabView.findViewById(R.id.tabName);
    }

    public void onSliding(int position, float positionOffset) {
        if (mSelectedPosition != position) {
            getTitleView(position).setTextColor(getResources().getColor(R.color.colorPrimary));
            getTitleView(mSelectedPosition).setTextColor(getResources().getColor(R.color.black));
        }
        mSelectedPosition = position;
        mSelectionOffset = positionOffset;
        invalidate();
    }

    public void onSelected(int position) {
        mSelectedPosition = position;
        invalidate();
    }

    private List<SlidingItem> mSlidingItems;

    private OnTabSwitchListener mOnTabSwitchListener;

    public void setOnTabSwitchListener(OnTabSwitchListener onTabSwitchListener) {
        mOnTabSwitchListener = onTabSwitchListener;
    }

    public interface OnTabSwitchListener {
        void onTabClicked(int position, SlidingItem slidingItem);
    }

    public int getTabCount() {
        if (mSlidingItems == null || mSlidingItems.size() == 0) {
            return 0;
        } else {
            return mSlidingItems.size();
        }
    }

}
