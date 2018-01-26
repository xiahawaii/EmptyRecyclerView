package com.xia.ervlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 描述：若列表为空，显示空背景
 * 作者：xia
 * 邮箱：weiyao.xia@gmail.com
 * 创建日期：2018/1/26 上午10:00
 */
public class EmptyRecyclerView extends RecyclerView {

    // 空背景、正常背景的缓存
    private Drawable emptyBackground;
    private Drawable normalBackground;

    /**
     * 构建函数
     */
    public EmptyRecyclerView(Context context) {
        this(context, null);
    }

    /**
     * 构建函数
     */
    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 构建函数
     */
    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // 获取空背景
        if (attrs != null) {
            TypedArray mTypedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EmptyRecyclerView, defStyle, 0);
            for (int i = 0; i < mTypedArray.getIndexCount(); i++) {
                int mIndex = mTypedArray.getIndex(i);
                if (mIndex == R.styleable.EmptyRecyclerView_emptyBackground) {
                    emptyBackground = mTypedArray.getDrawable(mIndex);
                }
            }
            mTypedArray.recycle();
        }

        // 获取正常背景
        normalBackground = getBackground();

        // 若无空背景，设置与正常背景相同
        if (emptyBackground == null)
            emptyBackground = normalBackground;
    }

    /**
     * Adapter数据监听
     */
    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkEmpty();
        }
    };


    @Override
    public void setAdapter(Adapter adapter) {

        // 去除旧Adapter的数据监听
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }

        // 设置新Adapter的数据监听
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }

        // 检测列表是否为空
        checkEmpty();
    }


    /**
     * 检测列表是否为空
     */
    private void checkEmpty() {

        if (getAdapter().getItemCount() == 0) {

            // 设置空背景
            setBackgroundDrawable(emptyBackground);
        } else {

            // 设置正常背景
            setBackgroundDrawable(normalBackground);
        }
    }
}
