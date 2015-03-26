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

import com.weightwatchers.pointmyplate.R;

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

        @Override
        public ImageRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_row, null);
            return new ImageRowHolder(row, i);
        }

        @Override
        public void onBindViewHolder(ImageRowHolder imageRowHolder, int i) {

            int[] imageIds = {
                    R.drawable.bagels,
                    R.drawable.pancake,
                    R.drawable.fish,
                    R.drawable.chicken
            };

            imageRowHolder.image.setImageDrawable(getResources().getDrawable(imageIds[i]));
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }

    public  class ImageRowHolder extends RecyclerView.ViewHolder {

        private long foodId;

        @InjectView(R.id.image)
        ImageView image;

        public ImageRowHolder(View view, int foodId) {
            super(view);
            this.foodId = foodId;

            ButterKnife.inject(this, view);
        }

        @OnClick(R.id.image)
        public void onClickImage() {
            FoodOverviewActivity.startWith(getActivity(), foodId);
        }
    }
}
