package io.github.h911002.firenews.support.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.github.h911002.firenews.R;

/**
 * Created by hexi on 15/12/2.
 */
public class CommonTitleBar extends RelativeLayout {
    private String leftActionText;
    private Drawable leftActionImage;
    private String rightActionText;
    private Drawable rightActionImage;
    private String title;
    private String subTitle;
    private View rootView;
    private View line;

    private TextView leftActionTextView;
    private ImageView leftActionImageView;

    public TextView getRightActionTextView() {
        return rightActionTextView;
    }

    private TextView rightActionTextView;

    public ImageView getRightActionImageView() {
        return rightActionImageView;
    }

    private ImageView rightActionImageView;
    private TextView titleView;
    private TextView subTitleView;
    private ViewGroup leftAction;
    private ViewGroup rightAction;

    private ViewGroup rightActionContainer;

    /**
     * action listener
     */
    public static class OnTitleClickListener {
        /**
         * left click method
         */
        public void onClickedLeftAction() {

        }

        /**
         * right click method
         */
        public void onClickedRightAction() {

        }
    }

    private OnTitleClickListener listener = new OnTitleClickListener();

    public void setOnTitleListener(OnTitleClickListener listener) {
        this.listener = listener;
    }

    public CommonTitleBar(Context context) {
        super(context);
        init(null, 0);
    }

    public CommonTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CommonTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        // Load attributes
        initAttrs(attrs, defStyleAttr);
        initView();
        refreshUI();
    }

    private void initAttrs(AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable
                .CommonTitleBar, defStyleAttr, 0);
        try {
            leftActionText = a.getString(R.styleable.CommonTitleBar_leftActionText);
            leftActionImage = a.getDrawable(R.styleable.CommonTitleBar_leftActionImage);
            rightActionText = a.getString(R.styleable.CommonTitleBar_rightActionText);
            rightActionImage = a.getDrawable(R.styleable.CommonTitleBar_rightActionImage);
            title = a.getString(R.styleable.CommonTitleBar_barTitle);
            subTitle = a.getString(R.styleable.CommonTitleBar_barSubTitle);
        } finally {
            a.recycle();
        }
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_common_title, this);
        this.rootView = findViewById(R.id.rl_common_title);
        this.leftActionTextView = (TextView) findViewById(R.id.tv_left_action);
        this.leftActionImageView = (ImageView) findViewById(R.id.iv_left_action);
        this.rightActionTextView = (TextView) findViewById(R.id.tv_right_action);
        this.rightActionImageView = (ImageView) findViewById(R.id.iv_right_action);
        this.titleView = (TextView) findViewById(R.id.tv_title);
        this.subTitleView = (TextView) findViewById(R.id.tv_sub_title);
        this.leftAction = (ViewGroup) findViewById(R.id.rl_left_action);
        this.rightAction = (ViewGroup) findViewById(R.id.rl_right_action);
        this.rightActionContainer = (ViewGroup) findViewById(R.id.fl_right_action_container);
        this.line = findViewById(R.id.divider_line);
    }

    /**
     * refresh ui
     */
    public void refreshUI() {
        boolean leftVisible = false;
        boolean rightVisible = false;
        if (!TextUtils.isEmpty(leftActionText)) {
            leftActionTextView.setVisibility(View.VISIBLE);
            leftActionTextView.setText(leftActionText);
            leftActionImageView.setVisibility(GONE);
            leftVisible = true;
        } else {
            leftActionTextView.setVisibility(View.GONE);
        }

        if (leftActionImage != null) {
            leftActionImageView.setImageDrawable(leftActionImage);
            leftActionImageView.setVisibility(VISIBLE);
            leftVisible = true;
        }

        if (!TextUtils.isEmpty(rightActionText)) {
            rightActionTextView.setVisibility(View.VISIBLE);
            rightActionTextView.setText(rightActionText);
            rightActionImageView.setVisibility(GONE);
            rightVisible = true;
        } else {
            rightActionTextView.setVisibility(View.GONE);
        }

        if (rightActionImage != null) {
            rightActionImageView.setImageDrawable(rightActionImage);
            rightActionImageView.setVisibility(VISIBLE);
            rightVisible = true;
        }

        if (!TextUtils.isEmpty(title)) {
            titleView.setText(title);
        }

        if (!TextUtils.isEmpty(subTitle)) {
            subTitleView.setText(subTitle);
            subTitleView.setVisibility(VISIBLE);
        } else {
            subTitleView.setVisibility(GONE);
        }

        if (leftVisible) {
            leftAction.setVisibility(View.VISIBLE);
            leftAction.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickedLeftAction();
                }
            });
        } else {
            leftAction.setVisibility(View.INVISIBLE);
        }

        if (rightVisible) {
            rightAction.setVisibility(View.VISIBLE);
            rightAction.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickedRightAction();
                }
            });
        } else {
            rightAction.setVisibility(View.INVISIBLE);
        }
    }

    public void setLeftTextAction(OnClickListener listener) {
        leftActionTextView.setOnClickListener(listener);
    }

    public void setLeftTextVisiable(int visiable) {
        leftActionTextView.setVisibility(visiable);
    }

    /**
     * add custom right action
     */
    public void addCustomRightAction(View customRightAction) {
        rightAction.setVisibility(GONE);
        rightActionContainer.addView(customRightAction);
    }

    /**
     * set sub title
     */
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        subTitleView.setText(subTitle);
        refreshUI();
    }

    /**
     * set title
     */
    public void setTitle(String title) {
        this.title = title;
        titleView.setText(title);
        refreshUI();
    }

    /**
     * set right action text
     */
    public void setRightActionText(String rightActionText) {
        this.rightActionText = rightActionText;
        rightActionTextView.setText(rightActionText);
        refreshUI();
    }

    /**
     * set right action text color
     */
    public void setRightActionTextColor(String color) {
        rightActionTextView.setTextColor(Color.parseColor(color));
        refreshUI();
    }

    /**
     * set left action text
     */
    public void setLeftActionText(String leftActionText) {
        this.leftActionText = leftActionText;
        leftActionTextView.setText(rightActionText);
        refreshUI();
    }

    /**
     * set right action image
     */
    public void setRightActionImageView(Drawable rightActionImage) {
        this.rightActionImage = rightActionImage;
        rightActionImageView.setImageDrawable(rightActionImage);
        refreshUI();
    }

    /**
     * set left action image
     */
    public void setLeftActionImageView(Drawable leftActionImage) {
        this.leftActionImage = leftActionImage;
        leftActionImageView.setImageDrawable(leftActionImage);
        refreshUI();
    }

    public void setBarBgColor(int colorId) {
        rootView.setBackgroundResource(colorId);
        if (colorId == R.color.white) {
            line.setVisibility(View.VISIBLE);
        } else {
            line.setVisibility(View.GONE);
        }
    }

    public void setTitleTextColorRes(int colorId) {
        titleView.setTextColor(getResources().getColor(colorId));
    }
}
