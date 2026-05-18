package com.example.laborator11;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Map;

public class ColumnChartView extends View {
    private Map<String, Integer> data;
    private Paint barPaint;
    private Paint textPaint;

    public ColumnChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        barPaint = new Paint();
        barPaint.setColor(Color.BLUE);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30f);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setData(Map<String, Integer> data) {
        this.data = data;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data == null || data.isEmpty()) return;

        int width = getWidth();
        int height = getHeight();
        int padding = 100;
        int usableHeight = height - 2 * padding;
        int usableWidth = width - 2 * padding;

        int maxVal = 0;
        for (int val : data.values()) {
            if (val > maxVal) maxVal = val;
        }

        int n = data.size();
        int barWidth = usableWidth / n - 20;
        int x = padding + 10;

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            float barHeight = ((float) entry.getValue() / maxVal) * usableHeight;
            
            // Draw column
            canvas.drawRect(x, height - padding - barHeight, x + barWidth, height - padding, barPaint);
            
            // Draw label
            canvas.drawText(entry.getKey(), x + barWidth / 2f, height - padding + 40, textPaint);
            
            // Draw value
            canvas.drawText(String.valueOf(entry.getValue()), x + barWidth / 2f, height - padding - barHeight - 10, textPaint);

            x += barWidth + 20;
        }
    }
}
