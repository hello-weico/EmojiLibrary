/*
 * Copyright 2014 Hieu Rocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.autohome.emojilibrary;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.autohome.emojilibrary.adapter.ExpressAdapter;
import com.autohome.emojilibrary.emoji.Emojicon;

import java.util.ArrayList;


/***
 * 用于每页表情显示的Fragment，默认是一行7列，如需修改请编辑{@link com.autoclub.emojilibrary.R.layout.emojicon_grid}布局修改android:numColumns="7"属性。<br>
 * 每页表情数为R*C-1，1为删除按钮。
 * @author Hieu Rocker (rockerhieu@gmail.com)
 */
public class EmojiconGridFragment extends Fragment implements AdapterView.OnItemClickListener {
    private OnEmojiconClickedListener mOnEmojiconClickedListener;
    private EmojiconRecents mRecents;
    private ArrayList<Emojicon> mData;

    private static final String EMOJICONS_KEY = "emojicons";

    /**
     *
     * @param emojicons
     * @param recents  最近使用表情管理接口
     * @return
     */
    public static EmojiconGridFragment newInstance(ArrayList<Emojicon> emojicons, EmojiconRecents recents) {
        EmojiconGridFragment emojiGridFragment = new EmojiconGridFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(EMOJICONS_KEY, emojicons);
        emojiGridFragment.setArguments(args);
        emojiGridFragment.setRecents(recents);
        return emojiGridFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.emojicon_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        GridView gridView = (GridView) view.findViewById(R.id.Emoji_GridView);
        Bundle bundle = getArguments();
        ArrayList<Emojicon> parcels=null;
        if (bundle == null) {
            parcels = savedInstanceState.getParcelableArrayList(EMOJICONS_KEY);
        } else {
            parcels = bundle.getParcelableArrayList(EMOJICONS_KEY);
        }
        if (parcels!=null) {
            mData = parcels;
        }

        gridView.setAdapter(new ExpressAdapter(view.getContext(), parcels, new RepeatListener(mOnEmojiconClickedListener)));
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EMOJICONS_KEY, mData);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnEmojiconClickedListener) {
            mOnEmojiconClickedListener = (OnEmojiconClickedListener) activity;
        } else if (getParentFragment() instanceof OnEmojiconClickedListener) {
            mOnEmojiconClickedListener = (OnEmojiconClickedListener) getParentFragment();
        } else {
            throw new IllegalArgumentException(activity + " must implement interface " + OnEmojiconClickedListener.class.getSimpleName());
        }
        if (this instanceof EmojiconRecents) {
            mRecents = (EmojiconRecents)this;
        }
    }

    @Override
    public void onDetach() {
        mOnEmojiconClickedListener = null;
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mOnEmojiconClickedListener != null) {
            if (mData.size() - 1 == position) {
                mOnEmojiconClickedListener.onEmojiconBackspaceClicked(view);
            } else {
                mOnEmojiconClickedListener.onEmojiconClicked((Emojicon) parent.getItemAtPosition(position));
            }
        }
        if (mRecents != null) {
            mRecents.addRecentEmoji(view.getContext(), ((Emojicon) parent
                    .getItemAtPosition(position)));
        }
    }

    private void setRecents(EmojiconRecents recents) {
        mRecents = recents;
    }

    /**
     * A class, that can be used as a TouchListener on any view (e.g. a Button).
     * It cyclically runs a clickListener, emulating keyboard-like behaviour. First
     * click is fired immediately, next before initialInterval, and subsequent before
     * normalInterval.
     * <p/>
     * <p>Interval is scheduled before the onClick completes, so it has to run fast.
     * If it runs slow, it does not generate skipped onClicks.
     * 长按一直删除
     */
    class RepeatListener implements View.OnTouchListener {

        private Handler handler = new Handler();

        private final int initialInterval;
        private final int normalInterval;
        private final OnEmojiconClickedListener clickListener;

        private View downView;

        private Runnable handlerRunnable = new Runnable() {
            @Override
            public void run() {
                if (downView == null) {
                    return;
                }
                handler.removeCallbacksAndMessages(downView);
                handler.postAtTime(this, downView, SystemClock.uptimeMillis() + normalInterval);
                if (clickListener != null) {
                    clickListener.onEmojiconBackspaceClicked(downView);
                }
            }
        };


        public RepeatListener(@NonNull OnEmojiconClickedListener clickListener) {
            this(500, 100, clickListener);
        }

        /**
         * @param initialInterval The interval before first click event
         * @param normalInterval  The interval before second and subsequent click
         *                        events
         * @param clickListener   The OnClickListener, that will be called
         *                        periodically
         */
        public RepeatListener(int initialInterval, int normalInterval, @NonNull OnEmojiconClickedListener clickListener) {
//            if (clickListener == null)
//                throw new IllegalArgumentException("null runnable");
            if (initialInterval < 0 || normalInterval < 0)
                throw new IllegalArgumentException("negative interval");

            this.initialInterval = initialInterval;
            this.normalInterval = normalInterval;
            this.clickListener = clickListener;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downView = view;
                    handler.removeCallbacks(handlerRunnable);
                    handler.postAtTime(handlerRunnable, downView, SystemClock.uptimeMillis() + initialInterval);
                    if (clickListener != null) {
                        clickListener.onEmojiconBackspaceClicked(view);
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                    handler.removeCallbacksAndMessages(downView);
                    downView = null;
                    return true;
            }
            return false;
        }
    }

    /**表情面板点击监听回调*/
    public interface OnEmojiconClickedListener {
        /**表情点击回调*/
        void onEmojiconClicked(Emojicon emojicon);
        /**backspace点击回调*/
        void onEmojiconBackspaceClicked(View view);
    }

}
