package com.autohome.emojilibrary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.autohome.emojilibrary.R;

/**
 * 合并emoji viewpager和pager indicators。
 * Created by hufeng on 24/2/16.
 */
public class EmojiLinearLayout extends LinearLayout {

    public EmojiLinearLayout(Context context) {
        super(context);
        init(context, null);
    }

    public EmojiLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EmojiLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EmojiLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        setGravity(Gravity.BOTTOM);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EmojiLinearLayout);
        boolean indicatorTop = a.getBoolean(R.styleable.EmojiLinearLayout_indicator_top, false);
        a.recycle();

        //add com.autoclub.emojilibrary.CirclePageIndicator top
        if (indicatorTop) {
            addIndicatorView(context);
            addEmojiPagers(context);
        } else {
        //add com.autoclub.emojilibrary.ExpressViewPager top
            addEmojiPagers(context);
            addIndicatorView(context);
        }
    }

    private void addIndicatorView(Context context) {
        inflate(context, R.layout.emojicon_page_indicator_layout, this);
    }

    private void addEmojiPagers(Context context) {
        inflate(context, R.layout.emojicon_viewpager_layout, this);
    }
}
