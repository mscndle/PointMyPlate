package com.weightwatchers.pointmyplate.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

    @InjectView(R.id.commentField)
    EditText commentField;

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

        commentField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                        keyCode == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    String commentStr = commentField.getText().toString().trim();
                    if (commentStr.length() == 0) {
                        return true;
                    }

                    Comment comment = new Comment(plate.getId(), commentStr);

                    PMPApplication.get().getModelAPI().addComment(plate.getId(), comment);
                    ((CommentAdapter)commentList.getAdapter()).notifyDataSetChanged();

                    commentField.setText("");

                    updateUI();

                    return true;
                }
                return false;
            }
        });

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

    private class PointsPagerAdapter extends FragmentPagerAdapter {

        public PointsPagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            return PointsFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 24;
        }
    }

    public static class PointsFragment extends Fragment {

        private int position;

        public static PointsFragment newInstance(int position) {
            PointsFragment frag = new PointsFragment();
            Bundle args = new Bundle();
            args.putInt("points", position);
            frag.setArguments(args);
            return frag;
        }

        public PointsFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            position = getArguments().getInt("points");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View panel = getLayoutInflater(null).inflate(R.layout.point_cell, null);
            ((TextView)panel.findViewById(R.id.pointLabel)).setText(String.valueOf(position));

            return panel;
        }
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
