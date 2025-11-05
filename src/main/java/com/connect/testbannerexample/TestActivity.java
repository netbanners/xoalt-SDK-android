package com.connect.testbannerexample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.connect.testbannerexample.ui.dashboard.DashboardFragment;
import com.connect.testbannerexample.ui.dashboard.Title3;
import com.connect.testbannerexample.ui.home.HomeFragment;
import com.connect.testbannerexample.ui.notifications.NotificationsFragment;
import com.gamoshi.app.BannerSDK;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.prebid.mobile.api.data.InitializationStatus;

public class TestActivity extends AppCompatActivity {

     public    NavController navController;
    ViewPager pager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(0xff301658);
        setContentView(R.layout.fragment_classic);
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
         pager = findViewById(R.id.page);
        //final LinearLayoutManager llm = new LinearLayoutManager(this);



        SharedPreferences pref = getSharedPreferences("BannerSDK", MODE_PRIVATE);
        BannerSDK.initializeSdk(this, Integer.parseInt( pref.getString("current_request" ,"35733")),
                status -> {
                    if (status == InitializationStatus.SUCCEEDED) {
                        //setContentView(R.layout.test_activity);
                     //   Fragment fragment = getSupportFragmentManager().findFragmentByTag("home");
                    // if(fragment == null) fragment = new HomeFragment();
                      //  tr.replace(R.id.refactor ,fragment,"home");
                        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
                        pager.setAdapter(adapter);
                        pager.setCurrentItem(0);
                      //  tr.commit();
                       //nav(0);
                    } else {
                    }
                }
        );
    }
    public static class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return new DashboardFragment();
                case 2:
                    return  new NotificationsFragment();
                case 3:
                    return  new Title3();

                default:
                    return new HomeFragment();
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 1:
                    return "Home1";
                case 2:
                    return "Home2";
                case 3:
                    return "Home3";

                default:
                    return "Home";
            }
        }
        @Override
        public int getCount() {
            return 4;
        }
    }
public void nav(String id){
  //  Fragment fragment = getSupportFragmentManager().findFragmentByTag(id);
    //FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
    //if(fragment == null){
        Log.w("ddddd" , "sssssss   "  + id);
        if(id.contains("home1")) pager.setCurrentItem(1);
        else if(id.contains("home2"))pager.setCurrentItem(2);
        else if(id.contains("home3"))pager.setCurrentItem(3);
        else pager.setCurrentItem(0);
      //  else
       // switch (id){
         //   case "page1": fragment = new DashboardFragment(); break;
         //   case "page2": fragment = new NotificationsFragment(); break;
         //   case "page3": fragment = new Title3(); break;
        //    case "home": fragment = new HomeFragment(); break;
       // }
   // }

   // tr.replace(R.id.refactor ,fragment,id);
    //tr.show(fragment);
//tr.commit();

 /* if(navController != null)  navController.navigate(id);
    BottomNavigationView navView = findViewById(R.id.nav_view);
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
            .build();
    navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


    NavigationUI.setupWithNavController( navView, navController);//*/

}
    @Override
    protected void onResume() {
        super.onResume();
    }
}
