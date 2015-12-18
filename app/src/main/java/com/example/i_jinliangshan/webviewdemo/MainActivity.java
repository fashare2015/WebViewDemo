package com.example.i_jinliangshan.webviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener{
    //private final String url = "javascript: alert(\"调用js\")";
    private final String url_baidu = "http://www.baidu.com";
    private final String url_js = "file:///android_asset/test.html";
    public final static String URL = "URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        findViewById(R.id.btn_show_baidu).setOnClickListener(this);
        findViewById(R.id.btn_invoked_by_js).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        int id = view.getId();
        if(id == R.id.btn_show_baidu)
            intent.putExtra(URL, url_baidu);
        else if(id == R.id.btn_invoked_by_js)
            intent.putExtra(URL, url_js);
        else
            return ;
        startActivity(intent);
    }
}
