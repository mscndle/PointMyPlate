package com.weightwatchers.pointmyplate.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.weightwatchers.pointmyplate.PMPApplication;
import com.weightwatchers.pointmyplate.R;
import com.weightwatchers.pointmyplate.model.Plate;
import com.weightwatchers.pointmyplate.model.Vote;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FoodOverviewActivity extends BaseActivity {

    private static final String KEY_PLATEID = "foodid";

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

    @InjectView(R.id.yumButton)
    RadioButton yumButton;

    @InjectView(R.id.yuckButton)
    RadioButton yuckButton;

    @InjectView(R.id.showMoreLocationLabel)
    TextView showMoreLocationLabel;

    @InjectView(R.id.showMoreNameLabel)
    TextView showMoreNameLabel;

    @InjectView(R.id.showMoreNotesLabel)
    TextView showMoreNotesLabel;

    private Plate plate;
    private Vote myVote;

    public static void startWith(Activity currentActivity, long foodId) {

        Intent intent = new Intent(currentActivity, FoodOverviewActivity.class);
        intent.putExtra(KEY_PLATEID, foodId);

        currentActivity.startActivity(intent);
    }

    @OnClick(R.id.yumButton)
    public void onClickYumButton() {
        if (myVote == Vote.YUM) {
            return;
        }
        if (myVote == Vote.YUCK) {
            plate.setYuckCount(plate.getYuckCount()-1);
        }
        plate.setYumCount(plate.getYumCount()+1);
        myVote = Vote.YUM;

        PMPApplication.get().getModelAPI().voteFor(plate.getId(), 0, myVote);

        updateUI();
    }

    @OnClick(R.id.yuckButton)
    public void onClickYuckButton() {
        if (myVote == Vote.YUCK) {
            return;
        }
        if (myVote == Vote.YUM) {
            plate.setYumCount(plate.getYumCount() - 1);
        }
        plate.setYuckCount(plate.getYuckCount()+1);
        myVote = Vote.YUCK;

        PMPApplication.get().getModelAPI().voteFor(plate.getId(), 0, myVote);

        updateUI();
    }

    @OnClick(R.id.detailsButton)
    public void onClickDetailsButton() {
        FoodStatsActivity.startWith(this, plate.getId());
    }

    @OnClick(R.id.detailsPanel)
    public void onClickDetailsPanel() {
        showMorePanel.setVisibility(View.VISIBLE);
        detailsPanel.setVisibility(View.GONE);
    }

    private void updateUI() {
        yumyuckLabel.setText(plate.getYumCount() + " Yums | " + plate.getYuckCount() + " Yucks");
        commentsLabel.setText(PMPApplication.get().getModelAPI().getCommentsFor(plate.getId()).size() + " Comments");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_overview);

        ButterKnife.inject(this);

        long plateId = getIntent().getLongExtra(KEY_PLATEID, -1);
        if (plateId < 0) {
            Toast.makeText(this, "Could not load that item", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        plate = PMPApplication.get().getModelAPI().getPlate(plateId);
        if (plate == null) {
            Toast.makeText(this, "Could not load that item", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        plate.applyImageTo(this, imageView);
        showMorePanel.setVisibility(View.GONE);
        myVote = PMPApplication.get().getModelAPI().getVoteFor(plate.getId(), 0);
        if (myVote != null) {
            switch(myVote) {
                case YUM: yumButton.setChecked(true); break;
                case YUCK: yuckButton.setChecked(true); break;
            }
        }

        nameLabel.setText(plate.getName());
        locationLabel.setText(plate.getLocation() + " ... see more");
        showMoreNameLabel.setText(plate.getName());
        showMoreLocationLabel.setText(plate.getLocation());
        showMoreNotesLabel.setText(plate.getNotes());

        updateUI();
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
