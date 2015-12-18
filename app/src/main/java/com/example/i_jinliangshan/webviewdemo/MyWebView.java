package com.example.i_jinliangshan.webviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by jinliangshan on 2015/12/17.
 */
public class MyWebView extends WebView {
    private ProgressBar progressBar;
    private OnLoadListener onLoadListener;

    public MyWebView(Context context){ this(context, null); }

    public MyWebView(Context context, AttributeSet attr){
        super(context, attr);

        //initProgressBar(context);
        initSettings();
        setClient();
        addJsInterface();
    }

    private void initProgressBar(Context context) {
        // progressBar = (ProgressBar)((Activity)context).findViewById(R.id.pb);

        // 本来想把 progressBar 直接放进来, 如上述代码
        // 但是不知道怎么才能调用 findViewById, 怎么搞都不行 :(
        // 最后无奈加了个接口 OnLoadListener
    }

    private void initSettings() {
        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);     // 使能 js
    }

    private void setClient() {
        // 防止调用系统浏览器，需要 set 一个 WebViewClient, 但不拦截 url 的话，不用重写 shouldOverrideUrlLoading
        this.setWebViewClient(new WebViewClient());

        // 设置这个 Client 则是为了显示和控制进度。
        this.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (onLoadListener == null) return;
                // 按照加载的进度，对外提供接口
                if (newProgress == 0)
                    onLoadListener.onLoadStart();
                else if (newProgress < 100)
                    onLoadListener.onLoading(newProgress);
                else
                    onLoadListener.onLoadComplete();

                super.onProgressChanged(view, newProgress);
            }
        });
    }

    private void addJsInterface() {
        this.addJavascriptInterface(new Object() {
            // 只有加了 JavascriptInterface 注解的函数，才能被 js 端调用
            // 使用这个注解是为了保证安全性。
            @JavascriptInterface
            public void functionInAndroid() {
                // 该函数在 assets/test.html 中被第二个 onclick 所调用
                // 调用方式为 "window." + 接口名 + "." + 函数名
                // 如 window.demo.functionInAndroid()

                Toast.makeText(getContext(), "This function is defined in Android.", Toast.LENGTH_SHORT).show();
            }

        }, "demo"); // 这里的"demo"定义了接口名
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }
}

/**
 *  监听接口<br/>
 *  用于控制加载进度
 */
interface OnLoadListener{
    /**
     * 开始加载
     */
    public void onLoadStart();

    /**
     * 正在加载
     * @param newProgress   加载进度值
     */
    public void onLoading(int newProgress);

    /**
     * 加载完成
     */
    public void onLoadComplete();
}
