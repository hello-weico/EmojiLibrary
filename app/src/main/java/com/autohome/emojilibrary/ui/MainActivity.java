package com.autohome.emojilibrary.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.autohome.emojilibrary.view.CirclePageIndicator;
import com.autohome.emojilibrary.view.EmojiconEditText;
import com.autohome.emojilibrary.EmojiconGridFragment;
import com.autohome.emojilibrary.view.ExpressViewPager;
import com.autohome.emojilibrary.R;
import com.autohome.emojilibrary.emoji.Emojicon;
import com.autohome.emojilibrary.manager.ExpressionUtil;

/***
 * 表情使用实例，需要实现{@link EmojiconGridFragment.OnEmojiconClickedListener}监听表情点击操作。
 */
public class MainActivity extends AppCompatActivity implements EmojiconGridFragment.OnEmojiconClickedListener {

    EmojiconEditText mEditEmojicon;
//    EmojiconTextView mTxtEmojicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditEmojicon = (EmojiconEditText) findViewById(R.id.editEmojicon);
//        mTxtEmojicon = (EmojiconTextView) findViewById(R.id.txtEmojicon);
//        mEditEmojicon.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mTxtEmojicon.setText(s);
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        // 表情面板 start
        ExpressViewPager expressViewPager = (ExpressViewPager) findViewById(R.id.emoji_viewpager);
        //r*c-1
        expressViewPager.initExpress(getSupportFragmentManager(), 4 * 7 - 1);
        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.emoji_circle_indicator);
        circlePageIndicator.setViewPager(expressViewPager);
        // 表情面板 end
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        ExpressionUtil.input(mEditEmojicon, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        ExpressionUtil.backspace(mEditEmojicon);
    }
}
