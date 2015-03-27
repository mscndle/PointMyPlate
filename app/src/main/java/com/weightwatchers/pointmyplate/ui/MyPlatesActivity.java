package com.weightwatchers.pointmyplate.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.weightwatchers.pointmyplate.PMPApplication;
import com.weightwatchers.pointmyplate.R;
import com.weightwatchers.pointmyplate.model.Plate;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MyPlatesActivity extends BaseActivity {

    @InjectView(R.id.imageList)
    RecyclerView imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plates);

        ButterKnife.inject(this);

        imageList.setAdapter(new MyPlatesAdapter());
        imageList.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_posts, menu);
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

    private class MyPlatesAdapter extends RecyclerView.Adapter<ImageRowHolder> {

        private List<Plate> plateList;

        public MyPlatesAdapter() {
            plateList = PMPApplication.get().getModelAPI().getPlates();
        }

        @Override
        public ImageRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myplates_image_row, null);
            return new ImageRowHolder(row);
        }

        @Override
        public void onBindViewHolder(ImageRowHolder imageRowHolder, int i) {

            imageRowHolder.plate = plateList.get(i);
            imageRowHolder.plate.applyImageTo(MyPlatesActivity.this, imageRowHolder.image);
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

        public ImageRowHolder(View view) {
            super(view);
            this.plate = plate;

            ButterKnife.inject(this, view);
        }

        @OnClick(R.id.image)
        public void onClickImage() {
            FoodOverviewActivity.startWith(MyPlatesActivity.this, plate.getId());
        }
    }

}
