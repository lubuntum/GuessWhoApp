package com.lubuntum.guesswhoapp.utils;

import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;

public class DialogAdapt {
    public static void adaptByScreenSize(DialogFragment dF, double occupiedArea){
        Window window = dF.getDialog().getWindow();
        if (window == null) return;
        int width = (int)(dF.getResources().getDisplayMetrics().widthPixels *  occupiedArea);
        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
    }
    public static void adaptByScreenSize(Window window, Context context, double occupiedArea){
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * occupiedArea);
        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
