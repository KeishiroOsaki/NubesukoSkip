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

    private Rect mutekisrc;
    private Rect mutekidest;
    private Bitmap mutekip;
    SurfaceHolder surfaceHolder;
    int screen_width, screen_height;
    private long mTime = 0;          //一つ前の描画時刻

    private Resources res;

    Context context;
    private Bitmap star, arrowUp, arrowDown, hoge;
    // private Bitmap[] player = new Bitmap[8];
    private Bitmap player;
    private int player_no;
    private Bitmap death_credit;

    private Boolean gameState = true;
    private int turns = 0;
    private int vscore = 0;
    private int bscore = 0;

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
    private Rect psrc;
    private Rect deathsrc;
    private Rect deathdst;

    // posiUpTask timerTask = null;
    Timer mTimer = null;
    Handler mHandler = new Handler();
    Handler pHandler = new Handler();
    Runnable ru;


    public GameSFV(Context context, SurfaceView surfaceView, int playerNumber) {
        //super(context);

        this.context = context;
        this.player_no = playerNumber;
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
        /*
        player[1] = BitmapFactory.decodeResource(res, R.drawable.player01);
        player[2] = BitmapFactory.decodeResource(res, R.drawable.player02);
        player[4] = BitmapFactory.decodeResource(res, R.drawable.player04);
        player[5] = BitmapFactory.decodeResource(res, R.drawable.player05);
        player[7] = BitmapFactory.decodeResource(res, R.drawable.player07);
        player[6] = BitmapFactory.decodeResource(res, R.drawable.player06);
        */
        obImg = new Bitmap[]{null, hoge, arrowUp, star, arrowDown};

        switch (player_no) {
            case 1:
                player = BitmapFactory.decodeResource(res, R.drawable.player01);
                break;
            case 2:
                player = BitmapFactory.decodeResource(res, R.drawable.player02);
                break;
            case 4:
                player = BitmapFactory.decodeResource(res, R.drawable.player04);
                break;
            case 5:
                player = BitmapFactory.decodeResource(res, R.drawable.player05);
                break;
            case 6:
                player = BitmapFactory.decodeResource(res, R.drawable.player06);
                break;
            case 7:
                player = BitmapFactory.decodeResource(res, R.drawable.player07);
                break;
        }

        mutekip = BitmapFactory.decodeResource(res, R.drawable.player03);
        death_credit = BitmapFactory.decodeResource(res, R.drawable.shi);
        Log.d("Notice", "読み込み完了");
        //

        src = new Rect[5];
        src[1] = new Rect(0, 0, hoge.getWidth(), hoge.getHeight());
        src[2] = new Rect(0, 0, arrowUp.getWidth(), arrowUp.getHeight());
        src[3] = new Rect(0, 0, star.getWidth(), star.getHeight());
        src[4] = new Rect(0, 0, arrowDown.getWidth(), arrowDown.getHeight());
        dst = new Rect[6][5];

        psrc = new Rect(0, 0, player.getWidth(), player.getHeight());
        mutekisrc = new Rect(0, 0, mutekip.getWidth(), mutekip.getHeight());
        deathsrc = new Rect(0, 0, death_credit.getWidth(), death_credit.getHeight());


    }


    @Override
    public void run() {  //常時行う処理


        Log.d("チェックポイント", "while直下");

        //描画処理
        doDraw();


        mHandler.post(this);

    }

    private void die() {
        Log.d("チェックポイント", "突然の死");
        pHandler.removeCallbacks(ru);
        pHandler = null;
        // mHandler.removeCallbacks(this);
        gameState = false;
    }

    //描画関数
    private void doDraw() {


        Log.d("チェックポイント", "doDraw先頭");
        Log.d("使用SurfaceHolder", surfaceHolder.toString());
        //描画処理
        Canvas cvs = surfaceHolder.lockCanvas();

        Paint paint = new Paint();
        Paint paintf = new Paint();

        cvs.drawColor(Color.WHITE);

        paintf.setColor(Color.WHITE);
        paintf.setStyle(Paint.Style.FILL);
        if (gameState) {
            for (int r = 0; r < 6; r++) {
                for (int c = 0; c < 5; c++) {
                    if (map[r][c] != 0) {
                        cvs.drawBitmap(obImg[map[r][c]], src[map[r][c]], dst[r][c], paint);
                    } else {
                        cvs.drawRect(dst[r][c], paintf);
                    }
                }
            }

            if (muteki == 0) {
                cvs.drawBitmap(player, psrc, dst[uPosiY][uPosiX], paint);
            } else {
                cvs.drawBitmap(mutekip, mutekisrc, mutekidest, paint);
            }
        } else {
            cvs.drawBitmap(death_credit, deathsrc, deathdst, paint);
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
        mutekidest = new Rect(0, screen_height - screen_width, screen_width, screen_height);
        double death_mrate = screen_width / death_credit.getWidth();
        deathdst = new Rect(0, (int) ((screen_height / 2) - (death_credit.getHeight() * death_mrate)),
                screen_width, (int) ((screen_height / 2) + (death_credit.getHeight() * death_mrate)));

        deathdst = new Rect(0, 200, screen_width, 800);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceHolder = holder;
        isAttached = true;


        ru = new Runnable() {  //ivalごとに行う位置更新等の処理
            @Override
            public void run() {
                //次の描画位置


                //当たり判定
                if (muteki == 0) {
                    Log.d("状況", "無敵じゃないよ");

                    switch (map[uPosiY][uPosiX]) {
                        case 1: //障害物と衝突
                            die();
                            break;

                        case 2: //上矢印と衝突
                            if (uPosiY >= 1) {

                                Log.d("接触", "上と");
                                map[uPosiY][uPosiX] = 0;
                                uPosiY--;
                            } else {
                                //die();
                            }
                            break;

                        case 3: //星と衝突
                            vscore++;
                            muteki = 9;
                            map[uPosiY][uPosiX] = 0;
                            break;

                        case 4: //下矢印と衝突
                            if (uPosiY <= 4) {

                                map[uPosiY][uPosiX] = 0;
                                uPosiY++;
                            } else {
                                //die();
                            }

                            break;


                    }
                } else {
                    muteki--;
                }

                bscore++;
                Log.d("チェックポイント", "当たり判定終了");

                //上のmapを下にコピー
                for (int r = 5; r > 0; r--) {
                    for (int c = 0; c < 5; c++) {
                        map[r][c] = map[r - 1][c];
                    }
                }
                Log.d("チェックポイント", "map転写完了");


                //最上段に新規アサイン
                for (int c = 0; c < 5; c++) {
                    map[0][c] = 0;
                }

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

                doDraw();
                if (gameState) {
                    pHandler.postDelayed(this, ival);
                }
            }
        };

        // mHandler.post(this);
        // doDraw();
        if (gameState) {
            pHandler.postDelayed(ru, ival);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        //  mTimer.cancel();
        //
        isAttached = false;

        //  while (mLooper.isAlive()) {
        // }

        //mLooper = null;
        if (gameState) {
            pHandler.removeCallbacks(ru);
        }
        mHandler.removeCallbacks(this);
    }

    //左移動メソッド
    public void player2left() {
        if (uPosiX != 0) {
            uPosiX--;
            Log.d("移動", "左");
            doDraw();
        }
    }

    //右移動メソッド
    public void player2right() {
        if (uPosiX != 4) {
            uPosiX++;
            Log.d("移動", "右");
            doDraw();
        }
    }

    //上移動メソッド
    public void player2up() {
        uPosiY--;
        Log.d("移動", "上");
        doDraw();
    }

    //下移動メソッド
    public void player2down() {
        uPosiY++;
        Log.d("移動", "下");
        doDraw();
    }

    public int getnowscore() {
        //TextView score_l = (TextView)
        return bscore + vscore;
    }

}


