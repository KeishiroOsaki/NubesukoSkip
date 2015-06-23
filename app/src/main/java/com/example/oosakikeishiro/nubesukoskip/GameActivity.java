package com.example.oosakikeishiro.nubesukoskip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;


public class GameActivity extends ActionBarActivity implements View.OnTouchListener {

    int imgSrcNum;
    SurfaceView sview;
    GameSFV gSFV;

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


        Log.d("src", Integer.toString(imgSrcNum));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
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
}
