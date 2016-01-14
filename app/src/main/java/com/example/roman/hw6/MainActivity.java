package com.example.roman.hw6;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
/*   List<String> countries  = new ArrayList<String>();
    ArrayList<String> cities  = new ArrayList<String>();
    ArrayList<String> CityId = new ArrayList<String>();
    Map<String, List<String>> countriesCities = new LinkedHashMap<String, List<String>>();
    String containerCountry; */

    public static  FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     /*   ExpandableListView expandableListView = (ExpandableListView) findViewById(R.CityId.expandableListView);
        parseCitiesXml();
        CustomExpandableListAdapter customExpandableListAdapter = new CustomExpandableListAdapter(this, countries , countriesCities);
        expandableListView.setAdapter(customExpandableListAdapter); */

        MainFragment fragment = new MainFragment();
        SecondFragment secondFragment =  new SecondFragment();
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
       // fragmentManager.beginTransaction().replace(R.id.container, secondFragment).addToBackStack(null).commit();
    }

 /*  public  void parseCitiesXml(){
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
                        CityId.add(parser.getAttributeValue(0));
                        parser.next();
                        if (parser.getEventType() == XmlPullParser.TEXT){
                            cities.add(parser.getText());
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
    }*/

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
