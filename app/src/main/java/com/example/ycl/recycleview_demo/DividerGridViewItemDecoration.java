package com.example.ycl.recycleview_demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class DividerGridViewItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    private int[] attrs = new int[]{
            android.R.attr.listDivider

    };


    /**
     * 构造函数初始化
     *
     * @param context
     */
    public DividerGridViewItemDecoration(Context context) {
        //从系统主题中获取attrs中的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();//实现对typedArray的回收
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        drawVertical(c, parent);//画垂直线
        drawHorizontal(c, parent);//画水平线方法
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        //绘制水平间隔线
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int right = child.getRight() + params.rightMargin + mDivider.getIntrinsicWidth();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        //绘制垂直的间隔线
        int chidCount = parent.getChildCount();//获取多少子条目
        for (int i = 0; i < chidCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            int top = child.getTop() + params.topMargin;
            int bottom = child.getBottom() + params.bottomMargin;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        //四个方向的偏移值
        int right = mDivider.getIntrinsicWidth();
        int bottom = mDivider.getIntrinsicHeight();
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        int itemPosition = params.getViewAdapterPosition();
        if (isLastColum(itemPosition, parent)) {
            right = 0;
        }
        if (isLastRow(itemPosition, parent)) {
            bottom = 0;

        }

        outRect.set(0, 0, right, bottom);

    }

    private boolean isLastColum(int itemPosition, RecyclerView parent) {
        int spanCount=getSpanCount(parent);
        if(spanCount!=-1){
            if((itemPosition+1)%spanCount==0){
                return true;
            }

        }
        return false;
    }

    /**
     * 是否是最后一行
     * @param itemPosition
     * @param parent
     * @return
     */
    private boolean isLastRow(int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        if(spanCount!=-1){
            int childCount=parent.getAdapter().getItemCount();
            int lastRowCount=childCount%spanCount;
            if(lastRowCount==0||lastRowCount<spanCount){
                return true;
            }

        }
        return false;
    }

    /**
     * 根据parent获取到列数
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        //判定layoutManager 是不是GridLayoutManager类的一个实例
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager lm = (GridLayoutManager) layoutManager;
            int spanCount = lm.getSpanCount();
            return spanCount;
        }
        return -1;
    }


}
