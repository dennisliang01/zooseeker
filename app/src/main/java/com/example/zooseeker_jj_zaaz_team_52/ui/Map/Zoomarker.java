package com.example.zooseeker_jj_zaaz_team_52.ui.Map;

import static org.apache.commons.lang3.ClassUtils.getPackageName;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.android.material.imageview.ShapeableImageView;
import com.example.zooseeker_jj_zaaz_team_52.R;
import com.example.zooseeker_jj_zaaz_team_52.ZooData;

public class Zoomarker extends LinearLayout {
    private Bitmap backgroundBitmap, placeholderImage;
    ZooData.VertexInfo markerData;
    private OnZoomarkerClickListener clickListener;
    private ShapeableImageView mapIcon;

    public Zoomarker(Context context, ZooData.VertexInfo info, double scale, boolean selected) {
        super(context);
        markerData = info;
        init(context);
        mapIcon = findViewById(R.id.zoomarkericon);
        ViewGroup.LayoutParams layoutParams = mapIcon.getLayoutParams();
        layoutParams.width = (int) (50.0 * scale);  // width in dp
        layoutParams.height = (int) (50.0 * scale);  // height in dp
        mapIcon.setLayoutParams(layoutParams);
        int resId = getResources().getIdentifier("@drawable/" + markerData.id, "drawable", getContext().getPackageName());
        mapIcon.setImageResource(resId);
        if(selected) {
            ColorStateList strokeColor = ColorStateList.valueOf(getResources().getColor(R.color.white));
            mapIcon.setStrokeColor(strokeColor);
        }
    }

    public void setPlanned() {
        ColorStateList strokeColor = ColorStateList.valueOf(getResources().getColor(R.color.red));
        mapIcon.setStrokeColor(strokeColor);
        this.invalidate();
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.zoomarker, this, true);
        init();
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
