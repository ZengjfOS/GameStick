package com.aplexos.gamestick;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by aplex on 16-6-19.
 */
public class GameStick extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    private String TAG = "GameStick";

    private SurfaceHolder holder;
    private Thread drawThread = null;
    boolean drawThreadIsRunning = true;
    int centerX = 0;
    int centerY = 0;
    int radius = 300;
    Canvas canvas = null;
    Paint paint = null;

    private GameStickBackground gameStickBackground= null;
    private GameStickBall gameStickBall = null;

    public GameStick(Context context) {
        this(context, null);
    }

    public GameStick(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameStick(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        holder = this.getHolder();
        holder.addCallback(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch(action) {
            case MotionEvent.ACTION_DOWN :
                gameStickBall.reTouch = true;
                gameStickBall.x = (int)(event.getX());
                gameStickBall.y = (int)(event.getY());
                break;
            case MotionEvent.ACTION_MOVE :
                gameStickBall.x = (int)(event.getX());
                gameStickBall.y = (int)(event.getY());

                break;
            case MotionEvent.ACTION_POINTER_DOWN :
                break;
            case MotionEvent.ACTION_POINTER_UP :

                break;
            case MotionEvent.ACTION_UP :
                gameStickBall.reTouch = false;
                new Thread(gameStickBall).start();
                break;
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        /**
         * 线程最好在这里创建
         */
        drawThread = new Thread(this);
        drawThreadIsRunning = true;
        drawThread.start();

        paint = new Paint();

        centerX = this.getMeasuredWidth()/2;
        centerY = this.getMeasuredHeight()/2;
        gameStickBackground = new GameStickBackground(centerX, centerY, radius);
        gameStickBall = new GameStickBall(centerX, centerY, radius);

        Log.e(TAG, "surfaceCreadted.");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e(TAG, "surfaceChanged.");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            drawThreadIsRunning = false;
            drawThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "surfaceDestroyed.");
    }

    @Override
    public void run() {
        //int count = 0;
        while(drawThreadIsRunning) {
            try {
                synchronized (holder) {
                    canvas = holder.lockCanvas();
                    if (canvas != null) {
                        canvas.drawColor(Color.WHITE);
                        draw(canvas);
                    }
                }

                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);//结束锁定画图，并提交改变。
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawStickModule(canvas, paint);

    }

    public void drawStickModule(Canvas canvas, Paint paint) {
        drawStickBackground(canvas, paint);
        drawStickBall(canvas, paint);
        drawActionButton(canvas, paint);
    }

    public void drawStickBackground(Canvas canvas, Paint paint) {
        gameStickBackground.draw(canvas, paint);
    }

    public void drawStickBall(Canvas canvas, Paint paint) {
        gameStickBall.draw(canvas, paint);
    }

    public void drawActionButton(Canvas canvas, Paint paint) {

    }
}
