package com.example.oosakikeishiro.nubesukoskip;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by oosakikeishiro on 15/06/19.
 */
public class GameSFV extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    SurfaceHolder surfaceHolder;
    int screen_width, screen_height;
    private long mTime = 0;          //一つ前の描画時刻

    private Resources res = this.getContext().getResources();

    private Bitmap star, arrowUp, arrowDown, hoge;
    private Bitmap[] player = new Bitmap[8];
    private Boolean gameState;
    private Thread mLooper;

    private int map[][] = {
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0}
    }; //0=空白、1=障害物、2=上矢印、3=星、4=下矢印

    private int uPosiX = 2, uPosiY = 5;
    private long ival = 300;
    private int muteki;


    public GameSFV(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        //ここから拡張実装
        //まずは画像読み込み
        star = BitmapFactory.decodeResource(res, R.drawable.fav);
        hoge = BitmapFactory.decodeResource(res, R.drawable.hoge);
        arrowUp = BitmapFactory.decodeResource(res, R.drawable.arrow_up);
        arrowDown = BitmapFactory.decodeResource(res, R.drawable.arrow_down);
        player[1] = BitmapFactory.decodeResource(res, R.drawable.player01);
        player[2] = BitmapFactory.decodeResource(res, R.drawable.player02);
        player[4] = BitmapFactory.decodeResource(res, R.drawable.player04);
        player[5] = BitmapFactory.decodeResource(res, R.drawable.player05);
        player[7] = BitmapFactory.decodeResource(res, R.drawable.player07);
        player[6] = BitmapFactory.decodeResource(res, R.drawable.player06);

        Log.d("Notice","読み込み完了");
        //

    }


    @Override
    public void run() {

        while (mLooper != null) {

            //描画処理
            doDraw();

            //位置更新処理

            //処理落ちによるスローモーションをさけるため現在時刻を取得
            long delta = System.currentTimeMillis() - mTime;
            Log.d("mtime", Long.toString(mTime));
            if (delta >= ival) {

                mTime = System.currentTimeMillis();


                //次の描画位置

                //上のmapを下にコピー
                for (int r = 5; r > 0; r--) {
                    for (int c = 0; c < 5; c++) {
                        map[c][r] = map[c][r - 1];
                    }
                }

                //最上段に新規アサイン
                map[0][(int) (Math.random() * 5)] = 1;
                map[0][(int) (Math.random() * 5)] = 2;
                if (Math.random() * 100 > 95) {
                    map[0][(int) (Math.random() * 5)] = 3;
                }

                if (Math.random() * 100 > 70) {
                    map[0][(int) (Math.random() * 5)] = 4;
                }

                if (muteki > 0) {
                    muteki--;
                }

                //当たり判定
                if (muteki == 0) {
                    switch (map[uPosiY][uPosiX]) {
                        case 1: //障害物と衝突
                            die();
                            break;

                        case 2: //上矢印と衝突
                            if (uPosiY <= 1) {
                                uPosiY--;
                            } else {
                                die();
                            }
                            break;

                        case 3: //星と衝突

                            break;

                        case 4: //下矢印と衝突
                            if (uPosiY >= 4) {
                                uPosiY++;
                            } else {
                                die();
                            }

                            break;


                    }
                }


            }
        }


    }

    private void die() {

    }

    //描画関数
    private void doDraw() {

        //描画処理
        Canvas cvs = surfaceHolder.lockCanvas();
        Paint paint = new Paint();
        /*
        c.drawBitmap(grass, 0, 0, paint);
        c.drawBitmap(goburin, 250, 150, paint);
        */
        cvs.drawColor(Color.WHITE);

        float blockWidth = screen_width / 5;
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 5; c++) {
                switch (map[c][r]) {
                    case 1:
                        cvs.drawBitmap(hoge, c * blockWidth, r * blockWidth, paint);
                        break;
                    case 2:
                        cvs.drawBitmap(arrowUp, c * blockWidth, r * blockWidth, paint);
                        break;
                    case 3:
                        cvs.drawBitmap(star, c * blockWidth, r * blockWidth, paint);
                        break;
                    case 4:
                        cvs.drawBitmap(arrowDown, c * blockWidth, r * blockWidth, paint);
                        break;
                }
            }
        }

        surfaceHolder.unlockCanvasAndPost(cvs);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screen_width = width;
        screen_height = height;
        //スレッド処理を開始
        if (mLooper != null) {
            screen_height = height;
            mTime = System.currentTimeMillis();
            mLooper.start();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceHolder = holder;
        mLooper = new Thread(this);
        mLooper.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mLooper = null;
    }


}
