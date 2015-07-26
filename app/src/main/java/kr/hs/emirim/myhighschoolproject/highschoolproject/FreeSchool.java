package kr.hs.emirim.myhighschoolproject.highschoolproject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;


public class FreeSchool extends Activity {

    private static ArrayList<String> list_private;
    private static ArrayList<String> list_public;

    TabHost tabHost;
    private static ArrayAdapter<String> Adapter1, Adapter2;
    private static ListView list1, list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_free_menu);

        list_private = new ArrayList<String>();
        list_public = new ArrayList<String>();

        xmlParsing();

        Adapter1 = new ArrayAdapter<String>(FreeSchool.this, android.R.layout.simple_expandable_list_item_1, list_private);
        Adapter2 = new ArrayAdapter<String>(FreeSchool.this, android.R.layout.simple_expandable_list_item_1, list_public);

        tabHost = (TabHost)findViewById(R.id.tabHost2);
        tabHost.setup();

        tabHost.addTab(tabHost.newTabSpec("tag1").setIndicator("자율형 사립고").setContent(R.id.free_private));
        tabHost.addTab(tabHost.newTabSpec("tag2").setIndicator("자율형 공립고").setContent(R.id.free_public));

        for (int i=0; i<tabHost.getTabWidget().getChildCount(); i++) {
            LinearLayout relLayout = (LinearLayout) tabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) relLayout.getChildAt(1);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
            tv.setTextSize(18.0f);
        }

        list1 = (ListView) findViewById(R.id.list_private);
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
                Intent intent = new Intent(FreeSchool.this, Information.class);
                intent.putExtra("select", tv.getText().toString());
                startActivity(intent);


            }
        });

        list2 = (ListView)findViewById(R.id.list_public);
        list2.setAdapter(Adapter2);
        list2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list2.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                TextView tv = (TextView) arg1;
                Intent intent = new Intent(FreeSchool.this, Information.class);
                intent.putExtra("select", tv.getText().toString());
                startActivity(intent);
            }
        });
    }

    public void xmlParsing() {
        XmlPullParser parser = null;
        parser = getResources().getXml(R.xml.free);

        int type = 0;
        boolean name = false;
        boolean sPublic = false, sPrivate = false;
        boolean bList = false;
        int temp = 0;

        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {

                } else if (eventType == XmlPullParser.START_TAG) {
                    String tag_name = parser.getName();

                    if(tag_name.equals("seperate"))
                        bList = true;

                    if(tag_name.equals("schoolName")) {
                        name = true;
                    }

                } else if (eventType == XmlPullParser.TEXT) {
                    String data = parser.getText();


                    if(bList){
                        if(data.equals("자율형 공립고")){
                            sPublic = true;
                        }else if(data.equals("자율형 사립고")){
                            sPrivate = true;
                        }
                        bList = false;
                    }

                    if(name) {
                        if (sPublic) {
                            //Adapter1.add(data);
                            list_public.add(data);
                            sPublic = false;
                        } else if (sPrivate) {
                            //Adapter2.add(data);
                            list_private.add(data);
                            sPrivate = false;
                        }
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
