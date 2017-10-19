
package com.autohome.emojilibrary.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;


import com.autohome.emojilibrary.adapter.EmojiPagerAdater;
import com.autohome.emojilibrary.manager.ExpressionUtil;

/**
 * @author lhc
 */
public class ExpressViewPager extends ViewPager {

	private EmojiPagerAdater mEmojiPagerAdapter;
//	private ViewPagerAdapter mExpressViewAdapter;
    private ExpressListener mExpressListener;
    private int mPageCount = 1;

    public ExpressViewPager(Context context) {
        super(context);
//        initExpress();
    }

    public ExpressViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
//        initExpress();
    }

    public void addExpressListener(ExpressListener l) {
        mExpressListener = l;
    }

//    public void initExpress() {
//        List<View> expressViewList = new ArrayList<View>();
//        mPageCount = ExpressionUtil.getExpressPageCount(ExpressionUtil.ptExpressPageNum);
//        for (int i = 1; i <= mPageCount; i++) {
//            GridView gridView = (GridView) LayoutInflater.from(getContext())
//                    .inflate(R.layout.layout_express_gridview, null);
//            gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
//            ExpressAdapter expressAdapter = new ExpressAdapter(getContext(), ExpressionUtil.getEmojiiconList(
//                            getContext(), i, ExpressionUtil.ptExpressPageNum));
//            gridView.setAdapter(expressAdapter);
//            expressViewList.add(gridView);
//            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                    if (arg2 == arg0.getAdapter().getCount() - 1) {
//                        if (mExpressListener != null) {
//                            mExpressListener.onExpressDelete(1);
//                        }
//                        return;
//                    }
//
//                    String name = (String) arg0.getAdapter().getItem(arg2);
//                    String desc = ExpressionUtil.sendExpressMap.get(name);
//                    if (mExpressListener != null) {
//                        mExpressListener.onExpressSelect(desc);
//                    }
//                }
//            });
//        }
//        mExpressViewAdapter = new ViewPagerAdapter(expressViewList);
//        setAdapter(mExpressViewAdapter);
//    }

    /**
     *
     * @param fm  FragmentManager
     * @param number   per page count
     */
    public void initExpress(FragmentManager fm,int number) {
        mPageCount = ExpressionUtil.getExpressPageCount(number);
        mEmojiPagerAdapter = new EmojiPagerAdater(fm, number);
        setAdapter(mEmojiPagerAdapter);
    }

    public int getPageCount() {
        return mPageCount;
    }

    public interface ExpressListener {
        public void onExpressSelect(String append);

        public void onExpressDelete(int length);
    }

}
