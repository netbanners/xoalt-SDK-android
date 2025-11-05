package com.connect.testbannerexample.ui.dashboard;



import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.connect.testbannerexample.R;
import com.connect.testbannerexample.RadialGradientDrw;
import com.connect.testbannerexample.TestActivity;
import com.gamoshi.app.BannerSDK;
import com.gamoshi.app.fr.BannerView;

import org.prebid.mobile.api.data.InitializationStatus;
import org.prebid.mobile.rendering.bidding.listeners.BidRequesterListener;


//import com.connect.xvpn.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    // private FragmentDashboardBinding binding;

    @Override
    public void onResume() {
        super.onResume();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_dashboard, null);
        // root.setBackground(RadialGradientDrw.getShader());
       TextView textView = root.findViewById(R.id.text_dashboard);
        textView.setText("Опасно помогать незнакомцу, особенно тому, кто теряет свою тень. Но отважная девочка Венди не может отказать в помощи очаровательному мальчишке. Её доброта и отзывчивость положат начало крепкой дружбе и невероятным приключениям. Героев ждут удивительные события на острове вечного детства: встречи с индейцами и русалками, битвы с коварными пиратами, веселье и много звонкого смеха. В таком чудесном краю нет места для грусти и тоски по дому, так стоит ли возвращаться? Перед Венди и её братьями стоит сложный выбор…");
        root.findViewById(R.id.ID).setOnClickListener(v->{
            ((TestActivity) getActivity()).nav("home");
                });

      /*  BannerView bannerView = new BannerView(getActivity());
        textView.addView(bannerView);
        bannerView.setCallback(new BannerView.Callback() {
            @Override
            public void LoadPage(String url) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse( url));
                if (browserIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(browserIntent);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "No application found to open this link.", Toast.LENGTH_SHORT).show();
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
          SharedPreferences pref = getActivity().getSharedPreferences("BannerSDK", MODE_PRIVATE);
          pref.edit().putString("current_request" ,"36387").commit();

                        bannerView.setSizeBanner(320 ,  50);
                        bannerView.removeAllViews();
                        bannerView.addBanner( );

        //*/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}