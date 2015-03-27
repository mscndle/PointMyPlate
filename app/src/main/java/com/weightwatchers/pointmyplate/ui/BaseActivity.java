package com.weightwatchers.pointmyplate.ui;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.weightwatchers.pointmyplate.R;

/**
 * Created by trevor on 3/26/2015.
 */
public class BaseActivity extends ActionBarActivity {

    private Handler handler = new Handler();

    protected void hideFocus() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                EditText focusHack = (EditText) findViewById(R.id.focusHack);
                focusHack.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                focusHack.requestFocus();
                focusHack.setInputType(InputType.TYPE_NULL);
            }
        });
    }


}
