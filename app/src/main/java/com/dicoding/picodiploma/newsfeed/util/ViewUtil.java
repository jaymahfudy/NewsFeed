package com.dicoding.picodiploma.newsfeed.util;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.dicoding.picodiploma.newsfeed.R;
import com.dicoding.picodiploma.newsfeed.activity.MainActivity;

public class ViewUtil {

    public static void setVisibility(View view_1, View view_2){
        view_1.setVisibility(View.GONE);
        view_2.setVisibility(View.VISIBLE);
    }

    public static void showToast(Context context){
        Toast.makeText(context, R.string.check_ur_connection, Toast.LENGTH_SHORT).show();
    }

    /**
     * Changes the icon of the drawer to back
     */
    public static void showBackButton(Context context) {
        if (context instanceof MainActivity)
            ((MainActivity) context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
