package com.example.roman.hw6;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by roman on 06.01.2016.
 */
public  class CustomExpandableListAdapter extends BaseExpandableListAdapter  {

    Activity context ;
    Map<String,ArrayList<String>> companiesCities;
    ArrayList<String> countries ;



    public  CustomExpandableListAdapter(Activity context, ArrayList<String> countries, Map<String,ArrayList<String>> companiesCities){
        this.context = context;
        this.countries = countries;
        this.companiesCities = companiesCities;
    }

    public Object  getChild(int groupPosition, int childPosition){
        return companiesCities.get(countries.get(groupPosition)).get(childPosition);
    }

    public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater = context.getLayoutInflater();
        final String city = (String) getChild(groupPosition, childPosition);

        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.child_item, null);
            TextView textView = (TextView) convertView.findViewById(R.id.textView2);
            textView.setText(city);
        }
        return convertView;
    }

    public  View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater = context.getLayoutInflater();
        String country = (String) getGroup(groupPosition);

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.group_item, null);
            TextView textView = (TextView) convertView.findViewById(R.id.textView);
            textView.setText(String.valueOf(groupPosition) +  " " + country);
        }
        return convertView;

    }





    public boolean isChildSelectable(int groupPosition, int childPosition){
        return true;
    }

    public int getGroupCount(){
        return countries.size();
    }

    public  boolean hasStableIds(){
        return  true;
    }

    public long getGroupId(int groupPosition){
        return groupPosition;
    }

    public Object getGroup(int groupPosition){
        return  countries.get(groupPosition);
    }

    public int getChildrenCount (int groupPosition){
        return companiesCities.get(countries.get(groupPosition)).size();
    }

    public long getChildId(int groupPosition, int childPosition){
        return childPosition;
    }
}
