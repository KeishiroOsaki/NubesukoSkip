package com.example.oosakikeishiro.nubesukoskip;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by oosakikeishiro on 15/06/19.
 */
public class GameSFV extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    SurfaceHolder surfaceHolder;
    Thread thread;
    int screen_width, screen_height;

    public GameSFV(Context context , AttributeSet attrs) {
        super(context , attrs);
        setFocusable(true);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }


    @Override
    public void run() {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screen_width = width;
        screen_height = height;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
    }


}
