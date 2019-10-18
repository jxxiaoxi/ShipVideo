package com.anding.shipvideo.activity;

import android.app.Activity;
import android.widget.Toast;

public class BaseActivity extends Activity {

    public void networkChange(boolean available) {

    }

    public void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
