package com.weightwatchers.pointmyplate;

import android.util.SparseArray;

import com.weightwatchers.pointmyplate.model.Comment;
import com.weightwatchers.pointmyplate.model.Plate;
import com.weightwatchers.pointmyplate.model.Vote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by trevor on 3/26/2015.
 */
public class ModelAPI {

    private List<Plate> plateList = new ArrayList<>();
    private SparseArray<List<Comment>> commentMap = new SparseArray<>();
    private Map<Integer, Map<Long, Vote>> plateUserVoteMap = new HashMap<>();

    public ModelAPI() {

        // Install some mock data
        plateList.addAll(Arrays.asList(new Plate[]{
                        new Plate("Steak Skillet Fry", "My mom made this for us tonight", "Mom's house", R.drawable.steak),
                        new Plate("Bagel breakfast", "I had this at the local bakery", "Bagel Bakery of Bakertown", R.drawable.bagels),
                        new Plate("Fish something", "Looks grilled, so that's healthier, right?", "Bob's Fish Grill", R.drawable.fish),
                        new Plate("Salad for Kings", "Not sure what those red things are", "Dave and Baxter's", R.drawable.salad),
                        new Plate("Super yummy dessert", "I'm not even sure what this is called, but it was yummy", "Aunt Peggy's", R.drawable.pancake)

                })
        );

        List<Comment> commentList = new ArrayList<Comment>();
        commentList.addAll(Arrays.asList(new Comment[]{
            new Comment(1, "How much of that did you eat?"),
                new Comment(0, "All of it")
        }));
        commentMap.put(1, commentList);
    }

    public void addPlate(Plate plate) {
        plateList.add(0, plate);
    }

    public Vote getVoteFor(int plateId, long userId) {
        Map<Long, Vote> userVoteMap = plateUserVoteMap.get(plateId);
        if (userVoteMap == null) {
            return Vote.NONE;
        }

        Vote vote = userVoteMap.get(userId);
        return vote != null ? vote : Vote.NONE;
    }

    public void voteFor(int plateId, long userId, Vote vote) {
        Map<Long, Vote> userVoteMap = plateUserVoteMap.get(plateId);
        if (userVoteMap == null) {
            userVoteMap = new HashMap<Long, Vote>();
            plateUserVoteMap.put(plateId, userVoteMap);
        }

        userVoteMap.put(userId, vote);
    }

    public Plate getPlate(long id) {
        for (Plate plate : plateList) {
            if (plate.getId() == id) {
                return plate;
            }
        }
        return null;
    }

    public List<Plate> getPlates() {
        return Collections.unmodifiableList(plateList);
    }

    public List<Comment> getCommentsFor(int plateId) {
        List<Comment> commentList = commentMap.get(plateId);
        if (commentList == null) {
            commentList = Collections.emptyList();
        }
        return commentList;
    }

    public void addComment(int plateId, Comment comment) {
        List<Comment> commentList = commentMap.get(plateId);
        if (commentList == null) {
            commentList = new ArrayList<>();
        }
        commentList.add(comment);
    }

}
