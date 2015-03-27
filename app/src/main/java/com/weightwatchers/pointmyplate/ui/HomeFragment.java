package com.weightwatchers.pointmyplate.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.weightwatchers.pointmyplate.PMPApplication;
import com.weightwatchers.pointmyplate.R;
import com.weightwatchers.pointmyplate.model.Plate;
import com.weightwatchers.pointmyplate.model.Vote;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    @InjectView(R.id.imageList)
    RecyclerView imageList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View panel = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.inject(this, panel);

        imageList.setAdapter(new ImageViewAdapter());
        imageList.setLayoutManager(new LinearLayoutManager(getActivity()));

        return panel;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class ImageViewAdapter extends RecyclerView.Adapter<ImageRowHolder> {

        private List<Plate> plateList;

        public ImageViewAdapter() {
            plateList = PMPApplication.get().getModelAPI().getPlates();
        }

        @Override
        public ImageRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_row, null);
            return new ImageRowHolder(row, plateList.get(i));
        }

        @Override
        public void onBindViewHolder(ImageRowHolder imageRowHolder, int i) {

            Plate plate = plateList.get(i);
            imageRowHolder.image.setImageDrawable(getResources().getDrawable(plate.getResourceImage()));
            imageRowHolder.nameLabel.setText(plate.getName());
            imageRowHolder.yuckButton.setChecked(PMPApplication.get().getModelAPI().getVoteFor(plate.getId(), 0) == Vote.YUCK);
            imageRowHolder.yumButton.setChecked(PMPApplication.get().getModelAPI().getVoteFor(plate.getId(), 0) == Vote.YUM);
        }

        @Override
        public int getItemCount() {
            return plateList.size();
        }
    }

    public  class ImageRowHolder extends RecyclerView.ViewHolder {

        private Plate plate;

        @InjectView(R.id.image)
        ImageView image;

        @InjectView(R.id.nameLabel)
        TextView nameLabel;

        @InjectView(R.id.yumButton)
        RadioButton yumButton;

        @InjectView(R.id.yuckButton)
        RadioButton yuckButton;

        public ImageRowHolder(View view, Plate plate) {
            super(view);
            this.plate = plate;

            ButterKnife.inject(this, view);
        }

        @OnClick(R.id.yumButton)
        public void OnClickYumButton() {
            PMPApplication.get().getModelAPI().voteFor(plate.getId(), 0, Vote.YUM);
        }

        @OnClick(R.id.yuckButton)
        public void OnClickYuckButton() {
            PMPApplication.get().getModelAPI().voteFor(plate.getId(), 0, Vote.YUCK);
        }

        @OnClick(R.id.image)
        public void onClickImage() {
            FoodOverviewActivity.startWith(getActivity(), plate.getId());
        }

        @OnClick(R.id.detailsButton)
        public void onClickDetailsButton() {
            FoodStatsActivity.startWith(getActivity(), plate.getId());
        }
    }
}
