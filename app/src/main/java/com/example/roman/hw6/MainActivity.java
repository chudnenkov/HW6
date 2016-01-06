package com.example.roman.hw6;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> countries  = new ArrayList<String>();
    ArrayList<String> cities  = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
        XmlPullParser parser = getResources().getXml(R.xml.cities);
        while (parser.getEventType()!= XmlPullParser.END_DOCUMENT){

            if (parser.getEventType()==XmlPullParser.START_TAG){
                //countries.add(parser.getName());
                String name = parser.getName();
                if (name.equals("country")){
                    countries.add(parser.getAttributeValue(0));

                }
                if (name.equals("city")){
                   // cities.add(parser.getText());
                   if (parser.getEventType() == XmlPullParser.TEXT){
                       cities.add(parser.getText());
                   }

                }
            }

            parser.next();
        }

        }catch (Exception e){}


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
