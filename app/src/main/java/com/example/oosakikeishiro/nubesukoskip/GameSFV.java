package com.example.oosakikeishiro.nubesukoskip;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Timer;


/**
 * Created by oosakikeishiro on 15/06/19.
 */
public class GameSFV /*extends SurfaceView */ implements Runnable, SurfaceHolder.Callback {

    SurfaceHolder surfaceHolder;
    int screen_width, screen_height;
    private long mTime = 0;          //一つ前の描画時刻

    private Resources res;

    Context context;
    private Bitmap star, arrowUp, arrowDown, hoge;
    private Bitmap[] player = new Bitmap[8];
    private Boolean gameState = true;
    private int turns = 0;
    private int addscore = 0;

    private Thread mLooper;

    boolean isAttached;


    int blockWidth;
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

    private Rect src[], dst[][];
    private Bitmap[] obImg;

    // posiUpTask timerTask = null;
    Timer mTimer = null;
    Handler mHandler = new Handler();


    public GameSFV(Context context, SurfaceView surfaceView) {
        //super(context);

        this.context = context;
        //setFocusable(true);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        res = context.getResources();
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
        obImg = new Bitmap[]{null, hoge, arrowUp, star, arrowDown};

        Log.d("Notice", "読み込み完了");
        //

        src = new Rect[5];
        src[1] = new Rect(0, 0, hoge.getWidth(), hoge.getHeight());
        src[2] = new Rect(0, 0, arrowUp.getWidth(), arrowUp.getHeight());
        src[3] = new Rect(0, 0, star.getWidth(), star.getHeight());
        src[4] = new Rect(0, 0, arrowDown.getWidth(), arrowDown.getHeight());
        dst = new Rect[6][5];


    }


    @Override
    public void run() {  //ivalごとに行う処理

        //while (/*mLooper != null*/ /*isAttached*/ true) {

            Log.d("チェックポイント", "while直下");

            //描画処理
        doDraw();

            //位置更新処理

          /*
            //処理落ちによるスローモーションをさけるため現在時刻を取得
            long delta = System.currentTimeMillis() - mTime;
            Log.d("mtime", Long.toString(mTime));
            Log.d("delta", Long.toString(delta));
            if (delta >= ival) {

                mTime = System.currentTimeMillis();


                //次の描画位置

                //上のmapを下にコピー
                for (int r = 5; r > 0; r--) {
                    for (int c = 0; c < 5; c++) {
                        map[r][c] = map[r - 1][c];
                    }
                }
                Log.d("チェックポイント", "map転写完了");


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

                Log.d("チェックポイント", "上段障害物生成完了");

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
                            if (uPosiY <= 4) {
                                uPosiY++;
                            } else {
                                die();
                            }

                            break;


                    }
                }
                Log.d("チェックポイント", "当たり判定終了");

                //doDraw();
            }*/


        // }

        //次の描画位置

        //上のmapを下にコピー
        for (int r = 5; r > 0; r--) {
            for (int c = 0; c < 5; c++) {
                map[r][c] = map[r - 1][c];
            }
        }
        Log.d("チェックポイント", "map転写完了");


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

        Log.d("チェックポイント", "上段障害物生成完了");

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
                    addscore++;
                    break;

                case 4: //下矢印と衝突
                    if (uPosiY <= 4) {
                        uPosiY++;
                    } else {
                        die();
                    }

                    break;


            }
        }
        Log.d("チェックポイント", "当たり判定終了");


        doDraw();



        mHandler.postDelayed(this, ival);


    }

    private void die() {
        Log.d("チェックポイント", "突然の死");
        gameState = false;
    }

    //描画関数
    private void doDraw() {

        Log.d("チェックポイント", "doDraw先頭");
        Log.d("使用SurfaceHolder", surfaceHolder.toString());
        //描画処理
        Canvas cvs = surfaceHolder.lockCanvas();
        Paint paint = new Paint();

        cvs.drawColor(Color.WHITE);



        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 5; c++) {
                /*
                switch (map[r][c]) {
                    case 1:
                        cvs.drawBitmap(hoge, src[], r * blockWidth, paint);
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
                */
                if (map[r][c] != 0) {
                    cvs.drawBitmap(obImg[map[r][c]], src[map[r][c]], dst[r][c], paint);
                }
            }
        }


        Log.d("チェックポイント", "描画完了");
        surfaceHolder.unlockCanvasAndPost(cvs);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screen_width = width;
        screen_height = height;


        blockWidth = screen_width / 5;

        for (int c = 0; c < 5; c++) {
            for (int r = 0; r < 6; r++) {
                dst[r][c] = new Rect(c * blockWidth, r * blockWidth, (c + 1) * blockWidth, (r + 1) * blockWidth);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceHolder = holder;
       /* timerTask = new posiUpTask();
        mTimer = new Timer(true);*/
        // mLooper = new Thread(this);
        isAttached = true;


        doDraw();

        mHandler.postDelayed(this, ival);
        // mTimer.schedule(timerTask, 0, 300);
        //  mLooper.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        //  mTimer.cancel();
        //
        isAttached = false;

        //  while (mLooper.isAlive()) {
        // }

        //mLooper = null;

        mHandler.removeCallbacks(this);
    }


    /*
    class posiUpTask extends TimerTask {

        @Override
        public void run() {
            //次の描画位置

            //上のmapを下にコピー
            for (int r = 5; r > 0; r--) {
                for (int c = 0; c < 5; c++) {
                    map[r][c] = map[r - 1][c];
                }
            }
            Log.d("チェックポイント", "map転写完了");


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

            Log.d("チェックポイント", "上段障害物生成完了");

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
                        addscore++;
                        break;

                    case 4: //下矢印と衝突
                        if (uPosiY <= 4) {
                            uPosiY++;
                        } else {
                            die();
                        }

                        break;


                }
            }
            Log.d("チェックポイント", "当たり判定終了");


            //doDraw(surfaceHolder);
        }
    }

    */
}


