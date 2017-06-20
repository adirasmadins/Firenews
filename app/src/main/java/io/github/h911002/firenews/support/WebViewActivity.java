package io.github.h911002.firenews.support;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.BaseActivity;
import io.github.h911002.firenews.base.mvp.IPresenter;
import io.github.h911002.firenews.support.constant.IntentConstants;
import io.github.h911002.firenews.support.utils.EmptyUtils;

public class WebViewActivity extends BaseActivity {

    private WebView webView;


    @Override
    protected IPresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int onContentViewID() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void onContentViewCreated(View contentView, Bundle savedInstanceState) {
        init();
    }

    private void init() {
        initBlueTitle(getIntent().getStringExtra(IntentConstants.WEB_TITLE), true);
        this.webView = (WebView) findViewById(R.id.web_view);
        this.webView.setWebViewClient(new WebViewClient());
        this.webView.setWebChromeClient(new WebChromeClient());
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setSupportZoom(true);
        this.webView.getSettings().setBuiltInZoomControls(true);
        this.webView.loadUrl(getIntent().getStringExtra(IntentConstants.WEB_URL));

    }

    public static boolean openURL(Context context, String title,String url) {
        if (EmptyUtils.isNotEmpty(url)) {
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra(IntentConstants.WEB_URL, url);
            intent.putExtra(IntentConstants.WEB_TITLE, title);
            context.startActivity(intent);
            return true;
        } else return false;
    }
}
