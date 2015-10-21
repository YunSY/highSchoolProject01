package kr.hs.emirim.myhighschoolproject.highschoolproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

    Button but1, but2, but3, but4;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //지워도 되는 부분
        but1 = (Button)findViewById(R.id.but_special);
        but2 = (Button)findViewById(R.id.but_major);
        but3 = (Button)findViewById(R.id.but_free);
        but4 = (Button)findViewById(R.id.but_general);

        but1.setTextColor(Color.WHITE);
        but2.setTextColor(Color.WHITE);
        but3.setTextColor(Color.WHITE);
        but4.setTextColor(Color.WHITE);


        //종료 전
        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    public void selectSchool(View v){
        int id = v.getId();
        Intent intent;
        switch (id)
        {
            case R.id.but_special:
                intent = new Intent(this, Temp_Special.class);
                //intent = new Intent(this, SpecialSchool.class);
                startActivity(intent);
                break;
            case R.id.but_major:
                intent = new Intent(this, MajorSchool.class);
                startActivity(intent);
                break;
            case R.id.but_free:
                intent = new Intent(this, FreeSchool.class);
                startActivity(intent);
                break;
            case R.id.but_general:
                intent = new Intent(this, GeneralSchool.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(getApplication(), "올바른 값이 아닙니다", Toast.LENGTH_LONG);
        }
    }

    public void startMap(View v){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    public class BackPressCloseHandler {

        private long backKeyPressedTime = 0;
        private Toast toast;

        private Activity activity;

        public BackPressCloseHandler(Activity context) {
            this.activity = context;
        }

        public void onBackPressed() {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                showGuide();
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                activity.finish();
                toast.cancel();
            }
        }

        public void showGuide() {
            toast = Toast.makeText(activity,
                    "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
