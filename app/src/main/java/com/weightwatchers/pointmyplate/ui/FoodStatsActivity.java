package com.weightwatchers.pointmyplate.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.weightwatchers.pointmyplate.R;

public class FoodStatsActivity extends BaseActivity {

    private static final String KEY_FOODID = "foodid";

    public static void startWith(Activity currentActivity, long foodId) {

        Intent intent = new Intent(currentActivity, FoodStatsActivity.class);
        intent.putExtra(KEY_FOODID, foodId);

        currentActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_stats);
    }


    @Override
    protected void onResume() {
        super.onResume();

        hideFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_stats, menu);
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
