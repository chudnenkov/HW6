package com.example.roman.hw6;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import java.util.List;

/**
 * Created by roman on 11.01.2016.
 */
public class SecondFragment extends Fragment {

    private boolean checkedCity = false;

    interface MyCallBack{
        public void setCity(String city);
    }
    MyCallBack myCallBack;


    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try {
            myCallBack = (MyCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnItemSelectedListener");
        }

    }


    @Override
    public View onCreateView (LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        CheckBox selectCity = (CheckBox) view.findViewById(R.id.selectCity);
        selectCity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //checkedCity = true;
                    myCallBack.setCity(((MainFragment) (getActivity().getFragmentManager().findFragmentByTag("TAG"))).getPickedCity());
                }
            }
        });

        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, MainFragment.cityWeatherTemp));

        return  view;
    }
}
