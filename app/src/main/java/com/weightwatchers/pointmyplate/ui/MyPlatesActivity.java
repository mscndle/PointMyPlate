package com.weightwatchers.pointmyplate.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.weightwatchers.pointmyplate.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MyPlatesActivity extends BaseActivity {

    int[] imageIds = {
            R.drawable.bagels,
            R.drawable.pancake,
            R.drawable.fish,
            R.drawable.chicken
    };

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

        @Override
        public ImageRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myplates_image_row, null);
            return new ImageRowHolder(row, i);
        }

        @Override
        public void onBindViewHolder(ImageRowHolder imageRowHolder, int i) {

            imageRowHolder.image.setImageDrawable(getResources().getDrawable(imageIds[i]));
        }

        @Override
        public int getItemCount() {
            return imageIds.length;
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
            FoodOverviewActivity.startWith(MyPlatesActivity.this, foodId);
        }
    }

}
