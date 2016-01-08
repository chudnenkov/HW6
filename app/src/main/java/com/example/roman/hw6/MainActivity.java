package com.example.roman.hw6;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> countries  = new ArrayList<String>();
    ArrayList<String> cities  = new ArrayList<String>();

    Map<String, ArrayList<String>> countriesCities = new LinkedHashMap<String, ArrayList<String>>();

    String containerCountry;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

 //       countriesCities = new LinkedHashMap<String, ArrayList<String>>();

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        try{
        XmlPullParser parser = getResources().getXml(R.xml.cities);
        while (parser.getEventType()!= XmlPullParser.END_DOCUMENT){

            if (parser.getEventType()==XmlPullParser.START_TAG){
                String name = parser.getName();
                if (name.equals("country")){
                    countries.add(parser.getAttributeValue(0));
                    containerCountry = parser.getAttributeValue(0);

                }
                if (name.equals("city")){
                    parser.next();
                   if (parser.getEventType() == XmlPullParser.TEXT){
                       cities.add(parser.getText());
                   }

                }
            }

            if ( (parser.getEventType()==XmlPullParser.END_TAG) && (parser.getName().equals("country")) ){
                countriesCities.put(containerCountry, cities);
                cities  = new ArrayList<String>();
              //  cities.clear();
            }

            parser.next();
        }

        }catch (Exception e){}


        CustomExpandableListAdapter customExpandableListAdapter = new CustomExpandableListAdapter(this, countries , countriesCities);
        expandableListView.setAdapter(customExpandableListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
