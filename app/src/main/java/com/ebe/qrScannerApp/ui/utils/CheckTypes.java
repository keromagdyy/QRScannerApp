package com.ebe.qrScannerApp.ui.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.ebe.qrScannerApp.R;

public class CheckTypes {

    public static String detectContentType(String content) {
        if (content == null || content.trim().isEmpty()) {
            return "Unknown";
        }

        content = content.trim().toLowerCase();

        if (content.startsWith("tel:") || android.util.Patterns.PHONE.matcher(content).matches()) {
            return "Phone Number";
        } else if (content.startsWith("mailto:") || android.util.Patterns.EMAIL_ADDRESS.matcher(content).matches()) {
            return "Email";
        } else if (content.startsWith("sms:") || content.startsWith("smsto:")) {
            return "SMS";
        } else if (content.startsWith("geo:")) {
            return "Location";
        } else if (content.startsWith("wifi:")) {
            return "Wi-Fi Configuration";
        } else if (content.startsWith("begin:vcard")) {
            return "Contact (vCard)";
        } else if (content.startsWith("begin:vevent")) {
            return "Calendar Event";
        } else if (content.startsWith("bitcoin:")) {
            return "Bitcoin Address";
        } else if (content.startsWith("upi://") || content.startsWith("paytm://")) {
            return "Payment Link";
        } else if (content.startsWith("intent:")) {
            return "App Link";
        } else if (android.util.Patterns.WEB_URL.matcher(content).matches()) {
            return "URL";
        } else if (isValidIPAddress(content)) {
            return "IP Address";
        } else {
            return "Text";
        }
    }

    private static boolean isValidIPAddress(String ip) {
        return ip.matches(
                "^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\\.){3}" +
                        "(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)$") ||
                ip.matches("([a-fA-F0-9:]+:+)+[a-fA-F0-9]+");
    }

    public static void checkContentResult(Context context, String content) {
        String result = detectContentType(content);
        switch (result) {
            case "Phone Number":
                Utils.callPhoneNumber(context, content);
            break;

            case "Email":
                Utils.sendEmail(context, content);
                break;

            case "SMS":
                Utils.sendSms(context, content);
                break;

            case "Location":
                Utils.openLocationInMaps(context, content);
                break;

            case "URL":
                Utils.openUrlLink(context, content);
                break;
        }
    }

    public static void checkContentForView(String content, ImageView img, View view) {
        String result = detectContentType(content);
        switch (result) {
            case "Phone Number":
                view.setVisibility(View.VISIBLE);
                img.setImageResource(R.drawable.ic_phone);
            break;

            case "Email":
                view.setVisibility(View.VISIBLE);
                img.setImageResource(R.drawable.ic_email);
                break;

            case "SMS":
                view.setVisibility(View.VISIBLE);
                img.setImageResource(R.drawable.ic_sms);
                break;

            case "Location":
                view.setVisibility(View.VISIBLE);
                img.setImageResource(R.drawable.ic_location);
                break;

            case "URL":
                view.setVisibility(View.VISIBLE);
                img.setImageResource(R.drawable.ic_link);
                break;
            default:
                view.setVisibility(View.GONE);
                break;
        }
    }
}
