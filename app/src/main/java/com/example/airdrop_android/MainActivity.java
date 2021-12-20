package com.example.airdrop_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    ImageButton addFileButton;
    ConstraintLayout settings;
    ImageButton closeSetButton;

    Animation animFlipInForward;
    Animation animFlipOutForward;
    Animation animFlipInBackward;
    Animation animFlipOutBackward;
    Animation settings_open;
    Animation setting_close;

    private boolean check;
    UserActivity user;
    private boolean isSettingOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.flipin_reverse, R.anim.flipout_reverse);

        addFileButton = findViewById(R.id.addFile_button);
        closeSetButton = findViewById(R.id.closeSettings_button);
        settings = findViewById(R.id.settings);

        animFlipInForward = AnimationUtils.loadAnimation(this,R.anim.flipin);
        animFlipOutForward = AnimationUtils.loadAnimation(this,R.anim.flipout);
        animFlipInBackward = AnimationUtils.loadAnimation(this,R.anim.flipin_reverse);
        animFlipOutBackward = AnimationUtils.loadAnimation(this,R.anim.flipout_reverse);
        settings_open = AnimationUtils.loadAnimation(this, R.anim.settings_open);
        setting_close = AnimationUtils.loadAnimation(this,R.anim.settings_close);

        check = false;
        isSettingOpen = false;
        user = new UserActivity();
    }



    private void swipeRight()
    {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return gestureDetector.onTouchEvent(event);
    }

    GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener()
    {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            float sensitivity = 250;

            if ((e2.getX() - e1.getX()) > sensitivity)
            {
                swipeRight();
            }

            return true;

        }

    };

    GestureDetector gestureDetector = new GestureDetector(getBaseContext(), simpleOnGestureListener);




    public void addFile(View view) {
        this.check = true;
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra("boolean", check);
        startActivity(intent);
    }


    public void closeSettings(View view) {
        if (this.isSettingOpen) {
            settings.setEnabled(false);
            closeSetButton.setEnabled(false);
            settings.setClickable(false);
            settings.setFocusable(false);
            settings.setFocusableInTouchMode(false);
            settings.startAnimation(setting_close);
            this.isSettingOpen = false;
        }
    }

    public void openSettings(View view) {
        this.isSettingOpen = true;
        settings.setEnabled(true);
        closeSetButton.setEnabled(true);
        settings.startAnimation(settings_open);
    }
}