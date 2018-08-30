package com.spark.webviewjsinteractive;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    Button callJSBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        webView = (WebView)findViewById(R.id.id_webView);
        callJSBtn = (Button)findViewById(R.id.id_callJsBtn);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);// 设置webview允许JS交互
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //设置允许JS弹窗

        webView.loadUrl("file:///android_asset/javascript.html");

        webView.addJavascriptInterface(this, "androidObj");

//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//
//                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//
//                return true;
//            }
//        });
    }

    public void callJSButtonClick(View view) {
        // webView调用js方法
        // 方式一：
//        webView.loadUrl("javascript:callJS()");

        // 方式二： 4.4以上
        webView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) { // 接受js方法的返回值
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }




    @JavascriptInterface
    public void jsCallAndroid(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
