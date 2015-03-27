package com.weightwatchers.pointmyplate;

import android.app.Application;

/**
 * Created by trevor on 3/26/2015.
 */
public class PMPApplication extends Application {

    private static PMPApplication instance;

    private ModelAPI modelAPI;

    public static PMPApplication get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public ModelAPI getModelAPI() {
        if (modelAPI == null) {
            modelAPI= new ModelAPI();
        }

        return modelAPI;
    }
}
