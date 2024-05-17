package com.example.zooseeker_jj_zaaz_team_52.ui.Map;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import com.example.zooseeker_jj_zaaz_team_52.R;

public class Zoomarker extends View {
    private final Paint paint = new Paint();
    private float posX = 0; // X position
    private float posY = 0; // Y position
    private String name = "MARKER_NAME";

    private OnZoomarkerClickListener clickListener;

    public Zoomarker(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.RED);

        // Get the position attributes
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Zoomarker,
                0, 0);

        try {
            posX = a.getFloat(R.styleable.Zoomarker_posX, 0);
            posY = a.getFloat(R.styleable.Zoomarker_posY, 0);
        } finally {
            a.recycle();
        }

        init();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = 200; // Replace with your desired size
        int desiredHeight = 200; // Replace with your desired size

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int radius = Math.min(width, height) / 4;

        canvas.drawCircle(width / 2, height / 2, radius, paint);

    }

    private void init() {
        // Set the OnClickListener for this view
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onZoomarkerClick();
                }
            }
        });
    }

    public void setOnZoomarkerClickListener(OnZoomarkerClickListener listener) {
        this.clickListener = listener;
    }

    public interface OnZoomarkerClickListener {
        void onZoomarkerClick();
    }

}

