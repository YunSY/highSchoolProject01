package kr.hs.emirim.myhighschoolproject.highschoolproject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Information extends Activity {

    /*
    private String key = "2adefeb4c167fd97a42f4a55a00b2d90";
    //private String url = "http://www.career.go.kr/cnet/openapi/getOpenApi.xml?apiKey=" +
    //           key+"&svcType=api&svcCode=SCHOOL&gubun=high_list&sch1=100364";
    private  String url = "http://www.career.go.kr/cnet/openapi/getOpenApi.xml?apiKey=" + key
            + "&svcType=api&svcCode=SCHOOL&gubun=high_list&sch1=100364";
    */

    private String data_link, text_address, text_major;
    private String text_id;
    private String type;

    private TextView nameTv, addressTv, majorTv;
    private ImageButton linkBut;
    private ImageView img, logo;
    private String personXMLString;
    private int xmlFile;
    private String schName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_information);

        boolean set = true;

        nameTv = (TextView) findViewById(R.id.school_name);
        addressTv = (TextView) findViewById(R.id.text_address);
        majorTv = (TextView) findViewById(R.id.text_major);
        linkBut = (ImageButton) findViewById(R.id.but_link);

        img = (ImageView)findViewById(R.id.img);
        logo = (ImageView) findViewById(R.id.logo);

        Intent intent = getIntent();
        schName = intent.getStringExtra("select");

        type = intent.getStringExtra("type");
        if(type.equals("특목고")) {
            xmlFile = R.xml.special;
            set = true;
        }
        else if(type.equals("자율고")) {
            xmlFile = R.xml.free;
            set = false;
        }
        else if(type.equals("특성화고")) {
            xmlFile = R.xml.major;
            set = true;
        }
        nameTv.append(schName);

        //xml 파싱
        xmlParsing(schName);

        addressTv.append(text_address);
        if(set)
            majorTv.append(text_major);
        else
            majorTv.append("정보 없음");

        String img1 =  "ab" + text_id;
        String img2 =  "a" + text_id;


        String resName1 = "@drawable/" + img1;
        String resName2 = "@drawable/" + img2;
        String packName = this.getPackageName(); // 패키지명
        int logoID = getResources().getIdentifier(resName1, "drawable", packName);
        int schoolID = getResources().getIdentifier(resName2, "drawable", packName);

        logo.setImageResource(logoID);
        img.setImageResource(schoolID);
        //new DownloadWepPage().execute(url);

    }


    public void startUrl(View v){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + data_link)));
    }

    public void xmlParsing(String name) {
        XmlResourceParser parser = null;
        parser = getResources().getXml(xmlFile);

        addressTv.setText("");
        majorTv.setText("");

        boolean bSet = false;
        boolean link = false, region = false, address = false, major = false;
        boolean allSet = true;
        boolean setId = false, temp = true, temp2 = true;


        try {


            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {

                } else if (eventType == XmlPullParser.START_TAG) {
                    String tag_name = parser.getName();

                    if(tag_name.equals("id")){
                        setId = true;
                    }
                    else if(tag_name.equals("schoolName")){
                        allSet = true;
                        bSet = true;
                    }else if(tag_name.equals("link")){
                        link = true;
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


                    if(setId && temp && temp2)
                    {
                        text_id = data;
                        setId = false;
                    }
                    if(bSet){

                        if(!data.equals(name)) {
                            allSet = false;
                            temp = true;
                        }else{
                            temp = false;
                            temp2 = false;
                        }
                        bSet = false;
                    }


                    if(allSet)
                    {
                        if(link){
                            data = parser.getText();
                            data_link = data;
                            link = false;
                        }if(address){
                            data = parser.getText();
                            text_address = data;
                            address = false;
                        }else if(major) {
                            data = parser.getText();
                            if (!data.equals("null")) {
                                text_major = data;
                            } else {
                                text_major = "정보 없음";
                            }
                            major = false;
                        }
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
