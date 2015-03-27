package com.weightwatchers.pointmyplate.model;

import com.weightwatchers.pointmyplate.util.MockUtil;

import java.io.File;
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
}
