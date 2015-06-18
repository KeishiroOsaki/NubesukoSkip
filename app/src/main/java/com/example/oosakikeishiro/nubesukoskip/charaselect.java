package com.example.oosakikeishiro.nubesukoskip;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


@SuppressWarnings("ALL")
public class charaselect extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charaselect);

        //ボタンリスナーを登録
        ImageButton b_chr1 = (ImageButton)findViewById(R.id.b_chr1);
        ImageButton b_chr2 = (ImageButton)findViewById(R.id.b_chr2);
        ImageButton b_chr4 = (ImageButton)findViewById(R.id.b_chr4);
        ImageButton b_chr5 = (ImageButton)findViewById(R.id.b_chr5);
        ImageButton b_chr7 = (ImageButton)findViewById(R.id.b_chr7);
        ImageButton b_chr6 = (ImageButton)findViewById(R.id.b_chr6);
        b_chr1.setOnClickListener(this);
        b_chr2.setOnClickListener(this);
        b_chr4.setOnClickListener(this);
        b_chr5.setOnClickListener(this);
        b_chr7.setOnClickListener(this);
        b_chr6.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_charaselect, menu);
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
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                Intent intent = new Intent(this, game.class);
                startActivity(intent);
                break;
        }
    }
}
