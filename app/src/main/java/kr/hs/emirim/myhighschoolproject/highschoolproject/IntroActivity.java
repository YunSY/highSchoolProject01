package kr.hs.emirim.myhighschoolproject.highschoolproject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.os.Handler;

import java.util.logging.LogRecord;


public class IntroActivity extends Activity {

    Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash);
        h = new Handler();
        h.postDelayed(irun, 4000);      //4초간 delay

    }

    Runnable irun = new Runnable(){
        public void run(){
            Intent i = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(i);
            finish();

            //fade in으로 시작하여 fade out으로 인트로 화면이 꺼지게 애니메이션 추가
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

}
