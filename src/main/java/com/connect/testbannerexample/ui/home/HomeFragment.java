package com.connect.testbannerexample.ui.home;



import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.connect.testbannerexample.MainActivity;
import com.connect.testbannerexample.R;
import com.connect.testbannerexample.RadialGradientDrw;
import com.connect.testbannerexample.TestActivity;
import com.gamoshi.app.BannerSDK;
import com.gamoshi.app.fr.BannerView;

import org.prebid.mobile.api.data.InitializationStatus;
import org.prebid.mobile.rendering.bidding.listeners.BidRequesterListener;


//import com.connect.xvpn.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
//static int id , w   , h  ;
    // private FragmentHomeBinding binding;
    int up = 0;
    SharedPreferences pref;
    BannerView    bannerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // HomeViewModel homeViewModel =
          //      new ViewModelProvider(this).get(HomeViewModel.class);

        // binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = inflater.inflate(R.layout.fragment_home, null);
        root.setBackground(RadialGradientDrw.getShader());
       FrameLayout textView = root.findViewById(R.id.text_home);
       // homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
         bannerView = new BannerView(getActivity());
        textView.addView(bannerView);
        bannerView.setCallback(new BannerView.Callback() {
            @SuppressLint("RestrictedApi")
            @Override
            public void LoadPage(String url) {
                if(url == null || url.length() == 0 ) return;
               // Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse( url));


                    Uri uri = Uri.parse(url);
                    String   EncodedPath =  uri.getEncodedPath();
                    //  NavController navController = ((TestActivity) getActivity()).navController;
                   // Log.e("ddddddddd" , " " + url);
                  //  Log.e("ddddddddd" , " " + EncodedPath);

                    if(EncodedPath.contains("home") ){
                        ((TestActivity) getActivity()).nav(EncodedPath);
                   // }else if(url.contains("home2") ){
                       // ((TestActivity) getActivity()).nav("page2");
                    }

              else  {
                  Toast.makeText(getActivity(),"Извините ссылка не найдена.", Toast.LENGTH_LONG).show();
               }



    };




                  //  Toast.makeText(getActivity().getApplicationContext(), "No application found to open this link.", Toast.LENGTH_SHORT).show();
               // }
           // }

            @Override
            public void Error(Exception e) {
                e.printStackTrace();
            }
            @Override
            public void RequesterListener(BidRequesterListener loadlistener) {

            }
        });
        pref = getActivity().getSharedPreferences("BannerSDK", MODE_PRIVATE);
     // pref.edit().putString("current_request" ,"35733").commit();



             //   bannerView.setSizeBanner(300 ,  250);
               // bannerView.removeAllViews();
              //  bannerView.addBanner( );
        update(pref.getString("current_request" ,"35733"));



        root.findViewById(R.id.ID).setOnClickListener(v->{
            AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
            AlertView edit = new AlertView(getActivity());
            ab.setView(edit);
            TextView t0 = edit.findViewById(R.id.block);
            TextView t1 = edit.findViewById(R.id.w);
            TextView t2 = edit.findViewById(R.id.h);
            t0.setText(pref.getString("current_request" ,"35733"));
            t1.setText("" + pref.getInt("w" , 300));
            t2.setText("" + pref.getInt("h" , 250));
            ab.setTitle("Change ID");
            ab.setNegativeButton("Cancel",(a,b)->{});
            ab.setNegativeButton("Ok",(a,b)->{

                String block = t0.getText().toString();
                String blockw = t1.getText().toString();
                String blockh = t2.getText().toString();
if(block.length()> 0){
 int w2 = Integer.parseInt(blockw);
 int h2 = Integer.parseInt(blockh);
    pref.edit().putString("current_request", ""+ block).commit();
    pref.edit().putInt("w", w2).commit();
    pref.edit().putInt("h", h2).commit();
    update(block);
}
                //String s = edit.getText().toString();
                //pref.edit().putString("current_request",s).commit();

            });
          /*  EditText edit = new EditText(getApplicationContext());
            edit.setInputType(InputType.TYPE_CLASS_NUMBER);

            edit.setText(pref.getString("current_request", "" + 35733));
            ab.setView(edit);
           //*/
            ab.show();

        });


        return root;
    }
    private  void update(String id){
      int  w = pref.getInt("w" , 300);
      int  h = pref.getInt("h" , 250);
        switch (id) {
            case "35733": {

                bannerView.setSizeBanner(w, h);
                bannerView.removeAllViews();
                bannerView.addBanner();
                break;
            }
            case "36387":{

                bannerView.setSizeBanner(w, h);
                bannerView.removeAllViews();
                bannerView.addBanner();
                break;
        }
            case "36388": {

                bannerView.setSizeBanner(w, h);
                bannerView.removeAllViews();
                bannerView.addBanner();
                break;
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}