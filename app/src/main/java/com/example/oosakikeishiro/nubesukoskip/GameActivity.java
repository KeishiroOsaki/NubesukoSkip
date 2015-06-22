package com.example.oosakikeishiro.nubesukoskip;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;


public class GameActivity extends ActionBarActivity {

    int imgSrcNum;
    SurfaceView sview;
    GameSFV gSFV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        FrameLayout viewfw = (FrameLayout) findViewById(R.id.viewfw);
        //GameSFV view = (GameSFV) getLayoutInflater().inflate(R.layout.gsfv, null);
        // GameSFV gsfv = new GameSFV(getApplicationContext(),)

        //viewfw.addView(view);

        sview = (SurfaceView) new SurfaceView(this.getApplicationContext());
        gSFV = new GameSFV(this, sview);

        sview.setFocusable(true);
        sview.setBackgroundColor(Color.WHITE);
        sview.setVisibility(View.VISIBLE);

        viewfw.addView((SurfaceView) sview);

        Intent i = getIntent();
        imgSrcNum = Integer.parseInt(i.getStringExtra("src"));

        Log.d("src",Integer.toString(imgSrcNum));

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
}
