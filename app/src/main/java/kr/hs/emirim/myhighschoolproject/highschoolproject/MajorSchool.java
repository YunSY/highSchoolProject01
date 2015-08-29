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
import android.widget.TabHost;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;


public class MajorSchool extends Activity {

    private static ArrayList<String> list_major;

    private static ArrayAdapter<String> Adapter1;
    private static ListView list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_major_menu);

        list_major = new ArrayList<String>();
        xmlParsing();
        Adapter1 = new ArrayAdapter<String>(MajorSchool.this, android.R.layout.simple_expandable_list_item_1, list_major);

        list1 = (ListView) findViewById(R.id.list_major);
        list1.setAdapter(Adapter1);
        list1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list1.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                /*Cursor c = (Cursor)list1.getItemAtPosition(arg2);
                String memoname = c.getString(arg2).toString();*/
                TextView tv = (TextView)arg1;
                Intent intent = new Intent(MajorSchool.this, Information.class);
                intent.putExtra("select", tv.getText().toString());
                intent.putExtra("type", "특성화고");
                startActivity(intent);


            }
        });
    }


    public void xmlParsing() {
        XmlPullParser parser = null;
        parser = getResources().getXml(R.xml.major);

        boolean name = false;

        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {

                } else if (eventType == XmlPullParser.START_TAG) {
                    String tag_name = parser.getName();

                   if(tag_name.equals("schoolName"))
                        name = true;


                } else if (eventType == XmlPullParser.TEXT) {
                    String data = parser.getText();


                    if (name) {
                        //Adapter1.add(data);
                        list_major.add(data);
                        name = false;
                    }

                } else if (eventType == XmlPullParser.END_TAG) {

                }

                eventType = parser.next();

            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
