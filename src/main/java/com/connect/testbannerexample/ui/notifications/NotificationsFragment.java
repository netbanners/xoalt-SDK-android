package com.connect.testbannerexample.ui.notifications;



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
import com.gamoshi.app.fr.BannerView;

import org.prebid.mobile.rendering.bidding.listeners.BidRequesterListener;


public class NotificationsFragment extends Fragment {

    // private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications,null);
       // root.setBackground(RadialGradientDrw.getShader());
        TextView textView = root.findViewById(R.id.text_notifications);
        textView.setText("Книги английской писательницы Дианы У. Джонс настолько ярки, что так и просятся на экран. По ее бестселлеру «Ходячий замок» знаменитый мультипликатор Хаяо Миядзаки («Унесенные призраками»), обладатель «Золотого льва» – высшей награды Венецианского фестиваля, снял анимационный фильм, побивший в Японии рекорд кассовых сборов.…Софи живет в сказочной стране, где ведьмы и русалки, семимильные сапоги и говорящие собаки – обычное дело. Поэтому, когда на нее обрушивается ужасное проклятие коварной Болотной Ведьмы, Софи ничего не остается, как обратиться за помощью к таинственному чародею Хоулу, обитающему в Ходячем замке. Однако, чтобы освободиться от чар, Софи предстоит разгадать немало загадок и прожить в замке у Хоула гораздо дольше, чем она рассчитывала. А для этого нужно подружиться с огненным демоном, поймать падающую звезду, подслушать пение русалок, отыскать мандрагору и многое, многое другое.");
        root.findViewById(R.id.ID).setOnClickListener(v->{
            ((TestActivity) getActivity()).nav("home");
        });

       /* BannerView bannerView = new BannerView(getActivity());
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
        pref.edit().putString("current_request" ,"36388").commit();

        bannerView.setSizeBanner(320,480);
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