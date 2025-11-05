package com.connect.testbannerexample.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.connect.testbannerexample.R;

public class AlertView extends FrameLayout {
    public AlertView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.aler_view, this);
    }


}
