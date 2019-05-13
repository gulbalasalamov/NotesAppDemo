package com.bytebala.notes.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

public class LinedEditText extends AppCompatEditText {
    //Global variables
    private Rect mRect;
    private Paint mPaint;

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(0XFFFFD966);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Entire height EditText occupying
        int height = ((View)this.getParent()).getHeight();

        //Get line height
        int lineHeight = getLineHeight();

        int numberOfLines = height/lineHeight;

        //Rectangele object
        Rect r = mRect;
        Paint paint = mPaint;

        //It defines a baseline for where a line gonna be located on the screen. Initial line of start
        //Pass rectange object to getLineBounds method it is setting properties to rectangle object
        //It defines where it lives. To define the position and space. Left most and right most
        int baseline = getLineBounds(0,r);

        for (int i = 0 ; i < numberOfLines; i++) {
            canvas.drawLine(r.left,baseline + 1 , r.right, baseline + 1, paint);
            baseline += lineHeight;
        }

        super.onDraw(canvas);
    }
}
