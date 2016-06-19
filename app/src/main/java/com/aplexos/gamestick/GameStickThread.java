package com.aplexos.gamestick;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by aplex on 16-6-19.
 */
public class GameStickThread extends Thread {

    String TAG = "GameStickThread";

    boolean isRunning = true;
    SurfaceHolder holder;
    Canvas canvas = null;

    public GameStickThread(SurfaceHolder holder) {
        this.holder = holder;
    }

    @Override
    public void run() {
        int count = 0;

        while(isRunning) {
            try {
                synchronized (holder) {
                    canvas = holder.lockCanvas();

                    if (canvas != null) {
                        canvas.drawColor(Color.BLACK);

                        Paint paint = new Paint();
                        paint.setColor(Color.WHITE);
                        Rect r = new Rect(100, 50, 300, 250);
                        canvas.drawRect(r, paint);
                        canvas.drawText("这是第" + (count++) + "秒", 100, 310, paint);
                    }
                }
                //Log.e(TAG, "zengjf: " + count);

                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);//结束锁定画图，并提交改变。
            }
        }
    }
}
