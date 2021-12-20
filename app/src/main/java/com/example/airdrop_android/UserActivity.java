package com.example.airdrop_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserActivity extends MainActivity {

    ImageView uploadImage;
    TextView infoText;
    private boolean check;
    MainActivity main;
    Bitmap savedImage;
    Animation send_file_animation;
    Animation get_file_animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_user);

        overridePendingTransition(R.anim.flipin, R.anim.flipout);

        send_file_animation = AnimationUtils.loadAnimation(this, R.anim.send_file);
        get_file_animation = AnimationUtils.loadAnimation(this,R.anim.get_file);

        uploadImage = findViewById(R.id.uploadImage);
        infoText = findViewById(R.id.InfoText);

        Intent intent = getIntent();
        check = intent.getBooleanExtra("boolean",  check);

        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        if (check) {
            startActivityForResult(photoPickerIntent, 1);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        switch (requestCode)
        {
            case 1:
                if (resultCode == RESULT_OK)
                {
                    try
                    {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        uploadImage.setImageBitmap(selectedImage);
                        infoText.setVisibility(View.GONE);
                        uploadImage.startAnimation(get_file_animation);
                        savedImage = selectedImage;

                    }
                    catch (FileNotFoundException ex)
                    {
                        ex.printStackTrace();
                    }

                }
                break;
        }
    }


    private void swipeLeft()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void swipeUp()
    {
        uploadImage.startAnimation(send_file_animation);
        Toast.makeText(this,"Отправлено",Toast.LENGTH_SHORT).show();
        infoText.setVisibility(View.VISIBLE);
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
            if ((e1.getX() - e2.getX()) > sensitivity)
            {
                swipeLeft();
            }
            else if ((e1.getY() - e2.getY()) > sensitivity)
            {
                swipeUp();
            }
            return true;

        }

    };

    GestureDetector gestureDetector = new GestureDetector(getBaseContext(), simpleOnGestureListener);

}
