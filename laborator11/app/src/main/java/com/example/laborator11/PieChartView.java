package com.example.laborator11;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Map;

public class PieChartView extends View {
    private Map<String, Integer> data;
    private Paint paint;
    private RectF rectF = new RectF();
    private int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA};

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setData(Map<String, Integer> data) {
        this.data = data;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data == null || data.isEmpty()) return;

        float total = 0;
        for (int val : data.values()) total += val;

        float startAngle = 0;
        float padding = 100;
        rectF.set(padding, padding, getWidth() - padding, getWidth() - padding);
        
        int i = 0;
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            float sweepAngle = (entry.getValue() / total) * 360;
            paint.setColor(colors[i % colors.length]);
            canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);
            
            // Text labeling
            paint.setColor(Color.BLACK);
            paint.setTextSize(40f);
            float medianAngle = startAngle + sweepAngle / 2f;
            double radius = (getWidth() - 2 * padding) / 3f;
            float centerX = getWidth() / 2f;
            float centerY = (rectF.top + rectF.bottom) / 2f;
            
            double x = centerX + radius * Math.cos(Math.toRadians(medianAngle));
            double y = centerY + radius * Math.sin(Math.toRadians(medianAngle));

            canvas.drawText(entry.getKey(), (float)x, (float)y, paint);

            startAngle += sweepAngle;
            i++;
        }
    }
}
