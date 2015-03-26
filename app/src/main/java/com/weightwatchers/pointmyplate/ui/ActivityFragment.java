package com.weightwatchers.pointmyplate.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weightwatchers.pointmyplate.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityFragment extends Fragment {

    @InjectView(R.id.myplatesCountLabel)
    TextView myplatesCountLabel;

    @InjectView(R.id.myreviewsCountLabel)
    TextView myreviewsCountLabel;

    @InjectView(R.id.notificationsCountLabel)
    TextView notificationsCountLabel;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivityFragment newInstance() {
        ActivityFragment fragment = new ActivityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);;

        ButterKnife.inject(this, view);

        return view;
    }

    @OnClick(R.id.myplatesPanel)
    public void onClickMyPlates() {
        startActivity(new Intent(getActivity(), MyPlatesActivity.class));
    }

    @OnClick(R.id.myreviewsPanel)
    public void onClickMyReviews() {
        startActivity(new Intent(getActivity(), MyPlatesActivity.class));
    }

    @OnClick(R.id.notificationsPanel)
    public void onClickNotifications() {
        startActivity(new Intent(getActivity(), NotificationsActivity.class));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
