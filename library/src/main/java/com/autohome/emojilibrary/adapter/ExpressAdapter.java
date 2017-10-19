package com.autohome.emojilibrary.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.autohome.emojilibrary.emoji.EmojiconSpan;
import com.autohome.emojilibrary.R;
import com.autohome.emojilibrary.emoji.Emojicon;
import com.autohome.emojilibrary.manager.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/***
 * 继承ArrayAdapter采用TextView方式显示表情。表情面板里最后一个为删除功能键，支持长按连续删除，需要传入{@link View.OnTouchListener}接口。<br>
 * 另还有两种实现方式：
 * <p>1 emojicon_item里用TextView的子类com.autoclub.emojilibrary.EmojiconTextView，EmojiconTextView里实现了emojiSpan的处理逻辑，
 * {@link #getView(int, View, ViewGroup)}里只需要处理判断删除按钮逻辑。</p>
 * <p>2 R.id.emojicon_icon对应的是Imageview而不是TextView，重写getView不调用父类方法，才用setImageResource()方式。</p>
 */
public class ExpressAdapter extends ArrayAdapter<Emojicon> {

    private View.OnTouchListener mOnTouchListener;

    private final int mEmojiSize;
    private final int mFieldId;

    public ExpressAdapter(Context context, @LayoutRes int resource, @IdRes int textViewResourceId,
                          @NonNull List<Emojicon> data, View.OnTouchListener onTouchListener) {
        super(context, resource, textViewResourceId, data);
        mOnTouchListener=onTouchListener;
        mEmojiSize=ScreenUtils.dpToPx(context, 24);
        mFieldId =textViewResourceId;
    }

    public ExpressAdapter(Context context, ArrayList<Emojicon> data, View.OnTouchListener onTouchListener) {
        super(context, R.layout.emojicon_item, R.id.emojicon_icon, data);
        mOnTouchListener=onTouchListener;
        mEmojiSize=ScreenUtils.dpToPx(context, 24);
        mFieldId =R.id.emojicon_icon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView tv = (TextView) view.findViewById(mFieldId);

        Emojicon emojicon = getItem(position);
        EmojiconSpan emojiconSpan = new EmojiconSpan(getContext(), emojicon.getIcon(), mEmojiSize, EmojiconSpan.ALIGN_BOTTOM, mEmojiSize);
        SpannableStringBuilder ss = new SpannableStringBuilder(emojicon.getEmoji());
        ss.setSpan(emojiconSpan, 0, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ss);

        if (position == getCount() - 1 && mOnTouchListener != null) {
            view.setOnTouchListener(mOnTouchListener);
        }
        return view;
    }

    public void setBackSpaceTouchLisenter(View.OnTouchListener onTouchListener) {
        mOnTouchListener = onTouchListener;
    }

}
