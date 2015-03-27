package com.weightwatchers.pointmyplate.model;

/**
 * Created by trevor on 3/26/2015.
 */
public class Comment {

    private static long nextCommentId;

    private long id;

    private long userId;

    private String comment;

    public Comment() {}

    public Comment(long userId, String comment) {
        id = nextCommentId++;
        this.userId = userId;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
