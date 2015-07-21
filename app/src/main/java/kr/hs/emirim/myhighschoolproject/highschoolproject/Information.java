package kr.hs.emirim.myhighschoolproject.highschoolproject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Information extends Activity {

    /*
    private String key = "2adefeb4c167fd97a42f4a55a00b2d90";
    //private String url = "http://www.career.go.kr/cnet/openapi/getOpenApi.xml?apiKey=" +
    //           key+"&svcType=api&svcCode=SCHOOL&gubun=high_list&sch1=100364";
    private  String url = "http://www.career.go.kr/cnet/openapi/getOpenApi.xml?apiKey=" + key
            + "&svcType=api&svcCode=SCHOOL&gubun=high_list&sch1=100364";
    */

    private String data_link;

    private TextView nameTv, addressTv, majorTv;
    private ImageButton linkBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_information);

        nameTv = (TextView) findViewById(R.id.school_name);
        addressTv = (TextView) findViewById(R.id.text_address);
        majorTv = (TextView) findViewById(R.id.text_major);
        linkBut = (ImageButton) findViewById(R.id.but_link);


        Intent intent = getIntent();
        String name = intent.getStringExtra("select");
        nameTv.append(name);

        xmlParsing(name);
        //new DownloadWepPage().execute(url);


    }

    public void startUrl(View v){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+data_link)));
    }

    public void xmlParsing(String name) {
        XmlResourceParser parser = null;
        parser = getResources().getXml(R.xml.students);

        int type = 0;
        boolean bSet = false;
        boolean link = false, region = false, address = false, major = false;
        boolean allSet = false;
        boolean info = false;

        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {

                } else if (eventType == XmlPullParser.START_TAG) {
                    String tag_name = parser.getName();

                    if(tag_name.equals("schoolName")){
                        bSet = true;
                    }else if(tag_name.equals("link")){
                        link = true;
                    }else if(tag_name.equals("region")){
                        region = true;
                    }else if(tag_name.equals("address")){
                        address = true;
                    }else if(tag_name.equals("major")){
                        major = true;
                    }

                   /* if(tag_name.equals("link") || tag_name.equals("region")
                            || tag_name.equals("address") || tag_name.equals("major")){
                        info = true;
                    }*/


                } else if (eventType == XmlPullParser.TEXT) {
                    String data = parser.getText();
                    if(bSet){

                        if(data.equals(name)) {
                            allSet = true;
                        }
                        else {

                        }
                        bSet = false;
                    }
                    if(allSet) {
                        ///String tag_name = parser.getName();
                        //String data2 = parser.getText();
                        /*if (tag_name.equals("address")) {
                            addressTv.append(data);
                        } else if (tag_name.equals("link")) {
                            data_link = data;
                        } else if (tag_name.equals("major")) {
                            if (data.equals("null")) {
                                majorTv.append("정보 없음");
                            } else {
                                majorTv.append(data);
                            }
                        }*/
                        //info = false;

                        if(address){
                            data = parser.getText();
                            addressTv.append(data);
                            address = false;
                        }else if(major){
                            data = parser.getText();
                            if(data.equals("null")){
                                majorTv.append("정보 없음");
                            }else {
                                data = parser.getText();
                                majorTv.append(data);
                            }
                            major = false;
                        }else if(link){
                            data = parser.getText();
                            data_link = data;
                            link = false;
                        }

                        allSet = false;

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
            boolean school = false;
            int temp=0;

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

                        if(tag_name.equals("schoolName") || tag_name.equals("region")
                                || tag_name.equals("link"))
                            bSet = true;


                    } else if (eventType == XmlPullParser.TEXT) {
                        String str = parser.getText();
                        String tag_name = parser.getName();

                        if(tag_name.equals("schoolName")){
                            if(parser.getText().equals(data)) {
                                if(temp == 2) break;
                                //tv.append(parser.getText()+"\n");
                                school = true;
                                temp++;
                                eventType = XmlPullParser.START_DOCUMENT;
                            }
                        }
                        if(school && bSet) {
                            tv.append(str + "\n");
                            school = false;
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

    }*/

}
