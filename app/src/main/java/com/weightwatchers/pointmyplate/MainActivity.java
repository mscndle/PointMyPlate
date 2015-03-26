package com.weightwatchers.pointmyplate;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.weightwatchers.pointmyplate.ui.NavActivity;


/**
 * This activity will bootstrap the app then die
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, NavActivity.class));
    }

}
