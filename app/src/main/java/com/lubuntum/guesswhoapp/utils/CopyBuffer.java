package com.lubuntum.guesswhoapp.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class CopyBuffer {
    public static void addTextToBuffer(Context context, String text){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("key", text);
        clipboard.setPrimaryClip(data);
    }
}
