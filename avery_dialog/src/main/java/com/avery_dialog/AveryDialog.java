package com.avery_dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AveryDialog extends Dialog {

    private AppCompatTextView title;
    private AppCompatRatingBar ratingBar;
    private String dialogTitle = "";
    private RelativeLayout dialogBackground;
    private int bgColor = 0;
    private Activity mActivity;
    private AppCompatButton later;
    private AppCompatButton rate;
    private boolean showOnceAday = false;
    private boolean showTwiceADay = false;
    private int positiveButtonDrawable = 0;
    private int negativeButtonDrawable = 0;
    private int titleTextColor = 0;
    private String positiveText = "";
    private String negativeText = "";
    private int positiveTextColor = 0;
    private int negativeTextColor = 0;
    private String colorCode = "";

    public AveryDialog(@NonNull Activity activity) {
        super(activity);
        mActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        setContentView(R.layout.dialog_main);
        title =  findViewById(R.id.title);
        ratingBar = findViewById(R.id.rating);
        dialogBackground = findViewById(R.id.dialog_main_layout);
        later = findViewById(R.id.later);
        rate = findViewById(R.id.rate);
        if (!positiveText.equals("")) {
            rate.setText(positiveText);
        }
        if (!negativeText.equals("")) {
            later.setText(negativeText);
        }
        if (positiveTextColor != 0) {
            rate.setTextColor(positiveTextColor);
        }
        if (negativeTextColor != 0) {
            later.setTextColor(negativeTextColor);
        }
        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ratingBar.getRating() >= 4) {
                    dismiss();
                } else {
                    dismiss();
                }

            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ratingBar.getRating() >= 4) {
                    Log.i("TAG", mActivity.getPackageName());
                    final String appPackageName = mActivity.getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                } else {
                    Toast.makeText(getContext(), "Thank you :)", Toast.LENGTH_SHORT).show();
                }
                dismiss();


            }
        });
        if (bgColor != 0) {
            dialogBackground.setBackgroundColor(bgColor);
        }
        LayerDrawable drawableRating = (LayerDrawable) ratingBar.getProgressDrawable();
        setRatingStarColor(drawableRating.getDrawable(2), Color.parseColor(colorCode));

        ratingBar.setIsIndicator(false);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            }
        });
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        if (!dialogTitle.equals("")) {
            title.setText(dialogTitle);
        } else {
            title.setText("Rate Us");
        }
        if (positiveButtonDrawable != 0) {
            rate.setBackgroundDrawable(mActivity.getApplicationContext().getResources()
                    .getDrawable(positiveButtonDrawable));
        }
        if (negativeButtonDrawable != 0) {
            later.setBackgroundDrawable(mActivity.getApplicationContext().getResources()
                    .getDrawable(negativeButtonDrawable));
        }
        if (titleTextColor != 0) {
            title.setTextColor(titleTextColor);
        }
        int orientation = mActivity.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setLayout((4 * width)/6, (6 * height)/10);
        } else {
            getWindow().setLayout((6 * width)/6, (4 * height)/10);
        }
    }

    private void setRatingStarColor(Drawable drawable, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DrawableCompat.setTint(drawable, color);
        }
        else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

    public void setTitle(String title) {
        dialogTitle = title;
    }

    public void setDialogBackgroundColor(int color) {
        bgColor = color;
    }

    public void showOnceADay() {
        showOnceAday = true;
    }

    public void setRatingBarStarsColor(String hexColor) {
        colorCode = hexColor;
    }


    public void showTwiceADay() {
        showTwiceADay = true;
    }

    public void setPositiveButtonBackground(int drawable, int textColor) {
        positiveButtonDrawable = drawable;
        positiveTextColor = textColor;
    }

    public void setNegativeButtonBackground(int drawable, int textColor) {
        negativeButtonDrawable = drawable;
        negativeTextColor = textColor;


    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    public void setPositiveText(String text) {
        positiveText = text;
    }

    public void setNegativeText(String text) {
        negativeText = text;
    }

    @Override
    public void show() {
        int count   = getTodayCount();
        if (showOnceAday) {
            if (getTodayCount() < 1) {
                saveTodayCount((count+1));
                super.show();
            }
        } else if (showTwiceADay) {
            if (getTodayCount() < 2) {
                saveTodayCount((count+1));
                super.show();
            }
        } else {
            super.show();
        }
    }

    protected SharedPreferences getPreferencesManager() {
        return PreferenceManager.getDefaultSharedPreferences(mActivity.getApplicationContext());
    }

    private String getToday() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        return df.format(c);
    }

    protected void saveTodayCount(int count) {
        SharedPreferences preferences = getPreferencesManager();
        preferences.edit().putInt(getToday(), count).apply();
    }

    protected int getTodayCount() {
        SharedPreferences preferences = getPreferencesManager();
        return preferences.getInt(getToday(), 0);
    }
}
