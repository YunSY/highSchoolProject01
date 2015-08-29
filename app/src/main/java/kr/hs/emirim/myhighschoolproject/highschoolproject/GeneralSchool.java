package kr.hs.emirim.myhighschoolproject.highschoolproject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class GeneralSchool extends Activity {

    private static ArrayList<String> list_general;

    private static ArrayAdapter<String> Adapter1;
    private static ListView list1;

    int json_length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_general_menu);

        list_general = new ArrayList<String>();
        Adapter1 = new ArrayAdapter<String>(GeneralSchool.this, android.R.layout.simple_expandable_list_item_1, list_general);

        getPlaceJasonParsing();

        list1 = (ListView) findViewById(R.id.list_general);
        list1.setAdapter(Adapter1);
        list1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list1.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                TextView tv = (TextView)arg1;
                Toast.makeText(getApplicationContext(), tv.getText().toString(), Toast.LENGTH_LONG).show();


            }
        });

    }

    public void getPlaceJasonParsing() {
        String fileName = "highSchool.json";
        String result = "";

        try {


            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buff = new byte[size];
            is.read(buff);      //is에 있는 내용을 buff로 읽어옴
            is.close();
            result = new String(buff, "UTF-8");     //byte를 String으로

            JSONObject json = new JSONObject(result);
            JSONArray jArr = json.getJSONArray("data");    //테이블이름
            json_length = jArr.length();

            for (int i = 0; i < json_length; i++) {
                json = jArr.getJSONObject(i);
                list_general.add(json.getString("name"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
