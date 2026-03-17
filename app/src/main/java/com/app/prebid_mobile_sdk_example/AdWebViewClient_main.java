package com.app.prebid_mobile_sdk_example;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.prebid.mobile.LogUtil;
import org.prebid.mobile.rendering.views.webview.ActionUrl;
import org.prebid.mobile.rendering.views.webview.AdWebViewClient;
import org.prebid.mobile.rendering.views.webview.WebViewBase;

import java.util.ArrayList;
import java.util.HashSet;

import static android.webkit.WebView.HitTestResult.SRC_ANCHOR_TYPE;
import static android.webkit.WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE;
public class AdWebViewClient_main extends AdWebViewClient {
    private static final String TAG = AdWebViewClient.class.getSimpleName();
    private static final String JS__GET_RENDERED_HTML = "javascript:window.HtmlViewer.showHTML" + "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');";
    OnResult onResult;
  interface OnResult{
    void getResult(WebView view, String url);

}
    OnFinish onFinish;
    interface OnFinish{
        void getFinish(WebView view, String url);

    }

    public void setOnFinish(OnFinish onFinish1) {
        onFinish = onFinish1;
    }

    public void setOnResult(OnResult onResult) {
        this.onResult = onResult;
    }

    public AdWebViewClient_main(AdAssetsLoadedListener adAssetsLoadedListener) {
        super(adAssetsLoadedListener);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void registerSpecialUrl(ActionUrl actionUrl) {
        super.registerSpecialUrl(actionUrl);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return super.shouldOverrideKeyEvent(view, event);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
    if(onResult != null)onResult.getResult(view,url);
      /*  LogUtil.debug(TAG, "shouldOverrideUrlLoading, url: " + url);
        if (view == null) {
            LogUtil.error(TAG, "onPageStarted failed, WebView is null");
            return false;
        }

        try {


            WebViewBase webViewBase = ((WebViewBase) view);

            if (!webViewBase.isClicked()) {
                // Get the rendered HTML
                reloadUrl(view, JS__GET_RENDERED_HTML);
                return true;
            }
            view.loadUrl(url);
          //  handleWebViewClick(url, webViewBase);
        }
        catch (Exception e) {
            LogUtil.error(TAG, "shouldOverrideUrlLoading failed for url: " + url + " : " + Log.getStackTraceString(e));
        }*/
        return super.shouldOverrideUrlLoading(view ,url);
    }

    private void reloadUrl(WebView view, String s) {
        view.stopLoading();

        // Get the rendered HTML
        view.loadUrl(s);
    }
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if(onFinish != null) onFinish.getFinish(view  ,url);
        super.onPageFinished(view, url);
    }
}
