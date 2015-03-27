package com.weightwatchers.pointmyplate.util;

import java.util.Random;

/**
 * Created by trevor on 3/27/2015.
 */
public class MockUtil {

    private static Random random = new Random();

    public static int nextInt(int range) {
        return random.nextInt(range);
    }
}
