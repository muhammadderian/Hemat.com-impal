package com.example.impal2;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =  new ActivityTestRule(MainActivity.class);
    private MainActivity mActivity = null ;

    @Before
    public void setUp() throws Exception {
        mActivity = activityRule.getActivity();
    }

    @Test
    public void launchLogin(){
        View u = mActivity.findViewById(R.id.username);
        View p = mActivity.findViewById(R.id.password);

        assertNotNull(u);
        assertNotNull(p);
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}