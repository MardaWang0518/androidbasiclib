package com.hxh.component.basicore.recycle;/**
 * Created by hxh on 2017/8/23.
 */

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * 可变速度的LinerLayoutManger
 */
public class SpeedLinearLayoutManager extends LinearLayoutManager {
    private double speedRatio;

    public SpeedLinearLayoutManager(Context context) {
        super(context);
    }

    public SpeedLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }


    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int a = super.scrollHorizontallyBy((int)(speedRatio*dx), recycler, state);//屏蔽之后无滑动效果，证明滑动的效果就是由这个函数实现
        if(a == (int)(speedRatio*dx)){
            return dx;
        }
        return a;
    }

    public void setSpeedRatio(double speedRatio){
        this.speedRatio = speedRatio;
    }


}
