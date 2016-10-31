package com.example.roman.hw6.testsuite;

import android.test.AndroidTestRunner;
import android.test.InstrumentationTestRunner;

import com.example.roman.hw6.TestClass1;
import com.example.roman.hw6.TestClass2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by roman on 23.02.2016.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AndroidTestRunner.class, TestClass2.class, TestClass1.class
        })
public class TestSuite {
    public static void main (String[] args) {
    }
}
