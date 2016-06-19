package com.aplexos.gamestick;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by aplex on 16-6-19.
 */
public class GameStick extends SurfaceView implements SurfaceHolder.Callback{
    private String TAG = "GameStick";
    private SurfaceHolder holder;
    private GameStickThread gameStickThread = null;

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

        if (gameStickThread == null) {
            gameStickThread = new GameStickThread(holder);
            gameStickThread.start();
            Log.e(TAG, "again.");
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e(TAG, "surfaceCreadted.");

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e(TAG, "surfaceChanged.");

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e(TAG, "surfaceDestroyed.");

    }
}
