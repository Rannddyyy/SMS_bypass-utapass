package com.brucehuang.utapass_bypass_sms;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity
        extends AppCompatActivity {
    private WebView webView;

    private void setupMessageButton() {
        ((Button) findViewById(R.id.btnDisplayMessage)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                Toast.makeText(MainActivity.this, "Bomb!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS},
                123);
        setupMessageButton();
        SmsReceiver.bindListener(new SmsListener() {
            public void messageReceived(String paramAnonymousString) {
                Log.e("@@@@@@@@@@@@@@", paramAnonymousString);
                String[] smsMessage = paramAnonymousString.split("https://");
                String url = smsMessage[1].contains(" ") ? smsMessage[1].substring(0, smsMessage[1].indexOf(" ")) : smsMessage[1];
                Log.e("@@@@@@@@@@@@@@URL", url);
                Toast.makeText(MainActivity.this, "Message: " + paramAnonymousString, Toast.LENGTH_LONG).show();
                webView = (WebView) findViewById(R.id.utapass_webview);
                webView.setWebChromeClient(new WebChromeClient());
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("https://" + url);
                webView.setWebViewClient(new WebViewClient() {
                    public void onPageFinished(WebView paramAnonymous2WebView, String paramAnonymous2String) {
                        MainActivity.this.webView.loadUrl("javascript:document.getElementsByClassName('engthButton_orange')[0].click()");
                    }
                });
            }
        });
    }
}
