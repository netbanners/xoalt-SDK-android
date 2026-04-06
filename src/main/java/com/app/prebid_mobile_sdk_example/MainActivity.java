package com.app.prebid_mobile_sdk_example;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.prebid.mobile.AdSize;
import org.prebid.mobile.BannerAdUnit;
import org.prebid.mobile.PrebidMobile;
import org.prebid.mobile.addendum.AdViewUtils;
import org.prebid.mobile.addendum.PbFindSizeError;
import org.prebid.mobile.api.data.AdFormat;
import org.prebid.mobile.api.data.InitializationStatus;
import org.prebid.mobile.api.exceptions.AdException;
import org.prebid.mobile.api.rendering.BannerView;
import org.prebid.mobile.api.rendering.listeners.BannerViewListener;
import org.prebid.mobile.rendering.utils.url.ActionNotResolvedException;
import org.prebid.mobile.rendering.utils.url.UrlHandler;
import org.prebid.mobile.rendering.utils.url.action.DeepLinkPlusAction;

import java.util.ArrayList;

public class MainActivity extends Activity {
    String TAG = "PrebidCustomInit";

    BlockView1 blockView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // PrebidMobile.setLogLevel(PrebidMobile.LogLevel.DEBUG);
     //  PrebidMobile.setPbsDebug(true);
        PrebidMobile.setPrebidServerAccountId("0689a263-318d-448b-a3d4-b02e8a709d9d");
        PrebidMobile.initializeSdk(this,
                "https://hb.xoalt.com/x-simb/"

                ,status -> {
                    if (status == InitializationStatus.SUCCEEDED) {
                       Log.d(TAG, "SDK initialized successfully!");

                        onResume();
                        //*/
                    } else {
                        Log.e(TAG, "SDK initialization error: $status\n${status.description}");
                      //  success = false;
                    }
                });
  blockView = new BlockView1(getApplicationContext());
        setContentView(blockView);

    }

    @Override
    protected void onResume() {
        super.onResume();

            blockView.removeAllViews();

              pageschange("39719" ,240,400);
              pageschange("39720" ,320,50);
         /*   FrameLayout container = new FrameLayout(this);
            setContentView(container);
            BannerView bannerView = new BannerView(
                    this,
                    "39719",
                    new AdSize(240, 400)
            );
            container.addView(bannerView);
            bannerView.loadAd();//*/
    }

    public   void pageschange(String curentid , int w, int h){

        blockView.addBanner(curentid ,w,h);


    }

}
