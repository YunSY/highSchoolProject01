package kr.hs.emirim.myhighschoolproject.highschoolproject;
//특목고 Activity
import android.app.Activity;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
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


public class SpecialSchool extends Activity {

    //private String key = "2adefeb4c167fd97a42f4a55a00b2d90";
    //private String url = "http://www.career.go.kr/cnet/openapi/getOpenApi.xml?apiKey=" +
    //           key+"&svcType=api&svcCode=SCHOOL&gubun=high_list&sch1=100364";
    //private  String url = "http://www.career.go.kr/cnet/openapi/getOpenApi.xml?apiKey=" +
    //        key + "&svcType=api&svcCode=SCHOOL&gubun=high_list&sch1=100364";
    private static ArrayList<String> list_foreign;
    private static ArrayList<String> list_science;
    private static ArrayList<String> list_meister;
    private static ArrayList<String> list_art;

    TabHost tabHost;
    private static ArrayAdapter<String> Adapter1, Adapter2, Adapter3, Adapter4;
    private static ListView list1, list2, list3, list4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_special_menu);

        list_foreign = new ArrayList<String>();
        list_science = new ArrayList<String>();
        list_meister = new ArrayList<String>();
        list_art = new ArrayList<String>();

        //new DownloadWepPage().execute(url);
        xmlParsing();


        Adapter1 = new ArrayAdapter<String>(SpecialSchool.this, android.R.layout.simple_expandable_list_item_1, list_foreign);
        Adapter2 = new ArrayAdapter<String>(SpecialSchool.this, android.R.layout.simple_expandable_list_item_1, list_science);
        Adapter3 = new ArrayAdapter<String>(SpecialSchool.this, android.R.layout.simple_expandable_list_item_1, list_meister);
        Adapter4 = new ArrayAdapter<String>(SpecialSchool.this, android.R.layout.simple_expandable_list_item_1, list_art);



        //태그명바꾸기
        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        tabHost.addTab(tabHost.newTabSpec("tag1").setIndicator("국제고/외고").setContent(R.id.foreign));
        tabHost.addTab(tabHost.newTabSpec("tag2").setIndicator("과고").setContent(R.id.science));
        tabHost.addTab(tabHost.newTabSpec("tag3").setIndicator("마이스터고").setContent(R.id.meister));
        tabHost.addTab(tabHost.newTabSpec("tag4").setIndicator("예고/체고").setContent(R.id.art));


        for (int i=0; i<tabHost.getTabWidget().getChildCount(); i++) {
            LinearLayout relLayout = (LinearLayout) tabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) relLayout.getChildAt(1);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
        }


        //tabHost.getCurrentTab()

        list1 = (ListView) findViewById(R.id.list_foreign);
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
                Intent intent = new Intent(SpecialSchool.this, Information.class);
                intent.putExtra("select", tv.getText().toString());
                startActivity(intent);


            }
        });

        list2 = (ListView)findViewById(R.id.list_science);
        list2.setAdapter(Adapter2);
        list2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list2.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                TextView tv = (TextView)arg1;
                Intent intent = new Intent(SpecialSchool.this, Information.class);
                intent.putExtra("select", tv.getText().toString());
                startActivity(intent);
            }
        });

        list3 = (ListView)findViewById(R.id.list_meister);
        list3.setAdapter(Adapter3);
        list3.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list3.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                TextView tv = (TextView)arg1;
                Intent intent = new Intent(SpecialSchool.this, Information.class);
                intent.putExtra("select", tv.getText().toString());
                startActivity(intent);
            }
        });

        list4 = (ListView)findViewById(R.id.list_art);
        list4.setAdapter(Adapter4);
        list4.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list4.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                TextView tv = (TextView)arg1;
                Intent intent = new Intent(SpecialSchool.this, Information.class);
                intent.putExtra("select", tv.getText().toString());
                startActivity(intent);
            }
        });


    }

    public void xmlParsing() {
        XmlResourceParser parser = null;
        parser = getResources().getXml(R.xml.students);

        int type = 0;
        boolean name = false;
        boolean foreign=false, science=false, meister=false, art=false;
        boolean bList = false;

        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {

                } else if (eventType == XmlPullParser.START_TAG) {
                    String tag_name = parser.getName();

                    if(tag_name.equals("schoolType"))
                        bList = true;

                    if(tag_name.equals("schoolName")) {
                        name = true;

                    }

                } else if (eventType == XmlPullParser.TEXT) {
                    String data = parser.getText();

                    if(bList){
                        if(data.equals("과학고") || data.equals("영재고"))
                            science = true;
                        else if(data.equals("외국어고") || data.equals("국제고"))
                            foreign = true;
                        else if(data.equals("마이스터고"))
                            meister = true;
                        else if(data.equals("예술고") || data.equals("체육고"))
                            art = true;
                        bList = false;
                    }

                    if(name) {
                        if (foreign) {
                            //Adapter1.add(data);
                            list_foreign.add(data);
                            foreign = false;
                        } else if (science) {
                            //Adapter2.add(data);
                            list_science.add(data);
                            science = false;
                        } else if (meister) {
                            //Adapter3.add(data);
                            list_meister.add(data);
                            meister = false;
                        } else if (art) {
                            //Adapter4.add(data);
                            list_art.add(data);
                            art = false;
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


    /*class DownloadWepPage extends AsyncTask<String, Void, String> {
        String page = "";
        @Override
        protected String doInBackground(String... url) {

            return downloadURL(url[0]);
        }

        public String downloadURL(String strUrl){
            HttpURLConnection con = null;
            String line = null;


            try {
                URL url = new URL(strUrl);
                con=(HttpURLConnection)url.openConnection();

                InputStream in = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        new BufferedInputStream(in),"utf-8"));

                while((line=br.readLine())!=null){
                    page += line;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                con.disconnect();
            }


            return page;
        }

        @Override
        protected void onPostExecute(String s) {


            boolean bSet = false;
            boolean foreign=false, science=false, meister=false, art=false;
            boolean name = false;
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new StringReader(page));
                int eventType = parser.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT)
                {
                    if (eventType == XmlPullParser.START_DOCUMENT) {

                    } else if (eventType == XmlPullParser.START_TAG) {
                        String tag_name = parser.getName();

                        if(tag_name.equals("schoolType"))
                            bSet = true;
                        else if(tag_name.equals("schoolName"))
                            name = true;


                    } else if (eventType == XmlPullParser.TEXT) {
                        String data = parser.getText();
                        String tag_name = parser.getName();
                        if(bSet) {
                            if(data.equals("외국어국제계열"))
                                foreign = true;
                            else if(data.equals("과학계열"))
                                science = true;
                            else if(data.equals("마이스터고"))
                                meister = true;
                            else if(data.equals("예술체육계열"))
                                art = true;
                            bSet = false;
                        }
                        if(name) {
                            if (foreign) {
                                //Adapter1.add(data);
                                list_foreign.add(data);
                                foreign = false;
                            } else if (science) {
                                //Adapter2.add(data);
                                list_science.add(data);
                                science = false;
                            } else if (meister) {
                                //Adapter3.add(data);
                                list_meister.add(data);
                                meister = false;
                            } else if (art) {
                                //Adapter4.add(data);
                                list_art.add(data);
                                art = false;
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
*/
}
