package com.weightwatchers.pointmyplate.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.weightwatchers.pointmyplate.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FoodOverviewActivity extends ActionBarActivity {

    private static final String KEY_FOODID = "foodid";

    @InjectView(R.id.showMorePanel)
    View showMorePanel;

    @InjectView(R.id.detailsPanel)
    View detailsPanel;

    @InjectView(R.id.image)
    ImageView imageView;

    @InjectView(R.id.nameLabel)
    TextView nameLabel;

    @InjectView(R.id.locationLabel)
    TextView locationLabel;

    @InjectView(R.id.yumyuckLabel)
    TextView yumyuckLabel;

    @InjectView(R.id.commentsLabel)
    TextView commentsLabel;

    private long foodId;

    public static void startWith(Activity currentActivity, long foodId) {

        Intent intent = new Intent(currentActivity, FoodOverviewActivity.class);
        intent.putExtra(KEY_FOODID, foodId);

        currentActivity.startActivity(intent);
    }

    @OnClick(R.id.yumButton)
    public void onClickYumButton() {

    }

    @OnClick(R.id.yuckButton)
    public void onClickYuckButton() {

    }

    @OnClick(R.id.detailsButton)
    public void onClickDetailsButton() {
        FoodStatsActivity.startWith(this, foodId);
    }

    @OnClick(R.id.detailsPanel)
    public void onClickDetailsPanel() {
        showMorePanel.setVisibility(View.VISIBLE);
        detailsPanel.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_overview);

        ButterKnife.inject(this);

        foodId = getIntent().getLongExtra(KEY_FOODID, -1);
        if (foodId < 0) {
            Toast.makeText(this, "Could not load that item", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        int[] imageIds = {
                R.drawable.bagels,
                R.drawable.pancake,
                R.drawable.fish,
                R.drawable.chicken
        };

        imageView.setImageDrawable(getResources().getDrawable(imageIds[(int)foodId]));

        showMorePanel.setVisibility(View.GONE);
    }

    @OnClick(R.id.showMorePanel)
    public void onClickShowMore() {
        showMorePanel.setVisibility(View.GONE);
        detailsPanel.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food_overview, menu);
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
