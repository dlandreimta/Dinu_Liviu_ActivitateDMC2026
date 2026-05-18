package com.example.laborator11;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Map;

public class BarChartView extends View {
    private Map<String, Integer> data;
    private Paint barPaint;
    private Paint textPaint;

    public BarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        barPaint = new Paint();
        barPaint.setColor(Color.BLUE);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(35f);
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
        int padding = 120;
        int usableWidth = width - 2 * padding;
        int usableHeight = height - 2 * padding;

        int maxVal = 0;
        for (int val : data.values()) {
            if (val > maxVal) maxVal = val;
        }

        int n = data.size();
        int barHeight = (usableHeight / n) - 20;
        int y = padding;

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            float barWidth = ((float) entry.getValue() / maxVal) * usableWidth;

            // Draw horizontal bar
            canvas.drawRect(padding, y, padding + barWidth, y + barHeight, barPaint);

            // Draw label
            textPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(entry.getKey(), padding - 10, y + barHeight / 2f + 10, textPaint);

            // Draw value
            textPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(String.valueOf(entry.getValue()), padding + barWidth + 10, y + barHeight / 2f + 10, textPaint);

            y += barHeight + 20;
        }
    }
}
