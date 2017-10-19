
package com.autohome.emojilibrary.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    public List<View> mViews;

    public ViewPagerAdapter(List<View> views) {
        this.mViews = views;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView(mViews.get(position));
        ((ViewGroup) container).removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }
    
    public void updateView(List<View> mViews){
        this.mViews = mViews;
    }

}
