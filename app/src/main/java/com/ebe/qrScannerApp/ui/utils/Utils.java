package com.ebe.qrScannerApp.ui.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.Toast;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String getDatetimeNow() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
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

    public static void sendEmail(Context context, String uriText) {
        Uri uri = Uri.parse(uriText);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void callPhoneNumber(Context context, String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(phoneNumber));
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "Error opening dialer", Toast.LENGTH_SHORT).show();
        }
    }

    public static void sendSms(Context context, String smsUri) {
        if (smsUri != null && smsUri.toLowerCase().startsWith("smsto:")) {
            String[] parts = smsUri.substring(6).split(":", 2);
            if (parts.length == 2) {
                String phoneNumber = parts[0];
                String message = parts[1];
                String smsUriFormatted = "smsto:" + phoneNumber;

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse(smsUriFormatted));
                intent.putExtra("sms_body", message);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Invalid SMSTO format", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Invalid SMSTO URI", Toast.LENGTH_SHORT).show();
        }
    }

    public static void openLocationInMaps(Context context, String url) {
        try {
            Uri mapUri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
            intent.setPackage("com.google.android.apps.maps");
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                context.startActivity(browserIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showDialog(Context context, String title, String message, int icon, Runnable onOkClick) {
        Drawable dialogIcon = context.getResources().getDrawable(icon);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setIcon(icon)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onOkClick.run();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
