package com.hxh.component.basicore.ui.scrollview;/**
 * Created by hxh on 2017/9/19.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 创建者：hxh
 * 时间：  2017/9/19
 * 描述：
 */
public class ObservableScrollView extends ScrollView{

    private ScrollViewListener scrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollViewChangeListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }


}
