package com.bobo.something.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my_computer on 2017/6/20.
 */

public class FlowLo extends ViewGroup {
    public FlowLo(Context context) {
        this(context, null);
    }

    public FlowLo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);


        /*wrap_content*/

        int width = 0;
        int height = 0;

        /*每一行的宽高*/
        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childWidth > sizeWidth) {
                width = Math.max(width, lineWidth);/*得到最大的 width*/
                lineWidth = childWidth;/*重置lineWidth*/
                height += lineHeight;/*记录行高*/
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(childHeight, lineHeight);
            }

            if (i == childCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }


        }

        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width, modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);



    }

    /*存储view 按照行存储*/
    private List<List<View>> mAllViews = new ArrayList<>();
    /*每一行的高度*/
    private List<Integer> mLineHeight = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();

        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;


        List<View> mLineViews = new ArrayList<>();

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (childWidth + lineWidth > width) {
                mLineHeight.add(lineHeight);
                mAllViews.add(mLineViews);

                lineWidth = 0;


                mLineViews = new ArrayList<>();
            }

            lineWidth += childWidth;
            lineHeight = Math.max(childHeight, lineHeight);
            mLineViews.add(child);
        }


        mLineHeight.add(lineHeight);
        mAllViews.add(mLineViews);

        int mTop = 0;
        int mLeft = 0;
        int lineNum = mAllViews.size();
        for (int i = 0; i < lineNum; i++) {
            mLineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);
            for (int j = 0; j < mLineViews.size(); i++) {

                View child = mLineViews.get(j);
                if (child.getVisibility() == GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int childWidth = child.getMeasuredWidth() + lp.rightMargin + lp.leftMargin;

                int lc = mLeft + lp.leftMargin;
                int tc = mTop + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                child.layout(lc, tc, rc, bc);

                mLeft += childWidth;


            }
            mLeft = 0;
            mTop += lineHeight;


        }


    }



    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
