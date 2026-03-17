package com.app.prebid_mobile_sdk_example;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import org.prebid.mobile.AdSize;
import org.prebid.mobile.BannerAdUnit;
import org.prebid.mobile.Host;
import org.prebid.mobile.LogUtil;
import org.prebid.mobile.OnCompleteListener;
import org.prebid.mobile.PrebidMobile;
import org.prebid.mobile.ResultCode;
import org.prebid.mobile.api.data.AdFormat;
import org.prebid.mobile.api.data.AdUnitFormat;
import org.prebid.mobile.rendering.bidding.listeners.BidRequesterListener;
import org.prebid.mobile.rendering.bidding.loader.BidLoader;
import org.prebid.mobile.rendering.models.AdPosition;
import org.prebid.mobile.rendering.sdk.PrebidContextHolder;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;

public class BannerAdUnit_main extends BannerAdUnit {
    public BannerAdUnit_main(@NonNull String configId, int width, int height, EnumSet<AdUnitFormat> adUnitFormats) {
        super(configId, width, height, adUnitFormats);
        configuration.addSize(new AdSize(width, height));
    }
    public void fetchDemand(Object adObject, OnCompleteListener listener , BidRequesterListener originalListener){
        if (TextUtils.isEmpty(PrebidMobile.getPrebidServerAccountId())) {
            LogUtil.error("Empty account id.");
            listener.onComplete(ResultCode.INVALID_ACCOUNT_ID);
            return;
        }
        if (TextUtils.isEmpty(configuration.getConfigId())) {
            LogUtil.error("Empty config id.");
            listener.onComplete(ResultCode.INVALID_CONFIG_ID);
            return;
        }
        if (PrebidMobile.getPrebidServerHost().equals(Host.CUSTOM)) {
            if (TextUtils.isEmpty(PrebidMobile.getPrebidServerHost().getHostUrl())) {
                LogUtil.error("Empty host url for custom Prebid Server host.");
                listener.onComplete(ResultCode.INVALID_HOST_URL);
                return;
            }
        }
        HashSet<AdSize> sizes = configuration.getSizes();
        for (AdSize size : sizes) {
            if (size.getWidth() < 0 || size.getHeight() < 0) {
                listener.onComplete(ResultCode.INVALID_SIZE);
                return;
            }
        }
        Context context = PrebidContextHolder.getContext();
        if (context != null) {
            ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr != null && context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == PackageManager.PERMISSION_GRANTED) {
                NetworkInfo activeNetworkInfo = conMgr.getActiveNetworkInfo();
                if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                    listener.onComplete(ResultCode.NETWORK_ERROR);
                    return;
                }
            }
        } else {
            LogUtil.error("Invalid context");
            listener.onComplete(ResultCode.INVALID_CONTEXT);
            return;
        }
        if ( supportedAdObject(adObject) || allowNullableAdObject) {
            this.adObject = adObject;
            bidLoader = new BidLoader(
                    configuration,
                    originalListener
            );

            if (configuration.getAutoRefreshDelay() > 0) {
                BidLoader.BidRefreshListener bidRefreshListener = () -> true;
                bidLoader.setBidRefreshListener(bidRefreshListener);
                LogUtil.verbose("Start fetching bids with auto refresh millis: " + configuration.getAutoRefreshDelay());
            } else {
                bidLoader.setBidRefreshListener(null);
                LogUtil.verbose("Start a single fetching.");
            }

            bidLoader.load();
        } else {
            this.adObject = null;
            listener.onComplete(ResultCode.INVALID_AD_OBJECT);
        }

    }
    static boolean supportedAdObject(Object adObj) {
        if (adObj == null) return false;
        /*if (adObj.getClass() == getClassFromString(AD_MANAGER_REQUEST_CLASS)
                || adObj.getClass() == getClassFromString(AD_MANAGER_REQUEST_CLASS_V20)
                || adObj.getClass() == getClassFromString(AD_MANAGER_REQUEST_BUILDER_CLASS)
                || adObj.getClass() == getClassFromString(AD_MANAGER_REQUEST_BUILDER_CLASS_V20)
                || adObj.getClass() == getClassFromString(ANDROID_OS_BUNDLE)
                || adObj.getClass() == getClassFromString(APPLOVIN_MAX_NATIVE_AD_LOADER)
                || adObj.getClass() == HashMap.class)//*/
            return true;
      //  return false;
    }
      public BannerAdUnit_main(@NonNull String configId, int width, int height) {
       super(configId, width, height);
          configuration.addSize(new AdSize(width, height));
    }
    public void addAdditionalSize(int width, int height) {
        configuration.addSize(new AdSize(width, height));
    }

    HashSet<AdSize> getSizes() {
        return configuration.getSizes();
    }

    public void setAdPosition(AdPosition adPosition) {
        configuration.setAdPosition(adPosition);
    }

}
