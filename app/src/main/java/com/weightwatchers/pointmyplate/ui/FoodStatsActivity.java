package com.weightwatchers.pointmyplate.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.weightwatchers.pointmyplate.PMPApplication;
import com.weightwatchers.pointmyplate.R;
import com.weightwatchers.pointmyplate.model.Comment;
import com.weightwatchers.pointmyplate.model.Plate;
import com.weightwatchers.pointmyplate.model.Vote;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FoodStatsActivity extends BaseActivity {

    private static final String KEY_PLATEID = "plateid";


    @InjectView(R.id.yumButton)
    RadioButton yumButton;

    @InjectView(R.id.yuckButton)
    RadioButton yuckButton;

    @InjectView(R.id.detailsButton)
    View detailsButton;

    @InjectView(R.id.yumyuckLabel)
    TextView yumyuckLabel;

    @InjectView(R.id.commentsLabel)
    TextView commentsLabel;

    @InjectView(R.id.commentList)
    ListView commentList;

    private Plate plate;
    private Vote myVote;

    public static void startWith(Activity currentActivity, int plateId) {

        Intent intent = new Intent(currentActivity, FoodStatsActivity.class);
        intent.putExtra(KEY_PLATEID, plateId);

        currentActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_stats);

        int plateId = getIntent().getIntExtra(KEY_PLATEID, -1);
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

        ButterKnife.inject(this);

        detailsButton.setVisibility(View.GONE);

        myVote = PMPApplication.get().getModelAPI().getVoteFor(plate.getId(), 0);

        yuckButton.setChecked(PMPApplication.get().getModelAPI().getVoteFor(plate.getId(), 0) == Vote.YUCK);
        yumButton.setChecked(PMPApplication.get().getModelAPI().getVoteFor(plate.getId(), 0) == Vote.YUM);

        commentList.setAdapter(new CommentAdapter(PMPApplication.get().getModelAPI().getCommentsFor(plate.getId())));

        updateUI();
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

    private void updateUI() {
        yumyuckLabel.setText(plate.getYumCount() + " Yums | " + plate.getYuckCount() + " Yucks");
        commentsLabel.setText(PMPApplication.get().getModelAPI().getCommentsFor(plate.getId()).size() + " Comments");

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

    private class CommentAdapter extends ArrayAdapter<Comment> {

        public CommentAdapter(List<Comment> commentList) {
            super(FoodStatsActivity.this, 0, commentList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.comment_row, null);
            }

            Comment comment = getItem(position);

            ((TextView)convertView.findViewById(R.id.commentLabel)).setText(comment.getComment());

            return convertView;
        }
    }
}
