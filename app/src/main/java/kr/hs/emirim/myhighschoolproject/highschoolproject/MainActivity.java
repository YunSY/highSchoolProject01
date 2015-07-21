package kr.hs.emirim.myhighschoolproject.highschoolproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selectSchool(View v){
        int id = v.getId();
        Intent intent;
        switch (id)
        {
            case R.id.but_special:
                intent = new Intent(this, SpecialSchool.class);
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
                Toast.makeText(getApplication(), "다시 클릭해주세요", Toast.LENGTH_LONG);
        }
    }

    public void startMap(View v){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}
