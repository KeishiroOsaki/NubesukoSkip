package com.example.oosakikeishiro.nubesukoskip;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


public class GameActivity extends ActionBarActivity implements View.OnTouchListener, View.OnClickListener {

    int imgSrcNum;
    SurfaceView sview;
    GameSFV gSFV;
    Handler sHandler;

    Runnable sr = new Runnable() {
        @Override
        public void run() {
            scoreUpdate();
            sHandler.post(this);
        }

        public void scoreUpdate() {
            TextView score_l = (TextView) findViewById(R.id.score_l);
            score_l.setText("SCORE:" + Integer.toString(gSFV.getnowscore()));
        }
    };

    private Button bt_reload, bt_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        Intent i = getIntent();
        imgSrcNum = Integer.parseInt(i.getStringExtra("src"));

        FrameLayout viewfw = (FrameLayout) findViewById(R.id.viewfw);
        //GameSFV view = (GameSFV) getLayoutInflater().inflate(R.layout.gsfv, null);
        // GameSFV gsfv = new GameSFV(getApplicationContext(),)

        //viewfw.addView(view);

        sview = (SurfaceView) new SurfaceView(this.getApplicationContext());
        gSFV = new GameSFV(this, sview, imgSrcNum);

        sview.setFocusable(true);
        // sview.setBackgroundColor(Color.WHITE);
        sview.setVisibility(View.VISIBLE);
        sview.setOnTouchListener(this);

        viewfw.addView(sview);

        sHandler = new Handler();
        sHandler.post(sr);
        Log.d("src", Integer.toString(imgSrcNum));

        bt_reload = (Button) findViewById(R.id.bt_reload);
        bt_share = (Button) findViewById(R.id.bt_share);
        bt_reload.setOnClickListener(this);
        bt_share.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        sHandler.removeCallbacks(sr);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // switch (v.getId()) {
        //   case R.id.viewfw:
        Log.d("touchX", String.valueOf(event.getX()));
        //        break;
        //  }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (gSFV.screen_width / 2 > event.getX()) {
                gSFV.player2left();
            } else {
                gSFV.player2right();
            }
        }

        return true;
    }


    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_reload:
                reload();
                break;
            case R.id.bt_share:
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, "あなたのスコアは" + Integer.toString(gSFV.getnowscore()) + "です。#nubesukoskip");
                    startActivity(intent);
                } catch (Exception e) {
                    Log.d("share機能", "Error");
                }
                break;

        }
    }
}
