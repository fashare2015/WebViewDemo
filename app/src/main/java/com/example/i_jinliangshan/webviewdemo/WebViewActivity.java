package com.example.i_jinliangshan.webviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;


public class WebViewActivity extends Activity {
    private String url;
    private ProgressBar progressBar;
    private MyWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        getUrl();
        init();
    }

    private void getUrl() {
        url = getIntent().getStringExtra(MainActivity.URL);
        Log.d("url", url);
    }

    private void init() {
        progressBar = (ProgressBar) findViewById(R.id.pb);

        webView = (MyWebView)findViewById(R.id.wv);
        // MyWebView 提供了 OnLoadListener 这个接口，通过它来控制 progressBar 的进度。
        webView.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoadStart() {
                hideProgressBar();
            }

            @Override
            public void onLoading(int newProgress) {
                showProgressBar(newProgress);
            }

            @Override
            public void onLoadComplete() {
                hideProgressBar();
            }
        });
        if(url != null)
            webView.loadUrl(url);
    }

    private void showProgressBar(int progress) {
        progressBar.setProgress(progress);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {   // 重写它，以处理 webview 的历史回退
        if(webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }
}
