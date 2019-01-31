package com.dicoding.picodiploma.newsfeed.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dicoding.picodiploma.newsfeed.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class WebFragment extends Fragment {
    private ProgressBar pbWebView;
    private WebView webView;
    public WebFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        String url;
        pbWebView = view.findViewById(R.id.pb_webview);
        webView = view.findViewById(R.id.wv_news);
        if (getArguments()!=null){
            url = getArguments().getString(NewsFragment.TAG_NEWS_URL);
            webView.setWebViewClient(new WebFragment.WebClient());
            webView.setWebChromeClient(new WebFragment.ChromeClient());
            webView.loadUrl(url);
        } else {
            setVisibility(View.GONE);
            showToast(R.string.error_mes);
        }
        return view;
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            pbWebView.setProgress(newProgress);
            if (newProgress==100){ setVisibility(View.VISIBLE); }
        }
    }

    public class WebClient extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            showToast(R.string.check_ur_connection);
        }
    }

    private void showToast(int resId){
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    private void setVisibility(int wvVisibility){
        pbWebView.setVisibility(View.GONE);
        webView.setVisibility(wvVisibility);
    }
}
