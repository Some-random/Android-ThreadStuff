package com.example.bob_jiang.asynctasktest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.TooManyListenersException;

/**
 * Created by Bob_JIANG on 10/14/2015.
 */

//Notice the difference between using looper and not using looper. Also, main thread has a default
// looper with it. If you define a thread class with handler in it, you have to define a looper.
// The sendmessage is sent to its caller, who will also implement handlemessage method.

public class SimpleLooper extends AppCompatActivity {

    private Button bt;
    private Handler mainHandler;
    private Handler childHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_looper);
        bt = (Button) findViewById(R.id.button1);
        final Task task = new Task();
        task.start();
        mainHandler = new Handler();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                childHandler.sendEmptyMessage(1014);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    class Task extends Thread {
        public void run() {
            Looper.prepare();
            childHandler = new Handler() {
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 1014:
                            Toast.makeText(getApplicationContext(), "OH YEAH", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            Looper.loop();
        }
    }
}