package com.weightwatchers.pointmyplate.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.weightwatchers.pointmyplate.PMPApplication;
import com.weightwatchers.pointmyplate.R;
import com.weightwatchers.pointmyplate.model.Plate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class UploadPhotoActivity extends BaseActivity {

    private static final String KEY_FILENAME = "filename";

    private File imageFile;

    @InjectView(R.id.image)
    ImageView imageView;

    @InjectView(R.id.nameLabel)
    TextView nameLabel;

    @InjectView(R.id.locationLabel)
    TextView locationLabel;

    @InjectView(R.id.notesLabel)
    TextView notesLabel;

    public static void startWith(Activity currentActivity, File imageFile) {

        Intent intent = new Intent(currentActivity, UploadPhotoActivity.class);
        intent.putExtra(KEY_FILENAME, imageFile);

        currentActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        imageFile = (File) getIntent().getSerializableExtra(KEY_FILENAME);

        if (imageFile == null || !imageFile.exists()) {
            Toast.makeText(this, "Couldn't find image locally", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ButterKnife.inject(this);

        try {
            imageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(imageFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.saveButton)
    public void onClickSaveButton() {

        String name = nameLabel.getText().toString();
        String location = locationLabel.getText().toString();
        String notes = notesLabel.getText().toString();

        PMPApplication.get().getModelAPI().addPlate(new Plate(name, location, notes,  imageFile));


        Intent intent = new Intent(this, NavActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upload_photo, menu);
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
