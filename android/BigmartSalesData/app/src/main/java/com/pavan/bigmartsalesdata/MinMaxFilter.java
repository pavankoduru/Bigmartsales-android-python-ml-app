package com.pavan.bigmartsalesdata;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MinMaxFilter implements InputFilter {

    private final double minVal;
    private final double maxVal;
    private final Context context;

    public MinMaxFilter(MainActivity mainActivity, int minVal, int maxVal) {
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.context = mainActivity;
    }

    public MinMaxFilter(MainActivity mainActivity, String minVal, String maxVal) {
        this.minVal = Double.parseDouble(minVal);
        this.maxVal = Double.parseDouble(maxVal);
        this.context = mainActivity;
    }

    public MinMaxFilter(MainActivity mainActivity, double v, double v1) {
        this.minVal = v;
        this.maxVal = v1;
        this.context = mainActivity;
    }


    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            double input = Double.parseDouble(dest.toString() + source.toString());
            if (isInRange(minVal, maxVal, input)) {
                return null;
            } else {
                Toast.makeText(context, "Enter value between " + minVal + " and " + maxVal, Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException nfe) {
        }
        return "";
    }

    private boolean isInRange(double a, double b, double c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}