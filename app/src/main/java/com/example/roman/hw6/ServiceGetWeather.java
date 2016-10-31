package com.example.roman.hw6;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ServiceGetWeather extends Service {

    SharedPreferences sharedPreferences;
    String idCity;
    String city;

    String cityWheather;

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    public ServiceGetWeather() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate() {
        // TODO Auto-generated method stub

        Toast.makeText(getApplicationContext(), "Service Created", Toast.LENGTH_LONG).show();

        sharedPreferences = getApplicationContext().getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        idCity = sharedPreferences.getString("id", "");
        city = sharedPreferences.getString("city", "");

        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        Toast.makeText(getApplicationContext(), "Service Running ", Toast.LENGTH_LONG).show();


        DownloadXML downloadXML = new DownloadXML();
        try {
            cityWheather = downloadXML.execute("http://export.yandex.ru/weather-ng/forecasts/" + idCity + ".xml").get();
            Log.e("city whether every times", cityWheather);
        }catch (ExecutionException e){}
        catch (InterruptedException e){e.printStackTrace();}


        //sqLiteDatabase.rawQuery("insert into info_weather (city, weather) values (?, ?)", new String[]{"kiev", "+4"});
        ContentValues cv = new ContentValues();
        cv.put("city", city);
        cv.put("weather", cityWheather);

        sqLiteDatabase.insert("info_weather ", null, cv);

    //    sqLiteDatabase.getPath();
        Cursor c =sqLiteDatabase.rawQuery("select * from info_weather", null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("_id");
            int nameColIndex = c.getColumnIndex("city");
            int emailColIndex = c.getColumnIndex("weather");

            do {
                // получаем значени€ по номерам столбцов и пишем все в лог
                Log.e("LOG_TAG",
                        "ID = " + c.getInt(idColIndex) +
                                ", city = " + c.getString(nameColIndex) +
                                ", weather = " + c.getString(emailColIndex));
                // переход на следующую строку
                // а если следующей нет (текуща€ - последн€€), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.e("LOG_TAG", "0 rows");
        c.close();


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Toast.makeText(getApplicationContext(), "Service Destroy", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    public  class DownloadXML extends AsyncTask<String, Integer, String> {

        String Url;

        @Override
        protected void onPreExecute(){
            Toast.makeText(getApplicationContext(), "Download city whether", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String ... url){
            String content = null;
            String cityWheather= "";
            Url= url[0];
            try{
                content = downloadData(url[0]);
               // Log.e("DOWNLOADED CONTENT", content);
            }catch (IOException e){}

            try {
                cityWheather = parseForecastXml(content);

            }catch (IOException e) {}
            catch (XmlPullParserException e) {}


            return cityWheather;
        }

        @Override
        public void onPostExecute(String result){
                /*try {
                   parseForecastXml(result);

                }catch (IOException e) {}
                catch (XmlPullParserException e) {}*/
            }



        public   String parseForecastXml(String content) throws XmlPullParserException, IOException {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(content));
            String  temperature = "";

            while (xmlPullParser.getEventType()!= XmlPullParser.END_DOCUMENT){// &&( xmlPullParser.getName().equals("fact") &&xmlPullParser.getEventType() ==XmlPullParser.END_TAG )){

                if(xmlPullParser.getEventType()==XmlPullParser.START_TAG && xmlPullParser.getName().equals("temperature")){
                    xmlPullParser.next();
                    if (xmlPullParser.getEventType() == XmlPullParser.TEXT){
                        temperature =xmlPullParser.getText();
                        break;
                    }
                }

                xmlPullParser.next();
            }
            return  temperature;
        }

        public String downloadData(String urlCity) throws IOException {
            InputStream is = null;
            try {
                URL url = new URL(urlCity);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.connect();
                is = httpURLConnection.getInputStream();
                String content = IOUtils.toString(is, "UTF-8");

                return content;

            }finally {
                if (is !=null){
                    is.close();
                }
            }
        }
    }
}
