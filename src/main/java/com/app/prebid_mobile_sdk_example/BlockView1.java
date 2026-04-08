package com.app.prebid_mobile_sdk_example;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import org.prebid.mobile.AdSize;
import org.prebid.mobile.api.rendering.BannerView;


public class BlockView1  extends LinearLayout {
    public BlockView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlockView1(Context context) {
        this(context, null);
    }

    public BlockView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }

    public void addBanner(String currentId, int w, int h) {
        BannerView bannerView = new BannerView(
                getContext(),
                currentId,
                new AdSize(w, h)
        );

        bannerView.loadAd();
        LinearLayout.LayoutParams lp = new LayoutParams(-2, -2);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        bannerView.setLayoutParams(lp);
        addView(bannerView);

    }

}
