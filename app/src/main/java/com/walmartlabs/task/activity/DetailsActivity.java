package com.walmartlabs.task.activity;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.walmartlabs.task.R;
import com.walmartlabs.task.api.URL;

public class DetailsActivity extends AppCompatActivity {
    TextView walmart_text;
    Typeface custom_font;

    String productNamew, shortDescriptionw, longDescriptionw, pricew, productImagew;
    String reviewRatingsw, reviewCountsw;
    String inStockw;
    ImageView productImageShow;

    TextView productNames, shortDescriptions, longDescriptions, prices, productImages;
    TextView reviewRatingss, reviewCountss;
    TextView inStocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        walmart_text=(TextView)findViewById(R.id.walmart_text);
        custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "Pacifico.ttf");
        walmart_text.setTypeface(custom_font);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            productNamew = bundle.getString("productName");
            shortDescriptionw = bundle.getString("shortDescription");
            longDescriptionw = bundle.getString("longDescription");
            pricew = bundle.getString("price");
            productImagew = bundle.getString("productImage");
            reviewRatingsw = bundle.getString("reviewRatings");
            reviewCountsw = bundle.getString("reviewCounts");
            inStockw = bundle.getString("inStock");
        }

        productImageShow=(ImageView)findViewById(R.id.productImageShow);
        try {
            Glide.with(getApplicationContext())
                    .load(URL.URL_ROOT+productImagew)
                    .centerCrop()
                    .error(R.drawable.default_background)
                    .into(productImageShow);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        productNames=(TextView) findViewById(R.id.productNamel);
        productNames.setText(productNamew);
        shortDescriptions=(TextView) findViewById(R.id.shortDescriptionl);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            shortDescriptions.setText(Html.fromHtml(shortDescriptionw, Html.FROM_HTML_MODE_COMPACT));
        } else {
            shortDescriptions.setText(Html.fromHtml(shortDescriptionw));
        }
        longDescriptions=(TextView) findViewById(R.id.longDescriptionl);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            longDescriptions.setText(Html.fromHtml(longDescriptionw, Html.FROM_HTML_MODE_COMPACT));
        } else {
            longDescriptions.setText(Html.fromHtml(longDescriptionw));
        }
        prices=(TextView) findViewById(R.id.pricel);
        prices.setText(pricew);
        reviewRatingss=(TextView) findViewById(R.id.reviewRatingl);
        reviewRatingss.setText(reviewRatingsw);
        reviewCountss=(TextView) findViewById(R.id.reviewCountl);
        reviewCountss.setText("Product Count : "+reviewCountsw);
        inStocks=(TextView) findViewById(R.id.stockl);
        inStocks.setText(inStockw);
    }
}
