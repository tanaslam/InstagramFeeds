package uk.co.crystalcube.instagramfeeds.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import uk.co.crystalcube.instagramfeeds.R;

/**
 * An activity that embeds web view to present an option for the user to authorize access request
 * when API being accessed for the first time.
 *
 * Created by Tanveer Aslam on 24/02/2015.
 */
@EActivity(R.layout.activity_web)
public class WebViewActivity extends ActionBarActivity {

    public static final java.lang.String KEY_EXTRA_ACCESS_TOKEN = "key-extra-access-token" ;

    @Extra
    protected String url;

    @ViewById(R.id.web_view)
    protected WebView webView;

    @AfterViews
    protected void setupView() {
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                super.onReceivedError(view, errorCode, description, failingUrl);

                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);

                Uri uri = Uri.parse(url);
                String fragment  = uri.getFragment();

                if(fragment != null) {

                    Intent intent  = new Intent()
                            .putExtra(KEY_EXTRA_ACCESS_TOKEN, fragment.split("=")[1]);

                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });

        webView.loadUrl(url);
    }
}
