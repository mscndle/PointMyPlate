package com.weightwatchers.pointmyplate.model;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.weightwatchers.pointmyplate.R;
import com.weightwatchers.pointmyplate.util.MockUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by trevor on 3/26/2015.
 */
public class Plate {

    private static int nextPlateId = 1;

    private int id;

    private String name;

    private String notes;

    private String location;

    private File localImage;

    private int resourceImage;

    private int yuckCount = MockUtil.nextInt(50);

    private int yumCount = MockUtil.nextInt(200) + 50;

    private List<Integer> pointList = new LinkedList<>();

    public Plate() {}

    public Plate(String name, String notes, String location, int resourceImage) {
        this.id = nextPlateId++;
        this.name = name;
        this.notes = notes;
        this.location = location;
        this.resourceImage = resourceImage;
    }

    public Plate(String name, String notes, String location, File localImage) {
        this.id = nextPlateId++;
        this.name = name;
        this.notes = notes;
        this.location = location;
        this.localImage = localImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public File getLocalImage() {
        return localImage;
    }

    public void setLocalImage(File localImage) {
        this.localImage = localImage;
    }

    public int getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(int resourceImage) {
        this.resourceImage = resourceImage;
    }

    public int getYuckCount() {
        return yuckCount;
    }

    public void setYuckCount(int yuckCount) {
        this.yuckCount = yuckCount;
    }

    public int getYumCount() {
        return yumCount;
    }

    public void setYumCount(int yumCount) {
        this.yumCount = yumCount;
    }

    public void addPointVote(int points) {
        pointList.add(points);
    }

    public void applyImageTo(Context context, ImageView imageView) {
        if (resourceImage > 0) {
            imageView.setImageDrawable(context.getResources().getDrawable(resourceImage));
            return;
        }
        if (localImage != null && localImage.exists()) {
            try {
                imageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(localImage)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        // Default for now
        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.steak));
    }
}
