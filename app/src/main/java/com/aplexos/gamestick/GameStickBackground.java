package com.aplexos.gamestick;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

/**
 * Created by aplex on 16-6-19.
 */
public class GameStickBackground {
    int x;
    int y;
    int radius = 300;
    int[] colors = new int[]{0x00f5f5f5, 0x88dfdfdf, 0x00f5f5f5};
    float[] positions = new float[]{0.8f, 0.9f, 1.0f };

    public GameStickBackground(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        RadialGradient radialGradient = new RadialGradient(x, y, radius, colors, positions, Shader.TileMode.CLAMP);
        paint.setShader(radialGradient);
        canvas.drawCircle(x, y, radius, paint);
        paint.reset();

        paint.setColor(0x88dfdfdf);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        canvas.drawLine(x - radius/12, y, x + radius/12, y, paint);
        canvas.drawLine(x, y - radius/12, x, y + radius/12, paint);
        paint.reset();
    }
}
