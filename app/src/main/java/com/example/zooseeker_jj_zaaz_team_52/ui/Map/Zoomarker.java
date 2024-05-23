package com.example.zooseeker_jj_zaaz_team_52.ui.Map;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.example.zooseeker_jj_zaaz_team_52.R;
import com.example.zooseeker_jj_zaaz_team_52.ZooData;

public class Zoomarker extends View {
    private final Paint paint = new Paint();
    private float posX = 0; // X position
    private float posY = 0; // Y position
    private String name = "MARKER_NAME";
    private Bitmap backgroundBitmap;
    ZooData.VertexInfo markerData;
    private OnZoomarkerClickListener clickListener;

    public Zoomarker(ZooData.VertexInfo value, Context context, AttributeSet attrs) {
        super(context, attrs);
        markerData = value;
        paint.setColor(Color.RED);

        // Load the background image
        backgroundBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background); // Replace with your drawable resource

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
        int desiredWidth = 50; // Replace with your desired size
        int desiredHeight = 50; // Replace with your desired size

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

        // Draw the background image
        if (backgroundBitmap != null) {
            canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
        }

        int radius = Math.min(width, height) / 2;


        // Draw the marker circle
        canvas.drawCircle(width / 2, height / 2, radius, paint);
    }

    private void init() {
        // Set the OnClickListener for this view
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onZoomarkerClick(markerData);
                }
            }
        });
    }

    public void setOnZoomarkerClickListener(OnZoomarkerClickListener listener) {
        this.clickListener = listener;
    }

    public interface OnZoomarkerClickListener {
        void onZoomarkerClick(ZooData.VertexInfo clickedMarkerData);
    }
}
