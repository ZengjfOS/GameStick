package com.aplexos.gamestick;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

/**
 * Created by aplex on 16-6-19.
 */
public class GameStickBall implements Runnable{
    String TAG = "GameStickBall";
    int x;
    int y;
    int centerX = 0;
    int centerY = 0;
    int radius = 50;
    int outline = 50;
    boolean reTouch = true;
    int[] colors = new int[]{0x55f5f5f5, 0x88dfdfdf, 0x55f5f5f5};
    float[] positions = new float[]{0.2f, 0.6f, 1.0f };

    public GameStickBall(int centerX, int centerY, int outline) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.x = centerX;
        this.y = centerY;
        this.outline = outline/10*9;
    }

    public void draw(Canvas canvas, Paint paint) {
        int dx = x - centerX;
        int dy = y - centerY;

        if ((dx * dx + dy * dy)  < (this.outline * this.outline)) {
            paint.setStyle(Paint.Style.FILL);
            RadialGradient radialGradient = new RadialGradient(x, y, radius, colors, positions, Shader.TileMode.CLAMP);
            paint.setShader(radialGradient);
            canvas.drawCircle(x, y, radius, paint);
            paint.reset();
        } else {
            int newy = 0;
            int newx = 0;

            double angle = (Math.atan((0.0 + dy) / dx) / 3.14 * 180);

            if (dx <= 0) {
                newy = (int) ( -(outline * Math.sin(angle / 180 * Math.PI)) + centerY);
                newx = (int) ( -(outline * Math.cos(angle / 180 * Math.PI)) + centerX);
            } else {
                newy = (int) (outline * Math.sin(angle / 180 * Math.PI) + centerY);
                newx = (int) (outline * Math.cos(angle / 180 * Math.PI) + centerX);
            }

            paint.setStyle(Paint.Style.FILL);
            RadialGradient radialGradient = new RadialGradient(newx, newy, radius, colors, positions, Shader.TileMode.CLAMP);
            paint.setShader(radialGradient);
            canvas.drawCircle(newx, newy, radius, paint);
            paint.reset();

            paint.setColor(0x88dfdfdf);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
            canvas.drawLine(centerX, centerY, newx, newy, paint);
            paint.reset();
        }

    }

    @Override
    public void run() {
        int dx = 0;
        int dy = 0;
        do {
            dx = x - centerX;
            dy = y - centerY;

            if (Math.abs(dx) < 20 && Math.abs(dy) < 20) {
                x = centerX;
                y = centerY;
                break;
            }

            x -= dx / 3;
            y -= dy / 3;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (reTouch == false);
    }
}
