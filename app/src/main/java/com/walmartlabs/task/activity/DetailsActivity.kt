package com.walmartlabs.task.activity

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.walmartlabs.task.R
import com.walmartlabs.task.api.URLs

/**
 * Created by Bipul on 25-08-2019.
 */
class DetailsActivity : AppCompatActivity() {
    internal lateinit var walmart_text: TextView
    internal lateinit var custom_font: Typeface

    internal var productNamew: String? = null
    internal var shortDescriptionw: String? = null
    internal var longDescriptionw: String? = null
    internal var pricew: String? = null
    internal var productImagew: String? = null
    internal var reviewRatingsw: String? = null
    internal var reviewCountsw: String? = null
    internal var inStockw: String? = null
    internal lateinit var productImageShow: ImageView

    internal lateinit var productNames: TextView
    internal lateinit var shortDescriptions: TextView
    internal lateinit var longDescriptions: TextView
    internal lateinit var prices: TextView
    internal var productImages: TextView? = null
    internal lateinit var reviewRatingss: TextView
    internal lateinit var reviewCountss: TextView
    internal lateinit var inStocks: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        walmart_text = findViewById<View>(R.id.walmart_text) as TextView
        custom_font = Typeface.createFromAsset(applicationContext.assets, "Pacifico.ttf")
        walmart_text.typeface = custom_font

        val bundle = intent.extras
        if (bundle != null) {

            productNamew = bundle.getString("productName")
            shortDescriptionw = bundle.getString("shortDescription")
            longDescriptionw = bundle.getString("longDescription")
            pricew = bundle.getString("price")
            productImagew = bundle.getString("productImage")
            reviewRatingsw = bundle.getString("reviewRatings")
            reviewCountsw = bundle.getString("reviewCounts")
            inStockw = bundle.getString("inStock")
        }

        productImageShow = findViewById<View>(R.id.productImageShow) as ImageView
        try {
            Glide.with(applicationContext)
                    .load(URLs.URL_ROOT + productImagew!!)
                    .centerCrop()
                    .error(R.drawable.default_background)
                    .into(productImageShow)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        productNames = findViewById<View>(R.id.productNamel) as TextView
        productNames.text = productNamew
        shortDescriptions = findViewById<View>(R.id.shortDescriptionl) as TextView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            shortDescriptions.text = Html.fromHtml(shortDescriptionw, Html.FROM_HTML_MODE_COMPACT)
        } else {
            shortDescriptions.text = Html.fromHtml(shortDescriptionw)
        }
        longDescriptions = findViewById<View>(R.id.longDescriptionl) as TextView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            longDescriptions.text = Html.fromHtml(longDescriptionw, Html.FROM_HTML_MODE_COMPACT)
        } else {
            longDescriptions.text = Html.fromHtml(longDescriptionw)
        }
        prices = findViewById<View>(R.id.pricel) as TextView
        prices.text = pricew
        reviewRatingss = findViewById<View>(R.id.reviewRatingl) as TextView
        reviewRatingss.text = reviewRatingsw
        reviewCountss = findViewById<View>(R.id.reviewCountl) as TextView
        reviewCountss.text = "Product Count : " + reviewCountsw!!
        inStocks = findViewById<View>(R.id.stockl) as TextView
        inStocks.text = inStockw
    }
}