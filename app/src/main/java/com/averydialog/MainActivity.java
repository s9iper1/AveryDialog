package com.averydialog;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.avery_dialog.AveryDialog;

public class MainActivity extends AppCompatActivity {

    private AveryDialog averyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        averyDialog = new AveryDialog(MainActivity.this);
        averyDialog.setTitle("This is a test");
        averyDialog.setPositiveText("Rate");
        averyDialog.setNegativeText("Later");
//        averyDialog.showOnceADay();
//        averyDialog.showTwiceADay();
//        averyDialog.setDialogBackgroundColor();
//        averyDialog.setTitleTextColor();
        averyDialog.setRatingBarStarsColor("#ff8000");
        averyDialog.setPositiveButtonBackground(R.drawable.button_background_selected, Color.WHITE);
        averyDialog.setNegativeButtonBackground(R.drawable.simple_button_background, Color.BLACK);
        averyDialog.show();
    }
}
