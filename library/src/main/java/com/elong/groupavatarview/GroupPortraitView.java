package com.elong.groupavatarview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elong on 17-9-5.
 */
public class GroupPortraitView extends View {
    private final int MAX_COLUMN = 3;
    private final int MAX_ROW = 3;
    private List<Bitmap> mPortraits = new ArrayList<>(9);
    private Rect mSrc = new Rect();
    private Rect mDst = new Rect();
    private Paint mPaint = new Paint();
    private int mItemWidth;
    private int mItemHeight;
    private int mFirstRowColumn;    //column count in first row
    private int mRow;   //total row count
    private int mPaddingVertical;
    private int mPaddingHorizonal;
    private int mSpacing;
    public GroupPortraitView(Context context) {
        super(context);
    }

    public GroupPortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupPortraitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GroupPortraitView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mItemWidth = w;
        mItemHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mPortraits.size() > 0 && mItemWidth > 0 && mItemHeight > 0){
            int index = 0;
            for(Bitmap bitmap : mPortraits){
                mSrc.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
                calculateDst(index);
                canvas.drawBitmap(bitmap, mSrc, mDst, mPaint);
                ++index;
            }
        }
    }

    private void reset(){
        mPortraits.clear();
        invalidate();
    }

    private void addPortrait(Bitmap bitmap){
        mPortraits.add(bitmap);
        calculateRowColumnCount();
        invalidate();
    }

    private void calculateRowColumnCount(){
        int count = mPortraits.size();
        mFirstRowColumn = count % MAX_COLUMN;
        mRow = count / MAX_COLUMN + mFirstRowColumn > 0 ? 1 : 0;
    }

    private void calculateDst(int index){
        int beginRow = index < mFirstRowColumn ? 0 : (index + MAX_COLUMN - mFirstRowColumn) / MAX_COLUMN;
        int beginColumn = index < mFirstRowColumn ? index : (index + 1 - mFirstRowColumn - (beginRow - 1) * MAX_COLUMN);
        int beginX = mPaddingHorizonal + beginColumn * (mItemWidth + mSpacing);
        int beginY = mPaddingVertical + beginRow * (mItemHeight + mSpacing);
        mDst.set(beginX, beginY, beginX + mItemWidth, beginY + mItemHeight);
    }


}
