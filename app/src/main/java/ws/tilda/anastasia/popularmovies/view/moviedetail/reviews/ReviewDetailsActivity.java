package ws.tilda.anastasia.popularmovies.view.moviedetail.reviews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.model.modelobjects.Review;

public class ReviewDetailsActivity extends AppCompatActivity {
    public static final String REVIEW_EXTRA = "review";
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (WebView) findViewById(R.id.activity_review_details_webview);
        progressBar = (ProgressBar) findViewById(R.id.activity_review_details_progressbar);

        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        }

        Review review = getIntent().getParcelableExtra(REVIEW_EXTRA);

        if (review != null) {
            updateReviewDetails(review);
        } else {
            Toast.makeText(ReviewDetailsActivity.this, R.string.no_review_passed_error,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @JavascriptInterface
    public void updateReviewDetails(Review review) {
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setSavePassword(false);
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ReviewDetailsActivity.this, R.string.error_loading_webpage, Toast.LENGTH_SHORT).show();
            }
        });

        webView.loadUrl(review.getUrl());
        getSupportActionBar().setTitle("Review by " + review.getAuthor());
    }

    public static void launch(Context context, Review review) {
        Intent intent = new Intent(context, ReviewDetailsActivity.class);
        intent.putExtra(REVIEW_EXTRA, review);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        webView.saveState(outState);
    }
}
