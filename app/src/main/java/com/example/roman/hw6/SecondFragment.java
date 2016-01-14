package com.example.roman.hw6;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by roman on 11.01.2016.
 */
public class SecondFragment extends Fragment {

    @Override
    public View onCreateView (LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, MainFragment.cityWeatherTemp));
        return  view;
    }
}
