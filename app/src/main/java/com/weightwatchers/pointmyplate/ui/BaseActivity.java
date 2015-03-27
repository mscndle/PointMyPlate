package com.weightwatchers.pointmyplate.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.weightwatchers.pointmyplate.R;

/**
 * Created by trevor on 3/26/2015.
 */
public class BaseActivity extends ActionBarActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.titlebar);

        getSupportActionBar().getCustomView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, NavActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

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
