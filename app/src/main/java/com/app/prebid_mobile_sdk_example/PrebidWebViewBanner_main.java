package com.app.prebid_mobile_sdk_example;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import org.json.JSONObject;
import org.prebid.mobile.LogUtil;
import org.prebid.mobile.api.exceptions.AdException;
import org.prebid.mobile.rendering.models.internal.MraidVariableContainer;
import org.prebid.mobile.rendering.mraid.handler.FetchPropertiesHandler;
import org.prebid.mobile.rendering.sdk.JSLibraryManager;
import org.prebid.mobile.rendering.views.interstitial.InterstitialManager;
import org.prebid.mobile.rendering.views.webview.MraidEventsManager;
import org.prebid.mobile.rendering.views.webview.PrebidWebViewBanner;
import org.prebid.mobile.rendering.views.webview.PrebidWebViewBase;
import org.prebid.mobile.rendering.views.webview.PreloadManager;
import org.prebid.mobile.rendering.views.webview.WebViewBanner;
import org.prebid.mobile.rendering.views.webview.WebViewBase;
import org.prebid.mobile.rendering.views.webview.mraid.Views;

public class PrebidWebViewBanner_main extends PrebidWebViewBase
        implements PreloadManager.PreloadedListener, MraidEventsManager.MraidListener {

    private static final String TAG = PrebidWebViewBanner.class.getSimpleName();

    private final FetchPropertiesHandler.FetchPropertyCallback expandPropertiesCallback = new FetchPropertiesHandler.FetchPropertyCallback() {
        @Override
        public void onResult(String propertyJson) {
            handleExpandPropertiesResult(propertyJson);
            Log.e("kkl", ", . k " + propertyJson);
        }

        @Override
        public void onError(Throwable throwable) {
            LogUtil.error(TAG, "executeGetExpandProperties failed: " + Log.getStackTraceString(throwable));
        }
    };

    @SuppressLint("ResourceType")
    public PrebidWebViewBanner_main(Context context, InterstitialManager interstitialManager) {
        super(context, interstitialManager);
        setId(11111);
    }

    //gets expand properties & also a close view(irrespective of usecustomclose is false)
    public void loadMraidExpandProperties() {
        Context context = getContext();
        if (!(context instanceof Activity)) {
            LogUtil.warning(TAG, "Context is null or is not activity context");
            return;
        }

        /*
         * If it's MRAID, we have to check the Ad designer's request to launch
         * the ad in a particular expanded size by checking the ad's
         * ExpandProperties per the MRAID spec. So we go to the js and extract these
         * properties and then the layout gets built based on these things.
         */
        //Fix MOBILE-2944 App crash navigating MRAID ad
        final WebViewBase currentWebView = (webView != null) ? webView : mraidWebView;

        if (currentWebView != null) {
            currentWebView.getMRAIDInterface()
                    .getJsExecutor()
                    .executeGetExpandProperties(new FetchPropertiesHandler(expandPropertiesCallback));
        }
        else {
            LogUtil.warning(TAG, "Error getting expand properties");
        }
    }

    @Override
    public void initTwoPartAndLoad(String url) {

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        setLayoutParams(layoutParams);
        //A null context can crash with an exception in webView creation through WebViewBanner. Catch it
        mraidWebView = new WebViewBanner(context, this, this);
        mraidWebView.setJSName("twopart");

        String script = JSLibraryManager.getInstance(mraidWebView.getContext()).getMRAIDScript();
        //inject mraid.js
        mraidWebView.setMraidAdAssetsLoadListener(mraidWebView, script);

        mraidWebView.loadUrl(url);
    }

    @Override
    public void loadHTML(String html, int width, int height) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );

        setLayoutParams(layoutParams);
        this.width = width;
        this.height = height;
        //A null context can crash with an exception in webView creation through WebViewBanner. Catch it
        webView = new WebViewBanner(context, html, width, height, this, this);
        webView.setJSName("1part");
//        webView.initContainsIFrame(creative.getCreativeModel().getHtml());
    //    webView.setTargetUrl(creative.getCreativeModel().getTargetUrl());
        webView.loadAd();
    }

    @Override
    public void preloaded(WebViewBase adBaseView) {

        if (adBaseView == null) {

            //This should never happen.
            LogUtil.error(TAG, "Failed to preload a banner ad. Webview is null.");

            if (webViewDelegate != null) {
                webViewDelegate.webViewFailedToLoad(new AdException(
                        AdException.INTERNAL_ERROR,
                        "Preloaded adview is null!"
                ));
            }

            return;
        }
        currentWebViewBase = adBaseView;
       // if (currentWebViewBase.MRAIDBridgeName.equals("twopart")) {
            //SHould have expanded url here, as last param
        //    interstitialManager.displayPrebidWebViewForMraid(mraidWebView, true);
        //} else {
            if (adBaseView.getParent() == null) {

                if (getChildCount() >= 1) {

                    LogUtil.debug(TAG, "Adding second view");
                    //safe removal from parent before adding
                    Views.removeFromParent(adBaseView);

                    addView(adBaseView, 1);
                    adBaseView.bringToFront();
                    swapWebViews();
                }
                else {
                    LogUtil.debug(TAG, "Adding first view");
                    //safe removal from parent before adding
                    Views.removeFromParent(adBaseView);

                    addView(adBaseView, 0);
                    renderAdView(adBaseView);
                }
            }
            else {
                LogUtil.debug(TAG, "Adding the only view");

                adBaseView.bringToFront();
                swapWebViews();
            }
        //}
        /*
         * This postInvalidate fixes the cosmetic issue that KitKat created with the white banner
         * fragment/remnant showing up at the bottom of the screen.
         */
        if (context instanceof Activity) {
            ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content).postInvalidate();
            ((Activity) context).getWindow()
                    .getDecorView()
                    .findViewById(android.R.id.content)
                    .postInvalidateDelayed(100);
        }

        if (webViewDelegate != null) {
            webViewDelegate.webViewReadyToDisplay();
        }
    }

    protected void swapWebViews() {
        if (getContext() != null) {
            fadeOutAnimation = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
        }
        final WebViewBase frontAdView = (WebViewBase) getChildAt(0);
        WebViewBase backAdView = (WebViewBase) getChildAt(1);

        if (frontAdView != null) {
            frontAdView.startAnimation(fadeOutAnimation);
            frontAdView.setVisibility(GONE);
        }

        if (backAdView != null) {
            renderAdView(backAdView);
            backAdView.bringToFront();
        }
    }

    private void handleExpandPropertiesResult(String expandProperties) {
        JSONObject jsonExpandProperties;

        WebViewBase currentWebView = (webView != null) ? webView : mraidWebView;
        final MraidVariableContainer mraidVariableContainer = currentWebView.getMRAIDInterface()
                .getMraidVariableContainer();
        mraidVariableContainer.setExpandProperties(expandProperties);

        try {
            jsonExpandProperties = new JSONObject(expandProperties);

            definedWidthForExpand = jsonExpandProperties.optInt("width", 0);
            definedHeightForExpand = jsonExpandProperties.optInt("height", 0);

            if (interstitialManager.getInterstitialDisplayProperties() != null) {
                interstitialManager.getInterstitialDisplayProperties().expandWidth = definedWidthForExpand;
                interstitialManager.getInterstitialDisplayProperties().expandHeight = definedHeightForExpand;
            }
        }
        catch (Exception e) {
            LogUtil.error(TAG, "handleExpandPropertiesResult: Failed. Reason: " + Log.getStackTraceString(e));
        }
    }
}
