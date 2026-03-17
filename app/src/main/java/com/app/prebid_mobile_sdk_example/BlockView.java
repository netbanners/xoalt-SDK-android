package com.app.prebid_mobile_sdk_example;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;




import org.json.JSONObject;
import org.prebid.mobile.BannerParameters;
import org.prebid.mobile.Signals;
import org.prebid.mobile.api.exceptions.AdException;
import org.prebid.mobile.configuration.AdUnitConfiguration;
import org.prebid.mobile.rendering.bidding.data.bid.BidResponse;
import org.prebid.mobile.rendering.bidding.listeners.BidRequesterListener;
import org.prebid.mobile.rendering.views.webview.AdWebView;
import org.prebid.mobile.rendering.views.webview.AdWebViewClient;
import org.prebid.mobile.rendering.views.webview.WebViewBanner;
import org.prebid.mobile.rendering.views.webview.WebViewBase;


import java.util.ArrayList;
import java.util.Collections;

public class BlockView extends LinearLayout {
 
    public BlockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public BlockView(Context context) {
        this(context, null);
    }
    public BlockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);

    }

    public void addBanner(String currentId ,int w ,int h) {
        AdUnitConfiguration configuration = new AdUnitConfiguration();
       BannerAdUnit_main adUnit= new  BannerAdUnit_main(currentId, w, h);
       // adUnit.setAutoRefreshInterval(120);
        BannerParameters parameters = new BannerParameters();
        ArrayList<Signals.Api> arr = new ArrayList<>();
        arr.add(Signals.Api.MRAID_3);
        arr.add(Signals.Api.OMID_1);
        //  arr.add(Signals.Api.MRAID_2);
        parameters.setApi(arr);

        adUnit.setBannerParameters(parameters);
          configuration.setConfigId(currentId);
        configuration.setIsOriginalAdUnit(true);
        FrameLayout fr = new FrameLayout(getContext());
      //  AdManagerAdRequest request= new AdManagerAdRequest.Builder().build();

       // AdWebViewClient_main  main =  new AdWebViewClient_main(new def());
        PrebidWebViewBanner_main defaultAdContainer = new PrebidWebViewBanner_main(getContext(), null);
  adUnit.fetchDemand(new Object(), result->{
Log.i("result", "" + result);

  },createBidListener(defaultAdContainer,w,h , null));


    // defaultAdContainer.loadHTML(js,w,h);
        fr.addView(defaultAdContainer);
        LinearLayout.LayoutParams lp = new LayoutParams(-2,-2);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        fr.setLayoutParams(lp);
     addView(fr);

    }
    int getbannerNumber(View v){
        int out = 0;
      for(int i = 0; i< getChildCount(); i++){
          View ii = getChildAt(i);
          if(ii.equals(v)){
              out = i; break;
          }
      }
      return out + 1;
    }
    BidRequesterListener createBidListener(PrebidWebViewBanner_main defaultAdContainer,int w ,int h, AdWebViewClient_main main){
        return new BidRequesterListener() {

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public void onFetchCompleted(BidResponse response) {

                try {
                    JSONObject     ob = new JSONObject(response.getWinningBidJson()).getJSONArray("seatbid").getJSONObject(0)
                            .getJSONArray("bid").getJSONObject(0);
                    String adm = ob.getString("adm");

                    defaultAdContainer.loadHTML(adm.replace("<head>" ,"<head>"+ s),w,h);
                  //  defaultAdContainer.getWebView().setWebViewClient(new AdWebViewClient_main(new def()));
                    WebViewBase adView = defaultAdContainer.getWebView();

                   new Handler(Looper.getMainLooper()).postDelayed(()->{
                        AdWebViewClient_main  main  =   new AdWebViewClient_main(new def());
                        adView.setWebViewClient(main);
                        main.setOnResult((view, url)->{
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse( url));
                          browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getContext().startActivity(browserIntent);
                        });
                    }, 4000);//*/
                    //  adView.setOnClickListener(v->{
                //   Toast.makeText(getContext(), "Click Banner to " , Toast.LENGTH_LONG).show();

                  //  });
                  //  adView.add

                   // });
                } catch (Exception e) {
                  e.printStackTrace();
                }


            }

            @Override
            public void onError(AdException exception) {
                //originalListener.onComplete(convertToResultCode(exception));
            }
        };
    }
private class def implements AdWebViewClient.AdAssetsLoadedListener {

    @Override
    public void startLoadingAssets() {

    }

    @Override
    public void adAssetsLoaded() {

    }

    @Override
    public void notifyMraidScriptInjected() {

    }
};
    String js = "<html><body><div>\n" +

            "  <img src=\"data:image/png;base64, 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9qc2RMtF/zfpep12Op04VtMCmPW6QfYI1hr5vq9WqzVx9rTruqpWqzP3LNb98TLRm4Idx9GrV69mhijNx9AkReGZ5/YbnOet08PDw5lviObjZTYbwdoAo9HoxtmpRT+k7r/+AX7LWNc6xWoRLABmMOgOwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcAMggXADIIFwAyCBcCM/wH/6xl/fWd1gAAAAABJRU5ErkJggg==\" />\n" +
            "</div> </body></html>";
    public static   String s = "<script type=\"text/javascript\">; var printDebug = function(messageBuilder) {};\n" +
            "\n" +
            "(function() {\n" +
            "\n" +
            "    const LOG_TO_CONSOLE_ENABLE = true;\n" +
            "    const TAG = \"PREBID_MRAID_JS\";\n" +
            "\n" +
            "    var mraid = window.mraid = {};\n" +
            "    mraid.eventListeners = {};\n" +
            "    mraid.state = \"ready\";\n" +
            "    mraid.viewable = false;\n" +
            "    mraid.resizePropertiesInitialized = false;\n" +
            "    mraid.volumePercentage = null;\n" +
            "\n" +
            "    var nativeCallQueue = [];\n" +
            "    var nativeCallInFlight = false;\n" +
            "\n" +
            "    mraid.currentPosition = {\n" +
            "        x: 0,\n" +
            "        y: 0,\n" +
            "        width: 0,\n" +
            "        height: 0\n" +
            "    };\n" +
            "\n" +
            "    mraid.maxSize = {\n" +
            "        width: 0,\n" +
            "        height: 0\n" +
            "    };\n" +
            "\n" +
            "    mraid.defaultPosition = {\n" +
            "        x: 0,\n" +
            "        y: 0,\n" +
            "        width: 0,\n" +
            "        height: 0\n" +
            "    };\n" +
            "\n" +
            "    mraid.screenSize = {\n" +
            "        width: 0,\n" +
            "        height: 0\n" +
            "    };\n" +
            "\n" +
            "    mraid.allSupports = {\n" +
            "        sms: false,\n" +
            "        tel: false,\n" +
            "        calendar: false,\n" +
            "        storePicture: false,\n" +
            "        inlineVideo: false,\n" +
            "        vpaid: false\n" +
            "    };\n" +
            "\n" +
            "    if (LOG_TO_CONSOLE_ENABLE) {\n" +
            "        printDebug = function(messageBuilder) {\n" +
            "            var message = messageBuilder();\n" +
            "            console.log(TAG + ': ' + message);\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    mraid.setCurrentPosition = function(x, y, width, height) {\n" +
            "\n" +
            "        printDebug(() => 'setCurrentPosition');\n" +
            "\n" +
            "        mraid.currentPosition.x = x;\n" +
            "        mraid.currentPosition.y = y;\n" +
            "        mraid.currentPosition.width = width;\n" +
            "        mraid.currentPosition.height = height;\n" +
            "\n" +
            "    };\n" +
            "\n" +
            "    mraid.setMaxSize = function(width, height) {\n" +
            "\n" +
            "        printDebug(() => 'setMaxSize');\n" +
            "\n" +
            "        mraid.maxSize.width = width;\n" +
            "        mraid.maxSize.height = height;\n" +
            "\n" +
            "    };\n" +
            "\n" +
            "    mraid.setDefaultPosition = function(x, y, width, height) {\n" +
            "\n" +
            "        printDebug(() => 'setDefaultPosition');\n" +
            "\n" +
            "        mraid.defaultPosition.x = x;\n" +
            "        mraid.defaultPosition.y = y;\n" +
            "        mraid.defaultPosition.width = width;\n" +
            "        mraid.defaultPosition.height = height;\n" +
            "\n" +
            "    };\n" +
            "\n" +
            "    mraid.setScreenSize = function(width, height) {\n" +
            "\n" +
            "        printDebug(() => 'setScreenSize');\n" +
            "\n" +
            "        mraid.screenSize.width = width;\n" +
            "        mraid.screenSize.height = height;\n" +
            "\n" +
            "    };\n" +
            "\n" +
            "    mraid.orientationProperties = {\n" +
            "        allowOrientationChange: true,\n" +
            "        forceOrientation: \"none\"\n" +
            "    };\n" +
            "\n" +
            "    mraid.expandProperties = {\n" +
            "        width: 0,\n" +
            "        height: 0,\n" +
            "        useCustomClose: false\n" +
            "    };\n" +
            "\n" +
            "    mraid.resizeProperties = {\n" +
            "        width: 0,\n" +
            "        height: 0,\n" +
            "        customClosePosition: \"top-right\",\n" +
            "        offsetX: 0,\n" +
            "        offsetY: 0,\n" +
            "        allowOffscreen: true\n" +
            "    };\n" +
            "\n" +
            "     mraid.lastExposure = {\n" +
            "         exposedPercentage: 100.0,\n" +
            "         visibleRectangle: {\n" +
            "            x: 0.0,\n" +
            "            y: 0.0,\n" +
            "            width: 0.0,\n" +
            "            height: 0.0\n" +
            "         },\n" +
            "         occlusionRectangles: null\n" +
            "     };\n" +
            "\n" +
            "    var safeString = function(str) {\n" +
            "\n" +
            "        printDebug(() => 'safeString' + str);\n" +
            "        return str ? str : '';\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    var callContainer = function(command) {\n" +
            "        printDebug(() => 'callContainer ' + command);\n" +
            "        var args = Array.prototype.slice.call(arguments);\n" +
            "        args.shift();\n" +
            "\n" +
            "        if (nativeCallInFlight) {\n" +
            "            var nextCommand = {\n" +
            "              \"command\" : command,\n" +
            "              \"args\" : args\n" +
            "            };\n" +
            "            printDebug(() => 'callContainer nextCommand in queue ' + nextCommand);\n" +
            "            nativeCallQueue.push(nextCommand);\n" +
            "        } else {\n" +
            "            nativeCallInFlight = true;\n" +
            "            printDebug(() => 'callContainer executing immediately.');\n" +
            "            jsBridge[command].apply(jsBridge, args);\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    mraid.open = function(url) {\n" +
            "        printDebug(() => 'open(' + url + ')');\n" +
            "\n" +
            "        callContainer('open', url);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.resize = function() {\n" +
            "        printDebug(() => 'resize');\n" +
            "        if(mraid.resizePropertiesInitialized) {\n" +
            "\n" +
            "            callContainer('resize');\n" +
            "\n" +
            "        } else {\n" +
            "\n" +
            "            mraid.onError(\"[width] and [height] parameters must be at least 1px. Set them using setResizeProperties() MRAID method.\", \"resize\");\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    mraid.expand = function(url) {\n" +
            "        printDebug(() => 'mraid.expand(' + url + ')');\n" +
            "\n" +
            "        if(url) {\n" +
            "            callContainer('expand', url);\n" +
            "        } else {\n" +
            "            callContainer('expand');\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.close = function() {\n" +
            "        printDebug(() => 'close');\n" +
            "        callContainer('close');\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getPlacementType = function() {\n" +
            "        printDebug(() => 'getPlacementType');\n" +
            "        return jsBridge.getPlacementType();\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getState = function() {\n" +
            "        printDebug(() => 'getState ' + mraid.state);\n" +
            "        return mraid.state;\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getVersion = function() {\n" +
            "        printDebug(() => 'getVersion');\n" +
            "        return '3.0';\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.isViewable = function() {\n" +
            "\n" +
            "        printDebug(() => 'isViewable ' + mraid.viewable);\n" +
            "        return mraid.viewable;\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getScreenSize = function() {\n" +
            "        printDebug(() => 'getScreenSize');\n" +
            "        return JSON.parse(jsBridge.getScreenSize());\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getDefaultPosition = function() {\n" +
            "        printDebug(() => 'getDefaultPosition');\n" +
            "        return JSON.parse(jsBridge.getDefaultPosition());\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getCurrentPosition = function() {\n" +
            "        printDebug(() => 'getCurrentPosition');\n" +
            "        return JSON.parse(jsBridge.getCurrentPosition());\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getMaxSize = function() {\n" +
            "        printDebug(() => 'getMaxSize');\n" +
            "        return JSON.parse(jsBridge.getMaxSize());\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.supports = function(feature) {\n" +
            "        printDebug(() => 'supports: ' + feature);\n" +
            "        printDebug(() => 'supports:regular call: ' + mraid.allSupports[feature]);\n" +
            "        return mraid.allSupports[feature];\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.playVideo = function(url) {\n" +
            "        printDebug(() => 'playVideo');\n" +
            "        callContainer('playVideo', url);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.addEventListener = function(event, listener) {\n" +
            "        printDebug(() => 'addEventListener: ' + event);\n" +
            "        var handlers = mraid.eventListeners[event];\n" +
            "        if(handlers == null) {\n" +
            "            handlers = mraid.eventListeners[event] = [];\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "        for(var handler = 0; handler < handlers.length; handler++) {\n" +
            "            if(listener == handlers[handler]) {\n" +
            "                return;\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "        handlers.push(listener);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.removeEventListener = function(event, listener) {\n" +
            "        printDebug(() => 'removeEventListener: ' + event);\n" +
            "        var handlers = mraid.eventListeners[event];\n" +
            "        if(handlers != null) {\n" +
            "            for(var handler = 0; handler < handlers.length; handler++) {\n" +
            "                if(handlers[handler] == listener) {\n" +
            "                    handlers.splice(handler, 1);\n" +
            "                    break;\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.setOrientationProperties = function(properties) {\n" +
            "        printDebug(() => 'setOrientationProperties');\n" +
            "        if(!properties)\n" +
            "            return;\n" +
            "        var aoc = properties.allowOrientationChange;\n" +
            "        if(aoc === true || aoc === false) {\n" +
            "            mraid.orientationProperties.allowOrientationChange = aoc;\n" +
            "        }\n" +
            "\n" +
            "        var fo = properties.forceOrientation;\n" +
            "        if(fo == 'landscape' || fo == 'portrait' || fo == 'none') {\n" +
            "            mraid.orientationProperties.forceOrientation = fo;\n" +
            "        }\n" +
            "\n" +
            "        callContainer('onOrientationPropertiesChanged', JSON.stringify(mraid.getOrientationProperties()));\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getOrientationProperties = function() {\n" +
            "        printDebug(() => 'getOrientationProperties');\n" +
            "        return mraid.orientationProperties;\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getResizeProperties = function() {\n" +
            "        printDebug(() => 'getResizeProperties');\n" +
            "        return mraid.resizeProperties;\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.getExpandProperties = function() {\n" +
            "        printDebug(() => 'getExpandProperties');\n" +
            "        mraid.expandProperties.isModal = true;\n" +
            "        return mraid.expandProperties;\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.setResizeProperties = function(properties) {\n" +
            "            printDebug(() => 'setResizeProperties ');\n" +
            "   \t\t    mraid.resizePropertiesInitialized = false;\n" +
            "\n" +
            "    \t\tif (!properties) {\n" +
            "    \t\t\tmraid.onError(\"properties is null\", \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "    \t\t//Allow Offscreen\n" +
            "    \t\tif (typeof(properties.allowOffscreen) !== \"boolean\") {\n" +
            "    \t\t\tmraid.onError(\"allowOffscreen param of [\" + properties.allowOffscreen + \"] is unusable.\", \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "    \t\tvar allowOffscreen = properties.allowOffscreen\n" +
            "\n" +
            "    \t\t//Get max size\n" +
            "    \t\tvar maxSize = mraid.getMaxSize();\n" +
            "    \t\tif (!maxSize || !maxSize.width || !maxSize.height) {\n" +
            "    \t\t\tmraid.onError(\"Unable to use maxSize of [\" + JSON.stringify(maxSize) + \"]\", \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "    \t\t//Width\n" +
            "    \t\tif (properties.width == null || typeof properties.width === 'undefined' || isNaN(properties.width)) {\n" +
            "    \t\t\tmraid.onError(\"width param of [\" + properties.width + \"] is unusable.\", \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "    \t\tif (properties.width < 50 || (properties.width > maxSize.width && !allowOffscreen)) {\n" +
            "    \t\t\tmraid.onError(\"width param of [\" + properties.width + \"] outside of acceptable range of 50 to \" + maxSize.width, \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "    \t\t//Height\n" +
            "    \t\tif (properties.height == null || typeof properties.height === 'undefined' || isNaN(properties.height)) {\n" +
            "    \t\t\tmraid.onError(\"height param of [\" + properties.height + \"] is unusable.\", \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "    \t\tif (properties.height < 50 || (properties.height > maxSize.height && !allowOffscreen)) {\n" +
            "    \t\t\tmraid.onError(\"height param of [\" + properties.height + \"] outside of acceptable range of 50 to \" + maxSize.height, \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "    \t\t//Offset\n" +
            "    \t\tif (properties.offsetX == null || typeof properties.offsetX === 'undefined' || isNaN(properties.offsetX)) {\n" +
            "    \t\t\tmraid.onError(\"offsetX param of [\" + properties.offsetX + \"] is unusable.\", \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "    \t\tif (properties.offsetY == null || typeof properties.offsetY === 'undefined' || isNaN(properties.offsetY)) {\n" +
            "    \t\t\tmraid.onError(\"offsetY param of [\" + properties.offsetY + \"] is unusable.\", \"setResizeProperties\");\n" +
            "    \t\t\treturn;\n" +
            "    \t\t}\n" +
            "\n" +
            "\n" +
            "    \t\tmraid.resizeProperties.width = properties.width;\n" +
            "    \t\tmraid.resizeProperties.height = properties.height;\n" +
            "    \t\tmraid.resizeProperties.customClosePosition = properties.customClosePosition;\n" +
            "    \t\tmraid.resizeProperties.offsetX = properties.offsetX;\n" +
            "    \t\tmraid.resizeProperties.offsetY = properties.offsetY;\n" +
            "    \t\tmraid.resizeProperties.allowOffscreen = properties.allowOffscreen;\n" +
            "\n" +
            "    \t\tmraid.resizePropertiesInitialized = true;\n" +
            "    \t};\n" +
            "\n" +
            "\n" +
            "    mraid.setExpandProperties = function(properties) {\n" +
            "\n" +
            "        printDebug(() => 'setExpandProperties');\n" +
            "\n" +
            "        if(properties && properties.width != null && typeof properties.width !== 'undefined' && !isNaN(properties.width)) {\n" +
            "            mraid.expandProperties.width = properties.width;\n" +
            "        }\n" +
            "\n" +
            "        if(properties && properties.height != null && typeof properties.height !== 'undefined' && !isNaN(properties.height)) {\n" +
            "            mraid.expandProperties.height = properties.height;\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    mraid.useCustomClose = function(useCustomClose) {\n" +
            "        printDebug(() => 'DEPRECATED useCustomClose' + useCustomClose);\n" +
            "        if(useCustomClose == true || useCustomClose == false) {\n" +
            "            mraid.expandProperties.useCustomClose = useCustomClose;\n" +
            "            //also call container's\n" +
            "            callContainer('shouldUseCustomClose', useCustomClose);\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.fireEvent = function(event, args) {\n" +
            "        printDebug(() => 'fireEvent ' + event);\n" +
            "        var handlers = mraid.eventListeners[event];\n" +
            "        if(handlers == null) {\n" +
            "            return;\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "        for(var handler = 0; handler < handlers.length; handler++) {\n" +
            "            if(event == 'ready') {\n" +
            "                handlers[handler]();\n" +
            "            } else if(event == 'error') {\n" +
            "                handlers[handler](args[0], args[1]);\n" +
            "            } else if(event == 'stateChange') {\n" +
            "                handlers[handler](args);\n" +
            "            } else if(event == 'viewableChange') {\n" +
            "                handlers[handler](args);\n" +
            "            } else if(event == 'sizeChange') {\n" +
            "                handlers[handler](args[0], args[1]);\n" +
            "            } else if(event == 'audioVolumeChange') {\n" +
            "                handlers[handler](args);\n" +
            "            } else if(event == 'exposureChange') {\n" +
            "                handlers[handler](mraid.lastExposure.exposedPercentage,\n" +
            "                 mraid.lastExposure.visibleRectangle,\n" +
            "                 mraid.lastExposure.occlusionRectangles);\n" +
            "            }\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.createCalendarEvent = function(parameters) {\n" +
            "        printDebug(() => 'createCalendarEvent');\n" +
            "        callContainer('createCalendarEvent', JSON.stringify(parameters));\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.storePicture = function(url) {\n" +
            "        printDebug(() => 'storePicture');\n" +
            "        callContainer('storePicture', url);\n" +
            "    };\n" +
            "\n" +
            "    mraid.onError = function(message, action) {\n" +
            "        printDebug(() => 'onError' + message);\n" +
            "        mraid.fireEvent(\"error\", [message, action]);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.onReady = function() {\n" +
            "        printDebug(() => 'ready');\n" +
            "\n" +
            "        mraid.fireEvent(\"ready\");\n" +
            "    };\n" +
            "\n" +
            "    mraid.onReadyExpanded = function() {\n" +
            "        printDebug(() => 'onReadyExpanded');\n" +
            "        mraid.fireEvent(\"ready\");\n" +
            "    };\n" +
            "\n" +
            "    mraid.onSizeChange = function(width, height) {\n" +
            "        printDebug(() => 'onSizeChange: width ' + width + 'height: ' + height);\n" +
            "        mraid.fireEvent(\"sizeChange\", [width, height]);\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    mraid.onStateChange = function(state) {\n" +
            "        printDebug(() => 'onStateChange' + state);\n" +
            "        mraid.state = state;\n" +
            "        mraid.fireEvent(\"stateChange\", mraid.getState());\n" +
            "    };\n" +
            "\n" +
            "\n" +
            "    /**\n" +
            "    * @deprecated Since MRAID 3. Use onExposureChange instead.\n" +
            "    */\n" +
            "    mraid.onViewableChange = function(isViewable) {\n" +
            "        printDebug(() => 'onViewableChange');\n" +
            "\n" +
            "        mraid.viewable = isViewable;\n" +
            "        mraid.fireEvent(\"viewableChange\", mraid.isViewable());\n" +
            "    };\n" +
            "\n" +
            "    mraid.getLocation = function() {\n" +
            "        printDebug(() => 'getLocation');\n" +
            "        return JSON.parse(jsBridge.getLocation());\n" +
            "    };\n" +
            "\n" +
            "    mraid.getCurrentAppOrientation = function() {\n" +
            "        printDebug(() => 'getCurrentAppOrientation');\n" +
            "        return JSON.parse(jsBridge.getCurrentAppOrientation());\n" +
            "    };\n" +
            "\n" +
            "    mraid.unload = function() {\n" +
            "        printDebug(() => 'unload');\n" +
            "        callContainer('unload');\n" +
            "    };\n" +
            "\n" +
            "    mraid.onExposureChange = function(viewExposureString) {\n" +
            "        printDebug(() => 'onExposureChange ' + viewExposureString);\n" +
            "        var viewExposure = JSON.parse(viewExposureString);\n" +
            "        mraid.lastExposure = viewExposure;\n" +
            "        mraid.fireEvent(\"exposureChange\");\n" +
            "    };\n" +
            "\n" +
            "    /*\n" +
            "        This event fires on changing of the audio volume.\n" +
            "        See https://www.iab.com/wp-content/uploads/2017/07/MRAID_3.0_FINAL.pdf , par. 7.6\n" +
            "\n" +
            "        @volumePercentage -  percentage of maximum audio playback\n" +
            "                             volume, a floating-point number between 0.0 and 100.0, or 0.0\n" +
            "                             if playback is not allowed, or null if volume can’t be\n" +
            "                             determined\n" +
            "    */\n" +
            "    mraid.onAudioVolumeChange = function(newVolumePercentage) {\n" +
            "            printDebug(() => 'onAudioVolumeChange(' + newVolumePercentage + ')');\n" +
            "            mraid.volumePercentage = newVolumePercentage;\n" +
            "            mraid.fireEvent(\"audioVolumeChange\", newVolumePercentage);\n" +
            "    };\n" +
            "\n" +
            "    mraid.nativeCallComplete = function() {\n" +
            "      if (nativeCallQueue.length === 0) {\n" +
            "        nativeCallInFlight = false;\n" +
            "        return;\n" +
            "     }\n" +
            "\n" +
            "      var nextCall = nativeCallQueue.shift();\n" +
            "      printDebug(() => 'nativeCallComplete nextCall: ' + nextCall.command + \" arg \" + nextCall.args);\n" +
            "\n" +
            "      jsBridge[nextCall.command].apply(jsBridge, nextCall.args);\n" +
            "    };\n" +
            "\n" +
            "}());</script>";
}
