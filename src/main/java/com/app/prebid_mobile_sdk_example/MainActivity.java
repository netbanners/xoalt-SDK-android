package com.app.prebid_mobile_sdk_example;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import androidx.annotation.Nullable;
import org.prebid.mobile.PrebidMobile;
import org.prebid.mobile.api.data.InitializationStatus;


public class MainActivity extends Activity {
    String TAG = "PrebidCustomInit";

    BlockView1 blockView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        PrebidMobile.setPrebidServerAccountId("0689a263-318d-448b-a3d4-b02e8a709d9d");
               PrebidMobile.initializeSdk(this,
                "https://hb.xoalt.com/x-simb/"

                ,status -> {
                    if (status == InitializationStatus.SUCCEEDED) {
                       Log.d(TAG, "SDK initialized successfully!");

                        onResume();

                    } else {
                        Log.e(TAG, "SDK initialization error: $status\n${status.description}");
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

    }

    public   void pageschange(String curentid , int w, int h){

        blockView.addBanner(curentid ,w,h);


    }

}
