package com.example.roman.hw6;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by roman on 11.01.2016.
 */
public class MainFragment extends Fragment {

    List<String> countries  = new ArrayList<String>();
    ArrayList<String> cities  = new ArrayList<String>();
    Map<String, String> CityId = new HashMap<String, String>();
    Map<String, List<String>> countriesCities = new LinkedHashMap<String, List<String>>();
    String containerCountry;

    String weather_type;
    String temperature;

    String pickedCity;
    String pickedId;
    public static  ArrayList<String> cityWeatherTemp = new ArrayList<String>();

    CustomExpandableListAdapter customExpandableListAdapter;
    ExpandableListView expandableListView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState){

        view  = inflater.inflate(R.layout.fragment_main, container, false);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        customExpandableListAdapter = new CustomExpandableListAdapter(getActivity(), countries , countriesCities);
        expandableListView.setAdapter(customExpandableListAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                pickedCity = countriesCities.get(countries.get(groupPosition)).get(childPosition);
                pickedId = CityId.get(pickedCity);

                DownloadXML downloadXML = new DownloadXML();
                ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    downloadXML.execute("http://export.yandex.ru/weather-ng/forecasts/" + pickedId + ".xml");
                 } else {
                    Toast.makeText(getActivity(), "No network connection available.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        if (countries.size() == 0 && countriesCities.size()==0 ){
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                DownloadXML downloadXML = new DownloadXML();
                downloadXML.execute("https://pogoda.yandex.ru/static/cities.xml");
            } else {
                Toast.makeText(getActivity(), "No network connection available.", Toast.LENGTH_SHORT).show();
            }
        }

        return view;
    }

    public void parseForecastXml(String content) throws XmlPullParserException, IOException {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(content));

            while (xmlPullParser.getEventType()!= XmlPullParser.END_DOCUMENT){// &&( xmlPullParser.getName().equals("fact") &&xmlPullParser.getEventType() ==XmlPullParser.END_TAG )){

                if(xmlPullParser.getEventType()==XmlPullParser.START_TAG && xmlPullParser.getName().equals("temperature")){
                    xmlPullParser.next();
                    if (xmlPullParser.getEventType() == XmlPullParser.TEXT){
                        temperature =xmlPullParser.getText();
                    }
                }

                if (xmlPullParser.getEventType()==XmlPullParser.START_TAG && xmlPullParser.getName().equals("weather_type")){
                    xmlPullParser.next();
                    if (xmlPullParser.getEventType() == XmlPullParser.TEXT) {
                        weather_type = xmlPullParser.getText();
                        break;
                    }
                }
                xmlPullParser.next();
            }
    }

    public  void parseCitiesXml(String content){
        try{

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            while (parser.getEventType()!= XmlPullParser.END_DOCUMENT){

                if (parser.getEventType()==XmlPullParser.START_TAG){
                    String name = parser.getName();
                    if (name.equals("country")){
                        countries.add(parser.getAttributeValue(0));
                        containerCountry = parser.getAttributeValue(0);
                    }
                    if (name.equals("city")){
                        String id = parser.getAttributeValue(0);
                        parser.next();
                        if (parser.getEventType() == XmlPullParser.TEXT){
                            cities.add(parser.getText());
                            CityId.put(parser.getText(), id);
                        }
                    }
                }
                if ( (parser.getEventType()==XmlPullParser.END_TAG) && (parser.getName().equals("country")) ){
                    countriesCities.put(containerCountry, cities);
                    cities  = new ArrayList<String>();
                }
                parser.next();
            }

        }catch (Exception e){}
    }

    public class DownloadXML extends AsyncTask<String, Integer, String> {

        public ProgressDialog progressDialog;
        String Url;

        @Override
        protected void onPreExecute(){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(true);
            progressDialog.setMessage("downloading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String ... url){
            String content = null;
            Url= url[0];
            try{
                content = downloadData(url[0]);
            }catch (IOException e){}

            return content;
        }

        @Override
        public void onPostExecute(String result){

            if (Url.equals("https://pogoda.yandex.ru/static/cities.xml")) {
                parseCitiesXml(result);
                progressDialog.cancel();
                customExpandableListAdapter.notifyDataSetChanged();
            }
            else {

                try {
                     parseForecastXml(result);
                     progressDialog.cancel();
                    }catch (IOException e) {}
                    catch (XmlPullParserException e) {}

                cityWeatherTemp.clear();
                cityWeatherTemp.add(pickedCity);
                cityWeatherTemp.add(weather_type);
                cityWeatherTemp.add(temperature);
                SecondFragment secondFragment = new SecondFragment();
                MainActivity.fragmentManager.beginTransaction().replace(R.id.container, secondFragment).addToBackStack(null).commit();
                }

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
