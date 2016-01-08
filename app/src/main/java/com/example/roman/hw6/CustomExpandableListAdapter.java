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
import java.util.List;
import java.util.Map;

/**
 * Created by roman on 06.01.2016.
 */
public  class CustomExpandableListAdapter extends BaseExpandableListAdapter  {

    Activity context ;
    Map<String,List<String>> companiesCities;
    List<String> countries ;

    public  CustomExpandableListAdapter(Activity context, List<String> countries, Map<String, List<String>> companiesCities){
        this.context = context;
        this.countries = countries;
        this.companiesCities = companiesCities;
    }

    public Object  getChild(int groupPosition, int childPosition){
        return companiesCities.get(countries.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition){
        return childPosition;
    }

    public View getChildView(final int groupPosition, final int childPosition, boolean isExpanded, View convertView, ViewGroup parent){

        final String city = (String) getChild(groupPosition, childPosition);
        LayoutInflater layoutInflater = context.getLayoutInflater();
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.child_item, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.textView2);
        textView.setText(city);
        return convertView;
    }

    public int getChildrenCount (int groupPosition){
        return companiesCities.get(countries.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition){
        return  countries.get(groupPosition);
    }

    public int getGroupCount(){
        return countries.size();
    }

    public long getGroupId(int groupPosition){
        return groupPosition;
    }

    public  View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){

        String country = (String) getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.group_item, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(country);
        return convertView;

    }

    public  boolean hasStableIds(){
        return  true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition){
        return true;
    }

}
