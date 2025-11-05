package com.connect.testbannerexample;

import android.app.Activity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.gamoshi.app.BannerSDK;
import com.gamoshi.app.fr.BannerView;
import org.prebid.mobile.api.data.InitializationStatus;
import org.prebid.mobile.rendering.bidding.listeners.BidRequesterListener;

public class MainActivity extends Activity {
    private final String TAG = MainActivity.class.getCanonicalName();
    FrameLayout area;
    BannerView bannerView;
    View Example_1 ,Example_2 ,Example_3;
    SharedPreferences pref  ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("BannerSDK", MODE_PRIVATE);

        bannerView = new BannerView(this);
        setContentView(R.layout.activity_main);
        area = findViewById(R.id.area);
        area.addView(bannerView);
        findViewById(R.id.ID).setVisibility(View.GONE);
        findViewById(R.id.ID).setOnClickListener(v->{
            AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
            EditText edit = new EditText(getApplicationContext());
            edit.setInputType(InputType.TYPE_CLASS_NUMBER);

            edit.setText(pref.getString("current_request", "" + 35733));
            ab.setView(edit);
            ab.setTitle("Change ID");
            ab.setNegativeButton("Cancel",(a,b)->{});
            ab.setNegativeButton("Ok",(a,b)->{
                String s = edit.getText().toString();
                pref.edit().putString("current_request",s).commit();

            });
            ab.show();

        });
        bannerView.setCallback(new BannerView.Callback() {
            @Override
            public void LoadPage(String url) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse( url));
                if (browserIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(browserIntent);
                }else{
                    Toast.makeText(getApplicationContext(), "No application found to open this link.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void Error(Exception e) {
e.printStackTrace();
            }
            @Override
            public void RequesterListener(BidRequesterListener loadlistener) {

            }
        });
        Example_1 = findViewById(R.id.Example_1);
        Example_2 = findViewById(R.id.Example_2);
        Example_3 = findViewById(R.id.Example_3);

    }

    private void start() {
        bannerView.addBanner();
        update();
    }


    private  void update(){
        Example_1.setOnClickListener(v->{
            pref.edit().putString("current_request" ,"35733").commit();
            bannerView.setSizeBanner(300 ,  250);
            bannerView.removeAllViews();
            bannerView.addBanner( );
        });
        Example_2.setOnClickListener(v->{
            pref.edit().putString("current_request" ,"36387").commit();
            bannerView.setSizeBanner(320 ,50);
            bannerView.removeAllViews();
            bannerView.addBanner( );
        });
        Example_3.setOnClickListener(v->{
            pref.edit().putString("current_request" ,"36388").commit();
            bannerView.setSizeBanner(320,480 );
            bannerView.removeAllViews();
            bannerView.addBanner();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("BannerSDK", MODE_PRIVATE);
        BannerSDK.initializeSdk(this, Integer.parseInt(pref.getString("current_request", "" + 35733)),
                status -> {
                    if (status == InitializationStatus.SUCCEEDED) {
                        Log.d(TAG, "SDK initialized successfully!");

                        start( );


                    } else {
                        Log.e(TAG, "SDK initialization error: $status\n${status.description}");
                    }
                }
        );
    }



}