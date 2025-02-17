package com.ebe.qrScannerApp.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String getDatetimeNow() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return now.format(formatter);
    }

    public static void openUrlLink(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(extractURL(url)));
        context.startActivity(intent);
    }

    private static String extractURL(String input) {
        String urlRegex = "https?://(?:www\\.)?\\S+";

        Pattern pattern = Pattern.compile(urlRegex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group();
        } else {
            return "";
        }
    }

}
