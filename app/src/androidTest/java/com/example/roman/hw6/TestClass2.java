package com.example.roman.hw6;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.Test;

/**
 * Created by roman on 23.02.2016.
 */
public class TestClass2 extends ActivityInstrumentationTestCase2<MainActivity> {
    public TestClass2() {super(MainActivity.class);}

    public Solo solo;
    private Activity activity;

    @Override
    public void setUp() throws Exception {
        this.activity = this.getActivity();  //This is where the solo object is created.
        this.solo = new Solo(getInstrumentation(), this.activity);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @Test
    public  void testfooBar(){

        assertTrue(13==4);
    }
}