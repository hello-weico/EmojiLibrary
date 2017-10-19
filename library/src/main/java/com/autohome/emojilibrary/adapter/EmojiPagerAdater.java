package com.autohome.emojilibrary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.autohome.emojilibrary.EmojiconGridFragment;
import com.autohome.emojilibrary.emoji.Emojicon;
import com.autohome.emojilibrary.manager.ExpressionUtil;

import java.util.ArrayList;

/**
 * <p>表情面板Adapter</p>
 * Created by hufeng on 19/2/16.
 */
public class EmojiPagerAdater extends FragmentPagerAdapter {

    /**表情页总数*/
    private int mPageCount;
    /**每页表情数量*/
    private int mPerPageNumber;

    public EmojiPagerAdater(FragmentManager fm, int perPageCount) {
        super(fm);
        mPerPageNumber = perPageCount;
        mPageCount = ExpressionUtil.getExpressPageCount(perPageCount);
    }

    @Override
    public Fragment getItem(int position) {
        ArrayList<Emojicon> emojicons = ExpressionUtil.getEmojiiconList(position, mPerPageNumber);
        return EmojiconGridFragment.newInstance(emojicons, null);
    }

    @Override
    public int getCount() {
        return mPageCount;
    }
}
