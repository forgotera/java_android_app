package com.example.android.cookies;

import android.annotation.TargetApi;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the cookie should be eaten.
     */
    public void eatCookie(View view) {
        ImageView afterCooces = (ImageView) findViewById(R.id.android_cookie_image_view);
        TextView afterText = (TextView) findViewById(R.id.status_text_view);

        afterCooces.setImageResource(R.drawable.after_cookie);
        afterText.setText("i'm fo full");


    }
}