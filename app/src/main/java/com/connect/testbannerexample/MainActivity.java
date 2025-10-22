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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bannerView = new BannerView(this);
        setContentView(R.layout.activity_main);
        area = findViewById(R.id.area);
        area.addView(bannerView);
        findViewById(R.id.ID).setOnClickListener(v->{
            AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
            EditText edit = new EditText(getApplicationContext());
            edit.setInputType(InputType.TYPE_CLASS_NUMBER);
            SharedPreferences pref = getSharedPreferences("BannerSDK", MODE_PRIVATE);

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
        bannerView.addBanner("\nStart\n", text1);
        update();
    }


    private  void update(){
        Example_1.setOnClickListener(v->{
            bannerView.removeAllViews();

            bannerView.addBanner("\nПитер Пэн  Джеймс Мэтью Барри\n", text1);
        });
        Example_2.setOnClickListener(v->{
            bannerView.removeAllViews();

          bannerView.addBanner("\nХодячий замок  Диана Уинн Джонс\n", text2);
        });
        Example_3.setOnClickListener(v->{
            bannerView.removeAllViews();

           bannerView.addBanner("\nПриключения Тома Сойера  Марк Твен\n", text3);
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

    String text1 ="\nОпасно помогать незнакомцу, особенно тому, кто теряет свою тень. Но отважная девочка Венди не может отказать в помощи очаровательному мальчишке. Её доброта и отзывчивость положат начало крепкой дружбе и невероятным приключениям. Героев ждут удивительные события на острове вечного детства: встречи с индейцами и русалками, битвы с коварными пиратами, веселье и много звонкого смеха. В таком чудесном краю нет места для грусти и тоски по дому, так стоит ли возвращаться? Перед Венди и её братьями стоит сложный выбор…";
    String text2 ="\nКниги английской писательницы Дианы У. Джонс настолько ярки, что так и просятся на экран. По ее бестселлеру «Ходячий замок» знаменитый мультипликатор Хаяо Миядзаки («Унесенные призраками»), обладатель «Золотого льва» – высшей награды Венецианского фестиваля, снял анимационный фильм, побивший в Японии рекорд кассовых сборов.…Софи живет в сказочной стране, где ведьмы и русалки, семимильные сапоги и говорящие собаки – обычное дело. Поэтому, когда на нее обрушивается ужасное проклятие коварной Болотной Ведьмы, Софи ничего не остается, как обратиться за помощью к таинственному чародею Хоулу, обитающему в Ходячем замке. Однако, чтобы освободиться от чар, Софи предстоит разгадать немало загадок и прожить в замке у Хоула гораздо дольше, чем она рассчитывала. А для этого нужно подружиться с огненным демоном, поймать падающую звезду, подслушать пение русалок, отыскать мандрагору и многое, многое другое.";
    String text3 ="\nВ книге о приключениях Тома Сойера писатель с большим мастерством нарисовал жизнь американского провинциального городка 40-х годов XIX века. Благодаря напряженному сюжету и блестящему юмору эта книга горячо любима читателями всего мира.";


}